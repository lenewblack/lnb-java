package com.lenewblack.wholesale.exception;

/**
 * Thrown when the API returns HTTP 429 Too Many Requests.
 * Check {@link #getRetryAfterSeconds()} for the recommended retry delay.
 */
public class RateLimitException extends ApiException {

    private final Integer retryAfterSeconds;

    public RateLimitException(String message, String responseBody, Integer retryAfterSeconds) {
        super(message, 429, responseBody);
        this.retryAfterSeconds = retryAfterSeconds;
    }

    /**
     * Seconds to wait before retrying, as indicated by the Retry-After header.
     * May be null if the header was absent.
     */
    public Integer getRetryAfterSeconds() {
        return retryAfterSeconds;
    }
}
