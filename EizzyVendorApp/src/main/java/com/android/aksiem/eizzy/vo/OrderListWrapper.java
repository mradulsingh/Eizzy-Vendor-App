package com.android.aksiem.eizzy.vo;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class OrderListWrapper implements Serializable {

    @NonNull
    @SerializedName("ordersArray")
    public final ArrayList<OrderListItem> items;

    public OrderListWrapper(@NonNull ArrayList<OrderListItem> items) {
        this.items = items;
    }
}
