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
import com.android.aksiem.eizzy.app.AppResourceManager;
import com.android.aksiem.eizzy.app.NavigationFragment;
import com.android.aksiem.eizzy.binding.FragmentDataBindingComponent;
import com.android.aksiem.eizzy.databinding.OrderDetailsFragmentBinding;
import com.android.aksiem.eizzy.ui.toolbar.CollapsableToolbarBuilder;
import com.android.aksiem.eizzy.ui.toolbar.NavigationBuilder;
import com.android.aksiem.eizzy.util.AutoClearedValue;
import com.android.aksiem.eizzy.view.timeline.TimelineConfigBuilder;
import com.android.aksiem.eizzy.view.timeline.TimelinePoint;
import com.android.aksiem.eizzy.view.timeline.TimelinePointListAdapter;
import com.android.aksiem.eizzy.vo.OrderItem;
import com.android.aksiem.eizzy.vo.support.order.OrderStateTransition;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by pdubey on 05/05/18.
 */

public class OrderDetailsFragment extends NavigationFragment {

    private static final String BUNDLE_ORDER_ID_KEY = "order_id_key";

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    private OrderItem orderItem;

    protected DataBindingComponent dataBindingComponent = new FragmentDataBindingComponent(this);

    AutoClearedValue<OrderDetailsFragmentBinding> binding;

    AutoClearedValue<TimelinePointListAdapter> ostAdapter;

    OrderItemsViewModel viewModel;

    public static OrderDetailsFragment create(String orderId) {
        Bundle args = new Bundle();
        args.putString(BUNDLE_ORDER_ID_KEY, orderId);
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
        return wrapNavigationLayout(inflater, container, dataBinding.getRoot());
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(OrderItemsViewModel.class);
        populateData(savedInstanceState);
    }

    private void populateData(@Nullable Bundle args) {
        TimelineConfigBuilder builder = new TimelineConfigBuilder()
                .setVerbose(true)
                .setVertical(true);
        TimelinePointListAdapter<OrderStateTransition> ostAdapter = new TimelinePointListAdapter<>(builder);
        this.ostAdapter = new AutoClearedValue<>(this, ostAdapter);
        getOrderItem(args);
        binding.get().timelineDetailsContainer.setAdapter(ostAdapter);
    }

    private void getOrderItem(@Nullable Bundle args) {
        Bundle bundle = args == null ? getArguments() : args;
        String orderId = bundle.getString(BUNDLE_ORDER_ID_KEY);
        if (orderId != null) {
            ArrayList<String> orderIds = new ArrayList<>();
            orderIds.add(orderId);
            viewModel.setOrderIds(orderIds);
            viewModel.getOrderItems().observe(this, listResource -> {
                if (listResource != null
                        && listResource.data != null
                        && listResource.data.get(0) != null
                        && listResource.data.get(0).item != null
                        && listResource.data.get(0).item.getOrderTracking() != null) {


                    List<OrderStateTransition> orderTracking = listResource.data
                            .get(0).item.getOrderTracking();

                    if (orderTracking != null) {
                        ostAdapter.get().replace(orderTracking);
                    } else {
                        ostAdapter.get().replace(Collections.emptyList());
                    }

                }
            });
        }
    }
}
