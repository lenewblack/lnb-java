package com.lenewblack.wholesale.resource;

import com.google.gson.Gson;
import com.lenewblack.wholesale.RequestOptions;
import com.lenewblack.wholesale.auth.TokenManager;
import com.lenewblack.wholesale.http.HttpClient;
import com.lenewblack.wholesale.http.HttpResponse;
import com.lenewblack.wholesale.model.ResultSet;
import com.lenewblack.wholesale.model.Selection;
import com.lenewblack.wholesale.pagination.PageIterable;
import com.lenewblack.wholesale.param.SelectionCreateParams;

import java.util.Map;

public final class SelectionResource extends AbstractResource {

    public SelectionResource(HttpClient httpClient, TokenManager tokenManager, Gson gson,
                             String baseUrl, RequestOptions defaultOptions) {
        super(httpClient, tokenManager, gson, baseUrl, defaultOptions);
    }

    public ResultSet<Selection> list(Map<String, String> params) {
        return list(params, null);
    }

    public ResultSet<Selection> list(Map<String, String> params, RequestOptions opts) {
        HttpResponse response = authenticatedGet("/selections", params, opts);
        return parseResultSet(response, Selection.class);
    }

    public Selection create(SelectionCreateParams params) {
        return create(params, null);
    }

    public Selection create(SelectionCreateParams params, RequestOptions opts) {
        HttpResponse response = authenticatedPost("/selections", params, opts);
        return parseBody(response, Selection.class);
    }

    public Selection get(String selectionId) {
        return get(selectionId, null);
    }

    public Selection get(String selectionId, RequestOptions opts) {
        HttpResponse response = authenticatedGet("/selections/" + selectionId, null, opts);
        return parseBody(response, Selection.class);
    }

    public void delete(String selectionId) {
        delete(selectionId, null);
    }

    public void delete(String selectionId, RequestOptions opts) {
        authenticatedDelete("/selections/" + selectionId, opts);
    }

    public Iterable<Selection> paginate(Map<String, String> params) {
        return new PageIterable<>(page -> {
            Map<String, String> p = new java.util.LinkedHashMap<>(params != null ? params : new java.util.HashMap<>());
            p.put("page", String.valueOf(page));
            return list(p);
        });
    }
}
