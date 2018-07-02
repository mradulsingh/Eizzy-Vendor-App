package com.android.aksiem.eizzy.vo.order;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class OrderInfo implements Serializable {

    @SerializedName("weight")
    public final String weight;

    @SerializedName("numberOfItems")
    public final String numberOfItems;

    @SerializedName("details")
    public final String details;

    @NonNull
    @SerializedName("Items")
    public ArrayList<OrderedItem> items;

    public OrderInfo(String weight, String numberOfItems, String details) {
        this.weight = weight;
        this.numberOfItems = numberOfItems;
        this.details = details;
    }

    @NonNull
    public ArrayList<OrderedItem> getItems() {
        return items;
    }

    public void setItems(@NonNull ArrayList<OrderedItem> items) {
        this.items = items;
    }
}
