package com.prography.sw.alooproduct.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class StoreProductItem implements Serializable {
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("amount")
    @Expose
    private String amount;
    @SerializedName("item")
    @Expose
    private StoreProductItemData item;
    private final static long serialVersionUID = 8841413362057127145L;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public StoreProductItemData getItem() {
        return item;
    }

    public void setItem(StoreProductItemData item) {
        this.item = item;
    }

}
