package com.lenewblack.wholesale.resource;

import com.google.gson.Gson;
import com.lenewblack.wholesale.RequestOptions;
import com.lenewblack.wholesale.auth.TokenManager;
import com.lenewblack.wholesale.http.HttpClient;
import com.lenewblack.wholesale.http.HttpResponse;
import com.lenewblack.wholesale.model.ResultSet;
import com.lenewblack.wholesale.model.Sizing;

import java.util.Map;

public final class SizingResource extends AbstractResource {

    public SizingResource(HttpClient httpClient, TokenManager tokenManager, Gson gson,
                          String baseUrl, RequestOptions defaultOptions) {
        super(httpClient, tokenManager, gson, baseUrl, defaultOptions);
    }

    public ResultSet<Sizing> list(Map<String, String> params) {
        return list(params, null);
    }

    public ResultSet<Sizing> list(Map<String, String> params, RequestOptions opts) {
        HttpResponse response = authenticatedGet("/sizings", params, opts);
        return parseResultSet(response, Sizing.class);
    }

    public Sizing get(String sizingId) {
        return get(sizingId, null);
    }

    public Sizing get(String sizingId, RequestOptions opts) {
        HttpResponse response = authenticatedGet("/sizings/" + sizingId, null, opts);
        return parseBody(response, Sizing.class);
    }
}
