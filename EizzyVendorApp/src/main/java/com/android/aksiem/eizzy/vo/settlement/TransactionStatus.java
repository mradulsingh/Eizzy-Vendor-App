package com.android.aksiem.eizzy.vo.settlement;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;



public enum TransactionStatus implements Serializable {

    @SerializedName("processing")
    PROCESSING("processing"),

    @SerializedName("successful")
    SUCCESSFULL("successful"),

    @SerializedName("failed")
    FAILED("failed"),

    @SerializedName("declined")
    DECLINED("declined");



    private String state;

    TransactionStatus(String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }
}
