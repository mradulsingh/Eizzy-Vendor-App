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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Actor actor = (Actor) o;

        if (!id.equals(actor.id)) return false;
        if (!name.equals(actor.name)) return false;
        if (!phone.equals(actor.phone)) return false;
        return role == actor.role;
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + phone.hashCode();
        result = 31 * result + role.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Actor{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", role=" + role +
                '}';
    }
}
