package com.lenewblack.wholesale.param;

import java.util.LinkedHashMap;
import java.util.Map;

public final class ProductListParams {

    private final Integer page;
    private final Integer pageSize;
    private final String collectionId;
    private final String updatedAfter;
    private final String updatedBefore;

    private ProductListParams(Builder builder) {
        this.page = builder.page;
        this.pageSize = builder.pageSize;
        this.collectionId = builder.collectionId;
        this.updatedAfter = builder.updatedAfter;
        this.updatedBefore = builder.updatedBefore;
    }

    public Map<String, String> toQueryParams() {
        Map<String, String> params = new LinkedHashMap<>();
        if (page != null) params.put("page", String.valueOf(page));
        if (pageSize != null) params.put("page_size", String.valueOf(pageSize));
        if (collectionId != null) params.put("collection_id", collectionId);
        if (updatedAfter != null) params.put("updated_after", updatedAfter);
        if (updatedBefore != null) params.put("updated_before", updatedBefore);
        return params;
    }

    public ProductListParams withPage(int page) {
        return toBuilder().setPage(page).build();
    }

    public Builder toBuilder() {
        Builder b = new Builder();
        b.page = this.page;
        b.pageSize = this.pageSize;
        b.collectionId = this.collectionId;
        b.updatedAfter = this.updatedAfter;
        b.updatedBefore = this.updatedBefore;
        return b;
    }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {
        private Integer page;
        private Integer pageSize;
        private String collectionId;
        private String updatedAfter;
        private String updatedBefore;

        public Builder setPage(int page) { this.page = page; return this; }
        public Builder setPageSize(int pageSize) { this.pageSize = pageSize; return this; }
        public Builder setCollectionId(String collectionId) { this.collectionId = collectionId; return this; }
        public Builder setUpdatedAfter(String updatedAfter) { this.updatedAfter = updatedAfter; return this; }
        public Builder setUpdatedBefore(String updatedBefore) { this.updatedBefore = updatedBefore; return this; }
        public ProductListParams build() { return new ProductListParams(this); }
    }
}
