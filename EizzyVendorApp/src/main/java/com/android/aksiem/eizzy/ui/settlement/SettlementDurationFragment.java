package com.android.aksiem.eizzy.ui.settlement;

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
import android.widget.Toast;

import com.android.aksiem.eizzy.R;
import com.android.aksiem.eizzy.app.NavigationFragment;
import com.android.aksiem.eizzy.binding.FragmentDataBindingComponent;
import com.android.aksiem.eizzy.databinding.SettlementDurationFragmentBinding;
import com.android.aksiem.eizzy.di.ApplicationContext;
import com.android.aksiem.eizzy.ui.common.NavigationController;
import com.android.aksiem.eizzy.ui.toolbar.MenuToastAction;
import com.android.aksiem.eizzy.ui.toolbar.NavigationBuilder;
import com.android.aksiem.eizzy.ui.toolbar.ToolbarMenuUtil;
import com.android.aksiem.eizzy.ui.toolbar.menu.MenuActions;
import com.android.aksiem.eizzy.util.AutoClearedValue;

import java.util.Collections;

import javax.inject.Inject;

import static com.android.aksiem.eizzy.ui.toolbar.CollapsableToolbarBuilder.mainCollapsableToolbar;


public class SettlementDurationFragment extends NavigationFragment {

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    private SettlementViewModel settlementViewModel;

    @Inject
    NavigationController navigationController;

    DataBindingComponent dataBindingComponent = new FragmentDataBindingComponent(this);
    AutoClearedValue<SettlementDurationFragmentBinding> binding;
    AutoClearedValue<SettlementItemAdapter> adapter;


    public static SettlementDurationFragment create() {
        return new SettlementDurationFragment();
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
        SettlementDurationFragmentBinding dataBinding = DataBindingUtil.inflate(inflater,
                R.layout.settlement_duration_fragment, container, false);
        binding = new AutoClearedValue<>(this, dataBinding);

        Toast.makeText(applicationContext, getArguments().getInt("selected_duration") + "", Toast.LENGTH_SHORT).show();


        return wrapNavigationLayout(inflater, container, dataBinding.getRoot());
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        settlementViewModel = ViewModelProviders.of(this, viewModelFactory).get(
                SettlementViewModel.class);
        initSettlementItemList();
        SettlementItemAdapter adapter = new SettlementItemAdapter(dataBindingComponent,
                settlementItem -> {
                    //TODO
                });
        this.adapter = new AutoClearedValue<>(this, adapter);


    }

    private void initSettlementItemList() {
        settlementViewModel.getOrderItems().observe(this, listResource -> {
            if (listResource != null && listResource.data != null) {
                //adapter.get().replace(listResource.data);
            } else {
                adapter.get().replace(Collections.emptyList());
            }
        });
    }


}
