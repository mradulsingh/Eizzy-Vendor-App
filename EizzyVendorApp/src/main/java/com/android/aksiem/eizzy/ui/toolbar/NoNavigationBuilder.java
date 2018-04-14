package com.android.aksiem.eizzy.ui.toolbar;

import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.aksiem.eizzy.ui.toolbar.layoutfactory.LayoutFactory;

public class NoNavigationBuilder extends NavigationBuilder<NoNavigationBuilder> {

    public NoNavigationBuilder(LayoutFactory layoutFactory) {
        super(layoutFactory, NavigationDefaults.getInstance());
    }

    public static NoNavigationBuilder includeNoNavigationItems() {
        return new NoNavigationBuilder(null).includeBottomNavBar(false).includeTopNavBar(false);
    }

    @Override
    protected NoNavigationBuilder getThis() {
        return this;
    }

    @Override
    public View createNavigationView(LayoutInflater inflater, @Nullable ViewGroup container, View viewAttachedFragment) {
        return viewAttachedFragment;
    }
}
