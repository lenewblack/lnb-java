package com.lenewblack.wholesale.resource;

import com.google.gson.Gson;
import com.lenewblack.wholesale.RequestOptions;
import com.lenewblack.wholesale.auth.TokenManager;
import com.lenewblack.wholesale.http.HttpClient;
import com.lenewblack.wholesale.http.HttpResponse;
import com.lenewblack.wholesale.model.Inventory;
import com.lenewblack.wholesale.model.ResultSet;
import com.lenewblack.wholesale.model.batch.BatchResponse;
import com.lenewblack.wholesale.param.InventoryUpdateParams;

import java.util.List;
import java.util.Map;

public final class InventoryResource extends AbstractResource {

    public InventoryResource(HttpClient httpClient, TokenManager tokenManager, Gson gson,
                             String baseUrl, RequestOptions defaultOptions) {
        super(httpClient, tokenManager, gson, baseUrl, defaultOptions);
    }

    public ResultSet<Inventory> list(Map<String, String> params) {
        return list(params, null);
    }

    public ResultSet<Inventory> list(Map<String, String> params, RequestOptions opts) {
        HttpResponse response = authenticatedGet("/inventory", params, opts);
        return parseResultSet(response, Inventory.class);
    }

    public Inventory update(InventoryUpdateParams params) {
        return update(params, null);
    }

    public Inventory update(InventoryUpdateParams params, RequestOptions opts) {
        HttpResponse response = authenticatedPost("/inventory", params, opts);
        return parseBody(response, Inventory.class);
    }

    public BatchResponse<Inventory> batchUpdate(List<InventoryUpdateParams> items) {
        return batchUpdate(items, null);
    }

    public BatchResponse<Inventory> batchUpdate(List<InventoryUpdateParams> items, RequestOptions opts) {
        return authenticatedBatchPost("/inventory/batch", items, Inventory.class, opts);
    }
}
