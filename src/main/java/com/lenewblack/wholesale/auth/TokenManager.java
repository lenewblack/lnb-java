package com.lenewblack.wholesale.auth;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.lenewblack.wholesale.exception.AuthenticationException;
import com.lenewblack.wholesale.http.HttpClient;
import com.lenewblack.wholesale.http.HttpMethod;
import com.lenewblack.wholesale.http.HttpRequest;
import com.lenewblack.wholesale.http.HttpResponse;

import java.time.Instant;

/**
 * Thread-safe OAuth2 client credentials token manager.
 *
 * <p>Uses double-checked locking with a volatile cached token to ensure that
 * concurrent callers only trigger a single token fetch when the cached token
 * is absent or about to expire.
 */
public final class TokenManager {

    private static final int REFRESH_BUFFER_SECONDS = 60;

    private final String clientId;
    private final String clientSecret;
    private final String tokenUrl;
    private final HttpClient httpClient;

    private volatile AccessToken cachedToken;
    private final Object lock = new Object();

    public TokenManager(String clientId, String clientSecret, String tokenUrl, HttpClient httpClient) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.tokenUrl = tokenUrl;
        this.httpClient = httpClient;
    }

    /**
     * Returns a valid access token string, fetching or refreshing as needed.
     * This method is safe to call from multiple threads concurrently.
     */
    public String getValidToken() {
        // Fast path: current token is still valid
        AccessToken current = cachedToken;
        if (current != null && !current.isExpired(REFRESH_BUFFER_SECONDS)) {
            return current.getValue();
        }
        // Slow path: needs refresh
        synchronized (lock) {
            // Re-check after acquiring lock (another thread may have refreshed)
            if (cachedToken == null || cachedToken.isExpired(REFRESH_BUFFER_SECONDS)) {
                cachedToken = fetchNewToken();
            }
            return cachedToken.getValue();
        }
    }

    private AccessToken fetchNewToken() {
        String body = "grant_type=client_credentials"
                + "&client_id=" + urlEncode(clientId)
                + "&client_secret=" + urlEncode(clientSecret);

        HttpRequest request = HttpRequest.builder(HttpMethod.POST, tokenUrl)
                .header("Content-Type", "application/x-www-form-urlencoded")
                .header("Accept", "application/json")
                .jsonBody(body)
                .build();

        HttpResponse response = httpClient.execute(request);

        if (!response.isSuccessful()) {
            throw new AuthenticationException(
                    "Failed to obtain access token: HTTP " + response.getStatusCode(),
                    response.getBody()
            );
        }

        JsonObject json = JsonParser.parseString(response.getBody()).getAsJsonObject();
        String tokenValue = json.get("access_token").getAsString();

        Instant expiresAt;
        if (json.has("expire_on")) {
            // expire_on can be a Unix timestamp (long) or ISO-8601 string
            String expireOn = json.get("expire_on").getAsString();
            try {
                long epochSeconds = Long.parseLong(expireOn);
                expiresAt = Instant.ofEpochSecond(epochSeconds);
            } catch (NumberFormatException e) {
                expiresAt = Instant.parse(expireOn);
            }
        } else if (json.has("expires_in")) {
            long expiresIn = json.get("expires_in").getAsLong();
            expiresAt = Instant.now().plusSeconds(expiresIn);
        } else {
            // Default to 1 hour if no expiry info provided
            expiresAt = Instant.now().plusSeconds(3600);
        }

        return new AccessToken(tokenValue, expiresAt);
    }

    private static String urlEncode(String value) {
        try {
            return java.net.URLEncoder.encode(value, "UTF-8");
        } catch (java.io.UnsupportedEncodingException e) {
            return value;
        }
    }
}
