package com.android.aksiem.eizzy.vo.support.order;

import android.support.annotation.NonNull;

import com.android.aksiem.eizzy.vo.OrderItem;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class OrderItemsList implements Serializable {

    @NonNull
    @SerializedName("ordersArray")
    public final ArrayList<OrderItem> items;

    public OrderItemsList(@NonNull ArrayList<OrderItem> items) {
        this.items = items;
    }
}
