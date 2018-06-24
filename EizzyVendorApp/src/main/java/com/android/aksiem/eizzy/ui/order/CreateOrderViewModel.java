/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.aksiem.eizzy.ui.order;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.VisibleForTesting;

import com.android.aksiem.eizzy.repository.OrderItemsRepository;
import com.android.aksiem.eizzy.vo.EizzyApiRespone;
import com.android.aksiem.eizzy.vo.OrderDetailItem;
import com.android.aksiem.eizzy.vo.Resource;

import javax.inject.Inject;

/**
 * Created by napendersingh on 31/03/18.
 */

public class CreateOrderViewModel extends ViewModel {

    private OrderItemsRepository orderItemsRepository;

    private String customerName;
    private String customerMobile;
    private Boolean cashOnDelivery;
    private String locality;
    private String customerAddress;
    private String eizzyZoneId;
    private Float amount;
    private String billNumber;
    private Integer orderWeight;
    private Integer itemCount;
    private String orderDetails;
    private Boolean customerSignature;
    private Boolean scheduleDetails;
    private Long scheduleTimeStart;

    @Inject
    public CreateOrderViewModel(OrderItemsRepository orderItemsRepository) {
        this.orderItemsRepository = orderItemsRepository;
    }

    @VisibleForTesting
    public LiveData<Resource<EizzyApiRespone<OrderDetailItem>>> createOrderListing() {
        orderItemsRepository.setShouldLoadItemsToList(true);
        return orderItemsRepository.createOrder(customerName, customerMobile, cashOnDelivery,
                locality, customerAddress, eizzyZoneId, amount, billNumber, orderWeight,
                itemCount, orderDetails, customerSignature, scheduleDetails, scheduleTimeStart);
    }

    @VisibleForTesting
    public void retry() {
        createOrderListing();
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public void setCustomerMobile(String customerMobile) {
        this.customerMobile = customerMobile;
    }

    public void setCashOnDelivery(Boolean cashOnDelivery) {
        this.cashOnDelivery = cashOnDelivery;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    public void setEizzyZoneId(String eizzyZoneId) {
        this.eizzyZoneId = eizzyZoneId;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }

    public void setBillNumber(String billNumber) {
        this.billNumber = billNumber;
    }

    public void setOrderWeight(Integer orderWeight) {
        this.orderWeight = orderWeight;
    }

    public void setItemCount(Integer itemCount) {
        this.itemCount = itemCount;
    }

    public void setOrderDetails(String orderDetails) {
        this.orderDetails = orderDetails;
    }

    public void setCustomerSignature(Boolean customerSignature) {
        this.customerSignature = customerSignature;
    }

    public void setScheduleDetails(Boolean scheduleDetails) {
        this.scheduleDetails = scheduleDetails;
    }

    public void setScheduleTimeStart(Long scheduleTimeStart) {
        this.scheduleTimeStart = scheduleTimeStart;
    }
}
