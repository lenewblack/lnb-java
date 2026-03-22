package com.lenewblack.wholesale.model;

import com.google.gson.annotations.SerializedName;

public final class Collection {

    @SerializedName("id")
    private String id;

    @SerializedName("name")
    private String name;

    @SerializedName("season")
    private String season;

    @SerializedName("year")
    private Integer year;

    @SerializedName("created_at")
    private String createdAt;

    @SerializedName("updated_at")
    private String updatedAt;

    public Collection() {}

    public String getId() { return id; }
    public String getName() { return name; }
    public String getSeason() { return season; }
    public Integer getYear() { return year; }
    public String getCreatedAt() { return createdAt; }
    public String getUpdatedAt() { return updatedAt; }
}
