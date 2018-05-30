package com.android.aksiem.eizzy.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.provider.Settings;
import android.support.annotation.NonNull;

import com.android.aksiem.eizzy.api.ApiResponse;
import com.android.aksiem.eizzy.api.AppService;
import com.android.aksiem.eizzy.api.DispatcherService;
import com.android.aksiem.eizzy.app.AppExecutors;
import com.android.aksiem.eizzy.di.AppScope;
import com.android.aksiem.eizzy.vo.EizzyApiRespone;
import com.android.aksiem.eizzy.vo.RequestConstants;
import com.android.aksiem.eizzy.vo.Resource;
import com.android.aksiem.eizzy.vo.Store;
import com.android.aksiem.eizzy.vo.StoreManager;


import javax.inject.Inject;

@AppScope
public class StoreManagerRepository {

    private final AppService appService;
    private final DispatcherService dispatcherService;
    private final AppExecutors appExecutors;

    @Inject
    public StoreManagerRepository(AppService appService,
                                  DispatcherService dispatcherService,
                                  AppExecutors appExecutors) {
        this.appService = appService;
        this.dispatcherService = dispatcherService;
        this.appExecutors = appExecutors;
    }

    public LiveData<Resource<EizzyApiRespone<Store>>> createStoreAccount(String businessName,
                                                                         String contactPerson,
                                                                         String contactMobile,
                                                                         String contactEmail) {
        return new NoCacheNetworkBoundResource<EizzyApiRespone<Store>, EizzyApiRespone<Store>>(appExecutors) {

            @NonNull
            @Override
            protected LiveData<EizzyApiRespone<Store>> getCallResult(@NonNull EizzyApiRespone<Store> item) {
                MutableLiveData<EizzyApiRespone<Store>> eizzyApiResponeMutableLiveData = new MutableLiveData<>();
                eizzyApiResponeMutableLiveData.setValue(item);
                return eizzyApiResponeMutableLiveData;
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<EizzyApiRespone<Store>>> createCall() {
                return appService.createUserAccount(RequestConstants.Language.english, businessName,
                        contactPerson, RequestConstants.CountryCode.india, contactMobile,
                        contactEmail, Settings.Secure.ANDROID_ID,
                        RequestConstants.Platform.android);
            }
        }.asLiveData();
    }

    public LiveData<Resource<EizzyApiRespone<StoreManager>>> doManagerLogin(String phone,
                                                                            String password) {

        return new NoCacheNetworkBoundResource<EizzyApiRespone<StoreManager>,
                EizzyApiRespone<StoreManager>>(appExecutors) {

            @NonNull
            @Override
            protected LiveData<EizzyApiRespone<StoreManager>> getCallResult(
                    @NonNull EizzyApiRespone<StoreManager> item) {
                MutableLiveData<EizzyApiRespone<StoreManager>> eizzyApiResponeMutableLiveData =
                        new MutableLiveData<>();
                eizzyApiResponeMutableLiveData.setValue(item);
                return eizzyApiResponeMutableLiveData;
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<EizzyApiRespone<StoreManager>>> createCall() {
                return dispatcherService.doUserLogin(RequestConstants.Language.english, phone,
                        RequestConstants.CountryCode.india, password, Settings.Secure.ANDROID_ID,
                        RequestConstants.Platform.android, System.currentTimeMillis());
            }
        }.asLiveData();
    }
}
