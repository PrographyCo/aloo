package com.prography.sw.aloocustomer.model;

import java.io.Serializable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrderItem implements Serializable {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("amount")
    @Expose
    private int amount;
    @SerializedName("item_price")
    @Expose
    private float itemPrice;
    @SerializedName("data")
    @Expose
    private OrderItemData orderItemData;
    @SerializedName("item")
    @Expose
    private OrderItemInfo orderItemInfo;
    private final static long serialVersionUID = -7170114835091497174L;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public float getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(float itemPrice) {
        this.itemPrice = itemPrice;
    }

    public OrderItemData getData() {
        return orderItemData;
    }

    public void setData(OrderItemData orderItemData) {
        this.orderItemData = orderItemData;
    }

    public OrderItemInfo getInfo() {
        return orderItemInfo;
    }

    public void setInfo(OrderItemInfo orderItemInfo) {
        this.orderItemInfo = orderItemInfo;
    }

}