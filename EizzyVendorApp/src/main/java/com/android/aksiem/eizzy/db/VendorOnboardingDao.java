package com.android.aksiem.eizzy.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.android.aksiem.eizzy.vo.VendorOnboarding;

/**
 * Created by pdubey on 04/04/18.
 */

public interface VendorOnboardingDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertVendorValueProp(VendorOnboarding vendorOnboarding);

    @Query("SELECT * FROM VendorOnboarding WHERE title = :title")
    LiveData<VendorOnboarding> findByTitle(String title);
}
