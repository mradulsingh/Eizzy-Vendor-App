package com.android.aksiem.eizzy.ui.order;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;

import com.android.aksiem.eizzy.app.AppPrefManager;
import com.android.aksiem.eizzy.app.AppResourceManager;
import com.android.aksiem.eizzy.app.EizzyAppState;
import com.android.aksiem.eizzy.repository.OrderItemsRepository;
import com.android.aksiem.eizzy.util.AbsentLiveData;
import com.android.aksiem.eizzy.vo.EizzyApiRespone;
import com.android.aksiem.eizzy.vo.EizzyZone;
import com.android.aksiem.eizzy.vo.OrderItem;
import com.android.aksiem.eizzy.vo.Resource;
import com.android.aksiem.eizzy.vo.StoreManager;
import com.android.aksiem.eizzy.vo.TimestampedItemWrapper;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by pdubey on 09/04/18.
 */

public class OrderItemsViewModel extends ViewModel {

    private LiveData<Resource<List<TimestampedItemWrapper<OrderItem>>>> timeStampedOrderItems;

    private MutableLiveData<List<String>> orderIds = new MutableLiveData<>();

    private OrderItemsRepository orderItemsRepository;

    @Inject
    AppResourceManager appResourceManager;

    @Inject
    AppPrefManager appPrefManager;

    @Inject
    public OrderItemsViewModel(OrderItemsRepository orderItemsRepository) {
        this.orderItemsRepository = orderItemsRepository;
        timeStampedOrderItems = Transformations.switchMap(
                this.orderItemsRepository.loadItems(),
                (items) -> addTimestampToList(items));
    }


    public LiveData<Resource<List<TimestampedItemWrapper<OrderItem>>>> getOrderItems() {
        return timeStampedOrderItems;
    }

    public LiveData<Resource<EizzyApiRespone<ArrayList<EizzyZone>>>> getEizzyZones() {
        StoreManager manager = EizzyAppState.ManagerLoggedIn.getManagerDetails(appPrefManager);
        return orderItemsRepository.getEizzyZones(manager.token, manager.cityId);
    }

    public void setOrderIds(List<String> listOrderIds) {
        this.orderIds.setValue(listOrderIds);
    }

    private LiveData<Resource<List<TimestampedItemWrapper<OrderItem>>>> addTimestampToList(
            Resource<List<OrderItem>> liveDataItems) {

        List<OrderItem> items = liveDataItems.data;
        List<TimestampedItemWrapper<OrderItem>> timestampedItemWrappers = new ArrayList<>();

        if (items != null) {

            for (OrderItem item : items) {
                timestampedItemWrappers.add(new TimestampedItemWrapper<OrderItem>(
                        null, item));
            }

            MutableLiveData<Resource<List<TimestampedItemWrapper<OrderItem>>>> orderItemWMLD =
                    new MutableLiveData<>();
            orderItemWMLD.setValue(Resource.success(timestampedItemWrappers));
            return orderItemWMLD;

        } else {

            return AbsentLiveData.create();

        }
    }

}
