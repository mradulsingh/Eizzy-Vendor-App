package com.android.aksiem.eizzy.ui.login;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.aksiem.eizzy.R;
import com.android.aksiem.eizzy.app.AppPrefManager;
import com.android.aksiem.eizzy.app.EizzyAppState;
import com.android.aksiem.eizzy.app.NavigationFragment;
import com.android.aksiem.eizzy.binding.FragmentDataBindingComponent;
import com.android.aksiem.eizzy.databinding.VendorOnboardingFragmentBinding;
import com.android.aksiem.eizzy.ui.common.NavigationController;
import com.android.aksiem.eizzy.ui.common.ToastController;
import com.android.aksiem.eizzy.ui.toolbar.NavigationBuilder;
import com.android.aksiem.eizzy.util.AutoClearedValue;

import javax.inject.Inject;

import static com.android.aksiem.eizzy.ui.toolbar.NoNavigationBuilder.includeNoNavigationItems;

/**
 * Created by pdubey on 04/04/18.
 */

public class VendorOnboardingFragment extends NavigationFragment {

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    @Inject
    NavigationController navigationController;

    @Inject
    AppPrefManager appPrefManager;

    @Inject
    ToastController toastController;

    private DataBindingComponent dataBindingComponent = new FragmentDataBindingComponent(this);
    private VendorOnboardingViewModel vendorOnboardingViewModel;

    @VisibleForTesting
    AutoClearedValue<VendorOnboardingFragmentBinding> binding;

    public static VendorOnboardingFragment newInstance() {
        VendorOnboardingFragment vendorValuePropFragment = new VendorOnboardingFragment();
        return vendorValuePropFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        VendorOnboardingFragmentBinding dataBinding = DataBindingUtil.inflate(inflater,
                R.layout.vendor_onboarding_fragment, container,
                false, dataBindingComponent);
        binding = new AutoClearedValue<>(this, dataBinding);
        return wrapNavigationLayout(inflater, container, dataBinding.getRoot());
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        vendorOnboardingViewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(VendorOnboardingViewModel.class);
        vendorOnboardingViewModel.getVendorOnboarding().observe(this,
                vendorOnboardingResource -> {
                    binding.get().setVendorOnboarding(
                            vendorOnboardingResource == null ? null : vendorOnboardingResource.data);
            binding.get().existingAccountAction.setOnClickListener(v -> {
                navigationController.navigateToLogin();
            });
                    binding.get().primaryAction.setOnClickListener(
                            v -> {
                                if (EizzyAppState.AccountCreated.isAccountCreated(appPrefManager)) {
                                    toastController.showErrorToast(
                                            getString(R.string.error_account_already_exists));
                                } else {
                                    navigationController.navigateToCreateUserAccount();
                                }
                            });
        });
    }

    @Override
    public NavigationBuilder buildNavigation() {
        return includeNoNavigationItems();
    }
}
