package com.android.aksiem.eizzy.vo;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class LatLng implements Serializable {

    @NonNull
    @SerializedName("latitude")
    public final Double lat;

    @NonNull
    @SerializedName("longitude")
    public final Double lng;

    public LatLng(@NonNull Double lat, @NonNull Double lng) {
        this.lat = lat;
        this.lng = lng;
    }
}
