package com.android.aksiem.eizzy.vo.support.order;

import com.google.gson.annotations.SerializedName;

public enum OrderActivityLogState {

    @SerializedName("created")
    CREATED {

        @Override
        public OrderState getOrderState() {
            return OrderState.CONFIRMED;
        }
    },

    @SerializedName("accepted")
    ACCEPTED {
        @Override
        public OrderState getOrderState() {
            return OrderState.CONFIRMED;
        }
    },

    @SerializedName("onTheWay")
    ON_THE_WAY {

        @Override
        public OrderState getOrderState() {
            return OrderState.ASSIGNED;
        }
    },

    @SerializedName("arrived")
    ARRIVED {

        @Override
        public OrderState getOrderState() {
            return OrderState.ASSIGNED;
        }
    },

    @SerializedName("journeyStart")
    JOURNEY_START {

        @Override
        public OrderState getOrderState() {
            return OrderState.PICKED;
        }
    },

    @SerializedName("reached")
    REACHED {

        @Override
        public OrderState getOrderState() {
            return OrderState.PICKED;
        }
    },

    @SerializedName("completed")
    COMPLETED {

        @Override
        public OrderState getOrderState() {
            return OrderState.DELIVERED;
        }
    };

    public abstract OrderState getOrderState();

    @Override
    public String toString() {
        return "OrderActivityLogState{ "
                + "state = " + getOrderState() + "}";
    }
}
