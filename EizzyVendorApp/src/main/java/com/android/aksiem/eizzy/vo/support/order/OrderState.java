package com.android.aksiem.eizzy.vo.support.order;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by pdubey on 09/04/18.
 */

public enum OrderState implements Serializable {

    @SerializedName("placed")
    PLACED("placed"),

    @SerializedName("confirmed")
    CONFIRMED("confirmed"),

    @SerializedName("assigned")
    ASSIGNED("assigned"),

    @SerializedName("picked")
    PICKED("picked"),

    @SerializedName("delivered")
    DELIVERED("delivered"),

    @SerializedName("undelivered")
    UNDELIVERED("undelivered");

    private String state;

    OrderState(String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }
}
