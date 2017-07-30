package com.perrygarg.khanapeena.common.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by perry.garg on 02/02/17.
 */

public class ValidationError {
    @SerializedName("param")
    public String param = null;

    @SerializedName("msg")
    public String msg = null;

    @SerializedName("value")
    public String value = null;

    @SerializedName("code")
    public String code = null;
}
