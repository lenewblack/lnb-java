package com.lenewblack.wholesale.auth;

import com.lenewblack.wholesale.TestUtils;
import com.lenewblack.wholesale.exception.AuthenticationException;
import com.lenewblack.wholesale.http.HttpClient;
import com.lenewblack.wholesale.http.HttpRequest;
import com.lenewblack.wholesale.http.HttpResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TokenManagerTest {

    @Mock
    private HttpClient httpClient;

    private TokenManager tokenManager;

    @BeforeEach
    void setUp() {
        tokenManager = new TokenManager(
                "client-id", "client-secret",
                "https://example.com/oauth/token",
                httpClient
        );
    }

    @Test
    void getValidToken_returnsToken_onSuccess() {
        String tokenJson = TestUtils.loadFixture("token.json");
        when(httpClient.execute(any(HttpRequest.class)))
                .thenReturn(new HttpResponse(200, tokenJson, Collections.emptyMap()));

        String token = tokenManager.getValidToken();

        assertEquals("test-token-abc123", token);
    }

    @Test
    void getValidToken_cacheToken_onSecondCall() {
        String tokenJson = TestUtils.loadFixture("token.json");
        when(httpClient.execute(any(HttpRequest.class)))
                .thenReturn(new HttpResponse(200, tokenJson, Collections.emptyMap()));

        tokenManager.getValidToken();
        tokenManager.getValidToken();

        // Should only fetch once
        verify(httpClient, times(1)).execute(any(HttpRequest.class));
    }

    @Test
    void getValidToken_throwsAuthenticationException_on401() {
        when(httpClient.execute(any(HttpRequest.class)))
                .thenReturn(new HttpResponse(401, "{\"error\":\"unauthorized\"}", Collections.emptyMap()));

        assertThrows(AuthenticationException.class, () -> tokenManager.getValidToken());
    }

    @Test
    void getValidToken_threadSafe_onlyFetchesOnce() throws InterruptedException {
        String tokenJson = TestUtils.loadFixture("token.json");
        AtomicInteger fetchCount = new AtomicInteger(0);

        when(httpClient.execute(any(HttpRequest.class))).thenAnswer(inv -> {
            fetchCount.incrementAndGet();
            // Simulate slight delay to increase thread contention
            Thread.sleep(10);
            return new HttpResponse(200, tokenJson, Collections.emptyMap());
        });

        int threadCount = 20;
        CountDownLatch startLatch = new CountDownLatch(1);
        CountDownLatch doneLatch = new CountDownLatch(threadCount);

        IntStream.range(0, threadCount).forEach(i -> {
            Thread t = new Thread(() -> {
                try {
                    startLatch.await();
                    tokenManager.getValidToken();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } finally {
                    doneLatch.countDown();
                }
            });
            t.start();
        });

        startLatch.countDown();
        doneLatch.await();

        assertEquals(1, fetchCount.get(), "Token should only be fetched once under concurrent access");
    }

    @Test
    void getValidToken_refreshesToken_whenExpired() {
        String tokenJson = TestUtils.loadFixture("token.json");
        // Token with expire_on in the past
        String expiredTokenJson = "{\"access_token\":\"expired-token\",\"expire_on\":\"1\"}";
        String freshTokenJson = TestUtils.loadFixture("token.json");

        when(httpClient.execute(any(HttpRequest.class)))
                .thenReturn(new HttpResponse(200, expiredTokenJson, Collections.emptyMap()))
                .thenReturn(new HttpResponse(200, freshTokenJson, Collections.emptyMap()));

        String first = tokenManager.getValidToken();
        assertEquals("expired-token", first);

        // Second call should detect expiry and refresh
        String second = tokenManager.getValidToken();
        assertEquals("test-token-abc123", second);

        verify(httpClient, times(2)).execute(any(HttpRequest.class));
    }
}
