package com.android.aksiem.eizzy.app;

import android.support.v4.app.FragmentActivity;

/**
 * Created by pdubey on 09/04/18.
 */

public class BottomNavigationFragment extends BaseInjectableFragment {

    @Override
    public void onStart() {
        super.onStart();
        setBottomNavigationViewVisibility(true);
    }
}
