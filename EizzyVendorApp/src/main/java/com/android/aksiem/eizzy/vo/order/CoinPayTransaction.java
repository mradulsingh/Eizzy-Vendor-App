package com.android.aksiem.eizzy.vo.order;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class CoinPayTransaction implements Serializable {

    @NonNull
    @SerializedName("transactionId")
    public final String transactionId;

    @NonNull
    @SerializedName("transactionAddress")
    public final String transactionAddress;

    @NonNull
    @SerializedName("transactionStatusMsg")
    public final String transactionStatusMessage;

    @NonNull
    @SerializedName("transactionQrUrl")
    public final String transactionQrUrl;

    @NonNull
    @SerializedName("actions")
    private ArrayList<CoinPayTransactionAction> actions;

    public CoinPayTransaction(@NonNull String transactionId, @NonNull String transactionAddress,
                              @NonNull String transactionStatusMessage,
                              @NonNull String transactionQrUrl) {

        this.transactionId = transactionId;
        this.transactionAddress = transactionAddress;
        this.transactionStatusMessage = transactionStatusMessage;
        this.transactionQrUrl = transactionQrUrl;
    }

    @NonNull
    public ArrayList<CoinPayTransactionAction> getActions() {
        return actions;
    }

    public void setActions(@NonNull ArrayList<CoinPayTransactionAction> actions) {
        this.actions = actions;
    }
}
