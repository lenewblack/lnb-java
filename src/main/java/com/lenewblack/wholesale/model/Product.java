package com.lenewblack.wholesale.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public final class Product {

    @SerializedName("id")
    private String id;

    @SerializedName("reference")
    private String reference;

    @SerializedName("name")
    private String name;

    @SerializedName("description")
    private String description;

    @SerializedName("collection_id")
    private String collectionId;

    @SerializedName("fabric_ids")
    private List<String> fabricIds;

    @SerializedName("sizing_id")
    private String sizingId;

    @SerializedName("variants")
    private List<ProductVariant> variants;

    @SerializedName("created_at")
    private String createdAt;

    @SerializedName("updated_at")
    private String updatedAt;

    public Product() {}

    public String getId() { return id; }
    public String getReference() { return reference; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public String getCollectionId() { return collectionId; }
    public List<String> getFabricIds() { return fabricIds; }
    public String getSizingId() { return sizingId; }
    public List<ProductVariant> getVariants() { return variants; }
    public String getCreatedAt() { return createdAt; }
    public String getUpdatedAt() { return updatedAt; }
}
