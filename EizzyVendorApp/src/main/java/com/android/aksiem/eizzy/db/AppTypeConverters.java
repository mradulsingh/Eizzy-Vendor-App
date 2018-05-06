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

import com.android.aksiem.eizzy.vo.OrderItem;
import com.android.aksiem.eizzy.vo.support.Actor;
import com.android.aksiem.eizzy.vo.support.Price;
import com.android.aksiem.eizzy.vo.support.order.OrderDetails;
import com.android.aksiem.eizzy.vo.support.order.OrderState;
import com.android.aksiem.eizzy.vo.support.order.OrderStateTransition;
import com.android.aksiem.eizzy.vo.support.order.OrderType;
import com.android.aksiem.eizzy.vo.support.order.OrderedItem;
import com.android.aksiem.eizzy.vo.support.order.PriceComponent;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
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
    public static List<OrderedItem> stringToListOfOrderedItem(String stringOI) {
        if (stringOI == null)
            return null;

        Type type = new TypeToken<List<OrderedItem>>() {
        }.getType();
        return new Gson().fromJson(stringOI, type);
    }

    @TypeConverter
    public static String listOfOrderedItemToString(List<OrderedItem> listOI) {
        if (listOI == null)
            return null;

        Type type = new TypeToken<List<OrderedItem>>() {
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

}
