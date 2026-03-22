package com.lenewblack.wholesale.model.batch;

import com.google.gson.annotations.SerializedName;

public final class BatchResult<T> {

    @SerializedName("success")
    private boolean success;

    @SerializedName("data")
    private T data;

    @SerializedName("error")
    private String error;

    @SerializedName("index")
    private int index;

    public BatchResult() {}

    public boolean isSuccess() { return success; }
    public T getData() { return data; }
    public String getError() { return error; }
    public int getIndex() { return index; }
}
