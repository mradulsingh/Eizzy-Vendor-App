package com.android.aksiem.eizzy.ui.settlement;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class SettlementPagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;
    static final int DAY = 0;
    static final int WEEK = 1;
    static final int MONTH = 2;


    public SettlementPagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }


    @Override
    public Fragment getItem(int position) {


            Bundle bundle = new Bundle();
            bundle.putInt("selected_duration", position);
            SettlementDurationFragment settlementDurationFragment = new SettlementDurationFragment();
            settlementDurationFragment.setArguments(bundle);
            return settlementDurationFragment;

    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}