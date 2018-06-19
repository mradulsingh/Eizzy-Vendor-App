package com.android.aksiem.eizzy.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.android.aksiem.eizzy.api.ApiResponse;
import com.android.aksiem.eizzy.api.AppService;
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

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;


@AppScope
public class SettlementRepository {

    protected final AppDb appDb;

    protected final SettlementItemDao settlementItemDao;

    private final AppService appService;

    private final AppExecutors appExecutors;

    protected final AppPrefManager appPrefManager;

    @Inject
    public SettlementRepository(AppDb appDb, SettlementItemDao settlementItemDao,
                                AppService appService,
                                AppExecutors appExecutors,
                                AppPrefManager appPrefManager) {
        this.appDb = appDb;
        this.settlementItemDao = settlementItemDao;
        this.appService = appService;
        this.appExecutors = appExecutors;
        this.appPrefManager = appPrefManager;
    }

    public LiveData<Resource<EizzyApiRespone<ArrayList<SettlementItem>>>> loadItems(
            long pageIndex, long startDate, long endDate) {

        return new DbNetworkBoundResource<EizzyApiRespone<ArrayList<SettlementItem>>,
                EizzyApiRespone<ArrayList<SettlementItem>>>(appExecutors) {

            @Override
            protected void saveCallResult(@NonNull EizzyApiRespone<ArrayList<SettlementItem>> item) {
                if (item != null && item.data != null && item.data != null
                        && item.data.isEmpty()) {
                    settlementItemDao.insertSettlementItems(item.data);
                }
            }

            @Override
            protected boolean shouldFetch(@Nullable EizzyApiRespone<ArrayList<SettlementItem>> data) {
                return data == null || data.data == null || data.data == null
                        || data.data.isEmpty();
            }

            @NonNull
            @Override
            protected LiveData<EizzyApiRespone<ArrayList<SettlementItem>>> loadFromDb() {
                LiveData<List<SettlementItem>> orderItems = settlementItemDao.getAllItems();
                ArrayList<SettlementItem> arrayList = new ArrayList<>();
                if (orderItems.getValue() != null) {
                    arrayList.addAll(orderItems.getValue());
                    ArrayList<SettlementItem> list = new ArrayList<SettlementItem>();
                    EizzyApiRespone<ArrayList<SettlementItem>> eizzyApiRespone =
                            new EizzyApiRespone<>("", list);
                    MutableLiveData<EizzyApiRespone<ArrayList<SettlementItem>>> mutableLiveData =
                            new MutableLiveData<>();
                    mutableLiveData.setValue(eizzyApiRespone);
                    return mutableLiveData;
                }
                return AbsentLiveData.create();
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<EizzyApiRespone<ArrayList<SettlementItem>>>> createCall() {
                StoreManager manager = EizzyAppState.ManagerLoggedIn.getManagerDetails(
                        appPrefManager);
                return appService.getAllSettlements(
                        RequestConstants.Language.english,
                        manager.token,
                        "5ac35cc8e360ea4c1e3afc2f"/*,
                        0,
                        0,
                        0*/);
            }
        }.asLiveData();

    }
}