package com.android.aksiem.eizzy.ui.order;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.aksiem.eizzy.R;
import com.android.aksiem.eizzy.app.NavigationFragment;
import com.android.aksiem.eizzy.binding.FragmentDataBindingComponent;
import com.android.aksiem.eizzy.databinding.OrderDetailsFragmentBinding;
import com.android.aksiem.eizzy.ui.toolbar.CollapsableToolbarBuilder;
import com.android.aksiem.eizzy.ui.toolbar.NavigationBuilder;
import com.android.aksiem.eizzy.util.AutoClearedValue;
import com.android.aksiem.eizzy.view.timeline.TimelineConfigBuilder;
import com.android.aksiem.eizzy.view.timeline.TimelinePointListAdapter;
import com.android.aksiem.eizzy.vo.OrderItem;
import com.android.aksiem.eizzy.vo.support.TitledAndSubtitled;
import com.android.aksiem.eizzy.vo.support.order.OrderStateTransition;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by pdubey on 05/05/18.
 */

public class OrderDetailsFragment extends NavigationFragment {

    private static final String BUNDLE_ORDER_ITEM = "order_key";

    private OrderItem orderItem;

    protected DataBindingComponent dataBindingComponent = new FragmentDataBindingComponent(this);

    AutoClearedValue<OrderDetailsFragmentBinding> binding;

    AutoClearedValue<TimelinePointListAdapter> ostAdapter;

    public static OrderDetailsFragment create(OrderItem order) {
        Bundle args = new Bundle();
        args.putSerializable(BUNDLE_ORDER_ITEM, order);
        OrderDetailsFragment fragment = new OrderDetailsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public NavigationBuilder buildNavigation() {
        return CollapsableToolbarBuilder.mainCollapsableToolbar()
                .toolbarTitle(orderItem.orderId)
                .toolbarSubtitle("Under Construction")
                .setToolbarNavClickListener(v -> onBackPressed());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        OrderDetailsFragmentBinding dataBinding = DataBindingUtil.inflate(inflater,
                R.layout.order_details_fragment, container,
                false, dataBindingComponent);
        binding = new AutoClearedValue<>(this, dataBinding);
        orderItem = getOrderItem(savedInstanceState);
        return wrapNavigationLayout(inflater, container, dataBinding.getRoot());
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        populateData();
    }

    private void populateData() {
        if (orderItem != null) {

            // Set order tracking view
            if (orderItem.getOrderTracking() != null
                    && !orderItem.getOrderTracking().isEmpty()) {

                TimelineConfigBuilder builder = new TimelineConfigBuilder()
                        .setVerbose(true)
                        .setVertical(true);
                TimelinePointListAdapter<OrderStateTransition> ostAdapter =
                        new TimelinePointListAdapter<>(getContext(), R.layout.timeline_row,
                                orderItem.getOrderTracking(), builder);
                this.ostAdapter = new AutoClearedValue<>(this, ostAdapter);
                binding.get().timelineDetailsContainer.setAdapter(ostAdapter);
            }

            // Set order breakdown and price breakdown
            if (orderItem.orderDetails != null) {

                if (orderItem.orderDetails.items != null
                        && orderItem.orderDetails.items.items != null
                        && !orderItem.orderDetails.items.items.isEmpty()) {

                    binding.get().orderItemsBreakdown.setItems(orderItem.orderDetails.items.items);
                }

                if (orderItem.orderDetails.priceComponents != null
                        && orderItem.orderDetails.priceComponents.items != null
                        && !orderItem.orderDetails.priceComponents.items.isEmpty()) {

                    binding.get().surchargesBreakdown.setItems(orderItem.orderDetails
                            .priceComponents.items);
                }
            }
        }
    }

    private OrderItem getOrderItem(@Nullable Bundle args) {
        Bundle bundle = args == null ? getArguments() : args;
        return ((OrderItem) bundle.getSerializable(BUNDLE_ORDER_ITEM));
    }
}
