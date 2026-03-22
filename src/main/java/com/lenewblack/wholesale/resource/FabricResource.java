package com.lenewblack.wholesale.resource;

import com.google.gson.Gson;
import com.lenewblack.wholesale.RequestOptions;
import com.lenewblack.wholesale.auth.TokenManager;
import com.lenewblack.wholesale.http.HttpClient;
import com.lenewblack.wholesale.http.HttpResponse;
import com.lenewblack.wholesale.model.Fabric;
import com.lenewblack.wholesale.model.ResultSet;

import java.util.Map;

public final class FabricResource extends AbstractResource {

    public FabricResource(HttpClient httpClient, TokenManager tokenManager, Gson gson,
                          String baseUrl, RequestOptions defaultOptions) {
        super(httpClient, tokenManager, gson, baseUrl, defaultOptions);
    }

    public ResultSet<Fabric> list(Map<String, String> params) {
        return list(params, null);
    }

    public ResultSet<Fabric> list(Map<String, String> params, RequestOptions opts) {
        HttpResponse response = authenticatedGet("/fabrics", params, opts);
        return parseResultSet(response, Fabric.class);
    }

    public Fabric get(String fabricId) {
        return get(fabricId, null);
    }

    public Fabric get(String fabricId, RequestOptions opts) {
        HttpResponse response = authenticatedGet("/fabrics/" + fabricId, null, opts);
        return parseBody(response, Fabric.class);
    }
}
