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

package com.android.aksiem.eizzy.db;

import android.arch.persistence.room.TypeConverter;

import com.android.aksiem.eizzy.vo.Accounting;
import com.android.aksiem.eizzy.vo.CoinPayTransaction;
import com.android.aksiem.eizzy.vo.CustomerDetails;
import com.android.aksiem.eizzy.vo.LatLng;
import com.android.aksiem.eizzy.vo.Location;
import com.android.aksiem.eizzy.vo.OrderActivityLog;
import com.android.aksiem.eizzy.vo.PaymentBreakupByMode;
import com.android.aksiem.eizzy.vo.support.Actor;
import com.android.aksiem.eizzy.vo.support.Price;
import com.android.aksiem.eizzy.vo.support.order.OrderActivityLogState;
import com.android.aksiem.eizzy.vo.support.order.OrderDetails;
import com.android.aksiem.eizzy.vo.support.order.OrderState;
import com.android.aksiem.eizzy.vo.support.order.OrderStateTransition;
import com.android.aksiem.eizzy.vo.support.order.OrderType;
import com.android.aksiem.eizzy.vo.support.order.OrderedItem;
import com.android.aksiem.eizzy.vo.support.order.PriceComponent;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class AppTypeConverters {

    @TypeConverter
    public static Price stringToPrice(String price) {
        return new Gson().fromJson(price, Price.class);
    }

    @TypeConverter
    public static String priceToString(Price price) {
        return new Gson().toJson(price, Price.class);
    }

    @TypeConverter
    public static OrderState stringToOrderState(String orderState) {
        return new Gson().fromJson(orderState, OrderState.class);
    }

    @TypeConverter
    public static String orderStateToString(OrderState orderState) {
        return new Gson().toJson(orderState, OrderState.class);
    }

    @TypeConverter
    public static OrderStateTransition stringToOrderStateTransition(String stateTransition) {
        return new Gson().fromJson(stateTransition, OrderStateTransition.class);
    }

    @TypeConverter
    public static String orderStateTransitionToString(OrderStateTransition stateTransition) {
        return new Gson().toJson(stateTransition, OrderStateTransition.class);
    }

    @TypeConverter
    public static List<OrderStateTransition> stringToListOfOrderStateTransition(String listOST) {
        if (listOST == null)
            return null;

        Type type = new TypeToken<List<OrderStateTransition>>() {
        }.getType();
        return new Gson().fromJson(listOST, type);
    }

    @TypeConverter
    public static String listOfOrderStateTransitionToString(List<OrderStateTransition> stringOST) {
        if (stringOST == null)
            return null;

        Type type = new TypeToken<List<OrderStateTransition>>() {
        }.getType();
        return new Gson().toJson(stringOST, type);
    }

    @TypeConverter
    public static Actor stringToActor(String actor) {
        return new Gson().fromJson(actor, Actor.class);
    }

    @TypeConverter
    public static String ActorToString(Actor actor) {
        return new Gson().toJson(actor, Actor.class);
    }

    @TypeConverter
    public static OrderType stringToOrderType(String orderType) {
        return new Gson().fromJson(orderType, OrderType.class);
    }

    @TypeConverter
    public static String orderTypeToString(OrderType orderType) {
        return new Gson().toJson(orderType, OrderType.class);
    }

    @TypeConverter
    public static OrderedItem stringToOrderedItem(String orderedItem) {
        return new Gson().fromJson(orderedItem, OrderedItem.class);
    }

    @TypeConverter
    public static String orderedItemToString(OrderedItem orderedItem) {
        return new Gson().toJson(orderedItem, OrderedItem.class);
    }

    @TypeConverter
    public static ArrayList<OrderedItem> stringToArrayListOfOrderedItem(String stringOI) {
        if (stringOI == null)
            return null;

        Type type = new TypeToken<ArrayList<OrderedItem>>() {
        }.getType();
        return new Gson().fromJson(stringOI, type);
    }

    @TypeConverter
    public static String arrayListOfOrderedItemToString(ArrayList<OrderedItem> listOI) {
        if (listOI == null)
            return null;

        Type type = new TypeToken<ArrayList<OrderedItem>>() {
        }.getType();
        return new Gson().toJson(listOI, type);
    }

    @TypeConverter
    public static PriceComponent stringToPriceComponent(String priceComponent) {
        return new Gson().fromJson(priceComponent, PriceComponent.class);
    }

    @TypeConverter
    public static String priceComponentToString(PriceComponent priceComponent) {
        return new Gson().toJson(priceComponent, PriceComponent.class);
    }

    @TypeConverter
    public static List<PriceComponent> stringToListOfPriceComponent(String stringPC) {
        if (stringPC == null)
            return null;

        Type type = new TypeToken<List<PriceComponent>>() {
        }.getType();
        return new Gson().fromJson(stringPC, type);
    }

    @TypeConverter
    public static String listOfPriceComponentToString(List<PriceComponent> listPC) {
        if (listPC == null)
            return null;

        Type type = new TypeToken<List<PriceComponent>>() {
        }.getType();
        return new Gson().toJson(listPC, type);
    }

    @TypeConverter
    public static OrderDetails stringToOrderDetails(String orderDetails) {
        return new Gson().fromJson(orderDetails, OrderDetails.class);
    }

    @TypeConverter
    public static String orderDetailsToString(OrderDetails orderDetails) {
        return new Gson().toJson(orderDetails, OrderDetails.class);
    }

    @TypeConverter
    public static ArrayList<String> stringToArrayListOfString(String stringALS) {
        if (stringALS == null)
            return null;

        Type type = new TypeToken<ArrayList<String>>() {
        }.getType();
        return new Gson().fromJson(stringALS, type);
    }

    @TypeConverter
    public static String arrayListOfStringToString(ArrayList<String> list) {
        if (list == null)
            return null;

        Type type = new TypeToken<ArrayList<String>>() {
        }.getType();
        return new Gson().toJson(list, type);
    }

    @TypeConverter
    public static LatLng stringToLatLng(String latLng) {
        return new Gson().fromJson(latLng, LatLng.class);
    }

    @TypeConverter
    public static String latLngToString(LatLng latLng) {
        return new Gson().toJson(latLng, LatLng.class);
    }

    @TypeConverter
    public static Location stringToLocation(String location) {
        return new Gson().fromJson(location, Location.class);
    }

    @TypeConverter
    public static String locationToString(Location location) {
        return new Gson().toJson(location, Location.class);
    }

    @TypeConverter
    public static PaymentBreakupByMode stringToPaymentBreakupByMode(String mode) {
        return new Gson().fromJson(mode, PaymentBreakupByMode.class);
    }

    @TypeConverter
    public static String paymentBreakupByModeToString(PaymentBreakupByMode mode) {
        return new Gson().toJson(mode, PaymentBreakupByMode.class);
    }

    @TypeConverter
    public static OrderActivityLog stringToOrderActivityLog(String log) {
        return new Gson().fromJson(log, OrderActivityLog.class);
    }

    @TypeConverter
    public static String orderActivityLogToString(OrderActivityLog log) {
        return new Gson().toJson(log, OrderActivityLog.class);
    }

    @TypeConverter
    public static ArrayList<OrderActivityLog> stringToArrayListOfOrderActivityLog(String string) {
        if (string == null)
            return null;

        Type type = new TypeToken<ArrayList<OrderActivityLog>>() {
        }.getType();
        return new Gson().fromJson(string, type);
    }

    @TypeConverter
    public static String arrayListOfOrderActivityLogToString(ArrayList<OrderActivityLog> list) {
        if (list == null)
            return null;

        Type type = new TypeToken<ArrayList<OrderActivityLog>>() {
        }.getType();
        return new Gson().toJson(list, type);
    }

    @TypeConverter
    public static CustomerDetails stringToCustomerDetails(String customerDetails) {
        return new Gson().fromJson(customerDetails, CustomerDetails.class);
    }

    @TypeConverter
    public static String customerDetailsToString(CustomerDetails customerDetails) {
        return new Gson().toJson(customerDetails, CustomerDetails.class);
    }

    @TypeConverter
    public static Accounting stringToAccounting(String accounting) {
        return new Gson().fromJson(accounting, Accounting.class);
    }

    @TypeConverter
    public static String accountingToString(Accounting accounting) {
        return new Gson().toJson(accounting, Accounting.class);
    }

    @TypeConverter
    public static OrderActivityLogState stringToOrderActivityLogState(String state) {
        return new Gson().fromJson(state, OrderActivityLogState.class);
    }

    @TypeConverter
    public static String orderActivityLogStateToString(OrderActivityLogState state) {
        return new Gson().toJson(state, OrderActivityLogState.class);
    }

    @TypeConverter
    public static String coinPayTransactionToString(CoinPayTransaction state) {
        return new Gson().toJson(state, CoinPayTransaction.class);
    }

    @TypeConverter
    public static CoinPayTransaction stringToCoinPayTransaction(String state) {
        return new Gson().fromJson(state, CoinPayTransaction.class);
    }

}
