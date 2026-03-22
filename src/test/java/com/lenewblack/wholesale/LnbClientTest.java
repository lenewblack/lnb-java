package com.lenewblack.wholesale;

import com.lenewblack.wholesale.exception.ApiException;
import com.lenewblack.wholesale.model.Product;
import com.lenewblack.wholesale.model.ResultSet;
import com.lenewblack.wholesale.param.ProductListParams;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Integration-style test using MockWebServer — exercises the full request chain.
 */
class LnbClientTest {

    private MockWebServer server;
    private LnbClient client;

    @BeforeEach
    void setUp() throws IOException {
        server = new MockWebServer();
        server.start();

        String baseUrl = server.url("").toString().replaceAll("/$", "");
        client = LnbClient.builder()
                .clientId("test-client-id")
                .clientSecret("test-client-secret")
                .baseUrl(baseUrl)
                .tokenUrl(baseUrl + "/oauth/token")
                .build();
    }

    @AfterEach
    void tearDown() throws IOException {
        server.shutdown();
    }

    @Test
    void builder_throwsException_whenClientIdMissing() {
        assertThrows(IllegalStateException.class, () ->
                LnbClient.builder().clientSecret("secret").build());
    }

    @Test
    void builder_throwsException_whenClientSecretMissing() {
        assertThrows(IllegalStateException.class, () ->
                LnbClient.builder().clientId("id").build());
    }

    @Test
    void products_list_fullChain() throws InterruptedException {
        // Token response
        server.enqueue(new MockResponse()
                .setResponseCode(200)
                .setBody("{\"access_token\":\"tok123\",\"expires_in\":3600}")
                .addHeader("Content-Type", "application/json"));

        // Products list response
        server.enqueue(new MockResponse()
                .setResponseCode(200)
                .setBody(TestUtils.loadFixture("product_list.json"))
                .addHeader("Content-Type", "application/json")
                .addHeader("X-Pagination-Page", "1")
                .addHeader("X-Pagination-Page-Size", "20")
                .addHeader("X-Pagination-Has-More", "false")
                .addHeader("X-Pagination-Total-Items", "2"));

        ResultSet<Product> result = client.products()
                .list(ProductListParams.builder().setPageSize(20).build());

        assertNotNull(result);
        assertEquals(2, result.getData().size());
        assertEquals("prod-001", result.getData().get(0).getId());
        assertFalse(result.getMetadata().isHasMore());
        assertEquals(2, result.getMetadata().getTotalItems());
    }

    @Test
    void products_get_throwsNotFoundException_on404() {
        // Token response
        server.enqueue(new MockResponse()
                .setResponseCode(200)
                .setBody("{\"access_token\":\"tok123\",\"expires_in\":3600}"));

        // 404 response
        server.enqueue(new MockResponse()
                .setResponseCode(404)
                .setBody("{\"message\":\"Product not found\"}"));

        ApiException ex = assertThrows(ApiException.class, () ->
                client.products().get("nonexistent"));

        assertEquals(404, ex.getStatusCode());
    }

    @Test
    void resources_notNull() {
        // All resource accessors should return non-null instances
        assertNotNull(client.products());
        assertNotNull(client.orders());
        assertNotNull(client.collections());
        assertNotNull(client.retailers());
        assertNotNull(client.fabrics());
        assertNotNull(client.sizings());
        assertNotNull(client.inventory());
        assertNotNull(client.prices());
        assertNotNull(client.salesDocuments());
        assertNotNull(client.files());
        assertNotNull(client.salesCatalogs());
        assertNotNull(client.invoices());
        assertNotNull(client.selections());
    }
}
