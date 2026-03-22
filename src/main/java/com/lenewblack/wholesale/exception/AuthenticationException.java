package com.lenewblack.wholesale.exception;

/**
 * Thrown when the API returns HTTP 401 Unauthorized.
 * Indicates invalid or expired credentials.
 */
public class AuthenticationException extends ApiException {

    public AuthenticationException(String message, String responseBody) {
        super(message, 401, responseBody);
    }
}
