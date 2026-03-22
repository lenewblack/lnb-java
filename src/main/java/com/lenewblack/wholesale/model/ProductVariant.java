package com.lenewblack.wholesale.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public final class ProductVariant {

    @SerializedName("id")
    private String id;

    @SerializedName("product_id")
    private String productId;

    @SerializedName("reference")
    private String reference;

    @SerializedName("color_code")
    private String colorCode;

    @SerializedName("color_name")
    private String colorName;

    @SerializedName("sizes")
    private List<String> sizes;

    @SerializedName("image_urls")
    private List<String> imageUrls;

    @SerializedName("alternatives")
    private List<String> alternatives;

    public ProductVariant() {}

    public String getId() { return id; }
    public String getProductId() { return productId; }
    public String getReference() { return reference; }
    public String getColorCode() { return colorCode; }
    public String getColorName() { return colorName; }
    public List<String> getSizes() { return sizes; }
    public List<String> getImageUrls() { return imageUrls; }
    public List<String> getAlternatives() { return alternatives; }
}
