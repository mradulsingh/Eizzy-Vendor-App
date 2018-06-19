package com.android.aksiem.eizzy.vo.support.order;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public enum ExclusiveTaxType implements Serializable {

    @SerializedName("Percentage")
    PERCENTAGE("Percentage"),

    @SerializedName("Flat")
    FLAT("Flat");

    private String taxTypeString;

    ExclusiveTaxType(String taxTypeString) {
        this.taxTypeString = taxTypeString;
    }

    public String getTaxTypeString() {
        return taxTypeString;
    }
}
