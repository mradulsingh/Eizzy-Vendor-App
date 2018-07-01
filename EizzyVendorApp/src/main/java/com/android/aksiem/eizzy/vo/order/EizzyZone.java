package com.android.aksiem.eizzy.vo.order;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class EizzyZone implements Serializable {

    @NonNull
    @SerializedName("_id")
    public final String zoneId;

    @NonNull
    @SerializedName("city")
    public final String city;

    @NonNull
    @SerializedName("currency")
    public final String currency;

    @NonNull
    @SerializedName("currencySymbol")
    public final String currencySymbol;

    @NonNull
    @SerializedName("title")
    public final String name;

    @NonNull
    @SerializedName("cityId")
    public final String cityId;

    public EizzyZone(@NonNull String zoneId, @NonNull String city, @NonNull String currency,
                     @NonNull String currencySymbol, @NonNull String name, @NonNull String cityId) {
        this.zoneId = zoneId;
        this.city = city;
        this.currency = currency;
        this.currencySymbol = currencySymbol;
        this.name = name;
        this.cityId = cityId;
    }
}
