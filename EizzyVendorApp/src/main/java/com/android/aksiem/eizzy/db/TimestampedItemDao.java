package com.android.aksiem.eizzy.db;

import android.arch.lifecycle.LiveData;

import com.android.aksiem.eizzy.vo.order.Timestamped;

import java.util.List;

/**
 * Created by Mradul on 10/06/18.
 */

public interface TimestampedItemDao<T extends Timestamped> {

    void insert(T... items);

    int getOrderItemCount();

    List<T> getOrderItems();

    LiveData<List<T>> getAllItems();

    LiveData<List<T>> getItemsFrom(Float timestamp);

    LiveData<List<T>> getItemsFromTo(Float start, Float end);

}
