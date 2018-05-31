package com.android.aksiem.eizzy.vo.support.order;

import com.android.aksiem.eizzy.R;
import com.android.aksiem.eizzy.app.AppResourceManager;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by pdubey on 09/04/18.
 */

public enum OrderState implements Serializable {

    @SerializedName("placed")
    PLACED("placed") {
        @Override
        public int getTheme(AppResourceManager resourceManager) {
            return resourceManager.getColor(R.color.orderPlaced);
        }
    },

    @SerializedName("confirmed")
    CONFIRMED("confirmed") {
        @Override
        public int getTheme(AppResourceManager resourceManager) {
            return resourceManager.getColor(R.color.orderConfirmed);
        }
    },

    @SerializedName("assigned")
    ASSIGNED("assigned") {
        @Override
        public int getTheme(AppResourceManager resourceManager) {
            return resourceManager.getColor(R.color.orderAssigned);
        }
    },

    @SerializedName("picked")
    PICKED("picked") {
        @Override
        public int getTheme(AppResourceManager resourceManager) {
            return resourceManager.getColor(R.color.orderPicked);
        }
    },

    @SerializedName("delivered")
    DELIVERED("delivered") {
        @Override
        public int getTheme(AppResourceManager resourceManager) {
            return resourceManager.getColor(R.color.orderDelivered);
        }
    },

    @SerializedName("undelivered")
    UNDELIVERED("undelivered") {
        @Override
        public int getTheme(AppResourceManager resourceManager) {
            return resourceManager.getColor(R.color.orderUndelivered);
        }
    };

    private String state;

    OrderState(String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }

    public abstract int getTheme(AppResourceManager resourceManager);

    @Override
    public String toString() {
        return "OrderState{" +
                "state='" + state + '\'' +
                '}';
    }
}
