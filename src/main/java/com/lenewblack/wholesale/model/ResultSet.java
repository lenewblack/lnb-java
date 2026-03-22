package com.lenewblack.wholesale.model;

import java.util.Collections;
import java.util.List;

/**
 * Generic paginated result container returned by all list/paginate operations.
 *
 * @param <T> the type of resource items
 */
public final class ResultSet<T> {

    private final List<T> data;
    private final ResultSetMetadata metadata;

    public ResultSet(List<T> data, ResultSetMetadata metadata) {
        this.data = data != null ? Collections.unmodifiableList(data) : Collections.emptyList();
        this.metadata = metadata;
    }

    public List<T> getData() { return data; }
    public ResultSetMetadata getMetadata() { return metadata; }

    public boolean hasMore() {
        return metadata != null && metadata.isHasMore();
    }
}
