package com.lenewblack.wholesale.resource;

import com.google.gson.Gson;
import com.lenewblack.wholesale.RequestOptions;
import com.lenewblack.wholesale.auth.TokenManager;
import com.lenewblack.wholesale.http.HttpClient;
import com.lenewblack.wholesale.http.HttpResponse;
import com.lenewblack.wholesale.model.ResultSet;
import com.lenewblack.wholesale.model.SalesDocument;
import com.lenewblack.wholesale.param.SalesDocumentStatusParams;

import java.util.Map;

public final class SalesDocumentResource extends AbstractResource {

    public SalesDocumentResource(HttpClient httpClient, TokenManager tokenManager, Gson gson,
                                 String baseUrl, RequestOptions defaultOptions) {
        super(httpClient, tokenManager, gson, baseUrl, defaultOptions);
    }

    public ResultSet<SalesDocument> list(Map<String, String> params) {
        return list(params, null);
    }

    public ResultSet<SalesDocument> list(Map<String, String> params, RequestOptions opts) {
        HttpResponse response = authenticatedGet("/sales-documents", params, opts);
        return parseResultSet(response, SalesDocument.class);
    }

    public SalesDocument get(String documentId) {
        return get(documentId, null);
    }

    public SalesDocument get(String documentId, RequestOptions opts) {
        HttpResponse response = authenticatedGet("/sales-documents/" + documentId, null, opts);
        return parseBody(response, SalesDocument.class);
    }

    public SalesDocument updateStatus(String documentId, SalesDocumentStatusParams params) {
        return updateStatus(documentId, params, null);
    }

    public SalesDocument updateStatus(String documentId, SalesDocumentStatusParams params,
                                       RequestOptions opts) {
        HttpResponse response = authenticatedPost(
                "/sales-documents/" + documentId + "/status", params, opts);
        return parseBody(response, SalesDocument.class);
    }
}
