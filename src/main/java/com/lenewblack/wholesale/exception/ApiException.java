package com.lenewblack.wholesale.exception;

/**
 * Base unchecked exception for all Le New Black Wholesale API errors.
 * Catch this to handle all API-related errors uniformly, or catch a specific
 * subclass for granular handling.
 */
public class ApiException extends RuntimeException {

    private final int statusCode;
    private final String responseBody;

    public ApiException(String message, int statusCode, String responseBody) {
        super(message);
        this.statusCode = statusCode;
        this.responseBody = responseBody;
    }

    public ApiException(String message, int statusCode, String responseBody, Throwable cause) {
        super(message, cause);
        this.statusCode = statusCode;
        this.responseBody = responseBody;
    }

    /** HTTP status code returned by the API, or 0 for network-level errors. */
    public int getStatusCode() {
        return statusCode;
    }

    /** Raw response body, may be null for network-level errors. */
    public String getResponseBody() {
        return responseBody;
    }
}
