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
import com.android.aksiem.eizzy.vo.VendorValueProp;

import javax.inject.Inject;

/**
 * Created by pdubey on 04/04/18.
 */

@AppScope
public class VendorValuePropRepository {

    private final AppService appService;
    private final AppExecutors appExecutors;
    private final AppResourceManager resourceManager;

    @Inject
    public VendorValuePropRepository(AppService appService, AppExecutors appExecutors, AppResourceManager resourceManager) {
        this.appService = appService;
        this.appExecutors = appExecutors;
        this.resourceManager = resourceManager;
    }

    public LiveData<Resource<VendorValueProp>> getVendorValueProp() {
        VendorValueProp vvp = new VendorValueProp(resourceManager.getString(R.string.app_name),
                "Eizzy Delivery is Uber for Courier services. We deliver products all around Metro cities.",
                "GET STARTED",
                "ALLREADY HAVE AN ACCOUNT?",
                " LOG IN!",
                "© 2018 Eizzy Inc.");
        Resource<VendorValueProp> vvpr = new Resource<>(Status.SUCCESS, vvp, null);
        MutableLiveData<Resource<VendorValueProp>> vvprld = new MutableLiveData<Resource<VendorValueProp>>() {
        };
        vvprld.setValue(vvpr);
        return vvprld;
    }

}
