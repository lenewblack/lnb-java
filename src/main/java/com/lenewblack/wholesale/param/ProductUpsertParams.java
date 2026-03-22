package com.lenewblack.wholesale.param;

import java.util.List;

public final class ProductUpsertParams {

    private final String id;
    private final String reference;
    private final String name;
    private final String description;
    private final String collectionId;
    private final List<String> fabricIds;
    private final String sizingId;

    private ProductUpsertParams(Builder builder) {
        this.id = builder.id;
        this.reference = builder.reference;
        this.name = builder.name;
        this.description = builder.description;
        this.collectionId = builder.collectionId;
        this.fabricIds = builder.fabricIds;
        this.sizingId = builder.sizingId;
    }

    public String getId() { return id; }
    public String getReference() { return reference; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public String getCollectionId() { return collectionId; }
    public List<String> getFabricIds() { return fabricIds; }
    public String getSizingId() { return sizingId; }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {
        private String id;
        private String reference;
        private String name;
        private String description;
        private String collectionId;
        private List<String> fabricIds;
        private String sizingId;

        public Builder setId(String id) { this.id = id; return this; }
        public Builder setReference(String reference) { this.reference = reference; return this; }
        public Builder setName(String name) { this.name = name; return this; }
        public Builder setDescription(String description) { this.description = description; return this; }
        public Builder setCollectionId(String collectionId) { this.collectionId = collectionId; return this; }
        public Builder setFabricIds(List<String> fabricIds) { this.fabricIds = fabricIds; return this; }
        public Builder setSizingId(String sizingId) { this.sizingId = sizingId; return this; }
        public ProductUpsertParams build() { return new ProductUpsertParams(this); }
    }
}
