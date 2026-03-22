package com.lenewblack.wholesale.resource;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.lenewblack.wholesale.RequestOptions;
import com.lenewblack.wholesale.auth.TokenManager;
import com.lenewblack.wholesale.exception.*;
import com.lenewblack.wholesale.http.*;
import com.lenewblack.wholesale.model.ResultSet;
import com.lenewblack.wholesale.model.ResultSetMetadata;
import com.lenewblack.wholesale.model.batch.BatchResponse;
import com.lenewblack.wholesale.model.batch.BatchResult;

import java.lang.reflect.Type;
import java.util.*;

/**
 * Base class for all API resource classes.
 *
 * <p>Provides authenticated HTTP helpers, response parsing, error mapping,
 * and {@link ResultSet} construction from pagination headers.
 */
public abstract class AbstractResource {

    protected static final String TOKEN_PARAM = "access_token";
    private static final String HEADER_PAGE = "X-Pagination-Page";
    private static final String HEADER_PAGE_SIZE = "X-Pagination-Page-Size";
    private static final String HEADER_HAS_MORE = "X-Pagination-Has-More";
    private static final String HEADER_TOTAL_PAGES = "X-Pagination-Total-Pages";
    private static final String HEADER_TOTAL_ITEMS = "X-Pagination-Total-Items";

    protected final HttpClient httpClient;
    protected final TokenManager tokenManager;
    protected final Gson gson;
    protected final String baseUrl;
    protected final RequestOptions defaultOptions;

    protected AbstractResource(
            HttpClient httpClient,
            TokenManager tokenManager,
            Gson gson,
            String baseUrl,
            RequestOptions defaultOptions) {
        this.httpClient = httpClient;
        this.tokenManager = tokenManager;
        this.gson = gson;
        this.baseUrl = baseUrl;
        this.defaultOptions = defaultOptions;
    }

    // -------------------------------------------------------------------------
    // Authenticated HTTP helpers
    // -------------------------------------------------------------------------

    protected HttpResponse authenticatedGet(String path, Map<String, String> queryParams, RequestOptions opts) {
        HttpRequest request = HttpRequest.builder(HttpMethod.GET, baseUrl + path)
                .queryParams(queryParams)
                .queryParam(TOKEN_PARAM, tokenManager.getValidToken())
                .header("Accept", "application/json")
                .options(resolveOptions(opts))
                .build();
        HttpResponse response = httpClient.execute(request);
        handleErrorResponse(response);
        return response;
    }

    protected HttpResponse authenticatedPost(String path, Object body, RequestOptions opts) {
        String json = body != null ? gson.toJson(body) : "{}";
        HttpRequest request = HttpRequest.builder(HttpMethod.POST, baseUrl + path)
                .queryParam(TOKEN_PARAM, tokenManager.getValidToken())
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .jsonBody(json)
                .options(resolveOptions(opts))
                .build();
        HttpResponse response = httpClient.execute(request);
        handleErrorResponse(response);
        return response;
    }

    protected HttpResponse authenticatedPostRaw(String path, String rawJson, RequestOptions opts) {
        HttpRequest request = HttpRequest.builder(HttpMethod.POST, baseUrl + path)
                .queryParam(TOKEN_PARAM, tokenManager.getValidToken())
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .jsonBody(rawJson)
                .options(resolveOptions(opts))
                .build();
        HttpResponse response = httpClient.execute(request);
        handleErrorResponse(response);
        return response;
    }

    protected HttpResponse authenticatedDelete(String path, RequestOptions opts) {
        HttpRequest request = HttpRequest.builder(HttpMethod.DELETE, baseUrl + path)
                .queryParam(TOKEN_PARAM, tokenManager.getValidToken())
                .header("Accept", "application/json")
                .options(resolveOptions(opts))
                .build();
        HttpResponse response = httpClient.execute(request);
        handleErrorResponse(response);
        return response;
    }

    protected HttpResponse authenticatedMultipart(String path, Map<String, Object> parts, RequestOptions opts) {
        HttpRequest request = HttpRequest.builder(HttpMethod.POST, baseUrl + path)
                .queryParam(TOKEN_PARAM, tokenManager.getValidToken())
                .header("Accept", "application/json")
                .multipartParts(parts)
                .options(resolveOptions(opts))
                .build();
        HttpResponse response = httpClient.execute(request);
        handleErrorResponse(response);
        return response;
    }

    protected <T> BatchResponse<T> authenticatedBatchPost(
            String path, List<?> items, Type itemType, RequestOptions opts) {
        String json = gson.toJson(items);
        HttpResponse response = authenticatedPostRaw(path, json, opts);
        Type batchType = TypeToken.getParameterized(BatchResponse.class, itemType).getType();
        return gson.fromJson(response.getBody(), batchType);
    }

    // -------------------------------------------------------------------------
    // Response parsing
    // -------------------------------------------------------------------------

    protected <T> T parseBody(HttpResponse response, Class<T> type) {
        return gson.fromJson(response.getBody(), type);
    }

    protected <T> T parseBody(HttpResponse response, Type genericType) {
        return gson.fromJson(response.getBody(), genericType);
    }

    /**
     * Parses a paginated list response. The item list is expected to be a JSON array
     * at the top level; pagination metadata is read from response headers.
     */
    protected <T> ResultSet<T> parseResultSet(HttpResponse response, Class<T> itemType) {
        Type listType = TypeToken.getParameterized(List.class, itemType).getType();
        List<T> data = gson.fromJson(response.getBody(), listType);
        ResultSetMetadata metadata = parseMetadata(response);
        return new ResultSet<>(data != null ? data : Collections.emptyList(), metadata);
    }

    private ResultSetMetadata parseMetadata(HttpResponse response) {
        int page = parseIntHeader(response, HEADER_PAGE, 1);
        int pageSize = parseIntHeader(response, HEADER_PAGE_SIZE, 0);
        boolean hasMore = parseBoolHeader(response, HEADER_HAS_MORE, false);
        int totalPages = parseIntHeader(response, HEADER_TOTAL_PAGES, 0);
        int totalItems = parseIntHeader(response, HEADER_TOTAL_ITEMS, 0);
        return new ResultSetMetadata(page, pageSize, hasMore, totalPages, totalItems);
    }

    // -------------------------------------------------------------------------
    // Error handling
    // -------------------------------------------------------------------------

    protected void handleErrorResponse(HttpResponse response) {
        if (response.isSuccessful()) return;

        int status = response.getStatusCode();
        String body = response.getBody();
        String message = extractMessage(body, status);

        switch (status) {
            case 401:
                throw new AuthenticationException(message, body);
            case 404:
                throw new NotFoundException(message, body);
            case 422:
                throw new ValidationException(message, body, parseValidationErrors(body));
            case 429:
                Integer retryAfter = parseRetryAfter(response);
                throw new RateLimitException(message, body, retryAfter);
            default:
                throw new ApiException(message, status, body);
        }
    }

    private String extractMessage(String body, int status) {
        if (body == null || body.isEmpty()) {
            return "HTTP " + status;
        }
        try {
            JsonObject obj = JsonParser.parseString(body).getAsJsonObject();
            if (obj.has("message")) return obj.get("message").getAsString();
            if (obj.has("error")) return obj.get("error").getAsString();
        } catch (Exception ignored) {}
        return "HTTP " + status;
    }

    private Map<String, List<String>> parseValidationErrors(String body) {
        if (body == null || body.isEmpty()) return Collections.emptyMap();
        try {
            JsonObject obj = JsonParser.parseString(body).getAsJsonObject();
            JsonElement errors = obj.has("errors") ? obj.get("errors") : null;
            if (errors != null && errors.isJsonObject()) {
                Map<String, List<String>> result = new LinkedHashMap<>();
                for (Map.Entry<String, JsonElement> entry : errors.getAsJsonObject().entrySet()) {
                    List<String> msgs = new ArrayList<>();
                    if (entry.getValue().isJsonArray()) {
                        for (JsonElement el : entry.getValue().getAsJsonArray()) {
                            msgs.add(el.getAsString());
                        }
                    } else {
                        msgs.add(entry.getValue().getAsString());
                    }
                    result.put(entry.getKey(), msgs);
                }
                return result;
            }
        } catch (Exception ignored) {}
        return Collections.emptyMap();
    }

    private Integer parseRetryAfter(HttpResponse response) {
        return response.header("Retry-After")
                .map(v -> {
                    try { return Integer.parseInt(v.trim()); }
                    catch (NumberFormatException e) { return null; }
                })
                .orElse(null);
    }

    // -------------------------------------------------------------------------
    // Utilities
    // -------------------------------------------------------------------------

    private RequestOptions resolveOptions(RequestOptions opts) {
        return opts != null ? opts : defaultOptions;
    }

    private int parseIntHeader(HttpResponse response, String headerName, int defaultValue) {
        return response.header(headerName)
                .map(v -> { try { return Integer.parseInt(v.trim()); } catch (NumberFormatException e) { return defaultValue; } })
                .orElse(defaultValue);
    }

    private boolean parseBoolHeader(HttpResponse response, String headerName, boolean defaultValue) {
        return response.header(headerName)
                .map(v -> "true".equalsIgnoreCase(v.trim()) || "1".equals(v.trim()))
                .orElse(defaultValue);
    }

    /** Converts a map of String → String params to a query string. */
    protected static Map<String, String> toQueryParams(Map<String, ?> params) {
        Map<String, String> result = new LinkedHashMap<>();
        if (params != null) {
            for (Map.Entry<String, ?> entry : params.entrySet()) {
                if (entry.getValue() != null) {
                    result.put(entry.getKey(), String.valueOf(entry.getValue()));
                }
            }
        }
        return result;
    }
}
