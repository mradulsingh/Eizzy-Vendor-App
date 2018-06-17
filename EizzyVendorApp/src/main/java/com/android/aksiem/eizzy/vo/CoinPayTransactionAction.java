package com.android.aksiem.eizzy.vo;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CoinPayTransactionAction implements Serializable {

    @NonNull
    @SerializedName("transactionStatusMsg")
    public final String transactionStatusMessage;

    @NonNull
    @SerializedName("customerId")
    public final String customerId;

    @NonNull
    @SerializedName("timestamp")
    public final Long timestamp;

    @NonNull
    @SerializedName("isoDate")
    public final String isoDate;

    public CoinPayTransactionAction(@NonNull String transactionStatusMessage,
                                    @NonNull String customerId, @NonNull Long timestamp,
                                    @NonNull String isoDate) {

        this.transactionStatusMessage = transactionStatusMessage;
        this.customerId = customerId;
        this.timestamp = timestamp;
        this.isoDate = isoDate;
    }
}
