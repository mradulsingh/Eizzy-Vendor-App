package com.android.aksiem.eizzy.ui.order;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.aksiem.eizzy.R;
import com.android.aksiem.eizzy.app.AppResourceManager;
import com.android.aksiem.eizzy.app.EizzyActivity;
import com.android.aksiem.eizzy.app.NavigationFragment;
import com.android.aksiem.eizzy.binding.FragmentDataBindingComponent;
import com.android.aksiem.eizzy.databinding.OrderItemsFragmentBinding;
import com.android.aksiem.eizzy.di.ApplicationContext;
import com.android.aksiem.eizzy.ui.common.NavigationController;
import com.android.aksiem.eizzy.ui.common.SortFilterDialogFragment;
import com.android.aksiem.eizzy.ui.common.ToastController;
import com.android.aksiem.eizzy.ui.toolbar.CollapsableToolbarBuilder;
import com.android.aksiem.eizzy.ui.toolbar.NavigationBuilder;
import com.android.aksiem.eizzy.ui.toolbar.ToolbarMenuUtil;
import com.android.aksiem.eizzy.ui.toolbar.menu.MenuActions;
import com.android.aksiem.eizzy.util.AutoClearedValue;
import com.android.aksiem.eizzy.vo.Resource;
import com.android.aksiem.eizzy.vo.order.OrderListItem;
import com.android.aksiem.eizzy.vo.order.TimestampedItemWrapper;
import com.cooltechworks.views.shimmer.ShimmerRecyclerView;

import java.util.List;

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

    @Inject
    AppResourceManager appResourceManager;

    DataBindingComponent dataBindingComponent = new FragmentDataBindingComponent(this);
    AutoClearedValue<OrderItemsFragmentBinding> binding;
    AutoClearedValue<OrderItemsAdapter> adapter;

    private SortFilterDialogFragment filterDialogFragment;

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
                })
                .menuRes(ToolbarMenuUtil.generateMenuFrom(R.menu.menu_order_items_fragment),
                        buildMenuActions());
    }

    private MenuActions buildMenuActions() {
        return new MenuActions.Builder()
                .action(R.id.nav_create_order, () -> {
                    navigateToCreateOrder();
                })
                .build();
    }

    private void onRefresh() {
        CollapsableToolbarBuilder toolbarBuilder = (CollapsableToolbarBuilder) getNavigationBuilder();
        orderItemsViewModel.getLatestOrderItems().observe(this, resource -> {
            binding.get().setResource(resource);
            switch (resource.status) {
                case LOADING:
                    toolbarBuilder.setRefreshing(true);
                    break;
                case SUCCESS:
                    updateAdapter(resource);
                    updatePrompt();
                    toolbarBuilder.setRefreshing(false);
                    break;
                case ERROR:
                    toolbarBuilder.setRefreshing(false);
                    break;
            }
        });
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

        OrderItemsAdapter adapter = new OrderItemsAdapter(appResourceManager, dataBindingComponent,
                orderItem -> navigationController.navigateToOrderDetailsFragment(orderItem.orderId),
                () -> showFilterDialog());
        this.adapter = new AutoClearedValue<>(this, adapter);
        ShimmerRecyclerView recyclerView = binding.get().orderList;
        ViewCompat.setNestedScrollingEnabled(recyclerView, false);
        recyclerView.setAdapter(adapter);
        binding.get().noOrderPrompt.getRoot().setVisibility(View.GONE);
        initOrdersList();
    }

    @Override
    protected void onBackPressed() {
        getActivity().finish();
    }

    @Override
    public void onResume() {
        super.onResume();
        setBottomNavigationViewVisibility(true);
    }

    private void initOrdersList() {
        initInitialList();
        initPaginatedList();
        initPrompt();
    }

    private void initInitialList() {
        orderItemsViewModel.getAllOrderItems().observe(this, resource -> {
            binding.get().setResource(resource);
            switch (resource.status) {
                case LOADING:
                    binding.get().orderList.showShimmerAdapter();
                    break;
                case SUCCESS:
                    OrderItemsFragment.this.updateAdapter(resource);
                    binding.get().orderList.hideShimmerAdapter();
                    OrderItemsFragment.this.updatePrompt();
                    break;
                case ERROR:
                    binding.get().orderList.hideShimmerAdapter();
                    OrderItemsFragment.this.updatePrompt();
                    break;
            }
        });
    }

    private void initFilteredList() {
        orderItemsViewModel.getAllItemsWithoutDb().observe(this, resource -> {
            binding.get().setResource(resource);
            switch (resource.status) {
                case LOADING:
                    binding.get().orderList.showShimmerAdapter();
                    break;
                case SUCCESS:
                    updateAdapter(resource);
                    binding.get().orderList.hideShimmerAdapter();
                    updatePrompt();
                    break;
                case ERROR:
                    binding.get().orderList.hideShimmerAdapter();
                    updatePrompt();
                    break;
            }
        });
    }

    private void initPaginatedList() {
        orderItemsViewModel.getLoadMoreStatus().observe(this, loadMoreState -> {
            if (loadMoreState == null) {
                binding.get().setLoadingMore(false);
            } else {
                binding.get().setLoadingMore(loadMoreState.isRunning());
                String error = loadMoreState.getErrorMessageIfNotHandled();
                if (error != null) {
                    toastController.showErrorToast(error);
                }
            }
            binding.get().executePendingBindings();
        });
    }

    private void initPrompt() {
        binding.get().noOrderPrompt.actionCreateOrder.setOnClickListener(v ->
                navigateToCreateOrder());
        binding.get().noOrderPrompt.actionUpdateFilter.setOnClickListener(v -> showFilterDialog());
    }

    private void updateAdapter(Resource<List<TimestampedItemWrapper<OrderListItem>>> resource) {
        if (resource != null && resource.data != null) {
            adapter.get().replace(resource.data);
        } else {
            adapter.get().replace(null);
        }

    }

    private void updatePrompt() {
        if (adapter.get().getItemCount() == 0) {
            binding.get().noOrderPrompt.getRoot().setVisibility(View.VISIBLE);
        } else {
            binding.get().noOrderPrompt.getRoot().setVisibility(View.GONE);
        }
    }

    private void navigateToCreateOrder() {
        navigationController.navigateToCreateOrderFragment(null);
    }

    private void onPageEndReached() {
        onLoadNextPage();
    }

    private void onLoadNextPage() {
        if (!binding.get().getLoadingMore()) {
            orderItemsViewModel.loadNextPage();
        }
    }

    private void showFilterDialog() {
        if (filterDialogFragment == null) {
            filterDialogFragment = new SortFilterDialogFragment();
            filterDialogFragment.setOnApplyFilter(() -> {
                orderItemsViewModel.setStartDate(filterDialogFragment.getStartDate());
                orderItemsViewModel.setEndDate(filterDialogFragment.getEndDate());
                orderItemsViewModel.setStateFilter(filterDialogFragment
                        .getStateFilter());
                updateAdapter(null);
                binding.get().orderList.showShimmerAdapter();
                initFilteredList();
            });
        }
        filterDialogFragment.show(getFragmentManager(), OrderItemsFragment.class.getSimpleName());
    }

}
