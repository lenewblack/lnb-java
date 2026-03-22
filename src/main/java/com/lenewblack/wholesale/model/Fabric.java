package com.lenewblack.wholesale.model;

import com.google.gson.annotations.SerializedName;

public final class Fabric {

    @SerializedName("id")
    private String id;

    @SerializedName("name")
    private String name;

    @SerializedName("composition")
    private String composition;

    @SerializedName("created_at")
    private String createdAt;

    @SerializedName("updated_at")
    private String updatedAt;

    public Fabric() {}

    public String getId() { return id; }
    public String getName() { return name; }
    public String getComposition() { return composition; }
    public String getCreatedAt() { return createdAt; }
    public String getUpdatedAt() { return updatedAt; }
}
