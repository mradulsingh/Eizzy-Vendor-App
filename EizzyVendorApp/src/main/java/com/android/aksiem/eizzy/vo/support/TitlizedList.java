package com.android.aksiem.eizzy.vo.support;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TitlizedList<V> {

    @SerializedName("listTitle")
    @NonNull
    public final String listTitle;

    @SerializedName("items")
    @NonNull
    public final List<V> items;

    public TitlizedList(@NonNull String listTitle, @NonNull List<V> items) {
        this.listTitle = listTitle;
        this.items = items;
    }

    @Override
    public String toString() {
        return "TitlizedList{" +
                "listTitle='" + listTitle + '\'' +
                ", items=" + items +
                '}';
    }
}
