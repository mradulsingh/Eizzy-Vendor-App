package com.android.aksiem.eizzy.vo;

import android.support.annotation.NonNull;

import com.android.aksiem.eizzy.vo.support.order.OrderActivityLogState;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class OrderActivityLog implements Serializable {

    @NonNull
    @SerializedName("state")
    public final OrderActivityLogState state;

    @NonNull
    @SerializedName("statusUpdatedBy")
    public final String statusUpdatedBy;

    @NonNull
    @SerializedName("userId")
    public final String userId;

    @NonNull
    @SerializedName("timestamp")
    public final Long timestamp;

    @NonNull
    @SerializedName("isoDate")
    public final String isoDate;

    @SerializedName("location")
    private Location location;

    public OrderActivityLog(@NonNull OrderActivityLogState state, @NonNull String statusUpdatedBy,
                            @NonNull String userId, @NonNull Long timestamp,
                            @NonNull String isoDate) {
        this.state = state;
        this.statusUpdatedBy = statusUpdatedBy;
        this.userId = userId;
        this.timestamp = timestamp;
        this.isoDate = isoDate;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
