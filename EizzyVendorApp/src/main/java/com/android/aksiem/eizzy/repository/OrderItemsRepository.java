package com.android.aksiem.eizzy.repository;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.android.aksiem.eizzy.api.ApiResponse;
import com.android.aksiem.eizzy.api.AppService;
import com.android.aksiem.eizzy.app.AppExecutors;
import com.android.aksiem.eizzy.db.AppDb;
import com.android.aksiem.eizzy.db.OrderItemsDao;
import com.android.aksiem.eizzy.util.RateLimiter;
import com.android.aksiem.eizzy.vo.OrderItem;
import com.android.aksiem.eizzy.vo.Resource;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

/**
 * Created by pdubey on 14/04/18.
 */

public class OrderItemsRepository {

    private final AppDb appDb;

    private final OrderItemsDao orderItemsDao;

    private final AppService appService;

    private final AppExecutors appExecutors;

    private RateLimiter<String> orderItemsRateLimiter = new RateLimiter<>(120, TimeUnit.SECONDS);

    @Inject
    public OrderItemsRepository(AppDb appDb, OrderItemsDao orderItemsDao, AppService appService,
                                AppExecutors appExecutors) {

        this.appDb = appDb;
        this.orderItemsDao = orderItemsDao;
        this.appService = appService;
        this.appExecutors = appExecutors;
    }

    public LiveData<Resource<List<OrderItem>>> loadOrders() {
        return new DbNetworkBoundResource<List<OrderItem>, List<OrderItem>>(appExecutors) {

            @Override
            protected void saveCallResult(@NonNull List<OrderItem> items) {
                for (OrderItem item : items) {
                    appDb.beginTransaction();
                    try {
                        orderItemsDao.insert(item);
                        appDb.setTransactionSuccessful();
                    } finally {
                        appDb.endTransaction();
                    }
                }
            }

            @Override
            protected boolean shouldFetch(@Nullable List<OrderItem> data) {
                return data == null;
            }

            @NonNull
            @Override
            protected LiveData<List<OrderItem>> loadFromDb() {
                return orderItemsDao.getAllOrderItems();
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<List<OrderItem>>> createCall() {
                // TODO integrate with backend API
                return null;
            }
        }.asLiveData();
    }

}
