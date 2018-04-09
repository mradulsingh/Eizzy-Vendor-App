package com.android.aksiem.eizzy.ui.vendorValueProp;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.aksiem.eizzy.R;
import com.android.aksiem.eizzy.app.NoBottomNavigationFragment;
import com.android.aksiem.eizzy.binding.FragmentDataBindingComponent;
import com.android.aksiem.eizzy.databinding.VendorValuePropFragmentBinding;
import com.android.aksiem.eizzy.di.Injectable;
import com.android.aksiem.eizzy.ui.common.NavigationController;
import com.android.aksiem.eizzy.util.AutoClearedValue;

import javax.inject.Inject;

/**
 * Created by pdubey on 04/04/18.
 */

public class VendorOnboardingFragment extends NoBottomNavigationFragment {

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    @Inject
    NavigationController navigationController;

    private DataBindingComponent dataBindingComponent = new FragmentDataBindingComponent(this);
    private VendorOnboardingViewModel vendorOnboardingViewModel;

    @VisibleForTesting
    AutoClearedValue<VendorValuePropFragmentBinding> binding;

    public static VendorOnboardingFragment create() {
        VendorOnboardingFragment vendorValuePropFragment = new VendorOnboardingFragment();
        return vendorValuePropFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        VendorValuePropFragmentBinding dataBinding = DataBindingUtil.inflate(inflater,
                R.layout.vendor_onboarding_fragment, container,
                false, dataBindingComponent);
        binding = new AutoClearedValue<>(this, dataBinding);
        return dataBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        vendorOnboardingViewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(VendorOnboardingViewModel.class);
        vendorOnboardingViewModel.getVendorValueProp().observe(this, vendorValuePropResource -> {
            binding.get().setVendorValueProp(vendorValuePropResource == null ? null : vendorValuePropResource.data);
            binding.get().existingAccountAction.setOnClickListener(v -> {
                navigationController.navigateToLogin();
            });
        });
    }
}
