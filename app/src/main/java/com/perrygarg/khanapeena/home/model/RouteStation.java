package com.perrygarg.khanapeena.home.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by PerryGarg on 03-12-2017.
 */

public class RouteStation {

    @SerializedName("lat")
    public float lat;

    @SerializedName("lng")
    public float lang;

    @SerializedName("code")
    public String stationCode;

    @SerializedName("name")
    public String stationFullName;

}
