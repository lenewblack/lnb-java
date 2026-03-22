package com.lenewblack.wholesale.param;

public final class InventoryUpdateParams {

    private final String variantId;
    private final String sizeCode;
    private final int quantity;

    private InventoryUpdateParams(Builder builder) {
        this.variantId = builder.variantId;
        this.sizeCode = builder.sizeCode;
        this.quantity = builder.quantity;
    }

    public String getVariantId() { return variantId; }
    public String getSizeCode() { return sizeCode; }
    public int getQuantity() { return quantity; }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {
        private String variantId;
        private String sizeCode;
        private int quantity;

        public Builder setVariantId(String variantId) { this.variantId = variantId; return this; }
        public Builder setSizeCode(String sizeCode) { this.sizeCode = sizeCode; return this; }
        public Builder setQuantity(int quantity) { this.quantity = quantity; return this; }
        public InventoryUpdateParams build() { return new InventoryUpdateParams(this); }
    }
}
