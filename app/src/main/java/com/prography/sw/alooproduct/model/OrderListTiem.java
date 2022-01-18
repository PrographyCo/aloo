package com.prography.sw.alooproduct.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class OrderListTiem implements Serializable {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("amount")
    @Expose
    private String amount;
    @SerializedName("item_price")
    @Expose
    private String itemPrice;
    @SerializedName("data")
    @Expose
    private ItemAddition data;
    @SerializedName("item")
    @Expose
    private Item item;
    private final static long serialVersionUID = 2107063374618105479L;

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(String itemPrice) {
        this.itemPrice = itemPrice;
    }

    public ItemAddition getData() {
        return data;
    }

    public void setData(ItemAddition data) {
        this.data = data;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

}