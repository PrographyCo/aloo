package com.prography.sw.alooproduct.data.response.data;

import java.io.Serializable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MyOrder implements Serializable {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("total_price")
    @Expose
    private float totalPrice;
    @SerializedName("items_sum_amount")
    @Expose
    private int itemsSumAmount;
    @SerializedName("date")
    @Expose
    private int date;
    @SerializedName("customer")
    @Expose
    private Customer customer;
    private final static long serialVersionUID = 6294663407875218839L;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getItemsSumAmount() {
        return itemsSumAmount;
    }

    public void setItemsSumAmount(int itemsSumAmount) {
        this.itemsSumAmount = itemsSumAmount;
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

}