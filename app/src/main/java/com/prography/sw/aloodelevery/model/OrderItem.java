package com.prography.sw.aloodelevery.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

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
    private Supplements data;
    @SerializedName("item")
    @Expose
    private ItemDetilse item;
    private final static long serialVersionUID = 3982313646600409530L;

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

    public Supplements getData() {
        return data;
    }

    public void setData(Supplements data) {
        this.data = data;
    }

    public ItemDetilse getItem() {
        return item;
    }

    public void setItem(ItemDetilse item) {
        this.item = item;
    }

}