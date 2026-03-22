package com.lenewblack.wholesale.model;

import com.google.gson.annotations.SerializedName;

public final class Inventory {

    @SerializedName("variant_id")
    private String variantId;

    @SerializedName("size_code")
    private String sizeCode;

    @SerializedName("quantity")
    private int quantity;

    @SerializedName("updated_at")
    private String updatedAt;

    public Inventory() {}

    public String getVariantId() { return variantId; }
    public String getSizeCode() { return sizeCode; }
    public int getQuantity() { return quantity; }
    public String getUpdatedAt() { return updatedAt; }
}
