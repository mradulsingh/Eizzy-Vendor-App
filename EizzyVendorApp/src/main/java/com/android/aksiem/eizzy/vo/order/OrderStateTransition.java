package com.android.aksiem.eizzy.vo.order;

import android.arch.persistence.room.Ignore;

import com.android.aksiem.eizzy.view.timeline.AppTimelinePointView;
import com.android.aksiem.eizzy.view.timeline.TimelinePoint;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by pdubey on 09/04/18.
 */

public class OrderStateTransition extends TimelinePoint implements Serializable {

    @Ignore
    private int index;

    @Ignore
    private AppTimelinePointView.TimelinePointState state;

    @SerializedName("orderState")
    public final OrderState orderState;

    @SerializedName("timestamp")
    public final Long timestamp;

    @SerializedName("stringLocation")
    private String stringLocation;

    @SerializedName("message")
    private String message;

    public OrderStateTransition(OrderState orderState, Long timestamp, String stringLocation) {

        this.orderState = orderState;
        this.timestamp = timestamp;
        this.stringLocation = stringLocation;
    }

    @Override
    public int getIndex() {
        return index;
    }

    public OrderStateTransition setIndex(int index) {
        this.index = index;
        return this;
    }

    @Override
    public Long getTimestamp() {
        return timestamp;
    }

    @Override
    public OrderStateTransition setTimestamp(long timestamp) {
        return null;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public OrderStateTransition setMessage(String message) {
        this.message = message;
        return this;
    }

    @Override
    public String getStringLocation() {
        return stringLocation;
    }

    @Override
    public OrderStateTransition setStringLocation(String stringLocation) {
        this.stringLocation = stringLocation;
        return this;
    }

    @Override
    public AppTimelinePointView.TimelinePointState getState() {
        return state;
    }

    @Override
    public OrderStateTransition setState(AppTimelinePointView.TimelinePointState state) {
        this.state = state;
        return this;
    }

    @Override
    public String toString() {
        return "OrderStateTransition{" +
                "index=" + index +
                ", state=" + state +
                ", orderState=" + orderState +
                ", timestamp=" + timestamp +
                ", stringLocation='" + stringLocation + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
