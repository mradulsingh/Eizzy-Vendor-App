package com.android.aksiem.eizzy.vo;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.os.Build;
import android.support.annotation.NonNull;

import com.android.aksiem.eizzy.util.Logger;
import com.android.aksiem.eizzy.vo.support.order.ExclusiveTax;
import com.android.aksiem.eizzy.vo.support.order.OrderState;
import com.android.aksiem.eizzy.vo.support.order.OrderType;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

@Entity(primaryKeys = "orderId", tableName = "order_list_item")
public class OrderListItem implements Serializable, Timestamped {

    @NonNull
    @SerializedName("orderId")
    public final String orderId;

    @NonNull
    @SerializedName("bookingDate")
    private String bookingDate;

    @NonNull
    @SerializedName("dropLat")
    private Float dropLat;

    @NonNull
    @SerializedName("dropLong")
    private Float dropLong;

    @NonNull
    @SerializedName("dropAddress")
    private String dropAddress;

    @NonNull
    @SerializedName("statusMsg")
    private String statusMsg;

    @NonNull
    @SerializedName("pickup")
    private Location pickup;

    @NonNull
    @SerializedName("status")
    private Integer status;

    @NonNull
    @SerializedName("storeName")
    private String storeName;

    @NonNull
    @SerializedName("storeAddress")
    private String storeAddress;

    @NonNull
    @SerializedName("totalAmount")
    private Float totalAmount;

    @NonNull
    @SerializedName("serviceType")
    private Integer serviceType;

    @NonNull
    @SerializedName("bookingType")
    private Integer bookingType;

    @NonNull
    @SerializedName("dueDatetime")
    private String dueDatetime;

    @NonNull
    @SerializedName("driverId")
    private String driverId;

    @NonNull
    @SerializedName("driverName")
    private String driverName;

    @NonNull
    @SerializedName("subTotalAmountWithExcTax")
    private Float subTotalAmountWithExcTax;

    @NonNull
    @SerializedName("subTotalAmount")
    private Float subTotalAmount;

    @NonNull
    @SerializedName("deliveryCharge")
    private Float deliveryCharge;

    @NonNull
    @SerializedName("excTax")
    private Float excTax;

    @NonNull
    @SerializedName("exclusiveTaxes")
    private ArrayList<ExclusiveTax> exclusiveTaxes;

    @NonNull
    @SerializedName("driverMobile")
    private String driverMobile;

    @NonNull
    @SerializedName("driverImage")
    private String driverImageUrl;

    @NonNull
    @SerializedName("driverEmail")
    private String driverEmail;

    @NonNull
    @SerializedName("activityLogs")
    private ArrayList<OrderActivityLog> activityLogs;

    @NonNull
    @SerializedName("bookingDateTimeStamp")
    private long timestamp;

    @NonNull
    @SerializedName("dueDatetimeTimeStamp")
    private long dueDatetimeTimeStamp;

    @NonNull
    @SerializedName("storeType")
    private OrderType storeType;

    @SerializedName("storeTypeMsg")
    private String storeTypeMsg;

    @NonNull
    @SerializedName("customerDetails")
    private CustomerDetails customerDetails;

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

    public OrderListItem(@NonNull String orderId) {
        this.orderId = orderId;
    }

    @NonNull
    public String getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(@NonNull String bookingDate) {
        this.bookingDate = bookingDate;
    }

    @NonNull
    public Float getDropLat() {
        return dropLat;
    }

    public void setDropLat(@NonNull Float dropLat) {
        this.dropLat = dropLat;
    }

    @NonNull
    public Float getDropLong() {
        return dropLong;
    }

    public void setDropLong(@NonNull Float dropLong) {
        this.dropLong = dropLong;
    }

    @NonNull
    public String getDropAddress() {
        return dropAddress;
    }

    public void setDropAddress(@NonNull String dropAddress) {
        this.dropAddress = dropAddress;
    }

    @NonNull
    public String getStatusMsg() {
        return statusMsg;
    }

    public void setStatusMsg(@NonNull String statusMsg) {
        this.statusMsg = statusMsg;
    }

    @NonNull
    public Location getPickup() {
        return pickup;
    }

    public void setPickup(@NonNull Location pickup) {
        this.pickup = pickup;
    }

    @NonNull
    public Integer getStatus() {
        return status;
    }

    public void setStatus(@NonNull Integer status) {
        this.status = status;
    }

    @NonNull
    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(@NonNull String storeName) {
        this.storeName = storeName;
    }

    @NonNull
    public String getStoreAddress() {
        return storeAddress;
    }

    public void setStoreAddress(@NonNull String storeAddress) {
        this.storeAddress = storeAddress;
    }

    @NonNull
    public Float getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(@NonNull Float totalAmount) {
        this.totalAmount = totalAmount;
    }

    @NonNull
    public Integer getServiceType() {
        return serviceType;
    }

    public void setServiceType(@NonNull Integer serviceType) {
        this.serviceType = serviceType;
    }

    @NonNull
    public Integer getBookingType() {
        return bookingType;
    }

    public void setBookingType(@NonNull Integer bookingType) {
        this.bookingType = bookingType;
    }

    @NonNull
    public String getDueDatetime() {
        return dueDatetime;
    }

    public void setDueDatetime(@NonNull String dueDatetime) {
        this.dueDatetime = dueDatetime;
    }

    @NonNull
    public String getDriverId() {
        return driverId;
    }

    public void setDriverId(@NonNull String driverId) {
        this.driverId = driverId;
    }

    @NonNull
    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(@NonNull String driverName) {
        this.driverName = driverName;
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
    public Float getDeliveryCharge() {
        return deliveryCharge;
    }

    public void setDeliveryCharge(@NonNull Float deliveryCharge) {
        this.deliveryCharge = deliveryCharge;
    }

    @NonNull
    public Float getExcTax() {
        return excTax;
    }

    public void setExcTax(@NonNull Float excTax) {
        this.excTax = excTax;
    }

    @NonNull
    public ArrayList<ExclusiveTax> getExclusiveTaxes() {
        return exclusiveTaxes;
    }

    public void setExclusiveTaxes(@NonNull ArrayList<ExclusiveTax> exclusiveTaxes) {
        this.exclusiveTaxes = exclusiveTaxes;
    }

    @NonNull
    public String getDriverMobile() {
        return driverMobile;
    }

    public void setDriverMobile(@NonNull String driverMobile) {
        this.driverMobile = driverMobile;
    }

    @NonNull
    public String getDriverImageUrl() {
        return driverImageUrl;
    }

    public void setDriverImageUrl(@NonNull String driverImageUrl) {
        this.driverImageUrl = driverImageUrl;
    }

    @NonNull
    public String getDriverEmail() {
        return driverEmail;
    }

    public void setDriverEmail(@NonNull String driverEmail) {
        this.driverEmail = driverEmail;
    }

    @NonNull
    public ArrayList<OrderActivityLog> getActivityLogs() {
        return activityLogs;
    }

    public void setActivityLogs(@NonNull ArrayList<OrderActivityLog> activityLogs) {
        this.activityLogs = activityLogs;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public long getDueDatetimeTimeStamp() {
        return dueDatetimeTimeStamp;
    }

    public void setDueDatetimeTimeStamp(long dueDatetimeTimeStamp) {
        this.dueDatetimeTimeStamp = dueDatetimeTimeStamp;
    }

    @NonNull
    public OrderType getStoreType() {
        return storeType;
    }

    public void setStoreType(@NonNull OrderType storeType) {
        this.storeType = storeType;
    }

    public String getStoreTypeMsg() {
        return storeTypeMsg;
    }

    public void setStoreTypeMsg(String storeTypeMsg) {
        this.storeTypeMsg = storeTypeMsg;
    }

    @NonNull
    public CustomerDetails getCustomerDetails() {
        return customerDetails;
    }

    public void setCustomerDetails(@NonNull CustomerDetails customerDetails) {
        this.customerDetails = customerDetails;
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
        return String.format("%.2f", totalAmount);
    }

    public String getStringTimestamp() {
        if (stringTimestamp == null) {
            SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy hh:mm");
            stringTimestamp = dateFormatter.format(new Date(timestamp));
        }
        return stringTimestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderListItem orderListItem = (OrderListItem) o;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            return Objects.equals(orderId, orderListItem.orderId);
        } else {
            return orderListItem.orderId.equals(orderId);
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

    @Override
    public String toString() {
        return "OrderListItem{" +
                "orderId='" + orderId + '\'' +
                ", bookingDate='" + bookingDate + '\'' +
                ", dropLat=" + dropLat +
                ", dropLong=" + dropLong +
                ", dropAddress='" + dropAddress + '\'' +
                ", statusMsg='" + statusMsg + '\'' +
                ", pickup=" + pickup +
                ", status=" + status +
                ", storeName='" + storeName + '\'' +
                ", storeAddress='" + storeAddress + '\'' +
                ", totalAmount=" + totalAmount +
                ", serviceType=" + serviceType +
                ", bookingType=" + bookingType +
                ", dueDatetime='" + dueDatetime + '\'' +
                ", driverId='" + driverId + '\'' +
                ", driverName='" + driverName + '\'' +
                ", subTotalAmountWithExcTax=" + subTotalAmountWithExcTax +
                ", subTotalAmount=" + subTotalAmount +
                ", deliveryCharge=" + deliveryCharge +
                ", excTax=" + excTax +
                ", exclusiveTaxes=" + exclusiveTaxes +
                ", driverMobile='" + driverMobile + '\'' +
                ", driverImageUrl='" + driverImageUrl + '\'' +
                ", driverEmail='" + driverEmail + '\'' +
                ", activityLogs=" + activityLogs +
                ", timestamp=" + timestamp +
                ", dueDatetimeTimeStamp=" + dueDatetimeTimeStamp +
                ", storeType=" + storeType +
                ", storeTypeMsg='" + storeTypeMsg + '\'' +
                ", customerDetails=" + customerDetails +
                ", customerSignatureRequired=" + customerSignatureRequired +
                ", cashOnDeliveryEnabled=" + cashOnDeliveryEnabled +
                ", eizzyZoneId='" + eizzyZoneId + '\'' +
                ", currentState=" + currentState +
                ", orderState=" + orderState +
                ", stringTimestamp='" + stringTimestamp + '\'' +
                '}';
    }
}
