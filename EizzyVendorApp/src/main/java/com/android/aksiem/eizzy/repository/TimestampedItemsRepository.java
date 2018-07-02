package com.android.aksiem.eizzy.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.android.aksiem.eizzy.api.AppService;
import com.android.aksiem.eizzy.app.AppExecutors;
import com.android.aksiem.eizzy.app.AppResourceManager;
import com.android.aksiem.eizzy.db.AppDb;
import com.android.aksiem.eizzy.db.TimestampedItemDao;
import com.android.aksiem.eizzy.util.AbsentLiveData;
import com.android.aksiem.eizzy.util.RateLimiter;
import com.android.aksiem.eizzy.util.TimeUtils;
import com.android.aksiem.eizzy.vo.Resource;
import com.android.aksiem.eizzy.vo.order.Timestamped;
import com.android.aksiem.eizzy.vo.order.TimestampedItemWrapper;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by pdubey on 15/04/18.
 */

public abstract class TimestampedItemsRepository<T extends Timestamped> {

    protected final AppDb appDb;

    protected final TimestampedItemDao timestampedItemDao;

    protected final AppService appService;

    protected final AppExecutors appExecutors;

    protected final AppResourceManager appResourceManager;

    protected RateLimiter<String> orderItemsRateLimiter = new RateLimiter<>(120,
            TimeUnit.SECONDS);

    public TimestampedItemsRepository(AppDb appDb, TimestampedItemDao timestampedItemDao,
                                      AppService appService, AppExecutors appExecutors,
                                      AppResourceManager appResourceManager) {
        this.appDb = appDb;
        this.timestampedItemDao = timestampedItemDao;
        this.appService = appService;
        this.appExecutors = appExecutors;
        this.appResourceManager = appResourceManager;
    }

    public abstract LiveData<Resource<List<TimestampedItemWrapper<T>>>> loadItems();

    protected LiveData<List<TimestampedItemWrapper<T>>> addTimestampToList(LiveData<List<T>> liveDataItems) {
        List<T> items = liveDataItems.getValue();
        List<TimestampedItemWrapper<T>> timestampedItemWrappers = new ArrayList<>();
        String initialTS = null;
        if (items != null) {
            for (T item : items) {
                String currTS = TimeUtils.getTimestamp(item.getTimestamp(),
                        appResourceManager);
                if (initialTS == null || !initialTS.equals(currTS)) {
                    timestampedItemWrappers.add(new TimestampedItemWrapper<T>(currTS, null));
                    initialTS = currTS;
                }
                timestampedItemWrappers.add(new TimestampedItemWrapper<T>(null, item));
            }
            MutableLiveData<List<TimestampedItemWrapper<T>>> orderItemWrappersMLD = new MutableLiveData<>();
            orderItemWrappersMLD.setValue(timestampedItemWrappers);
            return orderItemWrappersMLD;
        } else {
            return AbsentLiveData.create();
        }
    }
}
