package com.lenewblack.wholesale.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public final class Sizing {

    @SerializedName("id")
    private String id;

    @SerializedName("name")
    private String name;

    @SerializedName("sizes")
    private List<String> sizes;

    @SerializedName("created_at")
    private String createdAt;

    @SerializedName("updated_at")
    private String updatedAt;

    public Sizing() {}

    public String getId() { return id; }
    public String getName() { return name; }
    public List<String> getSizes() { return sizes; }
    public String getCreatedAt() { return createdAt; }
    public String getUpdatedAt() { return updatedAt; }
}
