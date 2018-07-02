package com.android.aksiem.eizzy.app;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

/**
 * Created by napendersingh on 02/07/18.
 */

public class FragmentBackStackStateManager {

    private FragmentManager fragmentManager;

    public FragmentBackStackStateManager() {
    }

    private FragmentBackStackCallback backstackCallbackImpl = new FragmentBackStackCallback() {
        @Override
        public void onFragmentPushed(Fragment parentFragment) {
            if (parentFragment instanceof BaseInjectableFragment) {
                ((BaseInjectableFragment) parentFragment).onFragmentPause();
            }
        }

        @Override
        public void onFragmentPopped(Fragment parentFragment) {
            if (parentFragment instanceof BaseInjectableFragment) {
                ((BaseInjectableFragment) parentFragment).onFragmentResume();
            }
        }
    };

    public FragmentBackStackChangeListenerImpl getListener() {
        return new FragmentBackStackChangeListenerImpl(fragmentManager, backstackCallbackImpl);
    }

    public void apply(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
        fragmentManager.addOnBackStackChangedListener(getListener());
    }
}