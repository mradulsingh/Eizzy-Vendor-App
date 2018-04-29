package com.android.aksiem.eizzy.ui.settlement;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.aksiem.eizzy.R;
import com.android.aksiem.eizzy.app.NavigationFragment;
import com.android.aksiem.eizzy.binding.FragmentDataBindingComponent;
import com.android.aksiem.eizzy.databinding.SettlementFragmentBinding;
import com.android.aksiem.eizzy.di.ApplicationContext;
import com.android.aksiem.eizzy.ui.common.NavigationController;
import com.android.aksiem.eizzy.ui.order.OrderItemsViewModel;
import com.android.aksiem.eizzy.ui.toolbar.MenuToastAction;
import com.android.aksiem.eizzy.ui.toolbar.NavigationBuilder;
import com.android.aksiem.eizzy.ui.toolbar.ToolbarMenuUtil;
import com.android.aksiem.eizzy.ui.toolbar.menu.MenuActions;
import com.android.aksiem.eizzy.util.AutoClearedValue;

import java.util.Collections;

import javax.inject.Inject;

import static com.android.aksiem.eizzy.ui.toolbar.CollapsableToolbarBuilder.mainCollapsableToolbar;


public class SettlementFragment extends NavigationFragment {

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    private SettlementViewModel settlementViewModel;

    @Inject
    NavigationController navigationController;

    DataBindingComponent dataBindingComponent = new FragmentDataBindingComponent(this);
    AutoClearedValue<SettlementFragmentBinding> binding;
    AutoClearedValue<SettlementItemAdapter> adapter;

    public static SettlementFragment create() {
        return new SettlementFragment();
    }


    @Inject
    @ApplicationContext
    Context applicationContext;

    @Override
    public NavigationBuilder buildNavigation() {
        return mainCollapsableToolbar()
                .includeBottomNavBar(true)
                .includeDrawerNav(true)
                .toolbarTitleRes(R.string.screen_title_settlements)
                .toolbarNavIconRes(R.drawable.ic_back)
                .setToolbarNavClickListener(v -> onBackPressed())
                .menuRes(ToolbarMenuUtil.generateMenuFrom(R.menu.menu_settlement_fragment),
                        buildMenuActions());
    }

    private MenuActions buildMenuActions() {
        return new MenuActions.Builder()
                .action(R.id.nav_checkout, new MenuToastAction(applicationContext,
                        "Do Checkout"))
                .build();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        SettlementFragmentBinding dataBinding = DataBindingUtil.inflate(inflater,
                R.layout.settlement_fragment, container, false);
        binding = new AutoClearedValue<>(this, dataBinding);


        binding.get().settlementTabs.addTab(binding.get().settlementTabs.newTab().setText("1 DAY"));
        binding.get().settlementTabs.addTab(binding.get().settlementTabs.newTab().setText("1 WEEK"));
        binding.get().settlementTabs.addTab(binding.get().settlementTabs.newTab().setText("1 MONTH"));

        binding.get().viewpager.setAdapter(new SettlementPagerAdapter(getFragmentManager(), binding.get().settlementTabs.getTabCount()));
        //binding.get().viewpager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(binding.get().settlementTabs));
        //binding.get().settlementTabs.setupWithViewPager(binding.get().viewpager);



        binding.get().settlementTabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                binding.get().viewpager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        return wrapNavigationLayout(inflater, container, dataBinding.getRoot());
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        settlementViewModel = ViewModelProviders.of(this, viewModelFactory).get(
                SettlementViewModel.class);
        initOrdersList();
        SettlementItemAdapter adapter = new SettlementItemAdapter(dataBindingComponent,
                orderItem -> {
                    //TODO
                });
        this.adapter = new AutoClearedValue<>(this, adapter);


    }

    private void initOrdersList() {
        settlementViewModel.getOrderItems().observe(this, listResource -> {
            if (listResource != null && listResource.data != null) {
                adapter.get().replace(listResource.data);
            } else {
                adapter.get().replace(Collections.emptyList());
            }
        });
    }


}
