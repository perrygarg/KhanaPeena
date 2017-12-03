package com.perrygarg.khanapeena.home.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by PerryGarg on 03-09-2017.
 */

public class TrainRoute {

    @SerializedName("station")
    public RouteStation routeStation;

    @SerializedName("day")
    public int day;

    @SerializedName("scharr")
    public String schArrival;

    @SerializedName("schdep")
    public String schDeparture;

}
