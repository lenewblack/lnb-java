package com.lenewblack.wholesale.resource;

import com.google.gson.Gson;
import com.lenewblack.wholesale.RequestOptions;
import com.lenewblack.wholesale.auth.TokenManager;
import com.lenewblack.wholesale.http.HttpClient;
import com.lenewblack.wholesale.http.HttpResponse;
import com.lenewblack.wholesale.model.Collection;
import com.lenewblack.wholesale.model.ResultSet;

import java.util.Map;

public final class CollectionResource extends AbstractResource {

    public CollectionResource(HttpClient httpClient, TokenManager tokenManager, Gson gson,
                              String baseUrl, RequestOptions defaultOptions) {
        super(httpClient, tokenManager, gson, baseUrl, defaultOptions);
    }

    public ResultSet<Collection> list(Map<String, String> params) {
        return list(params, null);
    }

    public ResultSet<Collection> list(Map<String, String> params, RequestOptions opts) {
        HttpResponse response = authenticatedGet("/collections", params, opts);
        return parseResultSet(response, Collection.class);
    }

    public Collection get(String collectionId) {
        return get(collectionId, null);
    }

    public Collection get(String collectionId, RequestOptions opts) {
        HttpResponse response = authenticatedGet("/collections/" + collectionId, null, opts);
        return parseBody(response, Collection.class);
    }
}
