package com.android.aksiem.eizzy.ui.toolbar;


import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.aksiem.eizzy.ui.toolbar.layoutfactory.LayoutFactory;

import java.util.ArrayList;
import java.util.List;

public abstract class NavigationBuilder<T extends NavigationBuilder<T>> {
    public static final int NO_NAV_ICON = -1;

    private final LayoutFactory layoutFactory;

    int toolbarNavigationIcon;
    int toolbarTitleRes;
    CharSequence toolbarTitle;
    int toolbarSubtitleRes;
    CharSequence toolbarSubtitle;
    int toolbarLogoRes;
    Drawable toolbarLogo;
    private NavigationDefaults navigationDefaults;
    boolean includeBottomNavBar;
    boolean includeTopNavBar;

    List<Integer> menuRes = new ArrayList<>();

    public NavigationBuilder(LayoutFactory layoutFactory, NavigationDefaults navigationDefaults) {
        this.layoutFactory = layoutFactory;
        this.navigationDefaults = navigationDefaults;
        includeBottomNavBar = navigationDefaults.includeBottomNavBar;
        includeTopNavBar = navigationDefaults.includeTopNavBar;
    }

    protected abstract T getThis();

    public abstract View createNavigationView(LayoutInflater inflater, @Nullable ViewGroup container, View viewAttachedFragment);

    protected LayoutFactory layoutFactory() {
        return layoutFactory;
    }

    public T toolbarTitleRes(int toolbarTitleRes) {
        this.toolbarTitleRes = toolbarTitleRes;
        return getThis();
    }

    public T toolbarTitle(CharSequence toolbarTitle) {
        this.toolbarTitle = toolbarTitle;
        return getThis();
    }

    public T toolbarSubtitleRes(int toolbarSubtitleRes) {
        this.toolbarSubtitleRes = toolbarSubtitleRes;
        return getThis();
    }

    public T toolbarSubtitle(CharSequence toolbarSubtitle) {
        this.toolbarSubtitle = toolbarSubtitle;
        return getThis();
    }

    public T toolbarLogoRes(int toolbarLogoRes) {
        this.toolbarLogoRes = toolbarLogoRes;
        return getThis();
    }

    public T toolbarLogo(Drawable toolbarLogo) {
        this.toolbarLogo = toolbarLogo;
        return getThis();
    }

    public T includeBottomNavBar(boolean includeBottomNavBar) {
        this.includeBottomNavBar = includeBottomNavBar;
        return getThis();
    }

    public boolean isIncludeBottomNavBar() {
        return includeBottomNavBar;
    }

    public T includeTopNavBar(boolean includeTopNavBar) {
        this.includeBottomNavBar = includeTopNavBar;
        return getThis();
    }

    public boolean isIncludeTopNavBar() {
        return includeBottomNavBar;
    }
}