package com.perrygarg.khanapeena.home.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by PerryGarg on 03-09-2017.
 */

public class TrainRoute {

    @SerializedName("lat")
    public float lat;

    @SerializedName("lng")
    public float lang;

    @SerializedName("day")
    public int day;

    @SerializedName("code")
    public String stationCode;

    @SerializedName("fullname")
    public String stationFullName;

    @SerializedName("scharr")
    public String schArrival;

    @SerializedName("schdep")
    public String schDeparture;

}
