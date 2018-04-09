package com.android.aksiem.eizzy.vo.support.order;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by pdubey on 09/04/18.
 */

public enum OrderState implements Serializable {

    @SerializedName("placed")
    PLACED,

    @SerializedName("confirmed")
    CONFIRMED,

    @SerializedName("assigned")
    ASSIGNED,

    @SerializedName("picked")
    PICKED,

    @SerializedName("delivered")
    DELIVERED,

    @SerializedName("undelivered")
    UNDELIVERED;

}
