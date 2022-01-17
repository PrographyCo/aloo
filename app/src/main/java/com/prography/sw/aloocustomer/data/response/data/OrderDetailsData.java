package com.prography.sw.aloocustomer.data.response.data;

import java.io.Serializable;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.prography.sw.aloocustomer.model.OrderItem;
import com.prography.sw.aloocustomer.model.Place;
import com.prography.sw.aloocustomer.model.Total;

public class OrderDetailsData implements Serializable {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("date")
    @Expose
    private int date;
    @SerializedName("total")
    @Expose
    private Total total;
    @SerializedName("items")
    @Expose
    private List<OrderItem> orderItems = null;
    @SerializedName("place")
    @Expose
    private Place place;
    private final static long serialVersionUID = 5030553852076142927L;

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

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public Total getTotal() {
        return total;
    }

    public void setTotal(Total total) {
        this.total = total;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public Place getPlace() {
        return place;
    }

    public void setPlace(Place place) {
        this.place = place;
    }

}