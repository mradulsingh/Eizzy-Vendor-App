package com.android.aksiem.eizzy.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.android.aksiem.eizzy.api.ApiResponse;
import com.android.aksiem.eizzy.api.AppService;
import com.android.aksiem.eizzy.app.AppExecutors;
import com.android.aksiem.eizzy.app.AppPrefManager;
import com.android.aksiem.eizzy.app.AppResourceManager;
import com.android.aksiem.eizzy.app.EizzyAppState;
import com.android.aksiem.eizzy.db.AppDb;
import com.android.aksiem.eizzy.db.OrderDetailItemDao;
import com.android.aksiem.eizzy.db.OrderListItemDao;
import com.android.aksiem.eizzy.di.AppScope;
import com.android.aksiem.eizzy.util.AbsentLiveData;
import com.android.aksiem.eizzy.util.RateLimiter;
import com.android.aksiem.eizzy.util.StringUtils;
import com.android.aksiem.eizzy.view.timeline.AppTimelinePointView;
import com.android.aksiem.eizzy.vo.Accounting;
import com.android.aksiem.eizzy.vo.CustomerDetails;
import com.android.aksiem.eizzy.vo.EizzyApiRespone;
import com.android.aksiem.eizzy.vo.EizzyZone;
import com.android.aksiem.eizzy.vo.LatLng;
import com.android.aksiem.eizzy.vo.Location;
import com.android.aksiem.eizzy.vo.OrderActivityLog;
import com.android.aksiem.eizzy.vo.OrderDetailItem;
import com.android.aksiem.eizzy.vo.OrderListItem;
import com.android.aksiem.eizzy.vo.OrderListWrapper;
import com.android.aksiem.eizzy.vo.PaymentBreakupByMode;
import com.android.aksiem.eizzy.vo.RequestConstants;
import com.android.aksiem.eizzy.vo.Resource;
import com.android.aksiem.eizzy.vo.StoreManager;
import com.android.aksiem.eizzy.vo.support.Actor;
import com.android.aksiem.eizzy.vo.support.ActorRole;
import com.android.aksiem.eizzy.vo.support.Price;
import com.android.aksiem.eizzy.vo.support.TitlizedList;
import com.android.aksiem.eizzy.vo.support.order.ExclusiveTax;
import com.android.aksiem.eizzy.vo.support.order.OrderActivityLogState;
import com.android.aksiem.eizzy.vo.support.order.OrderDetails;
import com.android.aksiem.eizzy.vo.support.order.OrderState;
import com.android.aksiem.eizzy.vo.support.order.OrderStateTransition;
import com.android.aksiem.eizzy.vo.support.order.OrderType;
import com.android.aksiem.eizzy.vo.support.order.OrderedItem;
import com.android.aksiem.eizzy.vo.support.order.PriceComponent;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import okhttp3.RequestBody;
import retrofit2.Response;

/**
 * Created by pdubey on 14/04/18.
 */

@AppScope
public class OrderItemsRepository {

    protected final AppDb appDb;

    protected final OrderDetailItemDao orderDetailItemDao;

    protected final OrderListItemDao orderListItemDao;

    protected final AppService appService;

    protected final AppExecutors appExecutors;

    protected final AppResourceManager appResourceManager;

    protected final AppPrefManager appPrefManager;

    protected RateLimiter<String> orderItemsRateLimiter = new RateLimiter<>(120,
            TimeUnit.SECONDS);

    private boolean shouldLoadItemsToList = false;
    private boolean shouldGetDetailedItem = false;

    @Inject
    public OrderItemsRepository(
            AppDb appDb,
            OrderDetailItemDao orderDetailItemDao,
            OrderListItemDao orderListItemDao,
            AppService appService,
            AppExecutors appExecutors,
            AppResourceManager appResourceManager,
            AppPrefManager appPrefManager) {

        this.appDb = appDb;
        this.orderDetailItemDao = orderDetailItemDao;
        this.orderListItemDao = orderListItemDao;
        this.appService = appService;
        this.appExecutors = appExecutors;
        this.appResourceManager = appResourceManager;
        this.appPrefManager = appPrefManager;

    }

    public LiveData<Resource<EizzyApiRespone<OrderListWrapper>>> loadItemsToList(
            long pageIndex, long status, long startDate, long endDate) {

        return new DbNetworkBoundResource<EizzyApiRespone<OrderListWrapper>,
                EizzyApiRespone<OrderListWrapper>>(appExecutors) {

            @Override
            protected void saveCallResult(@NonNull EizzyApiRespone<OrderListWrapper> item) {
                if (item != null && item.data != null && item.data.items != null
                        && !item.data.items.isEmpty()) {
                    orderListItemDao.insert(item.data.items);
                }
            }

            @Override
            protected boolean shouldFetch(@Nullable EizzyApiRespone<OrderListWrapper> data) {
                return data == null || data.data == null || data.data.items == null
                        || data.data.items.isEmpty() || checkAndToggle(shouldLoadItemsToList);
            }

            @NonNull
            @Override
            protected LiveData<EizzyApiRespone<OrderListWrapper>> loadFromDb() {

                return Transformations.switchMap(
                        orderListItemDao.getAllItems(), (orderListItems) -> {

                            if (orderListItems != null && !orderListItems.isEmpty()) {
                                ArrayList<OrderListItem> items = new ArrayList<>();
                                items.addAll(orderListItems);
                                OrderListWrapper wrapper = new OrderListWrapper(items);
                                EizzyApiRespone<OrderListWrapper> eizzyApiRespone =
                                        new EizzyApiRespone<>("", wrapper);
                                MutableLiveData<EizzyApiRespone<OrderListWrapper>> mutableLiveData =
                                        new MutableLiveData<>();
                                mutableLiveData.setValue(eizzyApiRespone);
                                return mutableLiveData;
                            } else {
                                return AbsentLiveData.create();
                            }

                });
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<EizzyApiRespone<OrderListWrapper>>> createCall() {
                StoreManager manager = EizzyAppState.ManagerLoggedIn.getManagerDetails(
                        appPrefManager);
                return appService.getAllOrders(
                        RequestConstants.Language.english,
                        manager.token,
                        manager.storeId,
                        pageIndex,
                        status,
                        startDate,
                        endDate);
            }
        }.asLiveData();

    }

    public LiveData<Resource<EizzyApiRespone<OrderDetailItem>>> getDetailedItem(String orderId) {
        return new DbNetworkBoundResource<EizzyApiRespone<OrderDetailItem>,
                EizzyApiRespone<OrderDetailItem>>(appExecutors) {

            @Override
            protected void saveCallResult(@NonNull EizzyApiRespone<OrderDetailItem> item) {
                if (item != null && item.data != null) {
                    orderDetailItemDao.insert(item.data);
                }
            }

            @Override
            protected boolean shouldFetch(@Nullable EizzyApiRespone<OrderDetailItem> data) {
                return data == null || data.data == null || checkAndToggle(shouldGetDetailedItem);
            }

            @NonNull
            @Override
            protected LiveData<EizzyApiRespone<OrderDetailItem>> loadFromDb() {
                List<String> orderIds = new ArrayList<>();
                orderIds.add(orderId);
                return Transformations.switchMap(orderDetailItemDao.getItemsByIds(orderIds),
                        (items) -> {
                    if (items != null && !items.isEmpty()) {
                        EizzyApiRespone<OrderDetailItem> item = new EizzyApiRespone<>("",
                                items.get(0));
                        MutableLiveData<EizzyApiRespone<OrderDetailItem>> mutableLiveData =
                                new MutableLiveData<>();
                        mutableLiveData.setValue(item);
                        return mutableLiveData;
                    }
                    return AbsentLiveData.create();
                });
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<EizzyApiRespone<OrderDetailItem>>> createCall() {
                StoreManager manager = EizzyAppState.ManagerLoggedIn.getManagerDetails(
                        appPrefManager);
                return appService.getItemById(RequestConstants.Language.english, manager.token,
                        orderId);
            }
        }.asLiveData();
    }

    public LiveData<Resource<ArrayList<OrderDetailItem>>> loadDummyItems(List<String> orderIds) {
        return new DbNetworkBoundResource<ArrayList<OrderDetailItem>,
                ArrayList<OrderDetailItem>>(appExecutors) {

            @Override
            protected void saveCallResult(@NonNull ArrayList<OrderDetailItem> items) {
                orderDetailItemDao.insert(items);
            }

            @Override
            protected boolean shouldFetch(@Nullable ArrayList<OrderDetailItem> data) {
                return data == null || data.isEmpty();
            }

            @NonNull
            @Override
            protected LiveData<ArrayList<OrderDetailItem>> loadFromDb() {
                if (orderIds == null) {
                    return Transformations.switchMap(orderDetailItemDao.getAllItems(), (items) -> {
                        if (items != null && !items.isEmpty()) {
                            MutableLiveData<ArrayList<OrderDetailItem>> mutableLiveData =
                                    new MutableLiveData<>();
                            ArrayList<OrderDetailItem> detailItems = new ArrayList<>();
                            detailItems.addAll(items);
                            mutableLiveData.setValue(detailItems);
                            return mutableLiveData;
                        }
                        return AbsentLiveData.create();
                    });
                } else {
                    return Transformations.switchMap(orderListItemDao.getItemsByIds(orderIds),
                            (items) -> {

                        if (items != null && !items.isEmpty()) {
                            MutableLiveData<ArrayList<OrderDetailItem>> mutableLiveData =
                                    new MutableLiveData<>();
                            ArrayList<OrderDetailItem> detailItems = new ArrayList<>();
                            detailItems.addAll(detailItems);
                            mutableLiveData.setValue(detailItems);
                            return mutableLiveData;
                        }
                        return AbsentLiveData.create();
                    });
                }
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<ArrayList<OrderDetailItem>>> createCall() {
                // TODO integrate with backend API
                Response<ArrayList<OrderDetailItem>> response =
                        Response.success(mockData(orderIds, 30));
                ApiResponse<ArrayList<OrderDetailItem>> apiItems =
                        new ApiResponse<>(response);
                MutableLiveData<ApiResponse<ArrayList<OrderDetailItem>>> mld =
                        new MutableLiveData<>();
                mld.setValue(apiItems);
                return mld;
            }
        }.asLiveData();
    }

    public LiveData<Resource<ArrayList<OrderDetailItem>>> loadDummyItems() {
        return new DbNetworkBoundResource<ArrayList<OrderDetailItem>,
                ArrayList<OrderDetailItem>>(appExecutors) {

            @Override
            protected void saveCallResult(@NonNull ArrayList<OrderDetailItem> items) {
                orderDetailItemDao.insert(items);
            }

            @Override
            protected boolean shouldFetch(@Nullable ArrayList<OrderDetailItem> data) {
                return data == null || data.isEmpty();
            }

            @NonNull
            @Override
            protected LiveData<ArrayList<OrderDetailItem>> loadFromDb() {

                return Transformations.switchMap(orderDetailItemDao.getAllItems(), (items) -> {
                    if (items != null && !items.isEmpty()) {
                        ArrayList<OrderDetailItem> orderDetailItems = new ArrayList<>();
                        orderDetailItems.addAll(items);
                        MutableLiveData<ArrayList<OrderDetailItem>> mutableLiveData =
                                new MutableLiveData<>();
                        mutableLiveData.setValue(orderDetailItems);
                        return mutableLiveData;
                    }
                    return AbsentLiveData.create();
                });
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<ArrayList<OrderDetailItem>>> createCall() {
                // TODO integrate with backend API
                Response<ArrayList<OrderDetailItem>> response =
                        Response.success(mockData(null, 30));
                ApiResponse<ArrayList<OrderDetailItem>> apiItems =
                        new ApiResponse<>(response);
                MutableLiveData<ApiResponse<ArrayList<OrderDetailItem>>> mld =
                        new MutableLiveData<>();
                mld.setValue(apiItems);
                return mld;
            }
        }.asLiveData();
    }

    public LiveData<Resource<EizzyApiRespone<ArrayList<EizzyZone>>>> getEizzyZones() {
        return new NoCacheNetworkBoundResource<EizzyApiRespone<ArrayList<EizzyZone>>,
                EizzyApiRespone<ArrayList<EizzyZone>>>(appExecutors) {

            @NonNull
            @Override
            protected LiveData<EizzyApiRespone<ArrayList<EizzyZone>>> getCallResult(
                    @NonNull EizzyApiRespone<ArrayList<EizzyZone>> item) {
                MutableLiveData<EizzyApiRespone<ArrayList<EizzyZone>>> m = new MutableLiveData<>();
                m.setValue(item);
                return m;
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<EizzyApiRespone<ArrayList<EizzyZone>>>> createCall() {
                StoreManager manager = EizzyAppState.ManagerLoggedIn.getManagerDetails(
                        appPrefManager);
                return appService.getEizzyZones(
                        RequestConstants.Language.english,
                        manager.token,
                        StringUtils.getPlainTextRequestBody(manager.cityId));
            }
        }.asLiveData();
    }

    public LiveData<Resource<EizzyApiRespone<OrderDetailItem>>> createOrder(
            String name, String mobile, Boolean cashOnDelivery, String locality,
            String customerAddress, String eizzyZoneId, Float billAmount, String billNumber,
            Integer orderWeight, Integer totalItems, String orderDetails, Boolean customerSignature,
            Boolean orderLater, Long scheduleTime) {


        return new DbNetworkBoundResource<EizzyApiRespone<OrderDetailItem>,
                EizzyApiRespone<OrderDetailItem>>(appExecutors) {

            private String orderId;

            @Override
            protected void saveCallResult(@NonNull EizzyApiRespone<OrderDetailItem> item) {
                if (item != null && item.data != null) {
                    orderId = item.data.orderId;
                    orderDetailItemDao.insert(item.data);
                }
            }

            @Override
            protected boolean shouldFetch(@Nullable EizzyApiRespone<OrderDetailItem> data) {
                return data == null || data.data == null;
            }

            @NonNull
            @Override
            protected LiveData<EizzyApiRespone<OrderDetailItem>> loadFromDb() {
                return Transformations.switchMap(orderDetailItemDao.getItemById(orderId),
                        (item) -> {
                    if (item != null) {
                        EizzyApiRespone<OrderDetailItem> respone =
                                new EizzyApiRespone<>("", item);
                        MutableLiveData<EizzyApiRespone<OrderDetailItem>> mutableLiveData =
                                new MutableLiveData<>();
                        mutableLiveData.setValue(respone);
                    }
                    return AbsentLiveData.create();
                });
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<EizzyApiRespone<OrderDetailItem>>> createCall() {
                StoreManager manager = EizzyAppState.ManagerLoggedIn.getManagerDetails(appPrefManager);
                long creationTime = System.currentTimeMillis();
                return appService.createOrder(
                        RequestConstants.Language.english,
                        manager.token,
                        StringUtils.getPlainTextRequestBody(name),
                        StringUtils.getPlainTextRequestBody(RequestConstants.CountryCode.india),
                        StringUtils.getPlainTextRequestBody(mobile),
                        StringUtils.getPlainTextRequestBody(cashOnDelivery.toString()),
                        StringUtils.getPlainTextRequestBody(locality),
                        StringUtils.getPlainTextRequestBody(customerAddress),
                        StringUtils.getPlainTextRequestBody(eizzyZoneId),
                        StringUtils.getPlainTextRequestBody(billAmount.toString()),
                        StringUtils.getPlainTextRequestBody(billNumber),
                        StringUtils.getPlainTextRequestBody(orderWeight.toString()),
                        StringUtils.getPlainTextRequestBody(totalItems.toString()),
                        StringUtils.getPlainTextRequestBody(orderDetails),
                        StringUtils.getPlainTextRequestBody(customerSignature.toString()),
                        StringUtils.getPlainTextRequestBody(Integer.toString(
                                RequestConstants.OrderSchedule.getOrderScheduleCode(orderLater))),
                        StringUtils.getPlainTextRequestBody(Long.toString(creationTime)),
                        StringUtils.getPlainTextRequestBody(RequestConstants.OrderSchedule
                                .getOrderScheduledTime(orderLater, scheduleTime, creationTime)),
                        StringUtils.getPlainTextRequestBody(
                                Integer.toString(RequestConstants.Platform.android)),
                        StringUtils.getPlainTextRequestBody(Integer.toString(RequestConstants
                                .OrderPayment.getOrderPaymentTypeCode(cashOnDelivery))),
                        StringUtils.getPlainTextRequestBody(Integer.toString(
                                RequestConstants.StoreType.getStoreType(manager.serviceType))),
                        StringUtils.getPlainTextRequestBody(Integer.toString(
                                RequestConstants.OrderToCustomerStrategy.delivery)),
                        StringUtils.getPlainTextRequestBody(manager.storeId));
            }
        }.asLiveData();
    }

    public void setShouldLoadItemsToList(boolean shouldLoadItemsToList) {
        this.shouldLoadItemsToList = shouldLoadItemsToList;
    }

    public void setShouldGetDetailedItem(boolean shouldGetDetailedItem) {
        this.shouldGetDetailedItem = shouldGetDetailedItem;
    }

    private boolean checkAndToggle(boolean value) {
        boolean toReturn = value;
        value = !value;
        return toReturn;
    }

    private ArrayList<OrderDetailItem> mockData(List<String> orderIds, int pageSize) {
        ArrayList<OrderDetailItem> items = new ArrayList<>();
        if (orderIds == null) {
            for (int i = 0; i < pageSize; i++) {
                items.add(generateSingleRandomOrder(null, i + 1));
            }
        } else if (orderIds.size() < pageSize) {
            for (String orderId : orderIds) {
                items.add(generateSingleRandomOrder(orderId, null));
            }
        }
        return items;
    }

    private OrderDetailItem generateSingleRandomOrder(String stringOrderId, Integer id) {

        String storeId = generateOrderItemId(10);
        LatLng storeCoordinates = new LatLng(0d, 0d);
        String storeName = "Cafe Coffee Day";
        Integer forcedAccept = 1;
        Integer storeCommissionType = 1;
        String storeCommissionTypeMessage = "";
        Integer driverType = 1;
        String storeAddress = "Marathalli";
        Long timestamp = System.currentTimeMillis();
        String orderTimestamp = Long.toString(timestamp);
        String cartId = generateOrderItemId(10);
        Float deliveryCharge = 25f;
        OrderType orderType = OrderType.FOOD;
        String orderTypeMsg = "Bulk Order";
        Integer paymentType = 1;
        String paymentTypeMessage = "card";

        Date datetime = new Date(timestamp);
        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/mm/yyyy");
        String bookingDate = dateFormatter.format(datetime);
        String dueDate = bookingDate;

        Integer serviceType = 1;
        Integer bookingType = 1;
        Integer pricingModel = 0;
        String zoneType = null;
        String extraNote = null;
        CustomerDetails customerDetails = generateCustomerDetails();
        Location location = generateLocation();
        String abbreviation = null;
        String abbreviationText = null;
        String currency = "INR";
        String currencySymbol = "₹";
        String mileageMetric = null;
        PaymentBreakupByMode paidBy = new PaymentBreakupByMode(250f, 0f, 0f);
        Accounting accounting = generateAccounting();
        String orderId;
        if (stringOrderId != null && stringOrderId.length() > 0) {
            orderId = stringOrderId;
        } else {
            orderId = (id == null || id <= 0) ? generateOrderItemId(16) : id.toString();
        }
        OrderDetailItem item = new OrderDetailItem(storeId, storeCoordinates, storeName, forcedAccept,
                storeCommissionType, storeCommissionTypeMessage, driverType, storeAddress, cartId,
                deliveryCharge, orderType, orderTypeMsg, paymentType, paymentTypeMessage,
                bookingDate, timestamp, dueDate, timestamp, serviceType, bookingType, pricingModel,
                zoneType, extraNote, customerDetails, location, abbreviation, abbreviationText,
                currency, currencySymbol, mileageMetric, paidBy, accounting, orderId);
        item.setActivityLogs(generateActivityLogs());
        item.setCartTotal(250f);
        item.setCartDiscount(0f);
        item.setStoreCommission(0f);
        item.setSubTotalAmountWithExcTax(250f);
        item.setSubTotalAmount(250f);
        item.setExcTax(0f);

        ArrayList<ExclusiveTax> exclusiveTaxes = new ArrayList<>();
        item.setExclusiveTaxes(exclusiveTaxes);

        item.setDiscount(0f);
        item.setTotalAmount(250f);
        item.setCustomerCoordinates(new LatLng(0d, 0d));
        item.setStatus(1);
        item.setStatusMessage("");

        return item;
    }

    private CustomerDetails generateCustomerDetails() {
        Random random = new Random();
        List<String> actorNames = getActorNames();
        return new CustomerDetails(generateOrderItemId(5),
                actorNames.get(random.nextInt(actorNames.size())),
                "a@b.c", generatePhoneNumber(), "+91", "",
                generateOrderItemId(24), 1, generateOrderItemId(24),
                "", "");
    }

    private Location generateLocation() {
        Location location = new Location();
        location.address = "";
        location.area = "";
        location.city = "";
        location.country = "";
        location.latLng = new LatLng(0d, 0d);
        location.locality = "";
        location.pickUpZone = 0;
        location.placeId = "";
        location.placeName = "";
        location.postalCode = "";
        location.state = "";
        return location;
    }

    private Accounting generateAccounting() {
        return new Accounting(0, "", 0f,
                0f, 0, "", 0f,
                0f, 0f, 0f,
                0f, 0f, 0f, "stripe",
                0f, 0f , 0f, 0f, 0f);
    }

    private ArrayList<OrderActivityLog> generateActivityLogs() {
        ArrayList<OrderActivityLog> activityLogs = new ArrayList<>();
        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'");
        dateFormatter.setTimeZone(TimeZone.getTimeZone("UTC"));
        long t0 = System.currentTimeMillis();
        OrderActivityLog a0 = new OrderActivityLog(OrderActivityLogState.CREATED,
                "manager", generateOrderItemId(10), t0,
                dateFormatter.format(new Date(t0)));
        activityLogs.add(a0);
        return activityLogs;
    }

    private List<OrderStateTransition> generateOrderStateTransitions() {
        Long t0 = System.currentTimeMillis();
        OrderState o0 = OrderState.PLACED;
        String l0 = "Bengaluru";
        String m0 = "Order Placed by User";
        AppTimelinePointView.TimelinePointState s0 = AppTimelinePointView.TimelinePointState.COMPLETE;
        OrderStateTransition ost0 = new OrderStateTransition(o0, t0, l0)
                .setIndex(0)
                .setMessage(m0)
                .setState(s0);
        Long t1 = System.currentTimeMillis();
        OrderState o1 = OrderState.CONFIRMED;
        String l1 = "Bengaluru";
        String m1 = "Order Confirmed by Shanmukha Restaurant";
        AppTimelinePointView.TimelinePointState s1 = AppTimelinePointView.TimelinePointState.COMPLETE;
        OrderStateTransition ost1 = new OrderStateTransition(o1, t1, l1)
                .setIndex(1)
                .setMessage(m1)
                .setState(s1);
        Long t2 = System.currentTimeMillis();
        OrderState o2 = OrderState.ASSIGNED;
        String l2 = "Bengaluru";
        String m2 = "Order Assigned To Rajesh Kumar";
        AppTimelinePointView.TimelinePointState s2 = AppTimelinePointView.TimelinePointState.COMPLETE;
        OrderStateTransition ost2 = new OrderStateTransition(o2, t2, l2)
                .setIndex(2)
                .setMessage(m2)
                .setState(s2);
        Long t3 = System.currentTimeMillis();
        OrderState o3 = OrderState.PICKED;
        String l3 = "Bengaluru";
        String m3 = "Order Picked for More Mega Store";
        AppTimelinePointView.TimelinePointState s3 = AppTimelinePointView.TimelinePointState.IN_PROGRESS;
        OrderStateTransition ost3 = new OrderStateTransition(o3, t3, l3)
                .setIndex(3)
                .setMessage(m3)
                .setState(s3);
        Long t4 = System.currentTimeMillis();
        OrderState o4 = OrderState.DELIVERED;
        String l4 = "Bengaluru";
        String m4 = "Order Delivered To User";
        AppTimelinePointView.TimelinePointState s4 = AppTimelinePointView.TimelinePointState.PENDING;
        OrderStateTransition ost4 = new OrderStateTransition(o4, t4, l4)
                .setIndex(4)
                .setMessage(m4)
                .setState(s4);
        List<OrderStateTransition> ostList = new ArrayList<>();
        ostList.add(ost0);
        ostList.add(ost1);
        ostList.add(ost2);
        ostList.add(ost3);
        ostList.add(ost4);
        return ostList;
    }

    private Actor generateActor(ActorRole role) {
        Random random = new Random();
        List<String> actorNames = getActorNames();
        return new Actor(random.nextInt(), actorNames.get(random.nextInt(actorNames.size())),
                generatePhoneNumber(), role);
    }

    private OrderDetails generateSingleOrderItemDetails() {
        TitlizedList<OrderedItem> orderedItems = generateListOfOrderedItems();
        TitlizedList<PriceComponent> priceComponents = generateAdditionalPriceComponents(orderedItems.items);
        PriceComponent total = generateTotal(orderedItems.items, priceComponents.items);
        OrderDetails orderDetails = new OrderDetails(orderedItems, priceComponents, total,
                "Cash");
        return orderDetails;
    }

    private TitlizedList<OrderedItem> generateListOfOrderedItems() {
        List<OrderedItem> items = new ArrayList<>();

        List<String> orderableItems = getOrderableItems();
        Random random = new Random();
        int max = random.nextInt(10) + 1;
        for (int i = 0; i < max; i++) {
            String itemName = orderableItems.get(random.nextInt(orderableItems.size()));
            double quantity = random.nextInt(5) + 1;
            double unitPrice = random.nextInt(500) + 100;
            double totalPrice = quantity * unitPrice;
            items.add(null);
        }
        return new TitlizedList<>("Orders", items);
    }

    private TitlizedList<PriceComponent> generateAdditionalPriceComponents(
            List<OrderedItem> items) {
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
        return new TitlizedList<>("Surcharges", additionalCharges);
    }

    private float getOrderedItemsTotal(List<OrderedItem> items) {
        float total = 0.0f;
        for (OrderedItem item : items) {
            //total += item.totalPrice;
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
