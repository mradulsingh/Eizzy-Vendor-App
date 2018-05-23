package com.android.aksiem.eizzy.ui.order;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.ArrayAdapter;

import com.android.aksiem.eizzy.vo.support.order.OrderedItem;

import java.util.List;

public class OrderDetailsAdapter extends ArrayAdapter<OrderedItem> {

    public OrderDetailsAdapter(@NonNull Context context, int resource, @NonNull List<OrderedItem> objects) {
        super(context, resource, objects);
    }
}
