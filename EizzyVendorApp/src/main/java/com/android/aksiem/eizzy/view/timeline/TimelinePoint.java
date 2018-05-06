package com.android.aksiem.eizzy.view.timeline;

/**
 * Created by pdubey on 05/05/18.
 */

public abstract class TimelinePoint {

    public abstract int getIndex();

    public abstract Long getTimestamp();

    public abstract TimelinePoint setTimestamp(long timestamp);

    public abstract String getMessage();

    public abstract TimelinePoint setMessage(String message);

    public abstract String getStringLocation();

    public abstract TimelinePoint setStringLocation(String stringLocation);

    public abstract AppTimelinePointView.TimelinePointState getState();

    public abstract TimelinePoint setState(
            AppTimelinePointView.TimelinePointState state);


    protected boolean areItemsTheSame(TimelinePoint otherPoint) {
        return this.getIndex() == otherPoint.getIndex();
    }

    protected boolean areContentsTheSame(TimelinePoint otherPoint) {
        return this.getIndex() == otherPoint.getIndex()
                && this.getTimestamp() == otherPoint.getTimestamp()
                && this.getMessage().equals(otherPoint.getMessage())
                && this.getStringLocation().equals(otherPoint.getStringLocation())
                && this.getState().id == otherPoint.getState().id;
    }

}
