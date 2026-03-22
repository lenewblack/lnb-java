package com.lenewblack.wholesale.resource;

import com.google.gson.Gson;
import com.lenewblack.wholesale.RequestOptions;
import com.lenewblack.wholesale.auth.TokenManager;
import com.lenewblack.wholesale.http.HttpClient;
import com.lenewblack.wholesale.http.HttpResponse;
import com.lenewblack.wholesale.model.ResultSet;
import com.lenewblack.wholesale.model.Retailer;

import java.util.Map;

public final class RetailerResource extends AbstractResource {

    public RetailerResource(HttpClient httpClient, TokenManager tokenManager, Gson gson,
                            String baseUrl, RequestOptions defaultOptions) {
        super(httpClient, tokenManager, gson, baseUrl, defaultOptions);
    }

    public ResultSet<Retailer> list(Map<String, String> params) {
        return list(params, null);
    }

    public ResultSet<Retailer> list(Map<String, String> params, RequestOptions opts) {
        HttpResponse response = authenticatedGet("/retailers", params, opts);
        return parseResultSet(response, Retailer.class);
    }

    public Retailer get(String retailerId) {
        return get(retailerId, null);
    }

    public Retailer get(String retailerId, RequestOptions opts) {
        HttpResponse response = authenticatedGet("/retailers/" + retailerId, null, opts);
        return parseBody(response, Retailer.class);
    }
}
