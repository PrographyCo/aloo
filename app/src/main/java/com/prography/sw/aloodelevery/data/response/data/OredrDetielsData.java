package com.prography.sw.aloodelevery.data.response.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.prography.sw.aloodelevery.model.Customer;
import com.prography.sw.aloodelevery.model.OrderItem;
import com.prography.sw.aloodelevery.model.Place;

import java.io.Serializable;
import java.util.List;

public class OredrDetielsData implements Serializable {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("total_price")
    @Expose
    private float totalPrice;
    @SerializedName("date")
    @Expose
    private int date;
    @SerializedName("customer")
    @Expose
    private Customer customer;
    @SerializedName("items")
    @Expose
    private List<OrderItem> items = null;
    @SerializedName("place")
    @Expose
    private Place place;
    private final static long serialVersionUID = 2250069414939199087L;

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

    public List<OrderItem> getItems() {
        return items;
    }

    public void setItems(List<OrderItem> items) {
        this.items = items;
    }

    public Place getPlace() {
        return place;
    }

    public void setPlace(Place place) {
        this.place = place;
    }

}