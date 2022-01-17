package com.prography.sw.aloodelevery.data.response.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.prography.sw.aloodelevery.model.Customer;
import com.prography.sw.aloodelevery.model.MyOrderDetails;
import com.prography.sw.aloodelevery.model.OrderItem;
import com.prography.sw.aloodelevery.model.OrderType;
import com.prography.sw.aloodelevery.model.Place;

import java.io.Serializable;
import java.util.List;

public class MyOrderDetailsData implements Serializable {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("total_price")
    @Expose
    private float totalPrice;
    @SerializedName("distance")
    @Expose
    private String distance;
    @SerializedName("date")
    @Expose
    private int date;
    @SerializedName("delivery_price")
    @Expose
    private String deliveryPrice;
    @SerializedName("delivery_time")
    @Expose
    private float deliveryTime;
    @SerializedName("customer")
    @Expose
    private Customer customer;
    @SerializedName("items")
    @Expose
    private List<OrderItem> items = null;
    @SerializedName("place")
    @Expose
    private Place place;
    @SerializedName("order_type")
    @Expose
    private OrderType orderType;

    private final static long serialVersionUID = 8658240018504013839L;

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

    public OrderType getOrderType() {
        return orderType;
    }

    public void setOrderType(OrderType orderType) {
        this.orderType = orderType;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public String getDeliveryPrice() {
        return deliveryPrice;
    }

    public void setDeliveryPrice(String deliveryPrice) {
        this.deliveryPrice = deliveryPrice;
    }

    public float getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(float deliveryTime) {
        this.deliveryTime = deliveryTime;
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