package com.android.aksiem.eizzy.db;

import android.arch.lifecycle.LiveData;

import com.android.aksiem.eizzy.vo.OrderDetailItem;
import com.android.aksiem.eizzy.vo.Timestamped;

import java.util.List;

/**
 * Created by pdubey on 14/04/18.
 */

public interface TimestampedItemDao<T extends Timestamped> {

    void insert(T... items);

    int getOrderItemCount();

    List<OrderDetailItem> getOrderItems();

    LiveData<List<T>> getAllItems();

    LiveData<List<T>> getItemsFrom(Float timestamp);

    LiveData<List<OrderDetailItem>> getItemsFromTo(Float start, Float end);

}
