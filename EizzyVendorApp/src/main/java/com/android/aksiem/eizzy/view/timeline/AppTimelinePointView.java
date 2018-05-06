package com.android.aksiem.eizzy.view.timeline;

import android.content.Context;
import android.content.res.TypedArray;
import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;

import com.android.aksiem.eizzy.R;
import com.android.aksiem.eizzy.databinding.TimelinePointVerticalBinding;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by pdubey on 01/05/18.
 */

public class AppTimelinePointView extends RelativeLayout {

    public enum TimelinePointState {
        PENDING(0),
        IN_PROGRESS(1),
        COMPLETE(2);

        int id;

        TimelinePointState(int id) {
            this.id = id;
        }

        public static TimelinePointState fromId(int id) {
            for (TimelinePointState s : values()) {
                if (s.id == id)
                    return s;
            }
            throw new IllegalArgumentException("Only id in range [0, 2] are valid");
        }
    };

    private int stateId;

    private TimelinePointState state;
    private String location;
    private String message;
    private Long timestamp = null;
    private boolean isLast;
    private boolean verbose;

    private TimelinePointVerticalBinding binding;

    public AppTimelinePointView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initAttrs(context, attrs);
        initView(context);
    }

    public AppTimelinePointView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(context, attrs);
        initView(context);
    }

    private void initAttrs(Context context, @Nullable AttributeSet attrs) {
        if (attrs != null) {
            TypedArray attributeArray = context.obtainStyledAttributes(
                    attrs,
                    R.styleable.AppTimelinePointView);
            stateId = attributeArray.getInt(R.styleable.AppTimelinePointView_progressState,
                    -1);
            try {
                state = TimelinePointState.fromId(stateId);
            } catch (IllegalArgumentException e) {
                state = TimelinePointState.PENDING;
            }
            location = attributeArray.getString(R.styleable.AppTimelinePointView_location);
            message = attributeArray.getString(R.styleable.AppTimelinePointView_message);
            isLast = attributeArray.getBoolean(R.styleable.AppTimelinePointView_isLast,
                    false);
            verbose = attributeArray.getBoolean(R.styleable.AppTimelinePointView_verbose,
                    true);
            attributeArray.recycle();
        }
    }

    private void initView(Context context) {
        binding = DataBindingUtil.inflate(LayoutInflater.from(context),
                R.layout.timeline_point_vertical, null, false);
        setVerbose(verbose);
        setState(state);
        setLast(isLast);
        setMessage(message);
        setLocation(location);
    }

    public TimelinePointState getState() {
        return state;
    }

    public void setState(TimelinePointState newState) {
        switch (newState) {
            case PENDING:
                this.state = newState;
                binding.milestoneIndicator.setPending();
                binding.milestoneProgressLine.setPending();
                break;
            case IN_PROGRESS:
                this.state = newState;
                binding.milestoneIndicator.setInProgress();
                binding.milestoneProgressLine.setInProgress();
                break;
            case COMPLETE:
                this.state = newState;
                binding.milestoneIndicator.setComplete();
                binding.milestoneProgressLine.setComplete();
                break;
        }
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = nullOrBlankChecked(location);
        binding.locationContainer.setText(location);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = nullOrBlankChecked(message);
        binding.message.setText(message);
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        String date = format.format(new Date(timestamp));
        format = new SimpleDateFormat("h:mm a");
        String time = format.format(new Date(timestamp));
        binding.dateContainer.setText(date);
        binding.timeContainer.setText(time);
    }

    public boolean isVerbose() {
        return verbose;
    }

    public void setVerbose(boolean verbose) {
        this.verbose = verbose;
        if (verbose) {
            binding.dateContainer.setVisibility(VISIBLE);
            binding.timeContainer.setVisibility(VISIBLE);
            binding.locationContainer.setVisibility(VISIBLE);
        } else {
            binding.dateContainer.setVisibility(GONE);
            binding.timeContainer.setVisibility(GONE);
            binding.locationContainer.setVisibility(GONE);
        }
    }

    public boolean isLast() {
        return isLast;
    }

    public void setLast(boolean last) {
        isLast = last;
        if (isLast) {
            binding.milestoneProgressLine.setVisibility(GONE);
        } else {
            binding.milestoneProgressLine.setVisibility(VISIBLE);
        }
    }

    private String nullOrBlankChecked(String s) {
        return (s == null || s.trim().equals("")) ? "" : s;
    }
}
