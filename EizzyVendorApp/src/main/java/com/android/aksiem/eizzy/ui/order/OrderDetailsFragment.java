package com.android.aksiem.eizzy.ui.order;

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
import com.android.aksiem.eizzy.view.timeline.TimelinePointListAdapter;
import com.android.aksiem.eizzy.vo.OrderDetailItem;

/**
 * Created by pdubey on 05/05/18.
 */

public class OrderDetailsFragment extends NavigationFragment {

    private static final String BUNDLE_ORDER_ITEM = "order_key";

    private OrderDetailItem orderDetailItem;

    protected DataBindingComponent dataBindingComponent = new FragmentDataBindingComponent(this);

    AutoClearedValue<OrderDetailsFragmentBinding> binding;

    AutoClearedValue<TimelinePointListAdapter> ostAdapter;

    public static OrderDetailsFragment create(OrderDetailItem order) {
        Bundle args = new Bundle();
        args.putSerializable(BUNDLE_ORDER_ITEM, order);
        OrderDetailsFragment fragment = new OrderDetailsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public NavigationBuilder buildNavigation() {
        return CollapsableToolbarBuilder.mainCollapsableToolbar()
                .toolbarTitle(orderDetailItem.orderId)
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
        orderDetailItem = getOrderItem(savedInstanceState);
        return wrapNavigationLayout(inflater, container, dataBinding.getRoot());
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        populateData();
    }

    private void populateData() {
//        if (orderDetailItem != null) {
//
//            // Set order tracking view
//            if (orderDetailItem.getOrderTracking() != null
//                    && !orderDetailItem.getOrderTracking().isEmpty()) {
//
//                TimelineConfigBuilder builder = new TimelineConfigBuilder()
//                        .setVerbose(true)
//                        .setVertical(true);
//                TimelinePointListAdapter<OrderStateTransition> ostAdapter =
//                        new TimelinePointListAdapter<>(getContext(), R.layout.timeline_row,
//                                orderDetailItem.getOrderTracking(), builder);
//                this.ostAdapter = new AutoClearedValue<>(this, ostAdapter);
//                binding.get().timelineDetailsContainer.setAdapter(ostAdapter);
//            }
//
//            // Set order breakdown and price breakdown
//            if (orderDetailItem.orderDetails != null) {
//
//                if (orderDetailItem.orderDetails.items != null
//                        && orderDetailItem.orderDetails.items.items != null
//                        && !orderDetailItem.orderDetails.items.items.isEmpty()) {
//
//                    binding.get().orderItemsBreakdown.setItems(orderDetailItem.orderDetails.items.items);
//                }
//
//                if (orderDetailItem.orderDetails.priceComponents != null
//                        && orderDetailItem.orderDetails.priceComponents.items != null
//                        && !orderDetailItem.orderDetails.priceComponents.items.isEmpty()) {
//
//                    binding.get().surchargesBreakdown.setItems(orderDetailItem.orderDetails
//                            .priceComponents.items);
//                }
//            }
//        }
    }

    private OrderDetailItem getOrderItem(@Nullable Bundle args) {
        Bundle bundle = args == null ? getArguments() : args;
        return ((OrderDetailItem) bundle.getSerializable(BUNDLE_ORDER_ITEM));
    }
}
