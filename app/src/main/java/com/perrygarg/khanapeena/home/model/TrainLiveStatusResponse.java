package com.perrygarg.khanapeena.home.model;

import com.google.gson.annotations.SerializedName;
import com.perrygarg.khanapeena.common.model.MasterResponse;

/**
 * Created by PerryGarg on 20-08-2017.
 */

public class TrainLiveStatusResponse extends MasterResponse {

    @SerializedName("start_date")
    public String startDate;

    @SerializedName("position")
    public String trainPosition;

    @SerializedName("current_station")
    public CurrentStation currentStation;

    @SerializedName("route")
    public CurrentStation liveRoute[];

}
