package com.perrygarg.khanapeena.home.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by PerryGarg on 11-09-2017.
 */

public class CurrentStation {

    @SerializedName("status")
    public String status;

    @SerializedName("actarr")
    public String actualArrivalTime;

    @SerializedName("scharr")
    public String scheduledArrivalTime;

    @SerializedName("actdep")
    public String actualDepartureTime;

    @SerializedName("schdep")
    public String scheduledDepartureTime;

    @SerializedName("scharr_date")
    public String scheduledArrivalDate;

    @SerializedName("actarr_date")
    public String actualArrivalDate;

    @SerializedName("station")
    public Station station;

    @SerializedName("day")
    public int day;

}
