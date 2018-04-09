package com.android.aksiem.eizzy.vo.support;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by pdubey on 09/04/18.
 */

public class Actor implements Serializable {

    @SerializedName("id")
    public final Integer id;

    @SerializedName("name")
    @NonNull
    public final String name;

    @SerializedName("phone")
    public final String phone;

    @SerializedName("role")
    @NonNull
    public final ActorRole role;

    public Actor(Integer id, @NonNull String name, String phone, @NonNull ActorRole role) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.role = role;
    }
}
