package com.android.aksiem.eizzy.vo.support.order;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by pdubey on 09/04/18.
 */

public class PriceComponent implements Serializable {

    @SerializedName("componentName")
    @NonNull
    public final String componentName;

    @SerializedName("price")
    @NonNull
    public final double amount;

    @SerializedName("currency")
    @NonNull
    public final String currency;

    public PriceComponent(@NonNull String componentName, @NonNull double amount,
                          @NonNull String currency) {
        this.componentName = componentName;
        this.amount = amount;
        this.currency = currency;
    }

    public String getAmount() {
        return String.format("%s %.2f", currency, amount);
    }
}
