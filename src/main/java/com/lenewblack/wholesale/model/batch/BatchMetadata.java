package com.lenewblack.wholesale.model.batch;

import com.google.gson.annotations.SerializedName;

public final class BatchMetadata {

    @SerializedName("total")
    private int total;

    @SerializedName("succeeded")
    private int succeeded;

    @SerializedName("failed")
    private int failed;

    public BatchMetadata() {}

    public BatchMetadata(int total, int succeeded, int failed) {
        this.total = total;
        this.succeeded = succeeded;
        this.failed = failed;
    }

    public int getTotal() { return total; }
    public int getSucceeded() { return succeeded; }
    public int getFailed() { return failed; }
}
