package com.android.aksiem.eizzy.vo.order;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CustomerDetails implements Serializable {

    @NonNull
    @SerializedName("customerId")
    public final String customerId;

    @NonNull
    @SerializedName("name")
    public final String name;

    @NonNull
    @SerializedName("email")
    public final String email;

    @NonNull
    @SerializedName("mobile")
    public final String mobile;

    @NonNull
    @SerializedName("countryCode")
    public final String countryCode;

    @NonNull
    @SerializedName("profilePic")
    public final String profilePicUrl;

    @NonNull
    @SerializedName("fcmTopic")
    public final String fcmTopic;

    @NonNull
    @SerializedName("deviceType")
    public final Integer deviceType;

    @NonNull
    @SerializedName("mqttTopic")
    public final String mqttTopic;

    @NonNull
    @SerializedName("mmjCard")
    public final String mmjCard;

    @NonNull
    @SerializedName("identityCard")
    public final String identityCard;

    public CustomerDetails(@NonNull String customerId, @NonNull String name, @NonNull String email,
                           @NonNull String mobile, @NonNull String countryCode,
                           @NonNull String profilePicUrl, @NonNull String fcmTopic,
                           @NonNull Integer deviceType, @NonNull String mqttTopic,
                           @NonNull String mmjCard, @NonNull String identityCard) {

        this.customerId = customerId;
        this.name = name;
        this.email = email;
        this.mobile = mobile;
        this.countryCode = countryCode;
        this.profilePicUrl = profilePicUrl;
        this.fcmTopic = fcmTopic;
        this.deviceType = deviceType;
        this.mqttTopic = mqttTopic;
        this.mmjCard = mmjCard;
        this.identityCard = identityCard;
    }
}
