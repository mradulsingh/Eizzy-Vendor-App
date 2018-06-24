package com.android.aksiem.eizzy.vo;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class EizzyApiRespone<T extends Serializable> {

    @NonNull
    @SerializedName("message")
    public final String message;

    @NonNull
    @SerializedName("data")
    public final T data;

    private Integer nextPage;

    public EizzyApiRespone(@NonNull String message, @NonNull T data) {
        this.message = message;
        this.data = data;
    }

    public void setNextPage(Integer nextPage) {
        this.nextPage = nextPage;
    }

    public Integer getNextPage() {
        return nextPage;
    }
}
