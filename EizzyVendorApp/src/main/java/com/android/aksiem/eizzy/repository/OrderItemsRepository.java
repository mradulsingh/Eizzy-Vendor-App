package com.android.aksiem.eizzy.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.android.aksiem.eizzy.api.ApiResponse;
import com.android.aksiem.eizzy.api.AppService;
import com.android.aksiem.eizzy.app.AppExecutors;
import com.android.aksiem.eizzy.app.AppResourceManager;
import com.android.aksiem.eizzy.db.AppDb;
import com.android.aksiem.eizzy.db.OrderItemDao;
import com.android.aksiem.eizzy.di.AppScope;
import com.android.aksiem.eizzy.util.RateLimiter;
import com.android.aksiem.eizzy.vo.OrderItem;
import com.android.aksiem.eizzy.vo.Resource;
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
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import retrofit2.Response;

/**
 * Created by pdubey on 14/04/18.
 */

@AppScope
public class OrderItemsRepository {

    protected final AppDb appDb;

    protected final OrderItemDao orderItemDao;

    protected final AppService appService;

    protected final AppExecutors appExecutors;

    protected final AppResourceManager appResourceManager;

    protected RateLimiter<String> orderItemsRateLimiter = new RateLimiter<>(120,
            TimeUnit.SECONDS);

    @Inject
    public OrderItemsRepository(AppDb appDb, OrderItemDao orderItemDao,
                                AppService appService, AppExecutors appExecutors,
                                AppResourceManager appResourceManager) {

        this.appDb = appDb;
        this.orderItemDao = orderItemDao;
        this.appService = appService;
        this.appExecutors = appExecutors;
        this.appResourceManager = appResourceManager;

    }

    public LiveData<Resource<List<OrderItem>>> loadItems() {
        return new DbNetworkBoundResource<List<OrderItem>,
                List<OrderItem>>(appExecutors) {

            @Override
            protected void saveCallResult(@NonNull List<OrderItem> items) {
                orderItemDao.insertOrderItems(items);
            }

            @Override
            protected boolean shouldFetch(@Nullable List<OrderItem> data) {
                return data == null || data.isEmpty();
            }

            @NonNull
            @Override
            protected LiveData<List<OrderItem>> loadFromDb() {
                return orderItemDao.getAllItems();
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<List<OrderItem>>> createCall() {
                // TODO integrate with backend API
                Response<List<OrderItem>> response =
                        Response.success(mockData(30));
                ApiResponse<List<OrderItem>> apiItems =
                        new ApiResponse<>(response);
                MutableLiveData<ApiResponse<List<OrderItem>>> mld =
                        new MutableLiveData<>();
                mld.setValue(apiItems);
                return mld;
            }
        }.asLiveData();
    }

    private List<OrderItem> mockData(int pageSize) {
        List<OrderItem> items = new ArrayList<>();
        for (int i = 0; i < pageSize; i++) {
            items.add(generateSingleRandomOrder());
        }
        return items;
    }

    private OrderItem generateSingleRandomOrder() {

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
        return orderItem;
    }

    private Actor generateActor(ActorRole role) {
        Random random = new Random();
        List<String> actorNames = getActorNames();
        return new Actor(random.nextInt(), actorNames.get(random.nextInt(actorNames.size())),
                generatePhoneNumber(), role);
    }

    private OrderDetails generateSingleOrderItemDetails() {
        List<OrderedItem> orderedItems = generateListOfOrderedItems();
        List<PriceComponent> priceComponents = generateAdditionalPriceComponents(orderedItems);
        PriceComponent total = generateTotal(orderedItems, priceComponents);
        OrderDetails orderDetails = new OrderDetails(orderedItems, priceComponents, total,
                "Cash");
        return orderDetails;
    }

    private List<OrderedItem> generateListOfOrderedItems() {
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

    private List<PriceComponent> generateAdditionalPriceComponents(List<OrderedItem> items) {
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

    private float getOrderedItemsTotal(List<OrderedItem> items) {
        float total = 0.0f;
        for (OrderedItem item : items) {
            total += item.totalPrice;
        }
        return total;
    }

    private PriceComponent generateTotal(List<OrderedItem> items,
                                         List<PriceComponent> additionalCharges) {
        String componentName = "Total";
        String currency = "Rs.";
        float total = getOrderedItemsTotal(items);
        for (PriceComponent additionalCharge : additionalCharges) {
            total += additionalCharge.price.amount;
        }
        return new PriceComponent(componentName, new Price(total, currency));
    }

    private String generatePhoneNumber() {
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

    private String generateOrderItemId(int length) {
        String validChars = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ!@#$_-&";
        Random random = new Random();
        StringBuilder stringBuilder = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            stringBuilder.append(validChars.charAt(random.nextInt(validChars.length())));
        }
        return stringBuilder.toString();
    }

    private List<String> getOrderableItems() {
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

    private List<String> getActorNames() {
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
