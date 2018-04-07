package com.android.aksiem.eizzy.ui.vendorValueProp;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.VisibleForTesting;

import com.android.aksiem.eizzy.repository.VendorValuePropRepository;
import com.android.aksiem.eizzy.vo.Resource;
import com.android.aksiem.eizzy.vo.VendorValueProp;

import javax.inject.Inject;

/**
 * Created by pdubey on 04/04/18.
 */

public class VendorValuePropViewModel extends ViewModel {

    private final LiveData<Resource<VendorValueProp>> vendorValueProp;

    @SuppressWarnings("unchecked")
    @Inject
    public VendorValuePropViewModel(VendorValuePropRepository repository) {
        this.vendorValueProp = repository.getVendorValueProp();
    }

    @VisibleForTesting
    public LiveData<Resource<VendorValueProp>> getVendorValueProp() {
        return vendorValueProp;
    }

    @VisibleForTesting
    public void retry() {
        // Nothing to do here
    }
}
