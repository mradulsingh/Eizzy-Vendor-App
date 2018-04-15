package com.android.aksiem.eizzy.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.android.aksiem.eizzy.vo.OrderItem;

import java.util.List;

/**
 * Created by pdubey on 14/04/18.
 */

@Dao
public abstract class OrderItemsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insert(OrderItem... orderItems);

    @Query("SELECT * FROM order_item")
    public abstract LiveData<List<OrderItem>> getAllOrderItems();

    @Query("SELECT * FROM order_item WHERE timestamp >= :timestamp")
    public abstract LiveData<List<OrderItem>> getOrderItemsFrom(Float timestamp);

    @Query("SELECT * FROM order_item WHERE timestamp >= :start AND timestamp <= :end")
    public abstract LiveData<List<OrderItem>> getOrderItemsFromTo(Float start, Float end);

}
