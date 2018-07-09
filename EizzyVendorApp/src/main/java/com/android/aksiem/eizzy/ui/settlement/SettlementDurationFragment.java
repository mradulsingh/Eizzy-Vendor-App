package com.android.aksiem.eizzy.ui.settlement;

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
import com.android.aksiem.eizzy.app.BaseInjectableFragment;
import com.android.aksiem.eizzy.binding.FragmentDataBindingComponent;
import com.android.aksiem.eizzy.databinding.SettlementDurationFragmentBinding;
import com.android.aksiem.eizzy.di.ApplicationContext;
import com.android.aksiem.eizzy.ui.common.NavigationController;
import com.android.aksiem.eizzy.ui.common.ToastController;
import com.android.aksiem.eizzy.util.AutoClearedValue;
import com.android.aksiem.eizzy.vo.Resource;
import com.android.aksiem.eizzy.vo.settlement.SettlementItem;
import com.cooltechworks.views.shimmer.ShimmerRecyclerView;

import java.util.List;

import javax.inject.Inject;


public class SettlementDurationFragment extends BaseInjectableFragment {

    @Inject
    ViewModelProvider.Factory viewModelFactory;
    static String TAG = SettlementDurationFragment.class.getSimpleName();
    private SettlementViewModel settlementViewModel;

    @Inject
    NavigationController navigationController;

    DataBindingComponent dataBindingComponent = new FragmentDataBindingComponent(this);
    AutoClearedValue<SettlementDurationFragmentBinding> binding;
    AutoClearedValue<SettlementItemAdapter> adapter;

    @Inject
    ToastController toastController;

    @Inject
    @ApplicationContext
    Context applicationContext;

    private int selectedPeriod = -1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        SettlementDurationFragmentBinding dataBinding = DataBindingUtil.inflate(inflater,
                R.layout.settlement_duration_fragment, container, false);
        binding = new AutoClearedValue<>(this, dataBinding);
        return dataBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        selectedPeriod = getArguments().getInt("selected_duration");
        settlementViewModel = ViewModelProviders.of(this, viewModelFactory).get(
                SettlementViewModel.class);
        settlementViewModel.setDurationType(selectedPeriod);
        SettlementItemAdapter adapter = new SettlementItemAdapter(dataBindingComponent,
                settlementItem -> {
                    navigationController.navigateToOrderDetailsFragment(settlementItem.orderId);
                });
        this.adapter = new AutoClearedValue<>(this, adapter);
        ShimmerRecyclerView recyclerView = binding.get().rvSettlement;
        ViewCompat.setNestedScrollingEnabled(recyclerView, false);
        recyclerView.setAdapter(adapter);

        initSettlementItemList();
    }

    public void onLoadNextPage() {
        if (!binding.get().getLoadingMore()) {
            settlementViewModel.loadNextPage();
        }
    }

    private void initSettlementItemList() {
        settlementViewModel.getSettlements().observe(this, resource -> {
            binding.get().setResource(resource);
            switch (resource.status) {
                case LOADING:
                    binding.get().rvSettlement.showShimmerAdapter();
                    break;
                case SUCCESS:
                    binding.get().rvSettlement.hideShimmerAdapter();
                    updateAdapter(resource);
                    break;
                case ERROR:
                    binding.get().rvSettlement.hideShimmerAdapter();
                    break;
            }
        });

        settlementViewModel.getLoadMoreStatus().observe(this, loadingMore -> {
            if (loadingMore == null) {
                binding.get().setLoadingMore(false);
            } else {
                binding.get().setLoadingMore(loadingMore.isRunning());
                String error = loadingMore.getErrorMessageIfNotHandled();
                if (error != null) {
                    toastController.showErrorToast(error);
                }
            }
            binding.get().executePendingBindings();
        });
    }

    private void updateAdapter(Resource<List<SettlementItem>> resource) {
        if (resource != null && resource.data != null) {
            adapter.get().replace(resource.data);
        } else {
            adapter.get().replace(null);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        setBottomNavigationViewVisibility(true);
        setNavDrawerMode(true);
    }

    @Override
    public void onFragmentResume() {
        super.onFragmentResume();
        setBottomNavigationViewVisibility(true);
        setNavDrawerMode(true);
    }
}
