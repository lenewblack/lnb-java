package com.lenewblack.wholesale.exception;

/**
 * Thrown when the API returns HTTP 404 Not Found.
 */
public class NotFoundException extends ApiException {

    public NotFoundException(String message, String responseBody) {
        super(message, 404, responseBody);
    }
}
