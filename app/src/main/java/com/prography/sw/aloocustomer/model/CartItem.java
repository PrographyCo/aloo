package com.prography.sw.aloocustomer.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.prography.sw.aloocustomer.data.response.data.VendorDetilseResturantData;

import java.io.Serializable;
import java.util.List;

public class CartItem implements Serializable {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("is_offer")
    @Expose
    private boolean isOffer;
    @SerializedName("amount")
    @Expose
    private int amount;
    @SerializedName("data")
    @Expose
    private CardItemChoices data = null;
    @SerializedName("unit_price")
    @Expose
    private String unitPrice;
    @SerializedName("total_price")
    @Expose
    private String totalPrice;
    @SerializedName("item")
    @Expose
    private CardItemDetalis item;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isOffer() {
        return isOffer;
    }

    public void setOffer(boolean offer) {
        isOffer = offer;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public CardItemChoices getData() {
        return data;
    }

    public void setData(CardItemChoices data) {
        this.data = data;
    }

    public String getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(String unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public CardItemDetalis getItem() {
        return item;
    }

    public void setItem(CardItemDetalis item) {
        this.item = item;
    }

}