package com.lenewblack.wholesale.param;

import java.util.List;

public final class VariantUpdateParams {

    private final String reference;
    private final String colorCode;
    private final String colorName;
    private final List<String> sizes;
    private final List<String> imageUrls;

    private VariantUpdateParams(Builder builder) {
        this.reference = builder.reference;
        this.colorCode = builder.colorCode;
        this.colorName = builder.colorName;
        this.sizes = builder.sizes;
        this.imageUrls = builder.imageUrls;
    }

    public String getReference() { return reference; }
    public String getColorCode() { return colorCode; }
    public String getColorName() { return colorName; }
    public List<String> getSizes() { return sizes; }
    public List<String> getImageUrls() { return imageUrls; }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {
        private String reference;
        private String colorCode;
        private String colorName;
        private List<String> sizes;
        private List<String> imageUrls;

        public Builder setReference(String reference) { this.reference = reference; return this; }
        public Builder setColorCode(String colorCode) { this.colorCode = colorCode; return this; }
        public Builder setColorName(String colorName) { this.colorName = colorName; return this; }
        public Builder setSizes(List<String> sizes) { this.sizes = sizes; return this; }
        public Builder setImageUrls(List<String> imageUrls) { this.imageUrls = imageUrls; return this; }
        public VariantUpdateParams build() { return new VariantUpdateParams(this); }
    }
}
