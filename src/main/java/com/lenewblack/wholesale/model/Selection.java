package com.lenewblack.wholesale.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public final class Selection {

    @SerializedName("id")
    private String id;

    @SerializedName("retailer_id")
    private String retailerId;

    @SerializedName("product_ids")
    private List<String> productIds;

    @SerializedName("created_at")
    private String createdAt;

    @SerializedName("updated_at")
    private String updatedAt;

    public Selection() {}

    public String getId() { return id; }
    public String getRetailerId() { return retailerId; }
    public List<String> getProductIds() { return productIds; }
    public String getCreatedAt() { return createdAt; }
    public String getUpdatedAt() { return updatedAt; }
}
