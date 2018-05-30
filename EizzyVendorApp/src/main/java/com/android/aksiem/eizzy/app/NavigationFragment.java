package com.android.aksiem.eizzy.app;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.aksiem.eizzy.ui.toolbar.NavigationBuilder;

public abstract class NavigationFragment extends BaseInjectableFragment {

    private NavigationBuilder navigationBuilder;

    @Nullable
    public View wrapNavigationLayout(LayoutInflater inflater, @Nullable ViewGroup container, @NonNull View viewAttachedFragment) {
        navigationBuilder = buildNavigation();
        return navigationBuilder.createNavigationView(inflater, container, viewAttachedFragment);
    }

    public abstract NavigationBuilder buildNavigation();

    @Override
    public void onResume() {
        super.onResume();
        setBottomNavigationViewVisibility(navigationBuilder.isIncludeBottomNavBar());
        setNavDrawerMode(navigationBuilder.isIncludeDrawerNav());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (navigationBuilder != null) {
            navigationBuilder.onDestroy();
        }
    }
}

