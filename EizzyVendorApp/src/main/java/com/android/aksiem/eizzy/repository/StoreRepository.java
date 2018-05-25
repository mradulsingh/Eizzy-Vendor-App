package com.android.aksiem.eizzy.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.android.aksiem.eizzy.api.ApiResponse;
import com.android.aksiem.eizzy.api.AppService;
import com.android.aksiem.eizzy.app.AppExecutors;
import com.android.aksiem.eizzy.db.StoreDao;
import com.android.aksiem.eizzy.di.AppScope;
import com.android.aksiem.eizzy.util.AbsentLiveData;
import com.android.aksiem.eizzy.util.StringUtils;
import com.android.aksiem.eizzy.vo.RequestConstants;
import com.android.aksiem.eizzy.vo.Resource;
import com.android.aksiem.eizzy.vo.Store;

import java.util.List;

import javax.inject.Inject;

@AppScope
public class StoreRepository {

    private final StoreDao storeDao;
    private final AppService appService;
    private final AppExecutors appExecutors;

    @Inject
    public StoreRepository(StoreDao storeDao, AppService appService,
                           AppExecutors appExecutors) {
        this.storeDao = storeDao;
        this.appService = appService;
        this.appExecutors = appExecutors;
    }

    public LiveData<Resource<Store>> createStoreAccount(String businessName,
                                                        String contactPerson,
                                                        String contactMobile,
                                                        String contactEmail) {

        return new DbNetworkBoundResource<Store, Store>(appExecutors) {

            @Override
            protected void saveCallResult(@NonNull Store item) {
                storeDao.insert(item);
            }

            @Override
            protected boolean shouldFetch(@Nullable Store data) {
                return data == null;
            }

            @NonNull
            @Override
            protected LiveData<Store> loadFromDb() {
                LiveData<List<Store>> stores = storeDao.getStores();
                if (stores.getValue().isEmpty()) {
                    return AbsentLiveData.create();
                } else {
                    MutableLiveData<Store> store = new MutableLiveData<>();
                    store.setValue(stores.getValue().get(0));
                    return store;
                }

            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<Store>> createCall() {
                return appService.createUserAccount(RequestConstants.Language.english, businessName,
                        contactPerson, RequestConstants.CountryCode.india, contactMobile,
                        contactEmail, StringUtils.randomString(8), Settings.Secure.ANDROID_ID,
                        RequestConstants.Platform.android);
            }
        }.asLiveData();
    }
}
