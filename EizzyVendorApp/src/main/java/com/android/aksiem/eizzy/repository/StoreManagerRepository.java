package com.android.aksiem.eizzy.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.provider.Settings;
import android.support.annotation.NonNull;

import com.android.aksiem.eizzy.api.ApiResponse;
import com.android.aksiem.eizzy.api.AppService;
import com.android.aksiem.eizzy.app.AppExecutors;
import com.android.aksiem.eizzy.di.AppScope;
import com.android.aksiem.eizzy.util.StringUtils;
import com.android.aksiem.eizzy.vo.RequestConstants;
import com.android.aksiem.eizzy.vo.Resource;
import com.android.aksiem.eizzy.vo.Store;


import javax.inject.Inject;

@AppScope
public class StoreManagerRepository {

    private final AppService appService;
    private final AppExecutors appExecutors;

    @Inject
    public StoreManagerRepository(AppService appService,
                                  AppExecutors appExecutors) {
        this.appService = appService;
        this.appExecutors = appExecutors;
    }

    public LiveData<Resource<Store>> createStoreAccount(String businessName,
                                                        String contactPerson,
                                                        String contactMobile,
                                                        String contactEmail) {

        return new NoCacheNetworkBoundResource<Store, Store>(appExecutors) {

            @NonNull
            @Override
            protected LiveData<Store> getCallResult(@NonNull Store item) {
                MutableLiveData<Store> storeMutableLiveData = new MutableLiveData<>();
                storeMutableLiveData.setValue(item);
                return storeMutableLiveData;
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<Store>> createCall() {
                return appService.createUserAccount(RequestConstants.Language.english, businessName,
                        contactPerson, RequestConstants.CountryCode.india, contactMobile,
                        contactEmail, Settings.Secure.ANDROID_ID,
                        RequestConstants.Platform.android);
            }
        }.asLiveData();
    }
}
