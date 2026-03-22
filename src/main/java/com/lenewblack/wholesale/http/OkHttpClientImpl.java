package com.lenewblack.wholesale.http;

import com.lenewblack.wholesale.RequestOptions;
import com.lenewblack.wholesale.exception.ApiException;
import okhttp3.*;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * OkHttp3-backed implementation of {@link HttpClient}.
 * A single shared {@link OkHttpClient} instance is used; OkHttp is thread-safe by design.
 */
public final class OkHttpClientImpl implements HttpClient {

    private static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    private static final MediaType FORM = MediaType.get("application/x-www-form-urlencoded");

    private final OkHttpClient okHttpClient;

    public OkHttpClientImpl(RequestOptions defaultOptions) {
        this.okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(defaultOptions.getConnectTimeoutMs(), TimeUnit.MILLISECONDS)
                .readTimeout(defaultOptions.getReadTimeoutMs(), TimeUnit.MILLISECONDS)
                .build();
    }

    /** Package-private constructor for testing — accepts a pre-configured OkHttpClient. */
    OkHttpClientImpl(OkHttpClient okHttpClient) {
        this.okHttpClient = okHttpClient;
    }

    @Override
    public HttpResponse execute(HttpRequest request) throws ApiException {
        Request okRequest = buildOkRequest(request);
        try (Response response = okHttpClient.newCall(okRequest).execute()) {
            ResponseBody responseBody = response.body();
            String body = responseBody != null ? responseBody.string() : "";
            Map<String, List<String>> headers = new HashMap<>(response.headers().toMultimap());
            return new HttpResponse(response.code(), body, headers);
        } catch (IOException e) {
            throw new ApiException("Network error: " + e.getMessage(), 0, null);
        }
    }

    private Request buildOkRequest(HttpRequest request) {
        // Build URL with query parameters
        HttpUrl.Builder urlBuilder = HttpUrl.parse(request.getUrl()).newBuilder();
        for (Map.Entry<String, String> entry : request.getQueryParams().entrySet()) {
            urlBuilder.addQueryParameter(entry.getKey(), entry.getValue());
        }
        HttpUrl url = urlBuilder.build();

        Request.Builder builder = new Request.Builder().url(url);

        // Add headers
        for (Map.Entry<String, String> entry : request.getHeaders().entrySet()) {
            builder.header(entry.getKey(), entry.getValue());
        }

        // Build body
        switch (request.getMethod()) {
            case GET:
                builder.get();
                break;
            case DELETE:
                builder.delete();
                break;
            case POST:
                if (request.getMultipartParts() != null) {
                    builder.post(buildMultipartBody(request.getMultipartParts()));
                } else if (request.getJsonBody() != null) {
                    // Check if this is a form body (used for token requests)
                    String contentType = request.getHeaders().getOrDefault("Content-Type", "");
                    if (contentType.startsWith("application/x-www-form-urlencoded")) {
                        builder.post(RequestBody.create(request.getJsonBody(), FORM));
                    } else {
                        builder.post(RequestBody.create(request.getJsonBody(), JSON));
                    }
                } else {
                    builder.post(RequestBody.create("", JSON));
                }
                break;
            default:
                throw new IllegalArgumentException("Unsupported HTTP method: " + request.getMethod());
        }

        return builder.build();
    }

    private RequestBody buildMultipartBody(Map<String, Object> parts) {
        MultipartBody.Builder multipart = new MultipartBody.Builder().setType(MultipartBody.FORM);
        for (Map.Entry<String, Object> entry : parts.entrySet()) {
            Object value = entry.getValue();
            if (value instanceof File) {
                File file = (File) value;
                String mimeType = parts.containsKey(entry.getKey() + "_mime")
                        ? (String) parts.get(entry.getKey() + "_mime")
                        : "application/octet-stream";
                multipart.addFormDataPart(
                        entry.getKey(),
                        file.getName(),
                        RequestBody.create(file, MediaType.parse(mimeType))
                );
            } else if (value instanceof byte[]) {
                byte[] bytes = (byte[]) value;
                String filename = (String) parts.getOrDefault(entry.getKey() + "_filename", entry.getKey());
                String mimeType = (String) parts.getOrDefault(entry.getKey() + "_mime", "application/octet-stream");
                multipart.addFormDataPart(
                        entry.getKey(),
                        filename,
                        RequestBody.create(bytes, MediaType.parse(mimeType))
                );
            } else if (value instanceof String) {
                multipart.addFormDataPart(entry.getKey(), (String) value);
            }
        }
        return multipart.build();
    }
}
