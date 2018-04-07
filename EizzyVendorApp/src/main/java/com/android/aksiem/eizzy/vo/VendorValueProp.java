package com.android.aksiem.eizzy.vo;

import android.arch.persistence.room.Entity;
import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

/**
 * Created by pdubey on 02/04/18.
 */

@Entity(primaryKeys = "title")
public class VendorValueProp {

    @SerializedName("title")
    @NonNull
    public final String title;

    @SerializedName("subtitle")
    @NonNull
    public final String subtitle;

    @SerializedName("primaryAction")
    @NonNull
    public final String primaryAction;

    @SerializedName("existingAccountPrompt")
    @NonNull
    public final String existingAccountPrompt;

    @SerializedName("existingAccountAction")
    @NonNull
    public final String existingAccountAction;

    @SerializedName("copyright")
    @NonNull
    public final String copyright;

    public VendorValueProp(@NonNull String title, @NonNull String subtitle,
                           @NonNull String primaryAction, @NonNull String existingAccountPrompt,
                           @NonNull String existingAccountAction, @NonNull String copyright) {
        this.title = title;
        this.subtitle = subtitle;
        this.primaryAction = primaryAction;
        this.existingAccountPrompt = existingAccountPrompt;
        this.existingAccountAction = existingAccountAction;
        this.copyright = copyright;
    }

    @Override
    public String toString() {
        return "VendorValueProp{" +
                "title='" + title + '\'' +
                ", subtitle='" + subtitle + '\'' +
                ", primaryAction='" + primaryAction + '\'' +
                ", existingAccountPrompt='" + existingAccountPrompt + '\'' +
                ", existingAccountAction='" + existingAccountAction + '\'' +
                ", copyright='" + copyright + '\'' +
                '}';
    }
}
