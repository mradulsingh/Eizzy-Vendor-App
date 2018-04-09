package com.android.aksiem.eizzy.vo.support.order;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by pdubey on 09/04/18.
 */

public class OrderStateTransition implements Serializable {

    @SerializedName("orderState")
    public final OrderState orderState;

    @SerializedName("stringDate")
    public final String stringDate;

    @SerializedName("stringTime")
    public final String stringTime;

    @SerializedName("stringLocation")
    public final String stringLocation;

    @SerializedName("detail")
    private String detail;

    @SerializedName("completed")
    private boolean completed;

    public OrderStateTransition(OrderState orderState, String stringDate, String stringTime,
                                String stringLocation) {

        this.orderState = orderState;
        this.stringDate = stringDate;
        this.stringTime = stringTime;
        this.stringLocation = stringLocation;
        this.completed = false;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}
