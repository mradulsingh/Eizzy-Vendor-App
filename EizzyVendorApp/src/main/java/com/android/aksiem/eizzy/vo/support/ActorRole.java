package com.android.aksiem.eizzy.vo.support;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by pdubey on 09/04/18.
 */

public enum ActorRole implements Serializable {

    @SerializedName("deliveryAssociate")
    DELIVERY_ASSOCIATE("deliveryAssociate"),

    @SerializedName("customer")
    CUSTOMER("customer");

    private String role;

    ActorRole(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
