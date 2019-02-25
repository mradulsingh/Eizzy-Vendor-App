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
import com.android.aksiem.eizzy.ui.common.ToastController;
import com.android.aksiem.eizzy.ui.toolbar.CollapsableToolbarBuilder;
import com.android.aksiem.eizzy.ui.toolbar.NavigationBuilder;
import com.android.aksiem.eizzy.ui.toolbar.OrderBookToolbarBuilder;
import com.android.aksiem.eizzy.util.AutoClearedValue;
import com.android.aksiem.eizzy.view.timeline.TimelineConfigBuilder;
import com.android.aksiem.eizzy.view.timeline.TimelinePointListAdapter;
import com.android.aksiem.eizzy.vo.order.OrderDetailItem;
import com.android.aksiem.eizzy.vo.order.OrderStateTransition;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by Mradul on 10/06/18.
 */

public class OrderDetailsFragment extends NavigationFragment {

    private static final String BUNDLE_ORDER_ID = "order_id_key";

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    @Inject
    ToastController toastController;

    OrderBookToolbarBuilder orderBookToolbarBuilder;

    private String orderId;

    private OrderDetailItem orderDetailItem;

    private OrderItemsViewModel orderItemsViewModel;

    protected DataBindingComponent dataBindingComponent = new FragmentDataBindingComponent(this);

    AutoClearedValue<OrderDetailsFragmentBinding> binding;

    AutoClearedValue<TimelinePointListAdapter> ostAdapter;

    public static OrderDetailsFragment create(String orderId) {
        Bundle args = new Bundle();
        args.putSerializable(BUNDLE_ORDER_ID, orderId);
        OrderDetailsFragment fragment = new OrderDetailsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public NavigationBuilder buildNavigation() {
        orderBookToolbarBuilder = OrderBookToolbarBuilder.mainOrderBookToolbar()
                .toolbarTitle("#" + orderId)
                .toolbarNavIconRes(R.drawable.ic_back)
                .setToolbarNavClickListener(v -> onBackPressed());
        return orderBookToolbarBuilder;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        OrderDetailsFragmentBinding dataBinding = DataBindingUtil.inflate(inflater,
                R.layout.order_details_fragment, container, false,
                dataBindingComponent);
        binding = new AutoClearedValue<>(this, dataBinding);
        extractOrderId(savedInstanceState);
        return wrapNavigationLayout(inflater, container, dataBinding.getRoot());
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        orderItemsViewModel = ViewModelProviders.of(this, viewModelFactory).get(
                OrderItemsViewModel.class);
        fetchOrderDetails();
    }

    private void extractOrderId(@Nullable Bundle args) {
        Bundle bundle = args == null ? getArguments() : args;
        orderId = bundle.getString(BUNDLE_ORDER_ID);
    }

    private void fetchOrderDetails() {
        if (orderId != null) {
            List<String> orderIds = new ArrayList<>();
            orderIds.add(orderId);
            orderItemsViewModel.setOrderIdToFetch(orderId);
            orderItemsViewModel.getDetailedItem().observe(this, (resource) -> {
                binding.get().setResource(resource);
                switch (resource.status) {
                    case LOADING:
                        break;
                    case SUCCESS:
                        orderDetailItem = resource.data.data;
                        populateData();
                        break;
                    case ERROR:
                        toastController.showErrorToast(resource.message);
                        break;
                }
            });
        }
    }

    private void populateData() {
        if (orderDetailItem != null) {

            populateToolbar();

            binding.get().setItem(orderDetailItem);
            populateOrderTracking();

            switch (orderDetailItem.orderType) {
                case GENERAL_ORDER:
                    // TODO
                    break;
                case CUSTOM_ORDER:
                    // TODO
                    break;
                case BULK_ORDER:
                    // TODO
                    break;
            }
        }
    }

    private void populateToolbar() {
        orderBookToolbarBuilder.setInfoItem1("Status", orderDetailItem.getCurrentState().state
                .getStateName());
        String driver = orderDetailItem.getDriverName();
        driver = (driver == null || driver.length() == 0) ? "Not Assigned" : driver;
        orderBookToolbarBuilder.setInfoItem2("Driver", driver);
    }

    private void populateOrderTracking() {
        if (orderDetailItem.getOrderTracking() != null
                && !orderDetailItem.getOrderTracking().isEmpty()) {

            TimelineConfigBuilder builder = new TimelineConfigBuilder()
                    .setVerbose(true)
                    .setVertical(true);
            TimelinePointListAdapter<OrderStateTransition> ostAdapter =
                    new TimelinePointListAdapter<>(getContext(), R.layout.timeline_row,
                            orderDetailItem.getOrderTracking(), builder);
            this.ostAdapter = new AutoClearedValue<>(this, ostAdapter);
            binding.get().timelineDetailsContainer.setAdapter(ostAdapter);
        }
    }
}
