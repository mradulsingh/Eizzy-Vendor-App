package com.android.aksiem.eizzy.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.android.aksiem.eizzy.vo.VendorValueProp;

/**
 * Created by pdubey on 04/04/18.
 */

public interface VendorValuePropDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertVendorValueProp(VendorValueProp vendorValueProp);

    @Query("SELECT * FROM vendorvalueprop WHERE title = :title")
    LiveData<VendorValueProp> findByTitle(String title);
}
