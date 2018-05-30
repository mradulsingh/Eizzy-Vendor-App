package com.android.aksiem.eizzy.ui.login;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.VisibleForTesting;

import com.android.aksiem.eizzy.repository.VendorOnboardingRepository;
import com.android.aksiem.eizzy.vo.Resource;
import com.android.aksiem.eizzy.vo.VendorOnboarding;

import javax.inject.Inject;

/**
 * Created by pdubey on 04/04/18.
 */

public class VendorOnboardingViewModel extends ViewModel {

    private final LiveData<Resource<VendorOnboarding>> vendorOnboarding;

    @SuppressWarnings("unchecked")
    @Inject
    public VendorOnboardingViewModel(VendorOnboardingRepository repository) {
        this.vendorOnboarding = repository.getVendorOnboarding();
    }

    @VisibleForTesting
    public LiveData<Resource<VendorOnboarding>> getVendorOnboarding() {
        return vendorOnboarding;
    }

    @VisibleForTesting
    public void retry() {
        // Nothing to do here
    }
}
