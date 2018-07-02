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

    Map<Integer, SettlementDurationFragment> mPageReferenceMap;

    private String[] tabTitles = new String[]{"1 DAY", "1 WEEK", "1 MONTH"};

    public SettlementPagerAdapter(FragmentManager fm) {
        super(fm);
        this.mNumOfTabs = tabTitles.length;
        mPageReferenceMap = new HashMap<>();
    }


    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
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