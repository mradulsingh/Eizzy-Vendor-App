package com.android.aksiem.eizzy.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.android.aksiem.eizzy.api.ApiResponse;
import com.android.aksiem.eizzy.api.AppService;
import com.android.aksiem.eizzy.app.AppExecutors;
import com.android.aksiem.eizzy.app.AppResourceManager;
import com.android.aksiem.eizzy.db.AppDb;
import com.android.aksiem.eizzy.db.OrderItemsDao;
import com.android.aksiem.eizzy.vo.OrderItem;
import com.android.aksiem.eizzy.vo.Resource;
import com.android.aksiem.eizzy.vo.TimestampedItemWrapper;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by pdubey on 14/04/18.
 */

public class OrderItemsRepository extends TimestampedItemsRepository<OrderItem> {


    @Inject
    public OrderItemsRepository(AppDb appDb, OrderItemsDao timestampedItemsDao,
                                AppService appService, AppExecutors appExecutors,
                                AppResourceManager appResourceManager) {

        super(appDb, timestampedItemsDao, appService, appExecutors, appResourceManager);

    }

    @Override
    public LiveData<Resource<List<TimestampedItemWrapper<OrderItem>>>> loadItems() {
        return new DbNetworkBoundResource<List<TimestampedItemWrapper<OrderItem>>,
                List<TimestampedItemWrapper<OrderItem>>>(appExecutors){

            @Override
            protected void saveCallResult(@NonNull List<TimestampedItemWrapper<OrderItem>> items) {
                for (TimestampedItemWrapper item : items) {
                    if (item.isItem()) {
                        appDb.beginTransaction();
                        try {
                            timestampedItemsDao.insert(item.item);
                            appDb.setTransactionSuccessful();
                        } finally {
                            appDb.endTransaction();
                        }
                    }
                }

            }

            @Override
            protected boolean shouldFetch(@Nullable List<TimestampedItemWrapper<OrderItem>> data) {
                return false;
            }

            @NonNull
            @Override
            protected LiveData<List<TimestampedItemWrapper<OrderItem>>> loadFromDb() {
                LiveData<List<OrderItem>> orderItemsLD = timestampedItemsDao.getAllItems();
                return addTimestampToList(orderItemsLD);
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<List<TimestampedItemWrapper<OrderItem>>>> createCall() {
                // TODO integrate with backend API
                return null;
            }
        }.asLiveData();
    }

}
