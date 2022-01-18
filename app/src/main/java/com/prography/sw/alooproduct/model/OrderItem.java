package com.prography.sw.alooproduct.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class OrderItem implements Serializable
{
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("total_price")
    @Expose
    private double totalPrice;
    @SerializedName("items_sum_amount")
    @Expose
    private int itemsSumAmount;
    @SerializedName("date")
    @Expose
    private int date;
    @SerializedName("customer")
    @Expose
    private Customer customer;
    private final static long serialVersionUID = -3383346080420507068L;

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

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
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