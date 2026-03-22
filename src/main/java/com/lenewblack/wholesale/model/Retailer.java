package com.lenewblack.wholesale.model;

import com.google.gson.annotations.SerializedName;

public final class Retailer {

    @SerializedName("id")
    private String id;

    @SerializedName("name")
    private String name;

    @SerializedName("email")
    private String email;

    @SerializedName("country")
    private String country;

    @SerializedName("city")
    private String city;

    @SerializedName("created_at")
    private String createdAt;

    @SerializedName("updated_at")
    private String updatedAt;

    public Retailer() {}

    public String getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getCountry() { return country; }
    public String getCity() { return city; }
    public String getCreatedAt() { return createdAt; }
    public String getUpdatedAt() { return updatedAt; }
}
