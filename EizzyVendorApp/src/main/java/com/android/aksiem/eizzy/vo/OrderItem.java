package com.android.aksiem.eizzy.vo;

import android.arch.persistence.room.Entity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.android.aksiem.eizzy.vo.support.Actor;
import com.android.aksiem.eizzy.vo.support.Price;
import com.android.aksiem.eizzy.vo.support.order.OrderDetails;
import com.android.aksiem.eizzy.vo.support.order.OrderState;
import com.android.aksiem.eizzy.vo.support.order.OrderStateTransition;
import com.android.aksiem.eizzy.vo.support.order.OrderType;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by pdubey on 09/04/18.
 */

@Entity(primaryKeys = "orderId")
public class OrderItem implements Serializable, Timestamped {

    @SerializedName("orderId")
    @NonNull
    public final String orderId;

    @SerializedName("orderDetails")
    @NonNull
    public final OrderDetails orderDetails;

    @SerializedName("customer")
    @NonNull
    public final Actor customer;

    @SerializedName("price")
    @NonNull
    public final Price price;

    @SerializedName("timestamp")
    @NonNull
    public final long timestamp;

    @SerializedName("stringTimestamp")
    @NonNull
    public final String stringTimestamp;

    @SerializedName("orderType")
    @NonNull
    public final OrderType orderType;

    @SerializedName("currentOrderState")
    @NonNull
    private OrderState orderState;

    @SerializedName("orderTracking")
    @NonNull
    private List<OrderStateTransition> orderTracking;

    @SerializedName("deliveryAssociate")
    @Nullable
    private Actor deliveryAssociate;

    public OrderItem(@NonNull String orderId, @NonNull OrderDetails orderDetails,
                     @NonNull Actor customer, @NonNull Price price, @NonNull long timestamp,
                     @NonNull String stringTimestamp, @NonNull OrderType orderType,
                     @NonNull OrderState currentOrderState) {

        this.orderId = orderId;
        this.orderDetails = orderDetails;
        this.customer = customer;
        this.price = price;
        this.timestamp = timestamp;
        this.stringTimestamp = stringTimestamp;
        this.orderType = orderType;
        this.orderState = currentOrderState;
    }

    @NonNull
    public OrderState getOrderState() {
        return orderState;
    }

    public void setOrderState(@NonNull OrderState currentOrderState) {
        this.orderState = currentOrderState;
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

    @Override
    @NonNull
    public long getTimestamp() {
        return timestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrderItem orderItem = (OrderItem) o;

        return orderId.equals(orderItem.orderId);
    }

    @Override
    public int hashCode() {
        return orderId.hashCode();
    }
}
