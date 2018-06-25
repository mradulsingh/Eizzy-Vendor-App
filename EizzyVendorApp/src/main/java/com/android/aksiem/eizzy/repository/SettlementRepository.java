package com.android.aksiem.eizzy.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
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

import java.io.IOException;
import java.util.ArrayList;

import javax.inject.Inject;

import retrofit2.Response;


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

    public LiveData<Resource<Boolean>> getNextPage(int pageIndex, long startDate, long endDate) {
        StoreManager manager = EizzyAppState.ManagerLoggedIn.getManagerDetails(
                appPrefManager);
        FetchNextPageTask nextPageTask = new FetchNextPageTask(appService, appDb, manager, pageIndex, startDate, endDate);
        appExecutors.networkIO().execute(nextPageTask);
        return nextPageTask.getLiveData();
    }

    public LiveData<Resource<EizzyApiRespone<ArrayList<SettlementItem>>>> loadItems(long startDate, long endDate) {

        return new DbNetworkBoundResource<EizzyApiRespone<ArrayList<SettlementItem>>,
                EizzyApiRespone<ArrayList<SettlementItem>>>(appExecutors) {

            @Override
            protected void saveCallResult(@NonNull EizzyApiRespone<ArrayList<SettlementItem>> item) {
                if (item != null && item.data != null
                        && !item.data.isEmpty()) {
                    settlementItemDao.insertSettlementItems(item.data);
                }
            }

            @Override
            protected boolean shouldFetch(@Nullable EizzyApiRespone<ArrayList<SettlementItem>> data) {
                return data == null || data.data == null
                        || data.data.isEmpty();
            }

            @NonNull
            @Override
            protected LiveData<EizzyApiRespone<ArrayList<SettlementItem>>> loadFromDb() {
                return Transformations.switchMap(settlementItemDao.getAllItems(), orderItems -> {
                    if (orderItems != null) {
                        ArrayList<SettlementItem> list = new ArrayList<>();
                        list.addAll(orderItems);
                        EizzyApiRespone<ArrayList<SettlementItem>> eizzyApiRespone =
                                new EizzyApiRespone<>("", list);
                        MutableLiveData<EizzyApiRespone<ArrayList<SettlementItem>>> mutableLiveData =
                                new MutableLiveData<>();
                        mutableLiveData.setValue(eizzyApiRespone);
                        return mutableLiveData;
                    }
                    return AbsentLiveData.create();
                });
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<EizzyApiRespone<ArrayList<SettlementItem>>>> createCall() {
                StoreManager manager = EizzyAppState.ManagerLoggedIn.getManagerDetails(
                        appPrefManager);
                return appService.getAllSettlements(
                        RequestConstants.Language.english,
                        manager.token,
                        "5ac35cc8e360ea4c1e3afc2f",
                        0,
                        0);
            }

            @Override
            protected EizzyApiRespone<ArrayList<SettlementItem>> processResponse(ApiResponse<EizzyApiRespone<ArrayList<SettlementItem>>> response) {
                EizzyApiRespone<ArrayList<SettlementItem>> body = response.body;
                if (body != null) {
                    body.setNextPage(1);
                }
                return body;
            }
        }.asLiveData();
    }

    private static class FetchNextPageTask implements Runnable {
        private MutableLiveData<Resource<Boolean>> liveData = new MutableLiveData<>();
        private int pageIndex;
        private long startDate;
        private long endDate;
        private final AppService appService;
        private final StoreManager manager;
        private final AppDb appDb;

        FetchNextPageTask(AppService appService, AppDb appDb, StoreManager manager,
                          int pageIndex, long startDate, long endDate) {
            this.appService = appService;
            this.manager = manager;
            this.appDb = appDb;
            this.pageIndex = pageIndex;
            this.startDate = startDate;
            this.endDate = endDate;
        }

        @Override
        public void run() {
            try {
                Response<EizzyApiRespone<ArrayList<SettlementItem>>> response = appService
                        .getSettlementsNextPage(
                                RequestConstants.Language.english,
                                manager.token,
                                "5ac35cc8e360ea4c1e3afc2f",
                                pageIndex,
                                startDate,
                                endDate).execute();
                ApiResponse<EizzyApiRespone<ArrayList<SettlementItem>>> apiResponse = new ApiResponse<>(response);
                if (apiResponse.isSuccessful()) {
                    ArrayList<SettlementItem> data = apiResponse.body.data;
                    if (data != null && !data.isEmpty()) {
                        try {
                            appDb.beginTransaction();
                            appDb.settlementItemDao().insertSettlementItems(apiResponse.body.data);
                            appDb.setTransactionSuccessful();
                        } finally {
                            appDb.endTransaction();
                        }
                        liveData.postValue(Resource.success(true));
                    } else {
                        liveData.postValue(Resource.success(false));
                    }
                } else {
                    liveData.postValue(Resource.error(apiResponse.errorMessage, true));
                }
            } catch (IOException e) {
                liveData.postValue(Resource.error(e.getMessage(), true));
            }
        }

        public LiveData<Resource<Boolean>> getLiveData() {
            return liveData;
        }
    }
}
