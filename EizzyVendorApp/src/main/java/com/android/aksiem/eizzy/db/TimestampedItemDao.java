package com.android.aksiem.eizzy.db;

import android.arch.lifecycle.LiveData;

import com.android.aksiem.eizzy.vo.OrderItem;
import com.android.aksiem.eizzy.vo.Timestamped;

import java.util.List;

/**
 * Created by pdubey on 14/04/18.
 */

public interface TimestampedItemDao<T extends Timestamped> {

    void insert(T... items);

    int getOrderItemCount();

    List<OrderItem> getOrderItems();

    LiveData<List<T>> getAllItems();

    LiveData<List<T>> getItemsFrom(Float timestamp);

    LiveData<List<OrderItem>> getItemsFromTo(Float start, Float end);

}
