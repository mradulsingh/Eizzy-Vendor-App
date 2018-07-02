package com.android.aksiem.eizzy.app;

import android.support.v4.app.Fragment;

/**
 * Created by napendersingh on 02/07/18.
 */

public interface FragmentBackStackCallback {
    void onFragmentPushed(Fragment parentFragment);

    void onFragmentPopped(Fragment parentFragment);
}