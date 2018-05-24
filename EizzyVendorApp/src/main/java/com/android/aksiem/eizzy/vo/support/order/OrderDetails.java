package com.android.aksiem.eizzy.vo.support.order;

import android.support.annotation.NonNull;

import com.android.aksiem.eizzy.vo.support.TitlizedList;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by pdubey on 09/04/18.
 */

public class OrderDetails implements Serializable {

    @SerializedName("items")
    @NonNull
    public final TitlizedList<OrderedItem> items;

    @SerializedName("priceComponents")
    @NonNull
    public final TitlizedList<PriceComponent> priceComponents;

    @SerializedName("total")
    @NonNull
    public final PriceComponent total;

    @SerializedName("paymentMethod")
    @NonNull
    public final String paymentMethod;

    public OrderDetails(@NonNull TitlizedList<OrderedItem> items,
                        @NonNull TitlizedList<PriceComponent> priceComponents,
                        @NonNull PriceComponent total, @NonNull String paymentMethod) {

        this.items = items;
        this.priceComponents = priceComponents;
        this.total = total;
        this.paymentMethod = paymentMethod;
    }

    @Override
    public String toString() {
        return "OrderDetails{" +
                "items=" + items +
                ", priceComponents=" + priceComponents +
                ", total=" + total +
                ", paymentMethod='" + paymentMethod + '\'' +
                '}';
    }
}
