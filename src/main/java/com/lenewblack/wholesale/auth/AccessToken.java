package com.lenewblack.wholesale.auth;

import java.time.Instant;

/**
 * Immutable value object representing an OAuth2 access token with its expiry time.
 */
public final class AccessToken {

    private final String value;
    private final Instant expiresAt;

    public AccessToken(String value, Instant expiresAt) {
        if (value == null || value.isEmpty()) throw new IllegalArgumentException("token value must not be empty");
        if (expiresAt == null) throw new IllegalArgumentException("expiresAt must not be null");
        this.value = value;
        this.expiresAt = expiresAt;
    }

    public String getValue() {
        return value;
    }

    public Instant getExpiresAt() {
        return expiresAt;
    }

    /**
     * Returns true if the token is expired (or will expire within {@code bufferSeconds}).
     */
    public boolean isExpired(int bufferSeconds) {
        return Instant.now().isAfter(expiresAt.minusSeconds(bufferSeconds));
    }
}
