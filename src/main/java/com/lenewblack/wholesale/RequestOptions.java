package com.lenewblack.wholesale;

/**
 * Immutable per-request configuration overrides.
 * When provided to a resource method, these values take precedence over
 * the client-level defaults set on {@link LnbClientBuilder}.
 */
public final class RequestOptions {

    public static final int DEFAULT_CONNECT_TIMEOUT_MS = 10_000;
    public static final int DEFAULT_READ_TIMEOUT_MS = 30_000;

    private final int connectTimeoutMs;
    private final int readTimeoutMs;

    private RequestOptions(Builder builder) {
        this.connectTimeoutMs = builder.connectTimeoutMs;
        this.readTimeoutMs = builder.readTimeoutMs;
    }

    public int getConnectTimeoutMs() { return connectTimeoutMs; }
    public int getReadTimeoutMs() { return readTimeoutMs; }

    public static RequestOptions defaults() {
        return new Builder().build();
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private int connectTimeoutMs = DEFAULT_CONNECT_TIMEOUT_MS;
        private int readTimeoutMs = DEFAULT_READ_TIMEOUT_MS;

        public Builder connectTimeoutMs(int ms) {
            this.connectTimeoutMs = ms;
            return this;
        }

        public Builder readTimeoutMs(int ms) {
            this.readTimeoutMs = ms;
            return this;
        }

        public RequestOptions build() {
            return new RequestOptions(this);
        }
    }
}
