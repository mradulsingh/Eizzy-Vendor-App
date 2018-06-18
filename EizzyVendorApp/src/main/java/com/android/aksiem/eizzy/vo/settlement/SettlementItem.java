package com.android.aksiem.eizzy.vo.settlement;

import android.arch.persistence.room.Entity;
import android.os.Build;
import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Objects;


@Entity(primaryKeys = "transactionId", tableName = "settlement_item")
public class SettlementItem implements Serializable {

    @SerializedName("_id")
    @NonNull
    public final String id;

    @SerializedName("txnId")
    @NonNull
    public final String transactionId;

    @SerializedName("userId")
    @NonNull
    public final String userId;

    @SerializedName("txnType")
    @NonNull
    public final String txnType;

    @SerializedName("trigger")
    public final String trigger;

    @SerializedName("comment")
    public final String comment;

    @SerializedName("cityId")
    public final String cityId;

    @SerializedName("currency")
    @NonNull
    public final String currency;

    @SerializedName("currencyAbbr")
    public final String currencyAbbr;

    @SerializedName("currencySymbol")
    public final String currencySymbol;

    @SerializedName("orderId")
    @NonNull
    public final String orderId;

    @SerializedName("paymentTypeText")
    @NonNull
    public final String paymentTypeText;

    @SerializedName("cityName")
    public final String cityName;

    @SerializedName("serviceTypeText")
    @NonNull
    public final String serviceTypeText;

    @SerializedName("paymentTxnId")
    @NonNull
    public final String paymentTxnId;

    @SerializedName("intiatedBy")
    @NonNull
    public final String intiatedBy;

    @SerializedName("openingBal")
    public final double openingBal;

    @SerializedName("amount")
    public final double amount;

    @SerializedName("closingBal")
    public final double closingBal;

    @SerializedName("cashCollected")
    public final double cashCollected;

    @SerializedName("timestamp")
    public final long timestamp;

    public SettlementItem(@NonNull String id, @NonNull String transactionId, @NonNull String userId,
                          @NonNull String txnType, String trigger, String comment,
                          String cityId, @NonNull String currency, String currencyAbbr,
                          String currencySymbol, @NonNull String orderId, @NonNull String paymentTypeText,
                          String cityName, @NonNull String serviceTypeText, String paymentTxnId,
                          @NonNull String intiatedBy, double openingBal, double amount,
                          double closingBal, double cashCollected, long timestamp) {
        this.id = id;
        this.transactionId = transactionId;
        this.userId = userId;
        this.txnType = txnType;
        this.trigger = trigger;
        this.comment = comment;
        this.cityId = cityId;
        this.currency = currency;
        this.currencyAbbr = currencyAbbr;
        this.currencySymbol = currencySymbol;
        this.orderId = orderId;
        this.paymentTypeText = paymentTypeText;
        this.cityName = cityName;
        this.serviceTypeText = serviceTypeText;
        this.paymentTxnId = paymentTxnId;
        this.intiatedBy = intiatedBy;
        this.openingBal = openingBal;
        this.amount = amount;
        this.closingBal = closingBal;
        this.cashCollected = cashCollected;
        this.timestamp = timestamp;
    }

    @NonNull
    public String getTransactionId() {
        return transactionId;
    }

    @NonNull
    public String getUserId() {
        return userId;
    }

    @NonNull
    public String getTxnType() {
        return txnType;
    }

    @NonNull
    public String getCurrency() {
        return currency;
    }

    public String getCurrencyAbbr() {
        return currencyAbbr;
    }

    @NonNull
    public String getOrderId() {
        return orderId;
    }

    @NonNull
    public String getPaymentTypeText() {
        return paymentTypeText;
    }

    @NonNull
    public String getServiceTypeText() {
        return serviceTypeText;
    }

    @NonNull
    public String getPaymentTxnId() {
        return paymentTxnId;
    }

    @NonNull
    public String getIntiatedBy() {
        return intiatedBy;
    }

    public double getOpeningBal() {
        return openingBal;
    }

    public double getAmount() {
        return amount;
    }

    public double getClosingBal() {
        return closingBal;
    }

    public double getCashCollected() {
        return cashCollected;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SettlementItem item = (SettlementItem) o;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            return Objects.equals(transactionId, item.transactionId);
        } else {
            return item.transactionId.equals(transactionId);
        }
    }

    @Override
    public int hashCode() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            return Objects.hash(transactionId);
        } else {
            return transactionId.hashCode();
        }
    }
}
