package com.android.aksiem.eizzy.ui.toolbar;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.aksiem.eizzy.R;
import com.android.aksiem.eizzy.databinding.CollapsableToolbarBinding;
import com.android.aksiem.eizzy.databinding.CollapsableToolbarBottomActionBarBinding;
import com.android.aksiem.eizzy.ui.common.ClickActionHandler;
import com.android.aksiem.eizzy.ui.toolbar.layoutfactory.IdLayoutFactory;
import com.android.aksiem.eizzy.ui.toolbar.layoutfactory.LayoutFactory;

public class CollapsableToolbarBuilder extends NavigationBuilder<CollapsableToolbarBuilder> {

    private final LayoutFactory bottomActionBarLayoutFactory;

    private CollapsableToolbarBottomActionBarBinding actionBarDataBinding;

    private ClickActionHandler bottomActionClickHandler;

    private String bottomActionTitle;

    private int bottomActionTitleRes;

    private NestedScrollView.OnScrollChangeListener onScrollChangeListener;

    private SwipeRefreshLayout.OnRefreshListener swipeRefreshListener = null;

    private CollapsableToolbarBinding binding;

    @Override
    protected CollapsableToolbarBuilder getThis() {
        return this;
    }

    @Override
    public View createNavigationView(LayoutInflater inflater, @Nullable ViewGroup container, View viewAttachedFragment) {
        View toolbarView = layoutFactory().produceLayout(inflater, container);
        CollapsableToolbarBinding dataBinding = DataBindingUtil.bind(toolbarView);
        dataBinding.fragmentContainer.addView(viewAttachedFragment);
        setupBottomActionBar(inflater, dataBinding);
        prepareToolbar(dataBinding);
        binding = dataBinding;
        return dataBinding.getRoot();
    }

    private void setupBottomActionBar(LayoutInflater inflater, CollapsableToolbarBinding dataBinding) {
        if (bottomActionBarLayoutFactory != null) {
            Context context = dataBinding.getRoot().getContext();
            dataBinding.fragmentBottomLayoutContainer.setVisibility(View.VISIBLE);
            View bottomActionBarLayout = bottomActionBarLayoutFactory.produceLayout(inflater, null);
            actionBarDataBinding = DataBindingUtil.bind(bottomActionBarLayout);
            if (bottomActionTitleRes != 0) {
                actionBarDataBinding.setActionTitle(context.getString(bottomActionTitleRes));
            } else if (bottomActionTitle != null) {
                actionBarDataBinding.setActionTitle(bottomActionTitle.toString());
            }
            actionBarDataBinding.setOnClickHandler(bottomActionClickHandler);
            dataBinding.fragmentBottomLayoutContainer.addView(actionBarDataBinding.getRoot());
        }
    }

    private void prepareToolbar(CollapsableToolbarBinding dataBinding) {
        Context context = dataBinding.getRoot().getContext();
        dataBinding.setTitleText(getToolbarTitle(context));
        dataBinding.setSubTitleText(getToolbarSubTitle(context));
        dataBinding.toolbar.setNavigationIcon(getToolbarNavIcon(context));
        dataBinding.toolbar.setNavigationOnClickListener(toolbarNavClickListener);

        dataBinding.appBar.addOnOffsetChangedListener((appBarLayout, verticalOffset) -> {
            dataBinding.tvTitle.setAlpha(Math.abs(verticalOffset / (float)
                    appBarLayout.getTotalScrollRange()));

            dataBinding.tvExpandedTitle.setAlpha(1 - (Math.abs(verticalOffset / (float)
                    appBarLayout.getTotalScrollRange())));
        });

        setupMenu(dataBinding.toolbar);

        if (onScrollChangeListener != null) {
            dataBinding.scrollviewContainer.setOnScrollChangeListener(onScrollChangeListener);
        }

        if (swipeRefreshListener != null) {
            dataBinding.setIsSwipeRefreshEnabled(true);
            dataBinding.swipeContainer.setOnRefreshListener(swipeRefreshListener);
        } else {
            dataBinding.setIsSwipeRefreshEnabled(false);
            dataBinding.setIsRefreshing(false);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (actionBarDataBinding != null) {
            actionBarDataBinding.progressButton.dispose();
            actionBarDataBinding = null;
        }
    }

    public static CollapsableToolbarBuilder mainCollapsableToolbar() {
        return new CollapsableToolbarBuilder
                (new IdLayoutFactory(
                        R.layout.collapsable_toolbar));
    }

    public static CollapsableToolbarBuilder mainCollapsableToolbarWithBottomAction() {
        return new CollapsableToolbarBuilder
                (new IdLayoutFactory(
                        R.layout.collapsable_toolbar), new IdLayoutFactory(
                        R.layout.collapsable_toolbar_bottom_action_bar));
    }

    public CollapsableToolbarBuilder setBottomActionClickHandler(ClickActionHandler bottomActionClickHandler) {
        this.bottomActionClickHandler = bottomActionClickHandler;
        return getThis();
    }

    public CollapsableToolbarBuilder setBottomActionTitle(String bottomActionTitle) {
        this.bottomActionTitle = bottomActionTitle;
        return getThis();
    }

    public CollapsableToolbarBuilder setBottomActionTitleRes(int bottomActionTitleRes) {
        this.bottomActionTitleRes = bottomActionTitleRes;
        return getThis();
    }

    public CollapsableToolbarBuilder setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener onScrollChangeListener) {
        this.onScrollChangeListener = onScrollChangeListener;
        return getThis();
    }

    public CollapsableToolbarBuilder setSwipeRefreshListener(SwipeRefreshLayout.OnRefreshListener swipeRefreshListener) {
        this.swipeRefreshListener = swipeRefreshListener;
        return getThis();
    }

    public CollapsableToolbarBuilder(LayoutFactory mainLayoutFactory) {
        super(mainLayoutFactory, NavigationDefaults.getInstance());
        this.bottomActionBarLayoutFactory = null;
    }

    public CollapsableToolbarBuilder(LayoutFactory mainLayoutFactory, LayoutFactory bottomActionBarLayoutFactory) {
        super(mainLayoutFactory, NavigationDefaults.getInstance());
        this.bottomActionBarLayoutFactory = bottomActionBarLayoutFactory;
    }

    public void setRefreshing(boolean isRefreshing) {
        binding.setIsRefreshing(isRefreshing);
    }

}
