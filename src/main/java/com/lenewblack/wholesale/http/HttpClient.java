package com.lenewblack.wholesale.http;

import com.lenewblack.wholesale.exception.ApiException;

/**
 * Abstraction over the underlying HTTP transport layer.
 * The default implementation is {@link OkHttpClientImpl}.
 * Implement this interface to provide a custom transport (e.g., for testing).
 */
public interface HttpClient {

    /**
     * Executes an HTTP request and returns the response.
     *
     * @param request the request to execute
     * @return the HTTP response
     * @throws ApiException if a network error or non-2xx response occurs
     */
    HttpResponse execute(HttpRequest request) throws ApiException;
}
