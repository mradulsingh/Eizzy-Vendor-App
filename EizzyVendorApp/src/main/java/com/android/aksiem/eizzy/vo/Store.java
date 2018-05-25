package com.android.aksiem.eizzy.vo;

import android.arch.persistence.room.Entity;
import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@Entity(primaryKeys = "storeId", tableName = "my_store_info")
public class Store implements Serializable {

    @NonNull
    @SerializedName("_id")
    public final String serverId;

    @NonNull
    @SerializedName("ownerName")
    public final String ownerName;

    @NonNull
    @SerializedName("countryCode")
    public final String countryCode;

    @NonNull
    @SerializedName("ownerPhone")
    public final String ownerPhone;

    @NonNull
    @SerializedName("ownerEmail")
    public final String ownerEmail;

    @NonNull
    @SerializedName("bcountryCode")
    public final String businessCountryCode;

    @NonNull
    @SerializedName("businessNumber")
    public final String businessNumber;

    @NonNull
    @SerializedName("coordinates")
    public final LatLng coordinates;

    @NonNull
    @SerializedName("email")
    public final String email;

    @NonNull
    @SerializedName("password")
    public final String password;

    @NonNull
    @SerializedName("firstName")
    public final String firstName;

    @NonNull
    @SerializedName("phone")
    public final String phone;

    @NonNull
    @SerializedName("storeId")
    public final String storeId;

    @NonNull
    @SerializedName("storeName")
    public final String storeName;

    public Store(@NonNull String serverId, @NonNull String ownerName, @NonNull String countryCode,
                 @NonNull String ownerPhone, @NonNull String ownerEmail,
                 @NonNull String businessCountryCode, @NonNull String businessNumber,
                 @NonNull LatLng coordinates, @NonNull String email, @NonNull String password,
                 @NonNull String firstName, @NonNull String phone, @NonNull String storeId,
                 @NonNull String storeName) {

        this.serverId = serverId;
        this.ownerName = ownerName;
        this.countryCode = countryCode;
        this.ownerPhone = ownerPhone;
        this.ownerEmail = ownerEmail;
        this.businessCountryCode = businessCountryCode;
        this.businessNumber = businessNumber;
        this.coordinates = coordinates;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.phone = phone;
        this.storeId = storeId;
        this.storeName = storeName;
    }
}
