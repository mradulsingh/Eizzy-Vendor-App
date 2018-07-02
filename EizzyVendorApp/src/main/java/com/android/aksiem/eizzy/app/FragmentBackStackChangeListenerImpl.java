package com.android.aksiem.eizzy.app;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import java.util.List;

/**
 * Created by napendersingh on 02/07/18.
 */

public class FragmentBackStackChangeListenerImpl implements FragmentManager.OnBackStackChangedListener {

    private int lastBackStackEntryCount = 0;
    private final FragmentManager fragmentManager;
    private final FragmentBackStackCallback backstackChangeListener;

    public FragmentBackStackChangeListenerImpl(FragmentManager fragmentManager, FragmentBackStackCallback backstackChangeListener) {
        this.fragmentManager = fragmentManager;
        this.backstackChangeListener = backstackChangeListener;
        lastBackStackEntryCount = fragmentManager.getBackStackEntryCount();
    }

    private boolean wasPushed(int backStackEntryCount) {
        return lastBackStackEntryCount < backStackEntryCount;
    }

    private boolean wasPopped(int backStackEntryCount) {
        return lastBackStackEntryCount > backStackEntryCount;
    }

    private boolean haveFragments() {
        List<Fragment> fragmentList = fragmentManager.getFragments();
        return fragmentList != null && !fragmentList.isEmpty();
    }


    /**
     * If we push a fragment to backstack then parent would be the one before => size - 2
     * If we pop a fragment from backstack logically it should be the last fragment in the list, but in Android popping a fragment just makes list entry null keeping list size intact, thus it's also size - 2
     *
     * @return fragment that is parent to the one that is pushed to or popped from back stack
     */
    private Fragment getParentFragment() {
        List<Fragment> fragmentList = fragmentManager.getFragments();
        return fragmentList.get(Math.max(0, fragmentList.size() - 2));
    }

    @Override
    public void onBackStackChanged() {
        int currentBackStackEntryCount = fragmentManager.getBackStackEntryCount();
        if (haveFragments()) {
            Fragment parentFragment = getParentFragment();

            //will be null if was just popped and was last in the stack
            if (parentFragment != null) {
                if (wasPushed(currentBackStackEntryCount)) {
                    backstackChangeListener.onFragmentPushed(parentFragment);
                } else if (wasPopped(currentBackStackEntryCount)) {
                    backstackChangeListener.onFragmentPopped(parentFragment);
                }
            } else {
                backstackChangeListener.onFragmentPopped(fragmentManager.getFragments().get(0));
            }
        }

        lastBackStackEntryCount = currentBackStackEntryCount;
    }
}
