package com.android.aksiem.eizzy.vo;

import android.arch.persistence.room.Entity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.android.aksiem.eizzy.vo.support.Actor;
import com.android.aksiem.eizzy.vo.support.order.OrderDetails;
import com.android.aksiem.eizzy.vo.support.order.OrderState;
import com.android.aksiem.eizzy.vo.support.order.OrderStateTransition;
import com.android.aksiem.eizzy.vo.support.order.OrderType;
import com.google.gson.annotations.SerializedName;

import java.util.Calendar;
import java.util.List;

/**
 * Created by pdubey on 09/04/18.
 */

@Entity(primaryKeys = "orderId")
public class Order {

    @SerializedName("orderId")
    @NonNull
    public final String orderId;

    @SerializedName("orderDetails")
    @NonNull
    public final OrderDetails orderDetails;

    @SerializedName("customer")
    @NonNull
    public final Actor customer;

    @SerializedName("amount")
    @NonNull
    public final Double amount;

    @SerializedName("timestamp")
    @NonNull
    public final Calendar timestamp;

    @SerializedName("stringTimestamp")
    @NonNull
    public final String stringTimestamp;

    @SerializedName("orderType")
    @NonNull
    public final OrderType orderType;

    @SerializedName("currentOrderState")
    @NonNull
    private OrderState currentOrderState;

    @SerializedName("orderTracking")
    @NonNull
    private List<OrderStateTransition> orderTracking;

    @SerializedName("deliveryAssociate")
    @Nullable
    private Actor deliveryAssociate;

    public Order(@NonNull String orderId, @NonNull OrderDetails orderDetails,
                 @NonNull Actor customer, @NonNull Double amount, @NonNull Calendar timestamp,
                 @NonNull String stringTimestamp, @NonNull OrderType orderType,
                 @NonNull OrderState currentOrderState) {

        this.orderId = orderId;
        this.orderDetails = orderDetails;
        this.customer = customer;
        this.amount = amount;
        this.timestamp = timestamp;
        this.stringTimestamp = stringTimestamp;
        this.orderType = orderType;
        this.currentOrderState = currentOrderState;
    }

    @NonNull
    public OrderState getCurrentOrderState() {
        return currentOrderState;
    }

    public void setCurrentOrderState(@NonNull OrderState currentOrderState) {
        this.currentOrderState = currentOrderState;
    }

    @NonNull
    public List<OrderStateTransition> getOrderTracking() {
        return orderTracking;
    }

    public void setOrderTracking(@NonNull List<OrderStateTransition> orderTracking) {
        this.orderTracking = orderTracking;
    }

    @Nullable
    public Actor getDeliveryAssociate() {
        return deliveryAssociate;
    }

    public void setDeliveryAssociate(@Nullable Actor deliveryAssociate) {
        this.deliveryAssociate = deliveryAssociate;
    }
}
