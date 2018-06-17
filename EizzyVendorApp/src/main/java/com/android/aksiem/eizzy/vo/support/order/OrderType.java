package com.android.aksiem.eizzy.vo.support.order;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by pdubey on 09/04/18.
 */

public enum OrderType implements Serializable {

    @SerializedName("2")
    GROCERY(2),

    @SerializedName("1")
    FOOD(1);

    private Integer orderType;

    OrderType(int orderType) {
        this.orderType = orderType;
    }

    public String getOrderType() {
        switch (orderType) {
            case 1:
                return "Food";
            case 2:
                return "Grocery";
        }
        return "";
    }

    @Override
    public String toString() {
        return "OrderType{" +
                "orderType='" + orderType + '\'' +
                '}';
    }
}
