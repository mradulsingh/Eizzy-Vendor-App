package com.android.aksiem.eizzy.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.android.aksiem.eizzy.api.AppService;
import com.android.aksiem.eizzy.app.AppExecutors;
import com.android.aksiem.eizzy.app.AppResourceManager;
import com.android.aksiem.eizzy.db.AppDb;
import com.android.aksiem.eizzy.db.TimestampedItemsDao;
import com.android.aksiem.eizzy.util.RateLimiter;
import com.android.aksiem.eizzy.util.StringUtils;
import com.android.aksiem.eizzy.vo.Resource;
import com.android.aksiem.eizzy.vo.Timestamped;
import com.android.aksiem.eizzy.vo.TimestampedItemWrapper;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by pdubey on 15/04/18.
 */

public abstract class TimestampedItemsRepository<T extends Timestamped> {

    protected final AppDb appDb;

    protected final TimestampedItemsDao timestampedItemsDao;

    protected final AppService appService;

    protected final AppExecutors appExecutors;

    protected final AppResourceManager appResourceManager;

    protected RateLimiter<String> orderItemsRateLimiter = new RateLimiter<>(120,
            TimeUnit.SECONDS);

    public TimestampedItemsRepository(AppDb appDb, TimestampedItemsDao timestampedItemsDao,
                                      AppService appService, AppExecutors appExecutors,
                                      AppResourceManager appResourceManager) {
        this.appDb = appDb;
        this.timestampedItemsDao = timestampedItemsDao;
        this.appService = appService;
        this.appExecutors = appExecutors;
        this.appResourceManager = appResourceManager;
    }

    public abstract LiveData<Resource<List<TimestampedItemWrapper<T>>>> loadItems();

    protected LiveData<List<TimestampedItemWrapper<T>>> addTimestampToList(LiveData<List<T>> liveDataItems) {
        List<T> items = liveDataItems.getValue();
        List<TimestampedItemWrapper<T>> orderItemWrappers = new ArrayList<>();
        String initialTS = null;
        for (T item : items) {
            String currTS = StringUtils.getTimestamp(item.getTimestamp(),
                    appResourceManager);
            if (initialTS == null || !initialTS.equals(currTS)) {
                orderItemWrappers.add(new TimestampedItemWrapper<T>(currTS, null));
                initialTS = currTS;
            }
            orderItemWrappers.add(new TimestampedItemWrapper<T>(null, item));
        }
        MutableLiveData<List<TimestampedItemWrapper<T>>> orderItemWrappersMLD = new MutableLiveData<>();
        orderItemWrappersMLD.setValue(orderItemWrappers);
        return orderItemWrappersMLD;
    }
}
