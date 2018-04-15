package com.android.aksiem.eizzy.vo.support;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by pdubey on 14/04/18.
 */

public class Price implements Serializable {

    @SerializedName("amount")
    @NonNull
    public final float amount;

    @SerializedName("currency")
    @NonNull
    public final String currency;

    public Price(@NonNull float amount, @NonNull String currency) {
        this.amount = amount;
        this.currency = currency;
    }

    @Override
    public String toString() {
        return String.format("%s %.2f", currency, amount);
    }
}
