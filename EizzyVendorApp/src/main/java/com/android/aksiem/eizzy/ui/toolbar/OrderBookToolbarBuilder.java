package com.android.aksiem.eizzy.ui.toolbar;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.aksiem.eizzy.R;
import com.android.aksiem.eizzy.databinding.OrderbookCollapsableToolbarBinding;
import com.android.aksiem.eizzy.ui.toolbar.layoutfactory.IdLayoutFactory;
import com.android.aksiem.eizzy.ui.toolbar.layoutfactory.LayoutFactory;

public class OrderBookToolbarBuilder extends NavigationBuilder<OrderBookToolbarBuilder> {

    OrderbookCollapsableToolbarBinding dataBinding;

    @Override
    protected OrderBookToolbarBuilder getThis() {
        return this;
    }

    @Override
    public View createNavigationView(LayoutInflater inflater, @Nullable ViewGroup container, View viewAttachedFragment) {
        View toolbarView = layoutFactory().produceLayout(inflater, container);
        dataBinding = DataBindingUtil.bind(toolbarView);
        dataBinding.fragmentContainer.addView(viewAttachedFragment);
        prepareToolbar(dataBinding);
        return dataBinding.getRoot();
    }

    private void prepareToolbar(OrderbookCollapsableToolbarBinding dataBinding) {
        Context context = dataBinding.getRoot().getContext();
        dataBinding.setTitleText(getToolbarTitle(context));
        dataBinding.toolbar.setNavigationIcon(getToolbarNavIcon(context));
        dataBinding.toolbar.setNavigationOnClickListener(toolbarNavClickListener);

        dataBinding.appBar.addOnOffsetChangedListener((appBarLayout, verticalOffset) -> {
            dataBinding.tvTitle.setAlpha(Math.abs(verticalOffset / (float)
                    appBarLayout.getTotalScrollRange()));

            dataBinding.tvExpandedTitle.setAlpha(1 - (Math.abs(verticalOffset / (float)
                    appBarLayout.getTotalScrollRange())));
        });

        setupMenu(dataBinding.toolbar);
    }

    public static OrderBookToolbarBuilder mainOrderBookToolbar() {
        return new OrderBookToolbarBuilder
                (new IdLayoutFactory(
                        R.layout.orderbook_collapsable_toolbar));
    }

    public OrderBookToolbarBuilder(LayoutFactory mainLayoutFactory) {
        super(mainLayoutFactory, NavigationDefaults.getInstance());
    }

    public OrderBookToolbarBuilder setInfoItem1(String key, String value) {
        dataBinding.info1Key.setText(key);
        dataBinding.info1Value.setText(value);
        return getThis();
    }

    public OrderBookToolbarBuilder setInfoItem2(String key, String value) {
        dataBinding.info2Key.setText(key);
        dataBinding.info2Value.setText(value);
        return getThis();
    }

}
