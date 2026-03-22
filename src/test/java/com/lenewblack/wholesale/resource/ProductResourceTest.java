package com.lenewblack.wholesale.resource;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lenewblack.wholesale.RequestOptions;
import com.lenewblack.wholesale.TestUtils;
import com.lenewblack.wholesale.auth.TokenManager;
import com.lenewblack.wholesale.exception.NotFoundException;
import com.lenewblack.wholesale.http.HttpClient;
import com.lenewblack.wholesale.http.HttpRequest;
import com.lenewblack.wholesale.http.HttpResponse;
import com.lenewblack.wholesale.model.Product;
import com.lenewblack.wholesale.model.ResultSet;
import com.lenewblack.wholesale.param.ProductListParams;
import com.lenewblack.wholesale.param.ProductUpsertParams;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductResourceTest {

    @Mock
    private HttpClient httpClient;

    @Mock
    private TokenManager tokenManager;

    private ProductResource resource;

    @BeforeEach
    void setUp() {
        when(tokenManager.getValidToken()).thenReturn("mock-token");
        Gson gson = new GsonBuilder().create();
        resource = new ProductResource(
                httpClient, tokenManager, gson,
                "https://api.example.com", RequestOptions.defaults()
        );
    }

    @Test
    void list_returnsResultSet() {
        String body = TestUtils.loadFixture("product_list.json");
        Map<String, List<String>> headers = new HashMap<>();
        headers.put("X-Pagination-Page", Collections.singletonList("1"));
        headers.put("X-Pagination-Page-Size", Collections.singletonList("20"));
        headers.put("X-Pagination-Has-More", Collections.singletonList("false"));
        headers.put("X-Pagination-Total-Pages", Collections.singletonList("1"));
        headers.put("X-Pagination-Total-Items", Collections.singletonList("2"));

        when(httpClient.execute(any(HttpRequest.class)))
                .thenReturn(new HttpResponse(200, body, headers));

        ResultSet<Product> result = resource.list(ProductListParams.builder().build());

        assertNotNull(result);
        assertEquals(2, result.getData().size());
        assertEquals("prod-001", result.getData().get(0).getId());
        assertEquals("Test Product 1", result.getData().get(0).getName());
        assertFalse(result.getMetadata().isHasMore());
        assertEquals(2, result.getMetadata().getTotalItems());
    }

    @Test
    void get_returnsSingleProduct() {
        String body = TestUtils.loadFixture("product.json");
        when(httpClient.execute(any(HttpRequest.class)))
                .thenReturn(new HttpResponse(200, body, Collections.emptyMap()));

        Product product = resource.get("prod-001");

        assertNotNull(product);
        assertEquals("prod-001", product.getId());
        assertEquals("REF-001", product.getReference());
        assertEquals("Test Product", product.getName());
        assertNotNull(product.getVariants());
        assertEquals(1, product.getVariants().size());
        assertEquals("BLK", product.getVariants().get(0).getColorCode());
    }

    @Test
    void get_throwsNotFoundException_on404() {
        when(httpClient.execute(any(HttpRequest.class)))
                .thenReturn(new HttpResponse(404, "{\"message\":\"Not found\"}", Collections.emptyMap()));

        assertThrows(NotFoundException.class, () -> resource.get("nonexistent"));
    }

    @Test
    void list_includesTokenInQueryParams() {
        String body = TestUtils.loadFixture("product_list.json");
        when(httpClient.execute(any(HttpRequest.class)))
                .thenReturn(new HttpResponse(200, body, Collections.emptyMap()));

        resource.list(ProductListParams.builder().build());

        ArgumentCaptor<HttpRequest> captor = ArgumentCaptor.forClass(HttpRequest.class);
        verify(httpClient).execute(captor.capture());
        HttpRequest captured = captor.getValue();
        assertEquals("mock-token", captured.getQueryParams().get("access_token"));
    }

    @Test
    void list_withPageParams_includesQueryParams() {
        String body = TestUtils.loadFixture("product_list.json");
        when(httpClient.execute(any(HttpRequest.class)))
                .thenReturn(new HttpResponse(200, body, Collections.emptyMap()));

        resource.list(ProductListParams.builder().setPage(2).setPageSize(50).build());

        ArgumentCaptor<HttpRequest> captor = ArgumentCaptor.forClass(HttpRequest.class);
        verify(httpClient).execute(captor.capture());
        HttpRequest captured = captor.getValue();
        assertEquals("2", captured.getQueryParams().get("page"));
        assertEquals("50", captured.getQueryParams().get("page_size"));
    }

    @Test
    void paginate_iteratesAllPages() {
        String body = TestUtils.loadFixture("product_list.json");

        Map<String, List<String>> page1Headers = new HashMap<>();
        page1Headers.put("X-Pagination-Has-More", Collections.singletonList("true"));
        Map<String, List<String>> page2Headers = new HashMap<>();
        page2Headers.put("X-Pagination-Has-More", Collections.singletonList("false"));

        when(httpClient.execute(any(HttpRequest.class)))
                .thenReturn(new HttpResponse(200, body, page1Headers))
                .thenReturn(new HttpResponse(200, body, page2Headers));

        int count = 0;
        for (Product p : resource.paginate(ProductListParams.builder().build())) {
            count++;
        }

        assertEquals(4, count); // 2 products × 2 pages
        verify(httpClient, times(2)).execute(any(HttpRequest.class));
    }

    @Test
    void upsert_sendsPostRequest() {
        String body = TestUtils.loadFixture("product.json");
        when(httpClient.execute(any(HttpRequest.class)))
                .thenReturn(new HttpResponse(200, body, Collections.emptyMap()));

        ProductUpsertParams params = ProductUpsertParams.builder()
                .setReference("REF-001")
                .setName("Test Product")
                .setCollectionId("col-001")
                .build();

        Product result = resource.upsert(params);

        assertNotNull(result);
        assertEquals("prod-001", result.getId());

        ArgumentCaptor<HttpRequest> captor = ArgumentCaptor.forClass(HttpRequest.class);
        verify(httpClient).execute(captor.capture());
        assertEquals(com.lenewblack.wholesale.http.HttpMethod.POST, captor.getValue().getMethod());
    }
}
