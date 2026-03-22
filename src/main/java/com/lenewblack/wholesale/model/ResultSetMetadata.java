package com.lenewblack.wholesale.model;

/**
 * Pagination metadata derived from X-Pagination-* response headers.
 */
public final class ResultSetMetadata {

    private final int page;
    private final int pageSize;
    private final boolean hasMore;
    private final int totalPages;
    private final int totalItems;

    public ResultSetMetadata(int page, int pageSize, boolean hasMore, int totalPages, int totalItems) {
        this.page = page;
        this.pageSize = pageSize;
        this.hasMore = hasMore;
        this.totalPages = totalPages;
        this.totalItems = totalItems;
    }

    public int getPage() { return page; }
    public int getPageSize() { return pageSize; }
    public boolean isHasMore() { return hasMore; }
    public int getTotalPages() { return totalPages; }
    public int getTotalItems() { return totalItems; }

    @Override
    public String toString() {
        return "ResultSetMetadata{page=" + page + ", pageSize=" + pageSize
                + ", hasMore=" + hasMore + ", totalPages=" + totalPages
                + ", totalItems=" + totalItems + '}';
    }
}
