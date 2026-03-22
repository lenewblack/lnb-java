package com.lenewblack.wholesale.resource;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lenewblack.wholesale.RequestOptions;
import com.lenewblack.wholesale.auth.TokenManager;
import com.lenewblack.wholesale.http.HttpClient;
import com.lenewblack.wholesale.http.HttpResponse;
import com.lenewblack.wholesale.model.Product;
import com.lenewblack.wholesale.model.ProductVariant;
import com.lenewblack.wholesale.model.ResultSet;
import com.lenewblack.wholesale.model.batch.BatchResponse;
import com.lenewblack.wholesale.pagination.PageIterable;
import com.lenewblack.wholesale.param.ProductListParams;
import com.lenewblack.wholesale.param.ProductUpsertParams;
import com.lenewblack.wholesale.param.VariantUpdateParams;

import java.util.List;

public final class ProductResource extends AbstractResource {

    public ProductResource(HttpClient httpClient, TokenManager tokenManager, Gson gson,
                           String baseUrl, RequestOptions defaultOptions) {
        super(httpClient, tokenManager, gson, baseUrl, defaultOptions);
    }

    public ResultSet<Product> list(ProductListParams params) {
        return list(params, null);
    }

    public ResultSet<Product> list(ProductListParams params, RequestOptions opts) {
        HttpResponse response = authenticatedGet("/products", params.toQueryParams(), opts);
        return parseResultSet(response, Product.class);
    }

    public Product get(String productId) {
        return get(productId, null);
    }

    public Product get(String productId, RequestOptions opts) {
        HttpResponse response = authenticatedGet("/products/" + productId, null, opts);
        return parseBody(response, Product.class);
    }

    public Product upsert(ProductUpsertParams params) {
        return upsert(params, null);
    }

    public Product upsert(ProductUpsertParams params, RequestOptions opts) {
        HttpResponse response = authenticatedPost("/products", params, opts);
        return parseBody(response, Product.class);
    }

    public BatchResponse<Product> batchUpsert(List<ProductUpsertParams> items) {
        return batchUpsert(items, null);
    }

    public BatchResponse<Product> batchUpsert(List<ProductUpsertParams> items, RequestOptions opts) {
        return authenticatedBatchPost("/products/batch", items, Product.class, opts);
    }

    public Iterable<Product> paginate(ProductListParams params) {
        return new PageIterable<>(page -> list(params.withPage(page)));
    }

    public ProductVariant getVariant(String productId, String variantId) {
        return getVariant(productId, variantId, null);
    }

    public ProductVariant getVariant(String productId, String variantId, RequestOptions opts) {
        HttpResponse response = authenticatedGet(
                "/products/" + productId + "/variants/" + variantId, null, opts);
        return parseBody(response, ProductVariant.class);
    }

    public ProductVariant updateVariant(String productId, String variantId, VariantUpdateParams params) {
        return updateVariant(productId, variantId, params, null);
    }

    public ProductVariant updateVariant(String productId, String variantId,
                                        VariantUpdateParams params, RequestOptions opts) {
        HttpResponse response = authenticatedPost(
                "/products/" + productId + "/variants/" + variantId, params, opts);
        return parseBody(response, ProductVariant.class);
    }

    public ProductVariant setVariantAlternatives(String productId, String variantId,
                                                  List<String> alternativeIds) {
        return setVariantAlternatives(productId, variantId, alternativeIds, null);
    }

    public ProductVariant setVariantAlternatives(String productId, String variantId,
                                                  List<String> alternativeIds, RequestOptions opts) {
        HttpResponse response = authenticatedPost(
                "/products/" + productId + "/variants/" + variantId + "/alternatives",
                alternativeIds, opts);
        return parseBody(response, ProductVariant.class);
    }
}
