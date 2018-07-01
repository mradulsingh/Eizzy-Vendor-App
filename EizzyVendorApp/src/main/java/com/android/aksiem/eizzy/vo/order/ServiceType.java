package com.android.aksiem.eizzy.vo.order;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by pdubey on 09/04/18.
 */

public enum ServiceType implements Serializable {

    @SerializedName("0")
    FOOD(0),

    @SerializedName("1")
    GROCERY(1),

    @SerializedName("2")
    DAILY_NEEDS(2),

    @SerializedName("3")
    ONLINE(3);

    private Integer orderType;

    ServiceType(int orderType) {
        this.orderType = orderType;
    }

    public String getOrderType() {
        switch (orderType) {
            case 0:
                return "Food";
            case 1:
                return "Grocery";
            case 2:
                return "Daily Needs";
            case 3:
                return "Online";
        }
        return "";
    }

    @Override
    public String toString() {
        return "ServiceType{" +
                "serviceType='" + orderType + '\'' +
                '}';
    }
}
