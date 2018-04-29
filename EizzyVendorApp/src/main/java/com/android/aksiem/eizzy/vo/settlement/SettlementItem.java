package com.android.aksiem.eizzy.vo.settlement;

import android.arch.persistence.room.Entity;
import android.support.annotation.NonNull;

import com.android.aksiem.eizzy.vo.support.order.OrderDetails;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;


@Entity(primaryKeys = "transactionId", tableName = "settlement_item")
public class SettlementItem implements Serializable {

    @SerializedName("transactionId")
    @NonNull
    public final String transactionId;

    @SerializedName("orderList")
    @NonNull
    public final List<OrderDetails> orderList;

    @SerializedName("timestamp")
    @NonNull
    public final long timestamp;

    @SerializedName("paymentId")
    @NonNull
    public final String paymentId;

    @SerializedName("stringTimestamp")
    @NonNull
    public final String stringTimestamp;


    @SerializedName("transactionStatus")
    @NonNull
    private TransactionStatus transactionStatus;


    public SettlementItem(@NonNull String transactionId, @NonNull List<OrderDetails> orderList,
                          @NonNull long timestamp, @NonNull String paymentId,
                          @NonNull String stringTimestamp, @NonNull TransactionStatus transactionStatus) {
        this.transactionId = transactionId;
        this.orderList = orderList;
        this.timestamp = timestamp;
        this.paymentId = paymentId;
        this.stringTimestamp = stringTimestamp;
        this.transactionStatus = transactionStatus;
    }

    @NonNull
    public String getTransactionId() {
        return transactionId;
    }

    @NonNull
    public List<OrderDetails> getOrderList() {
        return orderList;
    }

    @NonNull
    public long getTimestamp() {
        return timestamp;
    }

    @NonNull
    public String getPaymentId() {
        return paymentId;
    }

    @NonNull
    public String getStringTimestamp() {
        return stringTimestamp;
    }

    @NonNull
    public TransactionStatus getTransactionStatus() {
        return transactionStatus;
    }

    public void setTransactionStatus(@NonNull TransactionStatus transactionStatus) {
        this.transactionStatus = transactionStatus;
    }
}
