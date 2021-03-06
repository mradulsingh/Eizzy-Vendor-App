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

import com.android.aksiem.eizzy.R;
import com.android.aksiem.eizzy.app.EizzyActivity;
import com.android.aksiem.eizzy.app.NavigationFragment;
import com.android.aksiem.eizzy.binding.FragmentDataBindingComponent;
import com.android.aksiem.eizzy.databinding.SettlementFragmentBinding;
import com.android.aksiem.eizzy.di.ApplicationContext;
import com.android.aksiem.eizzy.ui.common.NavigationController;
import com.android.aksiem.eizzy.ui.toolbar.CollapsableToolbarBuilder;
import com.android.aksiem.eizzy.ui.toolbar.MenuToastAction;
import com.android.aksiem.eizzy.ui.toolbar.NavigationBuilder;
import com.android.aksiem.eizzy.ui.toolbar.menu.MenuActions;
import com.android.aksiem.eizzy.util.AutoClearedValue;

import javax.inject.Inject;

import static com.android.aksiem.eizzy.ui.toolbar.CollapsableToolbarBuilder.mainCollapsableToolbar;

/**
 * Created by Mradul on 10/06/18.
 */

public class SettlementFragment extends NavigationFragment {

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    private SettlementViewModel settlementViewModel;

    @Inject
    NavigationController navigationController;

    DataBindingComponent dataBindingComponent = new FragmentDataBindingComponent(this);
    AutoClearedValue<SettlementFragmentBinding> binding;

    public static SettlementFragment newInstance() {
        SettlementFragment fragment = new SettlementFragment();
        return fragment;
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
                .toolbarNavIconRes(R.drawable.ic_drawer_menu)
                .setToolbarNavClickListener(v -> {
                    ((EizzyActivity) getActivity()).openDrawer();
                })
                .setOnScrollChangeListener((v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
                    if (v.getChildAt(v.getChildCount() - 1) != null) {
                        if ((scrollY >= (v.getChildAt(v.getChildCount() - 1)
                                .getMeasuredHeight() - v.getMeasuredHeight())) &&
                                scrollY > oldScrollY) {
                            onPageEndReached();
                        }
                    }
                })
                .setSwipeRefreshListener(() -> {
                    onRefresh();
                });
//                .menuRes(ToolbarMenuUtil.generateMenuFrom(R.menu.menu_settlement_fragment),
//                        buildMenuActions());
    }

    private void onRefresh() {
        CollapsableToolbarBuilder toolbarBuilder = (CollapsableToolbarBuilder) getNavigationBuilder();
        int index = binding.get().viewpager.getCurrentItem();
        SettlementPagerAdapter adapter = ((SettlementPagerAdapter) binding.get().viewpager.getAdapter());
        SettlementDurationFragment fragment = adapter.getFragment(index);
        fragment.onRefresh(toolbarBuilder);
    }

    private void onPageEndReached() {
        int index = binding.get().viewpager.getCurrentItem();
        SettlementPagerAdapter adapter = ((SettlementPagerAdapter) binding.get().viewpager.getAdapter());
        SettlementDurationFragment fragment = adapter.getFragment(index);
        fragment.onLoadNextPage();
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
        return wrapNavigationLayout(inflater, container, dataBinding.getRoot());
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        settlementViewModel = ViewModelProviders.of(this, viewModelFactory).get(
                SettlementViewModel.class);
        SettlementPagerAdapter pagerAdapter = new SettlementPagerAdapter(getFragmentManager());
        binding.get().viewpager.setAdapter(pagerAdapter);
        binding.get().viewpager.setOffscreenPageLimit(0);
        binding.get().settlementTabs.setupWithViewPager(binding.get().viewpager);
    }

    @Override
    protected void onBackPressed() {
        getActivity().finish();
    }

}
