package com.android.aksiem.eizzy.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.android.aksiem.eizzy.R;
import com.android.aksiem.eizzy.api.AppService;
import com.android.aksiem.eizzy.app.AppExecutors;
import com.android.aksiem.eizzy.app.AppResourceManager;
import com.android.aksiem.eizzy.di.AppScope;
import com.android.aksiem.eizzy.vo.Resource;
import com.android.aksiem.eizzy.vo.Status;
import com.android.aksiem.eizzy.vo.VendorOnboarding;

import javax.inject.Inject;

/**
 * Created by pdubey on 04/04/18.
 */

@AppScope
public class VendorOnboardingRepository {

    private final AppService appService;
    private final AppExecutors appExecutors;
    private final AppResourceManager resourceManager;

    @Inject
    public VendorOnboardingRepository(AppService appService, AppExecutors appExecutors, AppResourceManager resourceManager) {
        this.appService = appService;
        this.appExecutors = appExecutors;
        this.resourceManager = resourceManager;
    }

    public LiveData<Resource<VendorOnboarding>> getVendorOnboarding() {
        VendorOnboarding vvp = new VendorOnboarding(resourceManager.getString(R.string.app_name),
                "Eizzy Delivery is Uber for Courier services. We deliver products all around Metro cities.",
                "GET STARTED",
                "ALLREADY HAVE AN ACCOUNT?",
                " LOG IN!",
                "© 2018 Eizzy Inc.");
        Resource<VendorOnboarding> vvpr = new Resource<>(Status.SUCCESS, vvp, null);
        MutableLiveData<Resource<VendorOnboarding>> vvprld = new MutableLiveData<Resource<VendorOnboarding>>() {
        };
        vvprld.setValue(vvpr);
        return vvprld;
    }

}
