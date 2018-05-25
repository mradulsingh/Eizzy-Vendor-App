package com.android.aksiem.eizzy.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.android.aksiem.eizzy.vo.Store;

import java.util.List;

@Dao
public abstract class StoreDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insert(Store... stores);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insertOrderItems(List<Store> stores);

    @Query("SELECT * FROM my_store_info")
    public abstract LiveData<List<Store>> getStores();
}
