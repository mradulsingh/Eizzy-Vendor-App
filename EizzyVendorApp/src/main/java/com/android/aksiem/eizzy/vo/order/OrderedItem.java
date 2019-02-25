package com.android.aksiem.eizzy.vo.order;

import android.support.annotation.NonNull;

import com.android.aksiem.eizzy.vo.support.TitledAndSubtitled;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Mradul on 09/04/18.
 */

public class OrderedItem implements Serializable {

    @NonNull
    @SerializedName("itemName")
    public final String item;

    @NonNull
    @SerializedName("quantity")
    public final double quantity;

    @NonNull
    @SerializedName("unitName")
    public final String unitName;

    @NonNull
    @SerializedName("unitPrice")
    public final Float unitPrice;

    @NonNull
    @SerializedName("finalPrice")
    public final Float finalPrice;

    @NonNull
    @SerializedName("appliedDiscount")
    public final Float appliedDiscount;

    @NonNull
    @SerializedName("itemImageURL")
    public final String itemImageURL;

    @NonNull
    @SerializedName("parentProductId")
    public final String parentProductId;

    @NonNull
    @SerializedName("offerId")
    public final String offerId;

    @NonNull
    @SerializedName("childProductId")
    public final String childProductId;

    @NonNull
    @SerializedName("taxes")
    public final ArrayList<ExclusiveTax> taxes;

    @NonNull
    @SerializedName("productName")
    public final String productName;

    public OrderedItem(@NonNull String item, @NonNull double quantity, @NonNull String unitName,
                       @NonNull Float unitPrice, @NonNull Float finalPrice,
                       @NonNull Float appliedDiscount, @NonNull String itemImageURL,
                       @NonNull String parentProductId, @NonNull String offerId,
                       @NonNull String childProductId, @NonNull ArrayList<ExclusiveTax> taxes,
                       @NonNull String productName) {

        this.item = item;
        this.quantity = quantity;
        this.unitName = unitName;
        this.unitPrice = unitPrice;
        this.finalPrice = finalPrice;
        this.appliedDiscount = appliedDiscount;
        this.itemImageURL = itemImageURL;
        this.parentProductId = parentProductId;
        this.offerId = offerId;
        this.childProductId = childProductId;
        this.taxes = taxes;
        this.productName = productName;
    }

}
