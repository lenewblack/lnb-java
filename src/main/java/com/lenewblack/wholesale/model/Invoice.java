package com.lenewblack.wholesale.model;

import com.google.gson.annotations.SerializedName;

public final class Invoice {

    @SerializedName("id")
    private String id;

    @SerializedName("reference")
    private String reference;

    @SerializedName("order_id")
    private String orderId;

    @SerializedName("total_amount")
    private double totalAmount;

    @SerializedName("currency")
    private String currency;

    @SerializedName("issued_at")
    private String issuedAt;

    @SerializedName("created_at")
    private String createdAt;

    public Invoice() {}

    public String getId() { return id; }
    public String getReference() { return reference; }
    public String getOrderId() { return orderId; }
    public double getTotalAmount() { return totalAmount; }
    public String getCurrency() { return currency; }
    public String getIssuedAt() { return issuedAt; }
    public String getCreatedAt() { return createdAt; }
}
