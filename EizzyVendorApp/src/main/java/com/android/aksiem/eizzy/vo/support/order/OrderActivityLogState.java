package com.android.aksiem.eizzy.vo.support.order;

import com.google.gson.annotations.SerializedName;

public enum OrderActivityLogState {

    @SerializedName("created")
    CREATED {

        @Override
        public OrderState getOrderState() {
            return OrderState.CONFIRMED;
        }
    };

    public abstract OrderState getOrderState();
}
