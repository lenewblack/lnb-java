package com.lenewblack.wholesale.resource;

import com.google.gson.Gson;
import com.lenewblack.wholesale.RequestOptions;
import com.lenewblack.wholesale.auth.TokenManager;
import com.lenewblack.wholesale.http.HttpClient;
import com.lenewblack.wholesale.http.HttpResponse;
import com.lenewblack.wholesale.model.Invoice;
import com.lenewblack.wholesale.model.ResultSet;

import java.util.Map;

public final class InvoiceResource extends AbstractResource {

    public InvoiceResource(HttpClient httpClient, TokenManager tokenManager, Gson gson,
                           String baseUrl, RequestOptions defaultOptions) {
        super(httpClient, tokenManager, gson, baseUrl, defaultOptions);
    }

    public ResultSet<Invoice> list(Map<String, String> params) {
        return list(params, null);
    }

    public ResultSet<Invoice> list(Map<String, String> params, RequestOptions opts) {
        HttpResponse response = authenticatedGet("/invoices", params, opts);
        return parseResultSet(response, Invoice.class);
    }

    public Invoice get(String invoiceId) {
        return get(invoiceId, null);
    }

    public Invoice get(String invoiceId, RequestOptions opts) {
        HttpResponse response = authenticatedGet("/invoices/" + invoiceId, null, opts);
        return parseBody(response, Invoice.class);
    }
}
