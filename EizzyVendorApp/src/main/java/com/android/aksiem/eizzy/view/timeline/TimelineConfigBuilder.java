package com.android.aksiem.eizzy.view.timeline;

/**
 * Created by pdubey on 05/05/18.
 */

public class TimelineConfigBuilder {

    boolean verbose;
    boolean vertical;

    public TimelineConfigBuilder() {
    }

    public boolean isVerbose() {
        return verbose;
    }

    public TimelineConfigBuilder setVerbose(boolean verbose) {
        this.verbose = verbose;
        return this;
    }

    public boolean isVertical() {
        return vertical;
    }

    public TimelineConfigBuilder setVertical(boolean vertical) {
        this.vertical = vertical;
        return this;
    }
}
