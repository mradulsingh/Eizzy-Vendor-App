package com.android.aksiem.eizzy.vo;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class StoreManager implements Serializable {

    @NonNull
    @SerializedName("token")
    public final String token;

    @NonNull
    @SerializedName("mid")
    public final String id;

    @NonNull
    @SerializedName("name")
    public final String name;

    @SerializedName("managerImage")
    public String imageUrl;

    @NonNull
    @SerializedName("phone")
    public final String phone;

    @NonNull
    @SerializedName("countryCode")
    public final String countryCode;

    @NonNull
    @SerializedName("email")
    public final String email;

    @NonNull
    @SerializedName("storeId")
    public final String storeId;

    @NonNull
    @SerializedName("storeName")
    public final String storeName;

    @NonNull
    @SerializedName("fcmManagerTopic")
    public final String fcmManagerTopic;

    @NonNull
    @SerializedName("fcmStoreTopic")
    public final String fcmStoreTopic;

    @NonNull
    @SerializedName("fcmTopic")
    public final String fcmTopic;

    @NonNull
    @SerializedName("mqttTopic")
    public final String mqttTopic;

    @NonNull
    @SerializedName("mqttManagerTopic")
    public final String mqttManagerTopic;

    @NonNull
    @SerializedName("mqttStoreTopic")
    public final String mqttStoreTopic;

    @NonNull
    @SerializedName("cityId")
    public final String cityId;

    @SerializedName("serviceZones")
    public List<String> serviceZones;

    @SerializedName("serviceType")
    public final String serviceType;

    public StoreManager(@NonNull String token, @NonNull String id, @NonNull String name,
                        String imageUrl, @NonNull String phone, @NonNull String countryCode,
                        @NonNull String email, @NonNull String storeId, @NonNull String storeName,
                        @NonNull String fcmManagerTopic, @NonNull String fcmStoreTopic,
                        @NonNull String fcmTopic, @NonNull String mqttTopic,
                        @NonNull String mqttManagerTopic, @NonNull String mqttStoreTopic,
                        @NonNull String cityId, List<String> serviceZones, String serviceType) {

        this.token = token;
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.phone = phone;
        this.countryCode = countryCode;
        this.email = email;
        this.storeId = storeId;
        this.storeName = storeName;
        this.fcmManagerTopic = fcmManagerTopic;
        this.fcmStoreTopic = fcmStoreTopic;
        this.fcmTopic = fcmTopic;
        this.mqttTopic = mqttTopic;
        this.mqttManagerTopic = mqttManagerTopic;
        this.mqttStoreTopic = mqttStoreTopic;
        this.cityId = cityId;
        this.serviceZones = serviceZones;
        this.serviceType = serviceType;

    }

    @Override
    public String toString() {
        return "StoreManager{" +
                "token='" + token + '\'' +
                ", id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", phone='" + phone + '\'' +
                ", countryCode='" + countryCode + '\'' +
                ", email='" + email + '\'' +
                ", storeId='" + storeId + '\'' +
                ", storeName='" + storeName + '\'' +
                ", fcmManagerTopic='" + fcmManagerTopic + '\'' +
                ", fcmStoreTopic='" + fcmStoreTopic + '\'' +
                ", fcmTopic='" + fcmTopic + '\'' +
                ", mqttTopic='" + mqttTopic + '\'' +
                ", mqttManagerTopic='" + mqttManagerTopic + '\'' +
                ", mqttStoreTopic='" + mqttStoreTopic + '\'' +
                ", cityId='" + cityId + '\'' +
                ", serviceZones=" + serviceZones +
                '}';
    }
}
