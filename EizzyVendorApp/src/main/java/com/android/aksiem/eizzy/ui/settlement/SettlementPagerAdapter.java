package com.android.aksiem.eizzy.ui.settlement;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import java.util.HashMap;
import java.util.Map;

public class SettlementPagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;
    static final int DAY = 0;
    static final int WEEK = 1;
    static final int MONTH = 2;

    Map<Integer, SettlementDurationFragment> mPageReferenceMap;

    public SettlementPagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
        mPageReferenceMap = new HashMap<>();
    }


    @Override
    public Fragment getItem(int position) {
        Bundle bundle = new Bundle();
        bundle.putInt("selected_duration", position);
        SettlementDurationFragment settlementDurationFragment = new SettlementDurationFragment();
        settlementDurationFragment.setArguments(bundle);
        mPageReferenceMap.put(position, settlementDurationFragment);
        return settlementDurationFragment;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
        mPageReferenceMap.remove(position);
    }

    public SettlementDurationFragment getFragment(int key) {
        return mPageReferenceMap.get(key);
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}