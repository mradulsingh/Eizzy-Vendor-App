package com.android.aksiem.eizzy.vo.support.order;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by pdubey on 09/04/18.
 */

public class OrderedItem implements Serializable {

    @SerializedName("item")
    @NonNull
    public final String item;

    @SerializedName("quantity")
    @NonNull
    public final double quantity;

    @SerializedName("unit")
    @NonNull
    public final String unit;

    @SerializedName("unitQuantity")
    @NonNull
    public final double unitQuantity;

    @SerializedName("unitPrice")
    @NonNull
    public final double unitPrice;

    @SerializedName("totalPrice")
    @NonNull
    public final double totalPrice;

    @SerializedName("currency")
    @NonNull
    public final String currency;

    public OrderedItem(@NonNull String item, @NonNull double quantity, @NonNull String unit,
                       @NonNull double unitQuantity, @NonNull double unitPrice,
                       @NonNull double totalPrice, @NonNull String currency) {

        this.item = item;
        this.quantity = quantity;
        this.unit = unit;
        this.unitQuantity = unitQuantity;
        this.unitPrice = unitPrice;
        this.totalPrice = totalPrice;
        this.currency = currency;
    }

    public String getTotalPrice() {
        return String.format("%s %.2f", currency, totalPrice);
    }
}
