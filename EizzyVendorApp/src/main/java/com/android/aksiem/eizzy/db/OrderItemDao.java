package com.android.aksiem.eizzy.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.android.aksiem.eizzy.vo.OrderItem;

import java.util.List;

/**
 * Created by pdubey on 15/04/18.
 */

@Dao
public abstract class OrderItemDao implements TimestampedItemDao<OrderItem> {

    @Override
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insert(OrderItem... items);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insertOrderItems(List<OrderItem> OrderItems);

    @Override
    @Query("SELECT * FROM order_item")
    public abstract List<OrderItem> getOrderItems();

    @Override
    @Query("SELECT * FROM order_item")
    public abstract LiveData<List<OrderItem>> getAllItems();

    @Override
    @Query("SELECT COUNT(*) FROM order_item")
    public abstract int getOrderItemCount();

    @Override
    @Query("SELECT * FROM order_item WHERE timestamp >= :timestamp")
    public abstract LiveData<List<OrderItem>> getItemsFrom(Float timestamp);

    @Override
    @Query("SELECT * FROM order_item WHERE timestamp >= :start AND timestamp <= :end")
    public abstract LiveData<List<OrderItem>> getItemsFromTo(Float start, Float end);

}
