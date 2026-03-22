package com.lenewblack.wholesale.resource;

import com.google.gson.Gson;
import com.lenewblack.wholesale.RequestOptions;
import com.lenewblack.wholesale.auth.TokenManager;
import com.lenewblack.wholesale.http.HttpClient;
import com.lenewblack.wholesale.http.HttpResponse;
import com.lenewblack.wholesale.model.ResultSet;
import com.lenewblack.wholesale.model.SalesCatalog;

import java.util.Map;

public final class SalesCatalogResource extends AbstractResource {

    public SalesCatalogResource(HttpClient httpClient, TokenManager tokenManager, Gson gson,
                                String baseUrl, RequestOptions defaultOptions) {
        super(httpClient, tokenManager, gson, baseUrl, defaultOptions);
    }

    public ResultSet<SalesCatalog> list(Map<String, String> params) {
        return list(params, null);
    }

    public ResultSet<SalesCatalog> list(Map<String, String> params, RequestOptions opts) {
        HttpResponse response = authenticatedGet("/sales-catalogs", params, opts);
        return parseResultSet(response, SalesCatalog.class);
    }

    public SalesCatalog get(String catalogId) {
        return get(catalogId, null);
    }

    public SalesCatalog get(String catalogId, RequestOptions opts) {
        HttpResponse response = authenticatedGet("/sales-catalogs/" + catalogId, null, opts);
        return parseBody(response, SalesCatalog.class);
    }
}
