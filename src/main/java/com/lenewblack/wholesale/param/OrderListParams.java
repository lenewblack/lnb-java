package com.lenewblack.wholesale.param;

import java.util.LinkedHashMap;
import java.util.Map;

public final class OrderListParams {

    private final Integer page;
    private final Integer pageSize;
    private final String retailerId;
    private final String status;
    private final String orderTimeFrom;
    private final String orderTimeTo;
    private final String confirmationTimeFrom;
    private final String confirmationTimeTo;
    private final String updateTimeFrom;
    private final String updateTimeTo;

    private OrderListParams(Builder builder) {
        this.page = builder.page;
        this.pageSize = builder.pageSize;
        this.retailerId = builder.retailerId;
        this.status = builder.status;
        this.orderTimeFrom = builder.orderTimeFrom;
        this.orderTimeTo = builder.orderTimeTo;
        this.confirmationTimeFrom = builder.confirmationTimeFrom;
        this.confirmationTimeTo = builder.confirmationTimeTo;
        this.updateTimeFrom = builder.updateTimeFrom;
        this.updateTimeTo = builder.updateTimeTo;
    }

    public Map<String, String> toQueryParams() {
        Map<String, String> params = new LinkedHashMap<>();
        if (page != null) params.put("page", String.valueOf(page));
        if (pageSize != null) params.put("page_size", String.valueOf(pageSize));
        if (retailerId != null) params.put("retailer_id", retailerId);
        if (status != null) params.put("status", status);
        if (orderTimeFrom != null) params.put("order_time_from", orderTimeFrom);
        if (orderTimeTo != null) params.put("order_time_to", orderTimeTo);
        if (confirmationTimeFrom != null) params.put("confirmation_time_from", confirmationTimeFrom);
        if (confirmationTimeTo != null) params.put("confirmation_time_to", confirmationTimeTo);
        if (updateTimeFrom != null) params.put("update_time_from", updateTimeFrom);
        if (updateTimeTo != null) params.put("update_time_to", updateTimeTo);
        return params;
    }

    public OrderListParams withPage(int page) {
        return toBuilder().setPage(page).build();
    }

    public Builder toBuilder() {
        Builder b = new Builder();
        b.page = this.page;
        b.pageSize = this.pageSize;
        b.retailerId = this.retailerId;
        b.status = this.status;
        b.orderTimeFrom = this.orderTimeFrom;
        b.orderTimeTo = this.orderTimeTo;
        b.confirmationTimeFrom = this.confirmationTimeFrom;
        b.confirmationTimeTo = this.confirmationTimeTo;
        b.updateTimeFrom = this.updateTimeFrom;
        b.updateTimeTo = this.updateTimeTo;
        return b;
    }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {
        private Integer page;
        private Integer pageSize;
        private String retailerId;
        private String status;
        private String orderTimeFrom;
        private String orderTimeTo;
        private String confirmationTimeFrom;
        private String confirmationTimeTo;
        private String updateTimeFrom;
        private String updateTimeTo;

        public Builder setPage(int page) { this.page = page; return this; }
        public Builder setPageSize(int pageSize) { this.pageSize = pageSize; return this; }
        public Builder setRetailerId(String retailerId) { this.retailerId = retailerId; return this; }
        public Builder setStatus(String status) { this.status = status; return this; }
        public Builder setOrderTimeFrom(String from) { this.orderTimeFrom = from; return this; }
        public Builder setOrderTimeTo(String to) { this.orderTimeTo = to; return this; }
        public Builder setConfirmationTimeFrom(String from) { this.confirmationTimeFrom = from; return this; }
        public Builder setConfirmationTimeTo(String to) { this.confirmationTimeTo = to; return this; }
        public Builder setUpdateTimeFrom(String from) { this.updateTimeFrom = from; return this; }
        public Builder setUpdateTimeTo(String to) { this.updateTimeTo = to; return this; }
        public OrderListParams build() { return new OrderListParams(this); }
    }
}
