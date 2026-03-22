package com.lenewblack.wholesale.http;

import com.lenewblack.wholesale.RequestOptions;

import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public final class HttpRequest {

    private final HttpMethod method;
    private final String url;
    private final Map<String, String> queryParams;
    private final Map<String, String> headers;
    private final String jsonBody;
    private final Map<String, Object> multipartParts;
    private final RequestOptions options;

    private HttpRequest(Builder builder) {
        this.method = builder.method;
        this.url = builder.url;
        this.queryParams = Collections.unmodifiableMap(new HashMap<>(builder.queryParams));
        this.headers = Collections.unmodifiableMap(new HashMap<>(builder.headers));
        this.jsonBody = builder.jsonBody;
        this.multipartParts = builder.multipartParts != null
                ? Collections.unmodifiableMap(new HashMap<>(builder.multipartParts))
                : null;
        this.options = builder.options;
    }

    public HttpMethod getMethod() { return method; }
    public String getUrl() { return url; }
    public Map<String, String> getQueryParams() { return queryParams; }
    public Map<String, String> getHeaders() { return headers; }
    public String getJsonBody() { return jsonBody; }
    public Map<String, Object> getMultipartParts() { return multipartParts; }
    public RequestOptions getOptions() { return options; }

    public static Builder builder(HttpMethod method, String url) {
        return new Builder(method, url);
    }

    public static final class Builder {
        private final HttpMethod method;
        private final String url;
        private final Map<String, String> queryParams = new HashMap<>();
        private final Map<String, String> headers = new HashMap<>();
        private String jsonBody;
        private Map<String, Object> multipartParts;
        private RequestOptions options;

        private Builder(HttpMethod method, String url) {
            this.method = method;
            this.url = url;
        }

        public Builder queryParam(String key, String value) {
            if (value != null) queryParams.put(key, value);
            return this;
        }

        public Builder queryParams(Map<String, String> params) {
            if (params != null) queryParams.putAll(params);
            return this;
        }

        public Builder header(String key, String value) {
            if (value != null) headers.put(key, value);
            return this;
        }

        public Builder jsonBody(String json) {
            this.jsonBody = json;
            return this;
        }

        public Builder multipartParts(Map<String, Object> parts) {
            this.multipartParts = parts;
            return this;
        }

        public Builder options(RequestOptions options) {
            this.options = options;
            return this;
        }

        public HttpRequest build() {
            if (method == null) throw new IllegalStateException("method is required");
            if (url == null || url.isEmpty()) throw new IllegalStateException("url is required");
            return new HttpRequest(this);
        }
    }
}
