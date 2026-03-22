package com.lenewblack.wholesale.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public final class Order {

    @SerializedName("id")
    private String id;

    @SerializedName("reference")
    private String reference;

    @SerializedName("retailer_id")
    private String retailerId;

    @SerializedName("status")
    private String status;

    @SerializedName("lines")
    private List<OrderLine> lines;

    @SerializedName("total_amount")
    private double totalAmount;

    @SerializedName("currency")
    private String currency;

    @SerializedName("order_time")
    private String orderTime;

    @SerializedName("confirmation_time")
    private String confirmationTime;

    @SerializedName("created_at")
    private String createdAt;

    @SerializedName("updated_at")
    private String updatedAt;

    public Order() {}

    public String getId() { return id; }
    public String getReference() { return reference; }
    public String getRetailerId() { return retailerId; }
    public String getStatus() { return status; }
    public List<OrderLine> getLines() { return lines; }
    public double getTotalAmount() { return totalAmount; }
    public String getCurrency() { return currency; }
    public String getOrderTime() { return orderTime; }
    public String getConfirmationTime() { return confirmationTime; }
    public String getCreatedAt() { return createdAt; }
    public String getUpdatedAt() { return updatedAt; }
}
