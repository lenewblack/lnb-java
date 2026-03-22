package com.lenewblack.wholesale.exception;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Thrown when the API returns HTTP 422 Unprocessable Entity.
 * The {@link #getErrors()} map contains field-level validation messages.
 */
public class ValidationException extends ApiException {

    private final Map<String, List<String>> errors;

    public ValidationException(String message, String responseBody, Map<String, List<String>> errors) {
        super(message, 422, responseBody);
        this.errors = errors != null ? errors : Collections.emptyMap();
    }

    /** Field-level validation errors, keyed by field name. */
    public Map<String, List<String>> getErrors() {
        return errors;
    }
}
