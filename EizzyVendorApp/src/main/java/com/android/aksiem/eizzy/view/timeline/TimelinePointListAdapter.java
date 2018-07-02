package com.android.aksiem.eizzy.view.timeline;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.android.aksiem.eizzy.R;
import com.android.aksiem.eizzy.databinding.TimelineRowBinding;

import java.util.List;


/**
 * Created by pdubey on 21/04/18.
 */

public class TimelinePointListAdapter<V extends TimelinePoint> extends ArrayAdapter<V> {

    private final TimelineConfigBuilder timelineConfig;

    public TimelinePointListAdapter(@NonNull Context context, int resource, @NonNull List<V> items,
                                    TimelineConfigBuilder timelineConfig) {
        super(context, resource, items);
        this.timelineConfig = timelineConfig;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        TimelineRowBinding binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()),
                R.layout.timeline_row, parent, false);
        V item = getItem(position);
        binding.rowItem.setVerbose(timelineConfig.isVerbose());
        binding.rowItem.setLast((getCount() - 1) == position);
        binding.rowItem.setState(item.getState());
        binding.rowItem.setTimestamp(item.getTimestamp() * 1000);
        binding.rowItem.setLocation(item.getStringLocation());
        binding.rowItem.setMessage(item.getMessage());
        return binding.getRoot();
    }
}
