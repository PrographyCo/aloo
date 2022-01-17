package com.prography.sw.aloocustomer.data.response.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.prography.sw.aloocustomer.model.AllChoices;
import com.prography.sw.aloocustomer.model.OldItemChoices;

import java.io.Serializable;

public class EditItemData implements Serializable {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("amount")
    @Expose
    private int amount;
    @SerializedName("data")
    @Expose
    private OldItemChoices data;
    @SerializedName("unit_price")
    @Expose
    private String unitPrice;
    @SerializedName("total_price")
    @Expose
    private String totalPrice;
    @SerializedName("item")
    @Expose
    private AllChoices item;
    private final static long serialVersionUID = 3868883206743840423L;

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

    public OldItemChoices getData() {
        return data;
    }

    public void setData(OldItemChoices data) {
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

    public AllChoices getItem() {
        return item;
    }

    public void setItem(AllChoices item) {
        this.item = item;
    }

}