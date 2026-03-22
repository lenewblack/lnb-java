package com.lenewblack.wholesale.param;

import java.util.List;

public final class SelectionCreateParams {

    private final String retailerId;
    private final List<String> productIds;

    private SelectionCreateParams(Builder builder) {
        this.retailerId = builder.retailerId;
        this.productIds = builder.productIds;
    }

    public String getRetailerId() { return retailerId; }
    public List<String> getProductIds() { return productIds; }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {
        private String retailerId;
        private List<String> productIds;

        public Builder setRetailerId(String retailerId) { this.retailerId = retailerId; return this; }
        public Builder setProductIds(List<String> productIds) { this.productIds = productIds; return this; }
        public SelectionCreateParams build() { return new SelectionCreateParams(this); }
    }
}
