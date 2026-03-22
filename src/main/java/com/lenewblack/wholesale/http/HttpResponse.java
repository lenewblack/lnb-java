package com.lenewblack.wholesale.http;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public final class HttpResponse {

    private final int statusCode;
    private final String body;
    private final Map<String, List<String>> headers;

    public HttpResponse(int statusCode, String body, Map<String, List<String>> headers) {
        this.statusCode = statusCode;
        this.body = body;
        this.headers = headers != null ? headers : Collections.emptyMap();
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getBody() {
        return body;
    }

    public Map<String, List<String>> getHeaders() {
        return headers;
    }

    /**
     * Returns the first value of the named header, case-insensitively.
     */
    public Optional<String> header(String name) {
        for (Map.Entry<String, List<String>> entry : headers.entrySet()) {
            if (entry.getKey() != null && entry.getKey().equalsIgnoreCase(name)) {
                List<String> values = entry.getValue();
                if (values != null && !values.isEmpty()) {
                    return Optional.ofNullable(values.get(0));
                }
            }
        }
        return Optional.empty();
    }

    public boolean isSuccessful() {
        return statusCode >= 200 && statusCode < 300;
    }
}
