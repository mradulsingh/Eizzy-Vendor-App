package com.android.aksiem.eizzy.ui.order;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.android.aksiem.eizzy.repository.OrderItemsRepository;
import com.android.aksiem.eizzy.vo.OrderItem;
import com.android.aksiem.eizzy.vo.Resource;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by pdubey on 09/04/18.
 */

public class OrderItemsViewModel extends ViewModel {

    private final LiveData<Resource<List<OrderItem>>> orderItems;

    @Inject
    public OrderItemsViewModel(OrderItemsRepository orderItemsRepository) {
        orderItems = orderItemsRepository.loadOrders();
    }

    public LiveData<Resource<List<OrderItem>>> getOrderItems() {
        return orderItems;
    }
}
