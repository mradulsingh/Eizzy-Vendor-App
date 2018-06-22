package com.android.aksiem.eizzy.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import android.database.sqlite.SQLiteConstraintException;

import com.android.aksiem.eizzy.vo.OrderListItem;

import java.util.List;

@Dao
public abstract class OrderListItemDao implements TimestampedItemDao<OrderListItem> {

    @Override
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insert(OrderListItem... items);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insert(List<OrderListItem> orderDetailItems);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    public abstract void update(List<OrderListItem> orderDetailItems);

    //public abstract LivePagedListProvider<Integer, OrderListItem>

    @Override
    @Query("SELECT * FROM order_list_item")
    public abstract List<OrderListItem> getOrderItems();

    @Override
    @Query("SELECT * FROM order_list_item")
    public abstract LiveData<List<OrderListItem>> getAllItems();

    @Query("SELECT * FROM order_list_item WHERE orderId IN (:orderIds)")
    public abstract LiveData<List<OrderListItem>> getItemsByIds(List<String> orderIds);

    @Override
    @Query("SELECT COUNT(*) FROM order_list_item")
    public abstract int getOrderItemCount();

    @Override
    @Query("SELECT * FROM order_list_item WHERE timestamp >= :timestamp")
    public abstract LiveData<List<OrderListItem>> getItemsFrom(Float timestamp);

    @Override
    @Query("SELECT * FROM order_list_item WHERE timestamp >= :start AND timestamp <= :end")
    public abstract LiveData<List<OrderListItem>> getItemsFromTo(Float start, Float end);
    
}
