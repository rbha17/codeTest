
package com.codingtest.codingtest.custom.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TransportResponse {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("fromcentral")
    @Expose
    private Fromcentral fromcentral;
    @SerializedName("location")
    @Expose
    private Location location;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Fromcentral getFromcentral() {
        return fromcentral;
    }

    public void setFromcentral(Fromcentral fromcentral) {
        this.fromcentral = fromcentral;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

}
