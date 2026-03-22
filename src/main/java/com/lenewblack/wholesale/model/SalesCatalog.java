package com.lenewblack.wholesale.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public final class SalesCatalog {

    @SerializedName("id")
    private String id;

    @SerializedName("name")
    private String name;

    @SerializedName("collection_id")
    private String collectionId;

    @SerializedName("product_ids")
    private List<String> productIds;

    @SerializedName("created_at")
    private String createdAt;

    @SerializedName("updated_at")
    private String updatedAt;

    public SalesCatalog() {}

    public String getId() { return id; }
    public String getName() { return name; }
    public String getCollectionId() { return collectionId; }
    public List<String> getProductIds() { return productIds; }
    public String getCreatedAt() { return createdAt; }
    public String getUpdatedAt() { return updatedAt; }
}
