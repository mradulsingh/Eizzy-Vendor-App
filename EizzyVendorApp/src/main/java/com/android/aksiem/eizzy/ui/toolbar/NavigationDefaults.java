package com.android.aksiem.eizzy.ui.toolbar;

public final class NavigationDefaults {

    private static NavigationDefaults mInstance = null;

    public final boolean includeBottomNavBar;
    public final boolean includeDrawerNav;
    public final boolean includeTopNavBar;

    private NavigationDefaults() {
        includeBottomNavBar = false;
        includeDrawerNav = false;
        includeTopNavBar = true;
    }

    public static NavigationDefaults getInstance() {
        mInstance = new NavigationDefaults();
        return mInstance;
    }
}
