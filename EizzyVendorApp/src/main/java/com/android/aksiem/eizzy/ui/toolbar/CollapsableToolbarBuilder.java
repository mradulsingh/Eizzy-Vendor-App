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
import com.android.aksiem.eizzy.ui.toolbar.layoutfactory.IdLayoutFactory;
import com.android.aksiem.eizzy.ui.toolbar.layoutfactory.LayoutFactory;
import com.android.aksiem.eizzy.ui.toolbar.menu.MenuActions;

public class CollapsableToolbarBuilder extends NavigationBuilder<CollapsableToolbarBuilder> {

    @Override
    protected CollapsableToolbarBuilder getThis() {
        return this;
    }

    @Override
    public View createNavigationView(LayoutInflater inflater, @Nullable ViewGroup container, View viewAttachedFragment) {
        View toolbarView = layoutFactory().produceLayout(inflater, container);
        CollapsableToolbarBinding dataBinding = DataBindingUtil.bind(toolbarView);
        dataBinding.fragmentContainer.addView(viewAttachedFragment);
        prepareToolbar(dataBinding);
        return dataBinding.getRoot();
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

    public CollapsableToolbarBuilder(LayoutFactory layoutFactory) {
        super(layoutFactory, NavigationDefaults.getInstance());
    }
}
