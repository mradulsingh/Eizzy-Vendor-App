package com.android.aksiem.eizzy.ui.order;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;

import com.android.aksiem.eizzy.app.AppResourceManager;
import com.android.aksiem.eizzy.repository.OrderItemsRepository;
import com.android.aksiem.eizzy.util.AbsentLiveData;
import com.android.aksiem.eizzy.vo.OrderItem;
import com.android.aksiem.eizzy.vo.Resource;
import com.android.aksiem.eizzy.vo.TimestampedItemWrapper;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by pdubey on 09/04/18.
 */

public class OrderItemsViewModel extends ViewModel {

    private LiveData<Resource<List<TimestampedItemWrapper<OrderItem>>>> timeStampedOrderItems;


    @Inject
    AppResourceManager appResourceManager;

    @Inject
    public OrderItemsViewModel(OrderItemsRepository orderItemsRepository) {
        timeStampedOrderItems = Transformations.switchMap(orderItemsRepository.loadItems(), (items) -> addTimestampToList(items));
    }

    public LiveData<Resource<List<TimestampedItemWrapper<OrderItem>>>> getOrderItems() {
        return timeStampedOrderItems;
    }

    private LiveData<Resource<List<TimestampedItemWrapper<OrderItem>>>> addTimestampToList(Resource<List<OrderItem>> liveDataItems) {
        List<OrderItem> items = liveDataItems.data;
        List<TimestampedItemWrapper<OrderItem>> timestampedItemWrappers = new ArrayList<>();
        //String initialTS = null;
        if (items != null) {
            for (OrderItem item : items) {
                /*String currTS = StringUtils.getTimestamp(item.getTimestamp(),
                        appResourceManager);
                if (initialTS == null || !initialTS.equals(currTS)) {
                    timestampedItemWrappers.add(new TimestampedItemWrapper<OrderItem>(Long.decode(currTS), null));
                    initialTS = currTS;
                }*/
                timestampedItemWrappers.add(new TimestampedItemWrapper<OrderItem>(null, item));
            }
            MutableLiveData<Resource<List<TimestampedItemWrapper<OrderItem>>>> orderItemWrappersMLD = new MutableLiveData<>();
            orderItemWrappersMLD.setValue(Resource.success(timestampedItemWrappers));
            return orderItemWrappersMLD;
        } else {
            return AbsentLiveData.create();
        }
    }

}
