package com.android.aksiem.eizzy.vo;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Location implements Serializable {
    
    @SerializedName("placeId")
    public String placeId;
    
    @SerializedName("placeName")
    public String placeName;
    
    @SerializedName("addressLine1")
    public String address;
    
    @SerializedName("addressLine2")
    public String locality;
    
    @SerializedName("city")
    public String city;
    
    @SerializedName("area")
    public String area;
    
    @SerializedName("state")
    public String state;
    
    @SerializedName("postalCode")
    public String postalCode;
    
    @SerializedName("country")
    public String country;
    
    @SerializedName("location")
    public LatLng latLng;
    
    @SerializedName("pickupZone")
    public Integer pickUpZone;

}
