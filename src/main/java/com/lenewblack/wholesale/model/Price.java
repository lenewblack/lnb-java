package com.lenewblack.wholesale.model;

import com.google.gson.annotations.SerializedName;

public final class Price {

    @SerializedName("variant_id")
    private String variantId;

    @SerializedName("currency")
    private String currency;

    @SerializedName("wholesale")
    private double wholesale;

    @SerializedName("retail")
    private double retail;

    @SerializedName("updated_at")
    private String updatedAt;

    public Price() {}

    public String getVariantId() { return variantId; }
    public String getCurrency() { return currency; }
    public double getWholesale() { return wholesale; }
    public double getRetail() { return retail; }
    public String getUpdatedAt() { return updatedAt; }
}
