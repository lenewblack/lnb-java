package com.lenewblack.wholesale.model;

import com.google.gson.annotations.SerializedName;

public final class OrderLine {

    @SerializedName("id")
    private String id;

    @SerializedName("product_id")
    private String productId;

    @SerializedName("variant_id")
    private String variantId;

    @SerializedName("size_code")
    private String sizeCode;

    @SerializedName("quantity")
    private int quantity;

    @SerializedName("unit_price")
    private double unitPrice;

    @SerializedName("currency")
    private String currency;

    public OrderLine() {}

    public String getId() { return id; }
    public String getProductId() { return productId; }
    public String getVariantId() { return variantId; }
    public String getSizeCode() { return sizeCode; }
    public int getQuantity() { return quantity; }
    public double getUnitPrice() { return unitPrice; }
    public String getCurrency() { return currency; }
}
