package com.android.aksiem.eizzy.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.android.aksiem.eizzy.vo.order.OrderDetailItem;

import java.util.List;

/**
 * Created by pdubey on 15/04/18.
 */

@Dao
public abstract class OrderDetailItemDao implements TimestampedItemDao<OrderDetailItem> {

    @Override
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insert(OrderDetailItem... items);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insert(List<OrderDetailItem> orderDetailItems);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    public abstract void update(List<OrderDetailItem> orderDetailItems);


    @Override
    @Query("SELECT * FROM order_detail_item")
    public abstract List<OrderDetailItem> getOrderItems();

    @Override
    @Query("SELECT * FROM order_detail_item")
    public abstract LiveData<List<OrderDetailItem>> getAllItems();

    @Query("SELECT * FROM order_detail_item WHERE orderId = :orderId")
    public abstract LiveData<OrderDetailItem> getItemById(String orderId);

    @Query("SELECT * FROM order_detail_item WHERE orderId IN (:orderIds)")
    public abstract LiveData<List<OrderDetailItem>> getItemsByIds(List<String> orderIds);

    @Override
    @Query("SELECT COUNT(*) FROM order_detail_item")
    public abstract int getOrderItemCount();

    @Override
    @Query("SELECT * FROM order_detail_item WHERE timestamp >= :timestamp")
    public abstract LiveData<List<OrderDetailItem>> getItemsFrom(Float timestamp);

    @Override
    @Query("SELECT * FROM order_detail_item WHERE timestamp >= :start AND timestamp <= :end")
    public abstract LiveData<List<OrderDetailItem>> getItemsFromTo(Float start, Float end);

}
