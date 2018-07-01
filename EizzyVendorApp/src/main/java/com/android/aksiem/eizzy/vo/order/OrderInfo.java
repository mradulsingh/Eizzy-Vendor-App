package com.android.aksiem.eizzy.vo.order;

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

    @SerializedName("Items")
    public final ArrayList<OrderedItem> items;

    public OrderInfo(String weight, String numberOfItems, String details,
                     ArrayList<OrderedItem> items) {
        this.weight = weight;
        this.numberOfItems = numberOfItems;
        this.details = details;
        this.items = items;
    }
}
