package com.android.aksiem.eizzy.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.android.aksiem.eizzy.vo.settlement.SettlementItem;

import java.util.List;

@Dao
public abstract class SettlementItemDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insert(SettlementItem... item);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insertSettlementItems(List<SettlementItem> items);


    @Query("SELECT * FROM settlement_item")
    public abstract List<SettlementItem> getSettlementItems();

    @Query("SELECT * FROM settlement_item")
    public abstract LiveData<List<SettlementItem>> getAllItems();

    @Query("SELECT * FROM settlement_item WHERE transactionId IN (:transactionIds)")
    public abstract LiveData<List<SettlementItem>> getItemsByIds(List<String> transactionIds);

}
