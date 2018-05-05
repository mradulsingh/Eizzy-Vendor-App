package com.android.aksiem.eizzy.ui.toolbar;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.android.aksiem.eizzy.R;
import com.android.aksiem.eizzy.databinding.CollapsableToolbarBinding;
import com.android.aksiem.eizzy.databinding.CollapsableToolbarBottomActionBarBinding;
import com.android.aksiem.eizzy.ui.toolbar.layoutfactory.IdLayoutFactory;
import com.android.aksiem.eizzy.ui.toolbar.layoutfactory.LayoutFactory;
import com.android.aksiem.eizzy.ui.toolbar.menu.MenuActions;

public class CollapsableToolbarBuilder extends NavigationBuilder<CollapsableToolbarBuilder> {

    private final LayoutFactory bottomActionBarLayoutFactory;

    private ClickActionHandler bottomActionClickHandler;

    private String bottomActionTitle;

    private int botootmActionTitleRes;

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
        return dataBinding.getRoot();
    }

    private void setupBottomActionBar(LayoutInflater inflater, CollapsableToolbarBinding dataBinding) {
        if (bottomActionBarLayoutFactory != null) {
            Context context = dataBinding.getRoot().getContext();
            dataBinding.fragmentBottomLayoutContainer.setVisibility(View.VISIBLE);
            View bottomActionBarLayout = bottomActionBarLayoutFactory.produceLayout(inflater, null);
            CollapsableToolbarBottomActionBarBinding actionBarDataBinding = DataBindingUtil.bind(bottomActionBarLayout);
            if (botootmActionTitleRes != 0) {
                actionBarDataBinding.setActionTitle(context.getString(botootmActionTitleRes));
            } else if (bottomActionTitle != null) {
                actionBarDataBinding.setActionTitle(bottomActionTitle.toString());
            }
            actionBarDataBinding.setOnClickHandler(bottomActionClickHandler);
            dataBinding.fragmentBottomLayoutContainer.addView(actionBarDataBinding.getRoot());
        }
    }

    private void prepareToolbar(CollapsableToolbarBinding dataBinding) {
        Context context = dataBinding.getRoot().getContext();

        if (toolbarTitleRes != 0) {
            dataBinding.setTitleText(context.getString(toolbarTitleRes));
        } else if (toolbarTitle != null) {
            dataBinding.setTitleText(toolbarTitle.toString());
        }

        if (toolbarSubtitleRes != 0) {
            dataBinding.setSubTitleText(context.getString(toolbarSubtitleRes));
        } else if (toolbarSubtitle != null) {
            dataBinding.setSubTitleText(toolbarSubtitle.toString());
        }

        if (toolbarNavIconRes != 0) {
            dataBinding.toolbar.setNavigationIcon(toolbarNavIconRes);
        } else if (toolbarNavIcon != null) {
            dataBinding.toolbar.setNavigationIcon(toolbarNavIcon);
        }

        dataBinding.appBar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                dataBinding.tvTitle.setAlpha(Math.abs(verticalOffset / (float)
                        appBarLayout.getTotalScrollRange()));

                dataBinding.tvExpandedTitle.setAlpha(1 - (Math.abs(verticalOffset / (float)
                        appBarLayout.getTotalScrollRange())));
            }
        });

        dataBinding.toolbar.setNavigationOnClickListener(toolbarNavClickListener);

        Menu menu = dataBinding.toolbar.getMenu();
        if (menu != null) {
            menu.clear();
        }
        if (!menuRes.isEmpty()) {
            final MenuActions actions = menuActions.build();
            for (Integer menuRes : menuRes) {
                dataBinding.toolbar.inflateMenu(menuRes);
            }
            dataBinding.toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    return actions.onMenuItemClick(item);
                }
            });
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

    public CollapsableToolbarBuilder setBotootmActionTitleRes(int botootmActionTitleRes) {
        this.botootmActionTitleRes = botootmActionTitleRes;
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

}
