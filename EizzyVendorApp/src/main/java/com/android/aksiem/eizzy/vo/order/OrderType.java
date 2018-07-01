package com.android.aksiem.eizzy.vo.order;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public enum OrderType implements Serializable {

    @SerializedName("1")
    GENERAL_ORDER("General Order"),

    @SerializedName("2")
    CUSTOM_ORDER("Custom Order"),

    @SerializedName("3")
    BULK_ORDER("Bulk Order");

    private String orderTypeMessage;

    OrderType(String orderTypeMessage) {
        this.orderTypeMessage = orderTypeMessage;
    }
}
