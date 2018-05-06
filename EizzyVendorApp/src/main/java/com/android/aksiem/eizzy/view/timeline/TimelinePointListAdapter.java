package com.android.aksiem.eizzy.view.timeline;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.android.aksiem.eizzy.R;
import com.android.aksiem.eizzy.databinding.TimelineRowBinding;
import com.android.aksiem.eizzy.ui.common.DataBoundListAdapter;


/**
 * Created by pdubey on 21/04/18.
 */

public class TimelinePointListAdapter<V extends TimelinePoint> extends DataBoundListAdapter<V, ViewDataBinding> {

    private final TimelineConfigBuilder timelineConfig;

    public TimelinePointListAdapter(TimelineConfigBuilder timelineConfig) {
        this.timelineConfig = timelineConfig;
    }

    @Override
    protected ViewDataBinding createBinding(ViewGroup parent, int viewType) {

        if (timelineConfig.isVertical()) {
            return createVerticalBinding(parent);
        }

        return null;
    }

    private TimelineRowBinding createVerticalBinding(ViewGroup parent) {
        return DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.timeline_row, parent,
                false);
    }

    @Override
    protected void bind(ViewDataBinding binding, int viewType, V item) {

        if (binding instanceof TimelineRowBinding) {
            AppTimelinePointView rowItem = ((TimelineRowBinding) binding).rowItem;
            rowItem.setVerbose(timelineConfig.isVerbose());
            rowItem.setLast(getItems().get(getItemCount() - 1).areItemsTheSame(item));
            rowItem.setState(item.getState());
            rowItem.setTimestamp(item.getTimestamp());
            rowItem.setLocation(item.getStringLocation());
            rowItem.setMessage(item.getMessage());
        }

    }

    @Override
    protected boolean areItemsTheSame(V oldItem, V newItem) {
        return newItem.areItemsTheSame(oldItem);
    }

    @Override
    protected boolean areContentsTheSame(V oldItem, V newItem) {
        return newItem.areContentsTheSame(oldItem);
    }

}
