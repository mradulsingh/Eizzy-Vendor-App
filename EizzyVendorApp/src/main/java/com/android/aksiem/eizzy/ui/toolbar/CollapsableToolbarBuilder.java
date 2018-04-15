package com.android.aksiem.eizzy.ui.toolbar;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.aksiem.eizzy.R;
import com.android.aksiem.eizzy.databinding.CollapsableToolbarBinding;
import com.android.aksiem.eizzy.ui.toolbar.layoutfactory.IdLayoutFactory;
import com.android.aksiem.eizzy.ui.toolbar.layoutfactory.LayoutFactory;

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
        } else {
            dataBinding.setTitleText(toolbarTitle.toString());
        }

        if (toolbarSubtitleRes != 0) {
            dataBinding.setSubTitleText(context.getString(toolbarSubtitleRes));
        } else {
            dataBinding.setSubTitleText(toolbarSubtitle.toString());
        }

        if (toolbarNavIconRes != 0) {
            dataBinding.toolbar.setNavigationIcon(toolbarNavIconRes);
        } else {
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

        dataBinding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
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
