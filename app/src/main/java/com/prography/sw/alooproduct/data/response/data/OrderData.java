package com.prography.sw.alooproduct.data.response.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.prography.sw.alooproduct.model.Customer;
import com.prography.sw.alooproduct.model.OrderListTiem;

import java.io.Serializable;
import java.util.List;

public class OrderData implements Serializable {

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
    @SerializedName("items")
    @Expose
    private List<OrderListTiem> items = null;
    @SerializedName("customer")
    @Expose
    private Customer customer;
    private final static long serialVersionUID = -5773976531814291052L;

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

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public List<OrderListTiem> getItems() {
        return items;
    }

    public void setItems(List<OrderListTiem> items) {
        this.items = items;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public int getItemsSumAmount() {
        return itemsSumAmount;
    }

    public void setItemsSumAmount(int itemsSumAmount) {
        this.itemsSumAmount = itemsSumAmount;
    }
}