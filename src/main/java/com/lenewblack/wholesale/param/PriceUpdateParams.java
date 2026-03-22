package com.lenewblack.wholesale.param;

public final class PriceUpdateParams {

    private final String variantId;
    private final String currency;
    private final Double wholesale;
    private final Double retail;

    private PriceUpdateParams(Builder builder) {
        this.variantId = builder.variantId;
        this.currency = builder.currency;
        this.wholesale = builder.wholesale;
        this.retail = builder.retail;
    }

    public String getVariantId() { return variantId; }
    public String getCurrency() { return currency; }
    public Double getWholesale() { return wholesale; }
    public Double getRetail() { return retail; }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {
        private String variantId;
        private String currency;
        private Double wholesale;
        private Double retail;

        public Builder setVariantId(String variantId) { this.variantId = variantId; return this; }
        public Builder setCurrency(String currency) { this.currency = currency; return this; }
        public Builder setWholesale(double wholesale) { this.wholesale = wholesale; return this; }
        public Builder setRetail(double retail) { this.retail = retail; return this; }
        public PriceUpdateParams build() { return new PriceUpdateParams(this); }
    }
}
