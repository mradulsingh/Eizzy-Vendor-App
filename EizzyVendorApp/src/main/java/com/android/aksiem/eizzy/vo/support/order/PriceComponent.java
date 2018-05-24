package com.android.aksiem.eizzy.vo.support.order;

import android.support.annotation.NonNull;

import com.android.aksiem.eizzy.vo.support.Price;
import com.android.aksiem.eizzy.vo.support.TitledAndSubtitled;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by pdubey on 09/04/18.
 */

public class PriceComponent implements Serializable, TitledAndSubtitled {

    @SerializedName("componentName")
    @NonNull
    public final String componentName;

    @SerializedName("price")
    @NonNull
    public final Price price;

    public PriceComponent(@NonNull String componentName, @NonNull Price price) {
        this.componentName = componentName;
        this.price = price;
    }

    public String getAmount() {
        return price.toString();
    }

    @Override
    public String getTitle() {
        return componentName;
    }

    @Override
    public String getSubtitle() {
        return price.toString();
    }

    @Override
    public String toString() {
        return "PriceComponent{" +
                "componentName='" + componentName + '\'' +
                ", price=" + price +
                '}';
    }

}
