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
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.aksiem.eizzy.R;
import com.android.aksiem.eizzy.app.BaseInjectableFragment;
import com.android.aksiem.eizzy.binding.FragmentDataBindingComponent;
import com.android.aksiem.eizzy.databinding.SettlementDurationFragmentBinding;
import com.android.aksiem.eizzy.di.ApplicationContext;
import com.android.aksiem.eizzy.ui.common.NavigationController;
import com.android.aksiem.eizzy.util.AutoClearedValue;
import com.android.aksiem.eizzy.vo.Resource;
import com.android.aksiem.eizzy.vo.settlement.SettlementItem;

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

    public static SettlementDurationFragment create() {
        return new SettlementDurationFragment();
    }

    @Inject
    @ApplicationContext
    Context applicationContext;


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
        settlementViewModel = ViewModelProviders.of(this, viewModelFactory).get(
                SettlementViewModel.class);

        SettlementItemAdapter adapter = new SettlementItemAdapter(dataBindingComponent,
                settlementItem -> {
                    //navigationController.navigateToSettlementDetailsFragment(settlementItem);
                });
        this.adapter = new AutoClearedValue<>(this, adapter);
        RecyclerView recyclerView = binding.get().rvSettlement;
        ViewCompat.setNestedScrollingEnabled(recyclerView, false);
        recyclerView.setAdapter(adapter);

        initSettlementItemList();
    }

    private void initSettlementItemList() {
        settlementViewModel.getSettlements().observe(this, resource -> {
            switch (resource.status) {
                case LOADING:
                    break;
                case SUCCESS:
                    updateAdapter(resource);
                    break;
                case ERROR:
                    break;
            }
        });
    }

    private void updateAdapter(Resource<List<SettlementItem>> resource) {
        if (resource != null && resource.data != null) {
            adapter.get().replace(resource.data);
        } else {
            adapter.get().replace(null);
        }
    }
}
