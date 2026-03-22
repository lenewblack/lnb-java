package com.lenewblack.wholesale.http;

import com.lenewblack.wholesale.RequestOptions;
import com.lenewblack.wholesale.exception.ApiException;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class OkHttpClientTest {

    private MockWebServer server;
    private OkHttpClientImpl client;

    @BeforeEach
    void setUp() throws IOException {
        server = new MockWebServer();
        server.start();
        client = new OkHttpClientImpl(RequestOptions.defaults());
    }

    @AfterEach
    void tearDown() throws IOException {
        server.shutdown();
    }

    @Test
    void execute_get_returnsResponse() throws Exception {
        server.enqueue(new MockResponse()
                .setResponseCode(200)
                .setBody("{\"id\":\"1\"}")
                .addHeader("Content-Type", "application/json"));

        HttpRequest request = HttpRequest.builder(HttpMethod.GET, server.url("/test").toString()).build();
        HttpResponse response = client.execute(request);

        assertEquals(200, response.getStatusCode());
        assertEquals("{\"id\":\"1\"}", response.getBody());
    }

    @Test
    void execute_post_sendsJsonBody() throws Exception {
        server.enqueue(new MockResponse().setResponseCode(201).setBody("{\"id\":\"2\"}"));

        HttpRequest request = HttpRequest.builder(HttpMethod.POST, server.url("/test").toString())
                .jsonBody("{\"name\":\"test\"}")
                .build();
        HttpResponse response = client.execute(request);

        assertEquals(201, response.getStatusCode());
        RecordedRequest recorded = server.takeRequest();
        assertEquals("POST", recorded.getMethod());
        assertEquals("{\"name\":\"test\"}", recorded.getBody().readUtf8());
    }

    @Test
    void execute_delete_sendsDeleteRequest() throws Exception {
        server.enqueue(new MockResponse().setResponseCode(204));

        HttpRequest request = HttpRequest.builder(HttpMethod.DELETE, server.url("/test/1").toString()).build();
        HttpResponse response = client.execute(request);

        assertEquals(204, response.getStatusCode());
        RecordedRequest recorded = server.takeRequest();
        assertEquals("DELETE", recorded.getMethod());
    }

    @Test
    void execute_addsQueryParams() throws Exception {
        server.enqueue(new MockResponse().setResponseCode(200).setBody("[]"));

        HttpRequest request = HttpRequest.builder(HttpMethod.GET, server.url("/test").toString())
                .queryParam("page", "2")
                .queryParam("page_size", "50")
                .build();
        client.execute(request);

        RecordedRequest recorded = server.takeRequest();
        String path = recorded.getPath();
        assertTrue(path.contains("page=2"), "Query param 'page' should be present");
        assertTrue(path.contains("page_size=50"), "Query param 'page_size' should be present");
    }

    @Test
    void execute_addsHeaders() throws Exception {
        server.enqueue(new MockResponse().setResponseCode(200).setBody("{}"));

        HttpRequest request = HttpRequest.builder(HttpMethod.GET, server.url("/test").toString())
                .header("Accept", "application/json")
                .header("X-Custom", "value")
                .build();
        client.execute(request);

        RecordedRequest recorded = server.takeRequest();
        assertEquals("application/json", recorded.getHeader("Accept"));
        assertEquals("value", recorded.getHeader("X-Custom"));
    }

    @Test
    void execute_returnsResponseHeaders() throws Exception {
        server.enqueue(new MockResponse()
                .setResponseCode(200)
                .setBody("[]")
                .addHeader("X-Pagination-Page", "1")
                .addHeader("X-Pagination-Has-More", "true")
                .addHeader("X-Pagination-Total-Items", "100"));

        HttpRequest request = HttpRequest.builder(HttpMethod.GET, server.url("/test").toString()).build();
        HttpResponse response = client.execute(request);

        assertEquals("1", response.header("X-Pagination-Page").orElse(null));
        assertEquals("true", response.header("X-Pagination-Has-More").orElse(null));
        assertEquals("100", response.header("X-Pagination-Total-Items").orElse(null));
    }
}
