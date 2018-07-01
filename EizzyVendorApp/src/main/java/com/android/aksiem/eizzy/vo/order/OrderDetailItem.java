package com.android.aksiem.eizzy.vo.order;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.os.Build;
import android.support.annotation.NonNull;

import com.android.aksiem.eizzy.view.timeline.AppTimelinePointView;
import com.android.aksiem.eizzy.vo.LatLng;
import com.android.aksiem.eizzy.vo.Location;
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

    @SerializedName("storeLogo")
    private String storeLogo;

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
    @SerializedName("orderId")
    public final String orderId;

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

    @SerializedName("orderDetails")
    private OrderInfo orderInfo;

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
    @SerializedName("bookingDateTimeStamp")
    public final Long timestamp;

    @NonNull
    @SerializedName("dueDatetime")
    public final String dueDate;

    @NonNull
    @SerializedName("dueDatetimeTimeStamp")
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
    public final ServiceType serviceType;

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
    @SerializedName("activityLogs")
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

    @SerializedName("customerSignature")
    private Boolean customerSignatureRequired;

    @SerializedName("cashOnDelivery")
    private Boolean cashOnDeliveryEnabled;

    @SerializedName("eizzyZone")
    private String eizzyZoneId;

    @Ignore
    private OrderActivityLog currentState;

    @Ignore
    private OrderState orderState;

    @Ignore
    private String stringTimestamp;

    @Ignore
    private ArrayList<OrderStateTransition> orderTracking;

    public OrderDetailItem(
            @NonNull String storeId, @NonNull LatLng storeCoordinates, @NonNull String storeName,
            @NonNull Integer forcedAccept, @NonNull int storeCommissionType,
            String storeCommissionTypeMessage, @NonNull Integer driverType,
            @NonNull String storeAddress, @NonNull String cartId, @NonNull Float deliveryCharge,
            @NonNull OrderType orderType, @NonNull String orderTypeMsg,
            @NonNull Integer paymentType, @NonNull String paymentTypeMessage,
            @NonNull String bookingDate, @NonNull Long timestamp, @NonNull String dueDate,
            @NonNull Long dueDateTimestamp, @NonNull ServiceType serviceType,
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

    public String getStoreLogo() {
        return storeLogo;
    }

    public void setStoreLogo(String storeLogo) {
        this.storeLogo = storeLogo;
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

    public OrderInfo getOrderInfo() {
        return orderInfo;
    }

    public void setOrderInfo(OrderInfo orderInfo) {
        this.orderInfo = orderInfo;
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

    public Boolean getCustomerSignatureRequired() {
        return customerSignatureRequired;
    }

    public void setCustomerSignatureRequired(Boolean customerSignatureRequired) {
        this.customerSignatureRequired = customerSignatureRequired;
    }

    public Boolean getCashOnDeliveryEnabled() {
        return cashOnDeliveryEnabled;
    }

    public void setCashOnDeliveryEnabled(Boolean cashOnDeliveryEnabled) {
        this.cashOnDeliveryEnabled = cashOnDeliveryEnabled;
    }

    public String getEizzyZoneId() {
        return eizzyZoneId;
    }

    public void setEizzyZoneId(String eizzyZoneId) {
        this.eizzyZoneId = eizzyZoneId;
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
            orderState = getCurrentState().getState().getOrderState();
        }
        return orderState;
    }

    public void setOrderState(OrderState orderState) {
        this.orderState = orderState;
    }

    public String getStringTotal() {
        return String.format("₹ %.2f", totalAmount);
    }

    public String getStringTimestamp() {
        if (stringTimestamp == null) {
            SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/mm/yyyy hh:mm");
            stringTimestamp = dateFormatter.format(new Date(timestamp));
        }
        return stringTimestamp;
    }

    public ArrayList<OrderStateTransition> getOrderTracking() {
        if (orderTracking == null) {
            orderTracking = new ArrayList<>();
            int index = 0;
            if (activityLogs != null && !activityLogs.isEmpty()) {
                for (; index < activityLogs.size(); index++) {
                    OrderActivityLog log = activityLogs.get(index);
                    OrderStateTransition transition = new OrderStateTransition(
                            log.getState().getOrderState(),
                            log.timestamp,
                            log.getLocation().city);
                    transition.setState(AppTimelinePointView.TimelinePointState.COMPLETE);
                    transition.setMessage(log.getMessage());
                    transition.setIndex(index);
                    orderTracking.add(transition);
                }
            }
        }
        return orderTracking;
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
