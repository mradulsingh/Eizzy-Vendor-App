package com.android.aksiem.eizzy.vo.order;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ExclusiveTax implements Serializable {

    @NonNull
    @SerializedName("taxId")
    public final String taxId;

    @NonNull
    @SerializedName("taxtName")
    public final String name;

    @NonNull
    @SerializedName("taxFlagMsg")
    public final ExclusiveTaxType taxType;

    @NonNull
    @SerializedName("taxValue")
    public final Float taxValue;

    @NonNull
    @SerializedName("taxCode")
    public final String taxCode;

    @NonNull
    @SerializedName("price")
    public final Float price;

    public ExclusiveTax(@NonNull String taxId, @NonNull String name,
                        @NonNull ExclusiveTaxType taxType, @NonNull Float taxValue,
                        @NonNull String taxCode, @NonNull Float price) {
        this.taxId = taxId;
        this.name = name;
        this.taxType = taxType;
        this.taxValue = taxValue;
        this.taxCode = taxCode;
        this.price = price;
    }
}
