package com.android.aksiem.eizzy.ui.order;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
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
import com.android.aksiem.eizzy.databinding.OrderItemsFragmentBinding;
import com.android.aksiem.eizzy.di.ApplicationContext;
import com.android.aksiem.eizzy.ui.common.NavigationController;
import com.android.aksiem.eizzy.ui.common.ToastController;
import com.android.aksiem.eizzy.ui.toolbar.NavigationBuilder;
import com.android.aksiem.eizzy.ui.toolbar.ToolbarMenuUtil;
import com.android.aksiem.eizzy.ui.toolbar.menu.MenuAction;
import com.android.aksiem.eizzy.ui.toolbar.menu.MenuActions;
import com.android.aksiem.eizzy.util.AutoClearedValue;

import java.util.Collections;

import javax.inject.Inject;

import static com.android.aksiem.eizzy.ui.toolbar.CollapsableToolbarBuilder.mainCollapsableToolbar;

/**
 * Created by pdubey on 09/04/18.
 */

public class OrderItemsFragment extends NavigationFragment {

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    private OrderItemsViewModel orderItemsViewModel;

    @Inject
    NavigationController navigationController;

    @Inject
    ToastController toastController;

    DataBindingComponent dataBindingComponent = new FragmentDataBindingComponent(this);
    AutoClearedValue<OrderItemsFragmentBinding> binding;
    AutoClearedValue<OrderItemsAdapter> adapter;

    public static OrderItemsFragment create() {
        return new OrderItemsFragment();
    }


    @Inject
    @ApplicationContext
    Context applicationContext;

    @Override
    public NavigationBuilder buildNavigation() {
        return mainCollapsableToolbar()
                .includeBottomNavBar(true)
                .includeDrawerNav(true)
                .toolbarTitleRes(R.string.screen_title_orders)
                .toolbarNavIconRes(R.drawable.ic_back)
                .setToolbarNavClickListener(v -> onBackPressed())
                .menuRes(ToolbarMenuUtil.generateMenuFrom(R.menu.menu_settlement_fragment),
                        buildMenuActions());
    }

    private MenuActions buildMenuActions() {
        return new MenuActions.Builder()
                .action(R.id.nav_checkout, new MenuAction() {
                    @Override
                    public void execute() {
                        toastController.showSuccessToast("test message");
                        //navigationController.openOrderSortFilterDialogFragment();
                    }
                })
                .build();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        OrderItemsFragmentBinding dataBinding = DataBindingUtil.inflate(inflater,
                R.layout.order_items_fragment, container, false);
        binding = new AutoClearedValue<>(this, dataBinding);
        return wrapNavigationLayout(inflater, container, dataBinding.getRoot());
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        orderItemsViewModel = ViewModelProviders.of(this, viewModelFactory).get(
                OrderItemsViewModel.class);
        initOrdersList();
        OrderItemsAdapter adapter = new OrderItemsAdapter(dataBindingComponent,
                orderItem -> {
                    //TODO
                });
        this.adapter = new AutoClearedValue<>(this, adapter);
        binding.get().orderList.setAdapter(adapter);
    }

    private void initOrdersList() {
        orderItemsViewModel.getOrderItems().observe(this, listResource -> {
            if (listResource != null && listResource.data != null) {
                adapter.get().replace(listResource.data);
            } else {
                adapter.get().replace(Collections.emptyList());
            }
        });
    }


}
