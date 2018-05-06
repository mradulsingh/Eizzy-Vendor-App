package com.android.aksiem.eizzy.ui.toolbar;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.aksiem.eizzy.R;
import com.android.aksiem.eizzy.databinding.AppToolbarBinding;
import com.android.aksiem.eizzy.databinding.CollapsableToolbarBottomActionBarBinding;
import com.android.aksiem.eizzy.ui.common.ClickActionHandler;
import com.android.aksiem.eizzy.ui.toolbar.layoutfactory.IdLayoutFactory;
import com.android.aksiem.eizzy.ui.toolbar.layoutfactory.LayoutFactory;

public class AppToolbarBuilder extends NavigationBuilder<AppToolbarBuilder> {

    private final LayoutFactory bottomActionBarLayoutFactory;

    private ClickActionHandler bottomActionClickHandler;

    private String bottomActionTitle;

    private int bottomActionTitleRes;

    @Override
    protected AppToolbarBuilder getThis() {
        return this;
    }

    @Override
    public View createNavigationView(LayoutInflater inflater, @Nullable ViewGroup container, View viewAttachedFragment) {
        View toolbarView = layoutFactory().produceLayout(inflater, container);
        AppToolbarBinding dataBinding = DataBindingUtil.bind(toolbarView);
        dataBinding.fragmentContainer.addView(viewAttachedFragment);
        setupBottomActionBar(inflater, dataBinding);
        prepareToolbar(dataBinding);
        return dataBinding.getRoot();
    }

    private void setupBottomActionBar(LayoutInflater inflater, AppToolbarBinding dataBinding) {
        if (bottomActionBarLayoutFactory != null) {
            Context context = dataBinding.getRoot().getContext();
            dataBinding.fragmentBottomLayoutContainer.setVisibility(View.VISIBLE);
            View bottomActionBarLayout = bottomActionBarLayoutFactory.produceLayout(inflater, null);
            CollapsableToolbarBottomActionBarBinding actionBarDataBinding = DataBindingUtil.bind(bottomActionBarLayout);
            if (bottomActionTitleRes != 0) {
                actionBarDataBinding.setActionTitle(context.getString(bottomActionTitleRes));
            } else if (bottomActionTitle != null) {
                actionBarDataBinding.setActionTitle(bottomActionTitle.toString());
            }
            actionBarDataBinding.setOnClickHandler(bottomActionClickHandler);
            dataBinding.fragmentBottomLayoutContainer.addView(actionBarDataBinding.getRoot());
        }
    }

    private void prepareToolbar(AppToolbarBinding dataBinding) {
        Context context = dataBinding.getRoot().getContext();
        dataBinding.setTitleText(getToolbarTitle(context));
        dataBinding.toolbar.setNavigationIcon(getToolbarNavIcon(context));
        dataBinding.toolbar.setNavigationOnClickListener(toolbarNavClickListener);
        setupMenu(dataBinding.toolbar);
    }

    public static AppToolbarBuilder mainToolbar() {
        return new AppToolbarBuilder
                (new IdLayoutFactory(
                        R.layout.app_toolbar));
    }

    public static AppToolbarBuilder mainToolbarWithBottomAction() {
        return new AppToolbarBuilder
                (new IdLayoutFactory(
                        R.layout.app_toolbar), new IdLayoutFactory(
                        R.layout.collapsable_toolbar_bottom_action_bar));
    }

    public AppToolbarBuilder setBottomActionClickHandler(ClickActionHandler bottomActionClickHandler) {
        this.bottomActionClickHandler = bottomActionClickHandler;
        return getThis();
    }

    public AppToolbarBuilder setBottomActionTitle(String bottomActionTitle) {
        this.bottomActionTitle = bottomActionTitle;
        return getThis();
    }

    public AppToolbarBuilder setBottomActionTitleRes(int bottomActionTitleRes) {
        this.bottomActionTitleRes = bottomActionTitleRes;
        return getThis();
    }

    public AppToolbarBuilder(LayoutFactory mainLayoutFactory) {
        super(mainLayoutFactory, NavigationDefaults.getInstance());
        this.bottomActionBarLayoutFactory = null;
    }

    public AppToolbarBuilder(LayoutFactory mainLayoutFactory, LayoutFactory bottomActionBarLayoutFactory) {
        super(mainLayoutFactory, NavigationDefaults.getInstance());
        this.bottomActionBarLayoutFactory = bottomActionBarLayoutFactory;
    }

}
