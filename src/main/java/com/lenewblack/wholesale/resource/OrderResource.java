package com.lenewblack.wholesale.resource;

import com.google.gson.Gson;
import com.lenewblack.wholesale.RequestOptions;
import com.lenewblack.wholesale.auth.TokenManager;
import com.lenewblack.wholesale.http.HttpClient;
import com.lenewblack.wholesale.http.HttpResponse;
import com.lenewblack.wholesale.model.Order;
import com.lenewblack.wholesale.model.ResultSet;
import com.lenewblack.wholesale.pagination.PageIterable;
import com.lenewblack.wholesale.param.OrderListParams;
import com.lenewblack.wholesale.param.OrderUpsertParams;

public final class OrderResource extends AbstractResource {

    public OrderResource(HttpClient httpClient, TokenManager tokenManager, Gson gson,
                         String baseUrl, RequestOptions defaultOptions) {
        super(httpClient, tokenManager, gson, baseUrl, defaultOptions);
    }

    public ResultSet<Order> list(OrderListParams params) {
        return list(params, null);
    }

    public ResultSet<Order> list(OrderListParams params, RequestOptions opts) {
        HttpResponse response = authenticatedGet("/orders", params.toQueryParams(), opts);
        return parseResultSet(response, Order.class);
    }

    public Order get(String orderId) {
        return get(orderId, null);
    }

    public Order get(String orderId, RequestOptions opts) {
        HttpResponse response = authenticatedGet("/orders/" + orderId, null, opts);
        return parseBody(response, Order.class);
    }

    public Order upsert(OrderUpsertParams params) {
        return upsert(params, null);
    }

    public Order upsert(OrderUpsertParams params, RequestOptions opts) {
        HttpResponse response = authenticatedPost("/orders", params, opts);
        return parseBody(response, Order.class);
    }

    public Iterable<Order> paginate(OrderListParams params) {
        return new PageIterable<>(page -> list(params.withPage(page)));
    }
}
