package com.android.aksiem.eizzy.vo.order;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Accounting implements Serializable {

    @NonNull
    @SerializedName("driverAccountType")
    public final Integer driverAccountType;

    @NonNull
    @SerializedName("driverAccountTypeMsg")
    public final String diverAccountTypeMessage;

    @NonNull
    @SerializedName("taxes")
    public final Float taxes;

    @NonNull
    @SerializedName("driverCommPer")
    public final Float driverCommPer;

    @NonNull
    @SerializedName("driverCommType")
    public final Integer driverCommType;

    @NonNull
    @SerializedName("driverCommTypeMsg")
    public final String driverCommTypeMessage;

    @NonNull
    @SerializedName("appEarningValue")
    public final Float appEarningValue;

    @NonNull
    @SerializedName("driverEarningValue")
    public final Float driverEarningValue;

    @NonNull
    @SerializedName("driverCommissionToAppValue")
    public final Float driverCommissionToAppValue;

    @NonNull
    @SerializedName("storeEarningToValue")
    public final Float storeEarmingToValue;

    @NonNull
    @SerializedName("storeCommissionToAppValue")
    public final Float storeCommissionToAppValue;

    @NonNull
    @SerializedName("cashCollected")
    public final Float cashCollected;

    @NonNull
    @SerializedName("pgComm")
    public final Float pgComm;

    @NonNull
    @SerializedName("pgCommName")
    public final String pgCommName;

    @NonNull
    @SerializedName("tollFee")
    public final Float tollFee;

    @NonNull
    @SerializedName("driverTip")
    public final Float driverTip;

    @NonNull
    @SerializedName("driverTotalEarningValue")
    public final Float driverTotalEarningValue;

    @NonNull
    @SerializedName("handlingFee")
    public final Float handlingFee;

    @NonNull
    @SerializedName("appProfitLoss")
    public final Float appProfitLoss;

    public Accounting(@NonNull Integer driverAccountType, @NonNull String diverAccountTypeMessage,
                      @NonNull Float taxes, @NonNull Float driverCommPer,
                      @NonNull Integer driverCommType, @NonNull String driverCommTypeMessage,
                      @NonNull Float appEarningValue, @NonNull Float driverEarningValue,
                      @NonNull Float driverCommissionToAppValue, @NonNull Float storeEarmingToValue,
                      @NonNull Float storeCommissionToAppValue, @NonNull Float cashCollected,
                      @NonNull Float pgComm, @NonNull String pgCommName, @NonNull Float tollFee,
                      @NonNull Float driverTip, @NonNull Float driverTotalEarningValue,
                      @NonNull Float handlingFee, @NonNull Float appProfitLoss) {

        this.driverAccountType = driverAccountType;
        this.diverAccountTypeMessage = diverAccountTypeMessage;
        this.taxes = taxes;
        this.driverCommPer = driverCommPer;
        this.driverCommType = driverCommType;
        this.driverCommTypeMessage = driverCommTypeMessage;
        this.appEarningValue = appEarningValue;
        this.driverEarningValue = driverEarningValue;
        this.driverCommissionToAppValue = driverCommissionToAppValue;
        this.storeEarmingToValue = storeEarmingToValue;
        this.storeCommissionToAppValue = storeCommissionToAppValue;
        this.cashCollected = cashCollected;
        this.pgComm = pgComm;
        this.pgCommName = pgCommName;
        this.tollFee = tollFee;
        this.driverTip = driverTip;
        this.driverTotalEarningValue = driverTotalEarningValue;
        this.handlingFee = handlingFee;
        this.appProfitLoss = appProfitLoss;
    }
}
