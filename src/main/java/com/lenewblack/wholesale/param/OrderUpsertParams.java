package com.lenewblack.wholesale.param;

import java.util.List;
import java.util.Map;

public final class OrderUpsertParams {

    private final String id;
    private final String reference;
    private final String retailerId;
    private final String status;
    private final List<Map<String, Object>> lines;

    private OrderUpsertParams(Builder builder) {
        this.id = builder.id;
        this.reference = builder.reference;
        this.retailerId = builder.retailerId;
        this.status = builder.status;
        this.lines = builder.lines;
    }

    public String getId() { return id; }
    public String getReference() { return reference; }
    public String getRetailerId() { return retailerId; }
    public String getStatus() { return status; }
    public List<Map<String, Object>> getLines() { return lines; }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {
        private String id;
        private String reference;
        private String retailerId;
        private String status;
        private List<Map<String, Object>> lines;

        public Builder setId(String id) { this.id = id; return this; }
        public Builder setReference(String reference) { this.reference = reference; return this; }
        public Builder setRetailerId(String retailerId) { this.retailerId = retailerId; return this; }
        public Builder setStatus(String status) { this.status = status; return this; }
        public Builder setLines(List<Map<String, Object>> lines) { this.lines = lines; return this; }
        public OrderUpsertParams build() { return new OrderUpsertParams(this); }
    }
}
