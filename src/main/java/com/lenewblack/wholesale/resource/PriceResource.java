package com.lenewblack.wholesale.resource;

import com.google.gson.Gson;
import com.lenewblack.wholesale.RequestOptions;
import com.lenewblack.wholesale.auth.TokenManager;
import com.lenewblack.wholesale.http.HttpClient;
import com.lenewblack.wholesale.http.HttpResponse;
import com.lenewblack.wholesale.model.Price;
import com.lenewblack.wholesale.model.ResultSet;
import com.lenewblack.wholesale.model.batch.BatchResponse;
import com.lenewblack.wholesale.param.PriceUpdateParams;

import java.util.List;
import java.util.Map;

public final class PriceResource extends AbstractResource {

    public PriceResource(HttpClient httpClient, TokenManager tokenManager, Gson gson,
                         String baseUrl, RequestOptions defaultOptions) {
        super(httpClient, tokenManager, gson, baseUrl, defaultOptions);
    }

    public ResultSet<Price> list(Map<String, String> params) {
        return list(params, null);
    }

    public ResultSet<Price> list(Map<String, String> params, RequestOptions opts) {
        HttpResponse response = authenticatedGet("/prices", params, opts);
        return parseResultSet(response, Price.class);
    }

    public Price update(PriceUpdateParams params) {
        return update(params, null);
    }

    public Price update(PriceUpdateParams params, RequestOptions opts) {
        HttpResponse response = authenticatedPost("/prices", params, opts);
        return parseBody(response, Price.class);
    }

    public BatchResponse<Price> batchUpdate(List<PriceUpdateParams> items) {
        return batchUpdate(items, null);
    }

    public BatchResponse<Price> batchUpdate(List<PriceUpdateParams> items, RequestOptions opts) {
        return authenticatedBatchPost("/prices/batch", items, Price.class, opts);
    }
}
