package com.android.aksiem.eizzy.vo.order;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class PaymentBreakupByMode implements Serializable {

    @NonNull
    @SerializedName("card")
    public final Float card;

    @NonNull
    @SerializedName("cash")
    public final Float cash;

    @NonNull
    @SerializedName("wallet")
    public final Float wallet;

    public PaymentBreakupByMode(@NonNull Float card, @NonNull Float cash, @NonNull Float wallet) {
        this.card = card;
        this.cash = cash;
        this.wallet = wallet;
    }
}
