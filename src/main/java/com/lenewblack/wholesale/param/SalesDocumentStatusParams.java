package com.lenewblack.wholesale.param;

public final class SalesDocumentStatusParams {

    private final String status;

    private SalesDocumentStatusParams(Builder builder) {
        this.status = builder.status;
    }

    public String getStatus() { return status; }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {
        private String status;

        public Builder setStatus(String status) { this.status = status; return this; }
        public SalesDocumentStatusParams build() { return new SalesDocumentStatusParams(this); }
    }
}
