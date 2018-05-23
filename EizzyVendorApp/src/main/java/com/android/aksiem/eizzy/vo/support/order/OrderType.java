package com.android.aksiem.eizzy.vo.support.order;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by pdubey on 09/04/18.
 */

public enum OrderType implements Serializable {

    @SerializedName("grocery")
    GROCERY("Grocery"),

    @SerializedName("food")
    FOOD("Food");

    private String orderType;

    OrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getOrderType() {
        return orderType;
    }

    @Override
    public String toString() {
        return "OrderType{" +
                "orderType='" + orderType + '\'' +
                '}';
    }
}
