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
import com.android.aksiem.eizzy.vo.OrderDetailItem;
import com.android.aksiem.eizzy.vo.OrderListItem;
import com.android.aksiem.eizzy.vo.RequestConstants;
import com.android.aksiem.eizzy.vo.Resource;
import com.android.aksiem.eizzy.vo.StoreManager;
import com.android.aksiem.eizzy.vo.TimestampedItemWrapper;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by pdubey on 09/04/18.
 */

public class OrderItemsViewModel extends ViewModel {

    private LiveData<Resource<List<TimestampedItemWrapper<OrderDetailItem>>>> dummyTimestampedOrderItems;

    private LiveData<Resource<List<TimestampedItemWrapper<OrderListItem>>>> timestampedOrderItems;


    private MutableLiveData<List<String>> orderIds = new MutableLiveData<>();

    private OrderItemsRepository orderItemsRepository;

    private Long pageIndex = 0l;

    private Long status = RequestConstants.OrderItemsList.all;

    private Long startDate = 0l;

    private Long endDate = 0l;

    @Inject
    AppResourceManager appResourceManager;

    @Inject
    AppPrefManager appPrefManager;

    @Inject
    public OrderItemsViewModel(OrderItemsRepository orderItemsRepository) {
        this.orderItemsRepository = orderItemsRepository;
    }


    public LiveData<Resource<List<TimestampedItemWrapper<OrderDetailItem>>>> getDummyOrderItems() {
        dummyTimestampedOrderItems = Transformations.switchMap(
                orderItemsRepository.loadDummyItems(),
                (items) -> {
                    if (items != null) {
                        List<OrderDetailItem> orderDetailItems = new ArrayList<>();
                        orderDetailItems.addAll(items.data);
                        Resource<List<OrderDetailItem>> resource = new Resource<>(items.status,
                                orderDetailItems, items.message);
                        return addTimestampToList(resource);
                    }
                    return AbsentLiveData.create();
                });
        return dummyTimestampedOrderItems;
    }

    public LiveData<Resource<List<TimestampedItemWrapper<OrderListItem>>>> getAllOrderItems() {

        timestampedOrderItems = Transformations.switchMap(orderItemsRepository.loadItemsToList(
                pageIndex, status, startDate, endDate), (items) -> {

            MutableLiveData<Resource<List<TimestampedItemWrapper<OrderListItem>>>> toReturn =
                    new MutableLiveData<>();

            if (items != null) {
                List<TimestampedItemWrapper<OrderListItem>> list = new ArrayList<>();
                switch (items.status) {
                    case SUCCESS:
                        if (items.data != null && items.data.data != null
                                && items.data.data.items != null
                                && !items.data.data.items.isEmpty()) {
                            for (OrderListItem item : items.data.data.items) {
                                list.add(new TimestampedItemWrapper<>(null, item));
                            }
                        }
                        break;
                }
                Resource<List<TimestampedItemWrapper<OrderListItem>>> resource = new Resource<>(
                        items.status, list, items.message);
                toReturn.setValue(resource);
                return toReturn;
            }
            return AbsentLiveData.create();
        });
        return timestampedOrderItems;
    }

    public LiveData<Resource<EizzyApiRespone<ArrayList<EizzyZone>>>> getEizzyZones() {
        StoreManager manager = EizzyAppState.ManagerLoggedIn.getManagerDetails(appPrefManager);
        return orderItemsRepository.getEizzyZones(manager.token, manager.cityId);
    }

    public void setOrderIds(List<String> listOrderIds) {
        this.orderIds.setValue(listOrderIds);
    }

    public void setPageIndex(Long pageIndex) {
        this.pageIndex = pageIndex;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public void setStartDate(Long startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(Long endDate) {
        this.endDate = endDate;
    }

    private LiveData<Resource<List<TimestampedItemWrapper<OrderDetailItem>>>> addTimestampToList(
            Resource<List<OrderDetailItem>> liveDataItems) {

        List<OrderDetailItem> items = liveDataItems.data;
        List<TimestampedItemWrapper<OrderDetailItem>> timestampedItemWrappers = new ArrayList<>();

        if (items != null) {

            for (OrderDetailItem item : items) {
                timestampedItemWrappers.add(new TimestampedItemWrapper<>(
                        null, item));
            }

            MutableLiveData<Resource<List<TimestampedItemWrapper<OrderDetailItem>>>> orderItemWMLD =
                    new MutableLiveData<>();
            orderItemWMLD.setValue(Resource.success(timestampedItemWrappers));
            return orderItemWMLD;

        } else {

            return AbsentLiveData.create();

        }
    }
}
