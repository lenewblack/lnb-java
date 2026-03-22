package com.lenewblack.wholesale.model.batch;

import com.google.gson.annotations.SerializedName;

import java.util.Collections;
import java.util.List;

public final class BatchResponse<T> {

    @SerializedName("results")
    private List<BatchResult<T>> results;

    @SerializedName("metadata")
    private BatchMetadata metadata;

    public BatchResponse() {}

    public List<BatchResult<T>> getResults() {
        return results != null ? results : Collections.emptyList();
    }

    public BatchMetadata getMetadata() { return metadata; }

    public int getSuccessCount() {
        return metadata != null ? metadata.getSucceeded() : 0;
    }

    public int getErrorCount() {
        return metadata != null ? metadata.getFailed() : 0;
    }
}
