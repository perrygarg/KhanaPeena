package com.perrygarg.khanapeena.home.model;

import com.google.gson.annotations.SerializedName;
import com.perrygarg.khanapeena.common.model.MasterResponse;

/**
 * Created by PerryGarg on 20-08-2017.
 */

public class TrainRouteResponse extends MasterResponse {

    @SerializedName("route")
    public TrainRoute trainRoute[];

}
