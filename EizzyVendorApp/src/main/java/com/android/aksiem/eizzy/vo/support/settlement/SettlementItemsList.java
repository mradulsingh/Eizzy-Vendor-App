package com.android.aksiem.eizzy.vo.support.settlement;

import android.support.annotation.NonNull;

import com.android.aksiem.eizzy.vo.settlement.SettlementItem;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class SettlementItemsList implements Serializable {

    @NonNull
    @SerializedName("data")
    public final ArrayList<SettlementItem> items;

    public SettlementItemsList(@NonNull ArrayList<SettlementItem> items) {
        this.items = items;
    }
}
