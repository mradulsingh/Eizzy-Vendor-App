package com.android.aksiem.eizzy.ui.toolbar;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
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
            dataBinding.setTitle(context.getString(toolbarTitleRes));
        } else {
            dataBinding.setTitle(toolbarTitle.toString());
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
