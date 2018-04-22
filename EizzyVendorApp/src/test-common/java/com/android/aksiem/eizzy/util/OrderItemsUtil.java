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

package com.android.aksiem.eizzy.util;

import com.android.aksiem.eizzy.vo.OrderItem;
import com.android.aksiem.eizzy.vo.TimestampedItemWrapper;
import com.android.aksiem.eizzy.vo.support.Actor;
import com.android.aksiem.eizzy.vo.support.ActorRole;
import com.android.aksiem.eizzy.vo.support.Price;
import com.android.aksiem.eizzy.vo.support.order.OrderDetails;
import com.android.aksiem.eizzy.vo.support.order.OrderState;
import com.android.aksiem.eizzy.vo.support.order.OrderType;
import com.android.aksiem.eizzy.vo.support.order.OrderedItem;
import com.android.aksiem.eizzy.vo.support.order.PriceComponent;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class OrderItemsUtil {

    public static List<OrderItem> createOrderItem(int pageSize) {
        List<OrderItem> items = new ArrayList<>();
        for (int i = 0; i < pageSize; i++) {
            items.add(generateSingleRandomOrder().item);
        }
        return items;
    }

    private static TimestampedItemWrapper<OrderItem> generateSingleRandomOrder() {
        Random random = new Random();
        String orderId = generateOrderItemId(16);
        Actor customer = generateActor(ActorRole.CUSTOMER);
        OrderDetails details = generateSingleOrderItemDetails();
        Price price = details.total.price;
        long timestamp = System.currentTimeMillis();
        SimpleDateFormat format = new SimpleDateFormat("h:mm a '|' MMM d',' yyyy");
        String stringTimestamp = format.format(new Date(timestamp));
        OrderType orderType = OrderType.FOOD;
        int size = OrderState.values().length;
        OrderState orderState = OrderState.values()[random.nextInt(size)];
        OrderItem orderItem = new OrderItem(orderId, details, customer, price, timestamp,
                stringTimestamp, orderType, orderState);
        return new TimestampedItemWrapper<>(null, orderItem);
    }

    private static Actor generateActor(ActorRole role) {
        Random random = new Random();
        List<String> actorNames = getActorNames();
        return new Actor(random.nextInt(), actorNames.get(random.nextInt(actorNames.size())),
                generatePhoneNumber(), role);
    }

    private static OrderDetails generateSingleOrderItemDetails() {
        List<OrderedItem> orderedItems = generateListOfOrderedItems();
        List<PriceComponent> priceComponents = generateAdditionalPriceComponents(orderedItems);
        PriceComponent total = generateTotal(orderedItems, priceComponents);
        OrderDetails orderDetails = new OrderDetails(orderedItems, priceComponents, total,
                "Cash");
        return orderDetails;
    }

    private static List<OrderedItem> generateListOfOrderedItems() {
        List<OrderedItem> toReturn = new ArrayList<>();
        List<String> orderableItems = getOrderableItems();
        Random random = new Random();
        int max = random.nextInt(10) + 1;
        for (int i = 0; i < max; i++) {
            String itemName = orderableItems.get(random.nextInt(orderableItems.size()));
            double quantity = random.nextInt(5) + 1;
            double unitPrice = random.nextInt(500) + 100;
            double totalPrice = quantity * unitPrice;
            OrderedItem item = new OrderedItem(itemName, quantity, "order", 1,
                    unitPrice, totalPrice, "Rs.");
            toReturn.add(item);
        }
        return toReturn;
    }

    private static List<PriceComponent> generateAdditionalPriceComponents(List<OrderedItem> items) {
        float rawTotal = getOrderedItemsTotal(items);
        PriceComponent cgst = new PriceComponent("cgst @2.5%",
                new Price(rawTotal * 1.025f, "Rs."));
        PriceComponent sgst = new PriceComponent("sgst @2.5%",
                new Price(rawTotal * 1.025f, "Rs."));
        PriceComponent serviceCharge = new PriceComponent("service charge @10%",
                new Price(rawTotal * 1.1f, "Rs."));
        List<PriceComponent> additionalCharges = new ArrayList<>();
        additionalCharges.add(cgst);
        additionalCharges.add(sgst);
        Random random = new Random();
        if (random.nextInt(10) < 5) {
            additionalCharges.add(serviceCharge);
        }
        return additionalCharges;
    }

    private static float getOrderedItemsTotal(List<OrderedItem> items) {
        float total = 0.0f;
        for (OrderedItem item : items) {
            total += item.totalPrice;
        }
        return total;
    }

    private static PriceComponent generateTotal(List<OrderedItem> items,
                                                List<PriceComponent> additionalCharges) {
        String componentName = "Total";
        String currency = "Rs.";
        float total = getOrderedItemsTotal(items);
        for (PriceComponent additionalCharge : additionalCharges) {
            total += additionalCharge.price.amount;
        }
        return new PriceComponent(componentName, new Price(total, currency));
    }

    private static String generatePhoneNumber() {
        String validChars = "0123456789";
        Random random = new Random();
        StringBuilder stringBuilder = new StringBuilder(10);
        for (int i = 0; i < 10; i++) {
            int index = random.nextInt(validChars.length());
            if (i == 0 && index == 0) {
                index++;
            }
            stringBuilder.append(validChars.charAt(index));
        }
        return stringBuilder.toString();
    }

    private static String generateOrderItemId(int length) {
        String validChars = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ!@#$_-&";
        Random random = new Random();
        StringBuilder stringBuilder = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            stringBuilder.append(validChars.charAt(random.nextInt(validChars.length())));
        }
        return stringBuilder.toString();
    }

    private static List<String> getOrderableItems() {
        List<String> orderableItems = new ArrayList<>();
        orderableItems.add("Chicken Malai Tikka");
        orderableItems.add("Tandoori Chicken");
        orderableItems.add("Kalmi Kebab");
        orderableItems.add("Steamed Chicken Momos");
        orderableItems.add("Drums of Heaven");
        orderableItems.add("Chicken Lollipop");
        orderableItems.add("Chilli Chicken");
        orderableItems.add("Dijej Al Faham");
        orderableItems.add("BBQ Chicken");
        orderableItems.add("Paneer Butter Masala");
        orderableItems.add("Kadhai Paneer");
        orderableItems.add("Dal Makhani");
        orderableItems.add("Chicken Butter Masala");
        orderableItems.add("Egg Fried Rice");
        orderableItems.add("Veg Fried Rice");
        orderableItems.add("Chicken Fried Rice");
        orderableItems.add("Shawarma Roll");
        orderableItems.add("Tandoori Roll");
        orderableItems.add("Butter Naan");
        orderableItems.add("Ghee Rice");
        orderableItems.add("Malabar Chicken Biriyani");
        orderableItems.add("Hyderabadi Dum Biriyani");
        orderableItems.add("Chicken Tikka Biriyani");
        orderableItems.add("Malabar Family Biriyani");
        orderableItems.add("Hyderabadi Mutton Biriyani");
        orderableItems.add("Veg Meal");
        orderableItems.add("Big Crunch Meal");
        orderableItems.add("Big Crunch Combo");
        orderableItems.add("Coke");
        orderableItems.add("Thums Up");
        orderableItems.add("Pepsi");
        orderableItems.add("Baby Meal Box");
        orderableItems.add("Chicken Meal Box");
        orderableItems.add("Chicken Snack Box");
        return orderableItems;
    }

    private static List<String> getActorNames() {
        List<String> names = new ArrayList<>();
        names.add("Sandeep");
        names.add("Napender");
        names.add("Subhasis");
        names.add("Netesh");
        names.add("Satish");
        names.add("Harish");
        return names;
    }


}
