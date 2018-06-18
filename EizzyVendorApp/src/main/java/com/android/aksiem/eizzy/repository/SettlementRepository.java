package com.android.aksiem.eizzy.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.android.aksiem.eizzy.api.ApiResponse;
import com.android.aksiem.eizzy.api.AppService;
import com.android.aksiem.eizzy.api.DispatcherService;
import com.android.aksiem.eizzy.app.AppExecutors;
import com.android.aksiem.eizzy.app.AppPrefManager;
import com.android.aksiem.eizzy.app.EizzyAppState;
import com.android.aksiem.eizzy.db.AppDb;
import com.android.aksiem.eizzy.db.SettlementItemDao;
import com.android.aksiem.eizzy.di.AppScope;
import com.android.aksiem.eizzy.util.AbsentLiveData;
import com.android.aksiem.eizzy.vo.EizzyApiRespone;
import com.android.aksiem.eizzy.vo.RequestConstants;
import com.android.aksiem.eizzy.vo.Resource;
import com.android.aksiem.eizzy.vo.StoreManager;
import com.android.aksiem.eizzy.vo.settlement.SettlementItem;
import com.android.aksiem.eizzy.vo.support.settlement.SettlementItemsList;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;


@AppScope
public class SettlementRepository {

    protected final AppDb appDb;

    protected final SettlementItemDao settlementItemDao;

    private final AppService appService;

    private final DispatcherService dispatcherService;

    private final AppExecutors appExecutors;

    protected final AppPrefManager appPrefManager;

    @Inject
    public SettlementRepository(AppDb appDb, SettlementItemDao settlementItemDao,
                                AppService appService,
                                DispatcherService dispatcherService,
                                AppExecutors appExecutors,
                                AppPrefManager appPrefManager) {
        this.appDb = appDb;
        this.settlementItemDao = settlementItemDao;
        this.appService = appService;
        this.dispatcherService = dispatcherService;
        this.appExecutors = appExecutors;
        this.appPrefManager = appPrefManager;
    }

    public LiveData<Resource<EizzyApiRespone<SettlementItemsList>>> loadItems(
            long pageIndex, long startDate, long endDate) {

        return new DbNetworkBoundResource<EizzyApiRespone<SettlementItemsList>,
                EizzyApiRespone<SettlementItemsList>>(appExecutors) {

            @Override
            protected void saveCallResult(@NonNull EizzyApiRespone<SettlementItemsList> item) {
                if (item != null && item.data != null && item.data.items != null
                        && item.data.items.isEmpty()) {
                    settlementItemDao.insertSettlementItems(item.data.items);
                }
            }

            @Override
            protected boolean shouldFetch(@Nullable EizzyApiRespone<SettlementItemsList> data) {
                return data == null || data.data == null || data.data.items == null
                        || data.data.items.isEmpty();
            }

            @NonNull
            @Override
            protected LiveData<EizzyApiRespone<SettlementItemsList>> loadFromDb() {
                LiveData<List<SettlementItem>> orderItems = settlementItemDao.getAllItems();
                ArrayList<SettlementItem> arrayList = new ArrayList<>();
                if (orderItems.getValue() != null) {
                    arrayList.addAll(orderItems.getValue());
                    SettlementItemsList list = new SettlementItemsList(arrayList);
                    EizzyApiRespone<SettlementItemsList> eizzyApiRespone =
                            new EizzyApiRespone<>("", list);
                    MutableLiveData<EizzyApiRespone<SettlementItemsList>> mutableLiveData =
                            new MutableLiveData<>();
                    mutableLiveData.setValue(eizzyApiRespone);
                    return mutableLiveData;
                }
                return AbsentLiveData.create();
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<EizzyApiRespone<SettlementItemsList>>> createCall() {
                StoreManager manager = EizzyAppState.ManagerLoggedIn.getManagerDetails(
                        appPrefManager);
                return appService.getAllSettlements(
                        RequestConstants.Language.english,
                        manager.token,
                        manager.storeId,
                        0,
                        0,
                        0);
            }
        }.asLiveData();

    }
}
