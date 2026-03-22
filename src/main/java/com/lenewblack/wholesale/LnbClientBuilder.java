package com.lenewblack.wholesale;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lenewblack.wholesale.auth.TokenManager;
import com.lenewblack.wholesale.http.HttpClient;
import com.lenewblack.wholesale.http.OkHttpClientImpl;

/**
 * Fluent builder for {@link LnbClient}.
 *
 * <p>Minimum required fields are {@code clientId} and {@code clientSecret}.
 * All other settings have sensible defaults.
 *
 * <pre>{@code
 * LnbClient client = LnbClient.builder()
 *     .clientId("your-client-id")
 *     .clientSecret("your-client-secret")
 *     .build();
 * }</pre>
 */
public final class LnbClientBuilder {

    static final String DEFAULT_BASE_URL = "https://www.lenewblack.com/apis/wholesale/v2";
    static final String DEFAULT_TOKEN_PATH = "/oauth/token";

    private String clientId;
    private String clientSecret;
    private String baseUrl = DEFAULT_BASE_URL;
    private String tokenUrl = null; // derived from baseUrl if null
    private RequestOptions defaultOptions = RequestOptions.defaults();
    private HttpClient httpClient = null; // constructed if null

    LnbClientBuilder() {}

    public LnbClientBuilder clientId(String clientId) {
        this.clientId = clientId;
        return this;
    }

    public LnbClientBuilder clientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
        return this;
    }

    public LnbClientBuilder baseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
        return this;
    }

    public LnbClientBuilder tokenUrl(String tokenUrl) {
        this.tokenUrl = tokenUrl;
        return this;
    }

    public LnbClientBuilder connectTimeoutMs(int ms) {
        this.defaultOptions = RequestOptions.builder()
                .connectTimeoutMs(ms)
                .readTimeoutMs(defaultOptions.getReadTimeoutMs())
                .build();
        return this;
    }

    public LnbClientBuilder readTimeoutMs(int ms) {
        this.defaultOptions = RequestOptions.builder()
                .connectTimeoutMs(defaultOptions.getConnectTimeoutMs())
                .readTimeoutMs(ms)
                .build();
        return this;
    }

    /**
     * Override the HTTP client (useful for testing with a mock implementation).
     */
    public LnbClientBuilder httpClient(HttpClient httpClient) {
        this.httpClient = httpClient;
        return this;
    }

    public LnbClient build() {
        if (clientId == null || clientId.trim().isEmpty()) {
            throw new IllegalStateException("clientId is required");
        }
        if (clientSecret == null || clientSecret.trim().isEmpty()) {
            throw new IllegalStateException("clientSecret is required");
        }

        String resolvedBaseUrl = baseUrl != null ? baseUrl : DEFAULT_BASE_URL;
        String resolvedTokenUrl = tokenUrl != null ? tokenUrl : resolvedBaseUrl + DEFAULT_TOKEN_PATH;

        HttpClient resolvedHttpClient = httpClient != null
                ? httpClient
                : new OkHttpClientImpl(defaultOptions);

        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(com.google.gson.FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();

        TokenManager tokenManager = new TokenManager(
                clientId, clientSecret, resolvedTokenUrl, resolvedHttpClient);

        return new LnbClient(resolvedHttpClient, tokenManager, gson, resolvedBaseUrl, defaultOptions);
    }
}
