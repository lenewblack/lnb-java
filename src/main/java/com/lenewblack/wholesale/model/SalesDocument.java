package com.lenewblack.wholesale.model;

import com.google.gson.annotations.SerializedName;

public final class SalesDocument {

    @SerializedName("id")
    private String id;

    @SerializedName("type")
    private String type;

    @SerializedName("status")
    private String status;

    @SerializedName("order_id")
    private String orderId;

    @SerializedName("reference")
    private String reference;

    @SerializedName("issued_at")
    private String issuedAt;

    @SerializedName("created_at")
    private String createdAt;

    @SerializedName("updated_at")
    private String updatedAt;

    public SalesDocument() {}

    public String getId() { return id; }
    public String getType() { return type; }
    public String getStatus() { return status; }
    public String getOrderId() { return orderId; }
    public String getReference() { return reference; }
    public String getIssuedAt() { return issuedAt; }
    public String getCreatedAt() { return createdAt; }
    public String getUpdatedAt() { return updatedAt; }
}
