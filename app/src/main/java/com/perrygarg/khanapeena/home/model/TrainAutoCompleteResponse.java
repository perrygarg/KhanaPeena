package com.perrygarg.khanapeena.home.model;

import com.google.gson.annotations.SerializedName;
import com.perrygarg.khanapeena.common.model.MasterResponse;

/**
 * Created by PerryGarg on 20-08-2017.
 */

public class TrainAutoCompleteResponse extends MasterResponse {

    @SerializedName("total")
    public int total;

    @SerializedName("trains")
    public Train train[];

    @SerializedName("debit")
    public int debit;

}
