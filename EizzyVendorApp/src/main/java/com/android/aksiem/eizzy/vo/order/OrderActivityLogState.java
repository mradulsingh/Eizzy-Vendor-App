package com.android.aksiem.eizzy.vo.order;

import com.google.gson.annotations.SerializedName;

public enum OrderActivityLogState {

    @SerializedName("created")
    CREATED("Created") {

        @Override
        public OrderState getOrderState() {
            return OrderState.CONFIRMED;
        }
    },

    @SerializedName("accepted")
    ACCEPTED("Accepted") {
        @Override
        public OrderState getOrderState() {
            return OrderState.CONFIRMED;
        }
    },

    @SerializedName("onTheWay")
    ON_THE_WAY("On The Way") {

        @Override
        public OrderState getOrderState() {
            return OrderState.ASSIGNED;
        }
    },

    @SerializedName("arrived")
    ARRIVED("Arrived") {

        @Override
        public OrderState getOrderState() {
            return OrderState.ASSIGNED;
        }
    },

    @SerializedName("journeyStart")
    JOURNEY_START("Journey Started") {

        @Override
        public OrderState getOrderState() {
            return OrderState.PICKED;
        }
    },

    @SerializedName("reached")
    REACHED("Reached") {

        @Override
        public OrderState getOrderState() {
            return OrderState.PICKED;
        }
    },

    @SerializedName("completed")
    COMPLETED("Completed") {

        @Override
        public OrderState getOrderState() {
            return OrderState.DELIVERED;
        }
    };

    public abstract OrderState getOrderState();

    private String stateName;

    OrderActivityLogState(String stateName) {
        this.stateName = stateName;
    }

    public String getStateName() {
        return stateName;
    }

    @Override
    public String toString() {
        return "OrderActivityLogState{ "
                + "state = " + getOrderState() + "}";
    }
}
