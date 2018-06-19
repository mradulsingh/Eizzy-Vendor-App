package com.android.aksiem.eizzy.vo;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.os.Build;
import android.support.annotation.NonNull;

import com.android.aksiem.eizzy.vo.support.order.ExclusiveTax;
import com.android.aksiem.eizzy.vo.support.order.OrderState;
import com.android.aksiem.eizzy.vo.support.order.OrderType;
import com.android.aksiem.eizzy.vo.support.order.OrderedItem;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

/**
 * Created by pdubey on 09/04/18.
 */

@Entity(primaryKeys = "orderId", tableName = "order_detail_item")
public class OrderDetailItem implements Serializable, Timestamped {

    /**
     * The following attributes are received when we create an order:
     *      estimatedId: PURPOSE UNKNOWN
     *      storeId: id of store where order was placed
     *      storeCoordinates: Lat/Lng in an object
     *      cartTotal: total value of items in the cart
     *      cartDiscount: any discount value applied to the cart
     *      storeLogo: url of logo
     *      storeName: name of store
     *      forcedAccept: PURPOSE UNKNOWN
     *      storeCommission: commission to be added to the store's account
     *      storeCommissionType: ENUM UNKNOWN AND PURPOSE UNKNOWN
     *      storeCommissionTypeMsg: PURPOSE UNKNOWN
     *      driverType: ENUM UNKNOWN AND PURPOSE UNKNOWN
     *      storeAddress: Stringified address of the store
     *      subTotalAmountWithExcTax: Total amount after tax
     *      orderId: id of the order created (ideally shouldn't be just the id)
     *      cartId: id of the cart being processed
     *      deliveryCharge: delivery charge levied (before or after tax?)
     *      subTotalAmount: total value of items in cart not including exclusive tax
     *      excTax: total value of exclusive tax
     *      exclusiveTaxes: list of breakdown of all taxes levied on the transaction
     *      driverMobile: driver's mobile number
     *      driverImage: driver's profile pic url
     *      driverEmail: driver's email
     *      orderType: (0: Food, 1: Grocery, 2: Daily Needs, 4: Online)
     *      orderTypeMsg: ENUM UNKNOWN AND PURPOSE UNKNOWN
     *      discount: PURPOSE UNKNOWN (how is it different from cartDiscount)
     *      totalAmount: PURPOSE UNKNOWN (how is it different from subTotalAmount/subTotalAmountWithExcTax/cartTotal)
     *      Items: list of items in the order (how to deal with when this info is unavailable)
     *      couponCode: string representing an applicable coupon code
     *      paymentType: (1: card, 2: cash, 3: wallet)
     *      paymentTypeMsg: string representation of paymentType
     *      coinpayTransaction: tracking of payment using payment gateway
     *      customerCoordinates: Lat/Lng in an object
     *      bookingDate: string time
     *      bookingDateTimeStamp: millisecond representation of epoch time (record creation time)
     *      dueDatetime: string time
     *      dueDatetimeTimeStamp: millisecond representation of time of scheduled delivery
     *      city: string city name
     *      cityId: city id
     *      status: (
     *          1: New order,
     *          2: Cancelled by manager,
     *          3: Rejected by manager,
     *          4: Accepted by manager,
     *          5: Order ready,
     *          6: Order picked,
     *          7: Order completed,
     *          8: Accepted by driver,
     *          9: Rejected by driver,
     *          10: Enroute to Store,
     *          11: At Store,
     *          12: Enroute to Customer,
     *          13: Customer Location,
     *          14: Delivered,
     *          15: Order completed,
     *          16: Cancelled by customer,
     *          17: Cancelled by driver,
     *          18: Order delayed,
     *          19: Central dispatch,
     *          20: Order expired
     *      )
     *      statusMsg: string representation of status
     *      serviceType: (1: Delivery, 2: Pickup)
     *      bookingType: (1: Deliver ASAP, 2: Deliver Later)
     *      pricingModel: ENUM UNKNOWN AND PURPOSE UNKNOWN
     *      zoneType: ENUM UNKNOWN AND PURPOSE UNKNOWN
     *      extraNote: extra note added by customer during order creation
     *      customerDetails: details of customers as an object
     *      pickup: Location object of pickup
     *      timeStamp: REDUNDANT
     *      activityLogs: activities performed on the order (state ENUM UNKNOWN)
     *      abbreviation: PURPOSE UNKNOWN
     *      abbreviationText: PURPOSE UNKNOWN
     *      currency: currency code
     *      currencySymbol: currency symbol
     *      mileageMetric: PURPOSE UNKNOWN
     *      paidBy: payment breakdown by mode of payments
     *      accounting: settlement related info
     *      _id: server specific id
     *
     * When we receive order via list, this is what we receive:
     *      orderId: same as above
     *      bookingDate: same as above
     *      pickAddress: string representation of pickup address
     *      pickupLong: Lng of pickup
     *      pickupLat: Lat of pickup
     *      dropLat: Lat of drop
     *      dropLong: Lng of drop
     *      dropAddress: string representation of drop address
     *      statusMessage: same as statusMsg above
     *      statusCode: same as status
     *      storeName: same as above
     *      storeAddress: same as above
     *      totalAmount: same as above
     *      serviceType: same as above
     *      items: same as above
     *      bookingType: same as above
     *      dueDatetime: same as above
     *      timeStamp: long representation of epoch time of creation (refer bookingDateTimestamp)
     *      driverId: same as above
     *      driverName: same as above
     *      subTotalAmountWithExcTax: same as above
     *      subTotalAmount: same as above
     *      deliveryCharge: same as above
     *      excTax: same as above
     *      exclusiveTaxes: same as above
     *      driverMobile: same as above
     *      driverImage: same as above
     *      driverEmail: same as above
     *      activityLogs: same as above
     *      bookingDateTimeStamp: same as above
     *      dueDatetimeTimeStamp: same as above
     *      storeType: same as above
     *      storeTypeMsg: same as above
     */


    @SerializedName("estimatedId")
    private String estimatedId;

    @NonNull
    @SerializedName("storeId")
    public final String storeId;

    @NonNull
    @SerializedName("storeCoordinates")
    public final LatLng storeCoordinates;

    @NonNull
    @SerializedName("cartTotal")
    private float cartTotal;

    @NonNull
    @SerializedName("cartDiscount")
    private float cartDiscount;

    @NonNull
    @SerializedName("storeName")
    public final String storeName;

    @NonNull
    @SerializedName("forcedAccept")
    public final Integer forcedAccept;

    @NonNull
    @SerializedName("storeCommission")
    private float storeCommission;

    @NonNull
    @SerializedName("storeCommissionType")
    public final Integer storeCommissionType;

    @SerializedName("storeCommisionTypeMsg")
    public final String storeCommissionTypeMessage;

    @NonNull
    @SerializedName("driverType")
    public final Integer driverType;

    @NonNull
    @SerializedName("storeAddress")
    public final String storeAddress;

    @NonNull
    @SerializedName("subTotalAmountWithExcTax")
    private Float subTotalAmountWithExcTax;

    @NonNull
    @SerializedName("cartId")
    public final String cartId;

    @NonNull
    @SerializedName("deliveryCharge")
    public final Float deliveryCharge;

    @NonNull
    @SerializedName("subTotalAmount")
    private Float subTotalAmount;

    @NonNull
    @SerializedName("excTax")
    private Float excTax;

    @SerializedName("exclusiveTaxes")
    private ArrayList<ExclusiveTax> exclusiveTaxes;

    @NonNull
    @SerializedName("orderType")
    public final OrderType orderType;

    @NonNull
    @SerializedName("orderTypeMsg")
    public final String orderTypeMsg;

    @NonNull
    @SerializedName("discount")
    private Float discount;

    @NonNull
    @SerializedName("totalAmount")
    private Float totalAmount;

    @SerializedName("Items")
    private ArrayList<OrderedItem> items;

    @SerializedName("couponCode")
    private String couponCode;

    @NonNull
    @SerializedName("paymentType")
    public final Integer paymentType;

    @NonNull
    @SerializedName("paymentTypeMsg")
    public final String paymentTypeMessage;

    @NonNull
    @SerializedName("coinpayTransaction")
    private CoinPayTransaction coinPayTransaction;

    @SerializedName("customerCoordinates")
    private LatLng customerCoordinates;

    @NonNull
    @SerializedName("bookingDate")
    public final String bookingDate;

    @NonNull
    @SerializedName("bookingDateTimestamp")
    public final Long timestamp;

    @NonNull
    @SerializedName("dueDate")
    public final String dueDate;

    @NonNull
    @SerializedName("dueDateTimestamp")
    public final Long dueDateTimestamp;

    @SerializedName("city")
    private String city;

    @SerializedName("cityId")
    private String cityId;

    @NonNull
    @SerializedName("status")
    private Integer status;

    @NonNull
    @SerializedName("statusMsg")
    private String statusMessage;

    @NonNull
    @SerializedName("serviceType")
    public final Integer serviceType;

    @NonNull
    @SerializedName("bookingType")
    public final Integer bookingType;

    @NonNull
    @SerializedName("pricingModel")
    public final Integer pricingModel;

    @SerializedName("zoneType")
    public final String zoneType;

    @SerializedName("extraNote")
    public final String extraNote;

    @NonNull
    @SerializedName("customerDetails")
    public final CustomerDetails customerDetails;

    @NonNull
    @SerializedName("pickup")
    public final Location pickupLocation;

    @NonNull
    @SerializedName("activtyLogs")
    private ArrayList<OrderActivityLog> activityLogs;

    @SerializedName("abbreviation")
    public final String abbreviation;

    @SerializedName("abbreviationText")
    public final String abbreviationText;

    @NonNull
    @SerializedName("currency")
    public final String currency;

    @NonNull
    @SerializedName("currencySymbol")
    public final String currencySymbol;

    @SerializedName("mileageMetric")
    public final String mileageMetric;

    @NonNull
    @SerializedName("paidBy")
    public final PaymentBreakupByMode paidBy;

    @NonNull
    @SerializedName("accounting")
    public final Accounting accounting;

    @NonNull
    @SerializedName("orderId")
    public final String orderId;

    @Ignore
    private OrderActivityLog currentState;

    @Ignore
    private OrderState orderState;

    @Ignore
    private String stringTimestamp;

    public OrderDetailItem(
            @NonNull String storeId, @NonNull LatLng storeCoordinates, @NonNull String storeName,
            @NonNull Integer forcedAccept, @NonNull int storeCommissionType,
            String storeCommissionTypeMessage, @NonNull Integer driverType,
            @NonNull String storeAddress, @NonNull String cartId, @NonNull Float deliveryCharge,
            @NonNull OrderType orderType, @NonNull String orderTypeMsg,
            @NonNull Integer paymentType, @NonNull String paymentTypeMessage,
            @NonNull String bookingDate, @NonNull Long timestamp, @NonNull String dueDate,
            @NonNull Long dueDateTimestamp, @NonNull Integer serviceType,
            @NonNull Integer bookingType, @NonNull Integer pricingModel, String zoneType,
            String extraNote, @NonNull CustomerDetails customerDetails,
            @NonNull Location pickupLocation, String abbreviation, String abbreviationText,
            @NonNull String currency, @NonNull String currencySymbol, String mileageMetric,
            @NonNull PaymentBreakupByMode paidBy, @NonNull Accounting accounting,
            @NonNull String orderId) {

        this.storeId = storeId;
        this.storeCoordinates = storeCoordinates;
        this.storeName = storeName;
        this.forcedAccept = forcedAccept;
        this.storeCommissionType = storeCommissionType;
        this.storeCommissionTypeMessage = storeCommissionTypeMessage;
        this.driverType = driverType;
        this.storeAddress = storeAddress;
        this.cartId = cartId;
        this.deliveryCharge = deliveryCharge;
        this.orderType = orderType;
        this.orderTypeMsg = orderTypeMsg;
        this.paymentType = paymentType;
        this.paymentTypeMessage = paymentTypeMessage;
        this.bookingDate = bookingDate;
        this.timestamp = timestamp;
        this.dueDate = dueDate;
        this.dueDateTimestamp = dueDateTimestamp;
        this.serviceType = serviceType;
        this.bookingType = bookingType;
        this.pricingModel = pricingModel;
        this.zoneType = zoneType;
        this.extraNote = extraNote;
        this.customerDetails = customerDetails;
        this.pickupLocation = pickupLocation;
        this.abbreviation = abbreviation;
        this.abbreviationText = abbreviationText;
        this.currency = currency;
        this.currencySymbol = currencySymbol;
        this.mileageMetric = mileageMetric;
        this.paidBy = paidBy;
        this.accounting = accounting;
        this.orderId = orderId;
    }

    @Override
    public long getTimestamp() {
        return timestamp;
    }

    public String getEstimatedId() {
        return estimatedId;
    }

    public void setEstimatedId(String estimatedId) {
        this.estimatedId = estimatedId;
    }

    @NonNull
    public float getCartTotal() {
        return cartTotal;
    }

    public void setCartTotal(@NonNull float cartTotal) {
        this.cartTotal = cartTotal;
    }

    @NonNull
    public float getCartDiscount() {
        return cartDiscount;
    }

    public void setCartDiscount(@NonNull float cartDiscount) {
        this.cartDiscount = cartDiscount;
    }

    @NonNull
    public float getStoreCommission() {
        return storeCommission;
    }

    public void setStoreCommission(@NonNull float storeCommission) {
        this.storeCommission = storeCommission;
    }

    @NonNull
    public Float getSubTotalAmountWithExcTax() {
        return subTotalAmountWithExcTax;
    }

    public void setSubTotalAmountWithExcTax(@NonNull Float subTotalAmountWithExcTax) {
        this.subTotalAmountWithExcTax = subTotalAmountWithExcTax;
    }

    @NonNull
    public Float getSubTotalAmount() {
        return subTotalAmount;
    }

    public void setSubTotalAmount(@NonNull Float subTotalAmount) {
        this.subTotalAmount = subTotalAmount;
    }

    @NonNull
    public Float getExcTax() {
        return excTax;
    }

    public void setExcTax(@NonNull Float excTax) {
        this.excTax = excTax;
    }

    public ArrayList<ExclusiveTax> getExclusiveTaxes() {
        return exclusiveTaxes;
    }

    public void setExclusiveTaxes(ArrayList<ExclusiveTax> exclusiveTaxes) {
        this.exclusiveTaxes = exclusiveTaxes;
    }

    @NonNull
    public Float getDiscount() {
        return discount;
    }

    public void setDiscount(@NonNull Float discount) {
        this.discount = discount;
    }

    @NonNull
    public Float getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(@NonNull Float totalAmount) {
        this.totalAmount = totalAmount;
    }

    public ArrayList<OrderedItem> getItems() {
        return items;
    }

    public void setItems(ArrayList<OrderedItem> items) {
        this.items = items;
    }

    public String getCouponCode() {
        return couponCode;
    }

    public void setCouponCode(String couponCode) {
        this.couponCode = couponCode;
    }

    @NonNull
    public CoinPayTransaction getCoinPayTransaction() {
        return coinPayTransaction;
    }

    public void setCoinPayTransaction(@NonNull CoinPayTransaction coinPayTransaction) {
        this.coinPayTransaction = coinPayTransaction;
    }

    public LatLng getCustomerCoordinates() {
        return customerCoordinates;
    }

    public void setCustomerCoordinates(LatLng customerCoordinates) {
        this.customerCoordinates = customerCoordinates;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    @NonNull
    public Integer getStatus() {
        return status;
    }

    public void setStatus(@NonNull Integer status) {
        this.status = status;
    }

    @NonNull
    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(@NonNull String statusMessage) {
        this.statusMessage = statusMessage;
    }

    @NonNull
    public ArrayList<OrderActivityLog> getActivityLogs() {
        return activityLogs;
    }

    public void setActivityLogs(@NonNull ArrayList<OrderActivityLog> activityLogs) {
        this.activityLogs = activityLogs;
    }

    public OrderActivityLog getCurrentState() {
        if (currentState == null) {
            if (activityLogs != null && !activityLogs.isEmpty()) {
                for (OrderActivityLog log : activityLogs) {
                    if (currentState == null || currentState.timestamp <= log.timestamp)
                        currentState = log;
                }
            }
        }
        return currentState;
    }

    public OrderState getOrderState() {
        if (orderState == null) {
            orderState = getCurrentState().state.getOrderState();
        }
        return orderState;
    }

    public void setOrderState(OrderState orderState) {
        this.orderState = orderState;
    }

    public String getStringTotal() {
        return String.format("%s %.2f", currencySymbol, totalAmount);
    }

    public String getStringTimestamp() {
        if (stringTimestamp == null) {
            SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/mm/yyyy hh:mm");
            stringTimestamp = dateFormatter.format(new Date(timestamp));
        }
        return stringTimestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderDetailItem orderDetailItem = (OrderDetailItem) o;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            return Objects.equals(orderId, orderDetailItem.orderId);
        } else {
            return orderDetailItem.orderId.equals(orderId);
        }
    }

    @Override
    public int hashCode() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            return Objects.hash(orderId);
        } else {
            return orderId.hashCode();
        }
    }
}
