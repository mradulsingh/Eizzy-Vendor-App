package com.android.aksiem.eizzy.app;

/**
 * Created by pdubey on 09/04/18.
 */

public class NoBottomNavigationFragment extends BaseInjectableFragment {

    @Override
    public void onStart() {
        super.onStart();
        setBottomNavigationViewVisibility(false);
    }
}
