package com.android.aksiem.eizzy.ui.order;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.android.aksiem.eizzy.R;
import com.android.aksiem.eizzy.databinding.EizzyZoneItemBinding;
import com.android.aksiem.eizzy.vo.order.EizzyZone;

import java.util.List;

/**
 * Created by Mradul on 10/06/18.
 */

public class EizzyZoneAdapter extends ArrayAdapter<EizzyZone> {


    public EizzyZoneAdapter(@NonNull Context context, int res, @NonNull List<EizzyZone> items) {
        super(context, res, items);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        EizzyZoneItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()),
                R.layout.eizzy_zone_item, parent, false);
        binding.tvEizzyZoneItem.setText(getItem(position).name);
        return binding.getRoot();
    }
}
