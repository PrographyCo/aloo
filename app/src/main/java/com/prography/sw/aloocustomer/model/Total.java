package com.prography.sw.aloocustomer.model;

import java.io.Serializable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Total implements Serializable {

    @SerializedName("price")
    @Expose
    private float price;
    @SerializedName("delivery")
    @Expose
    private int delivery;
    @SerializedName("total")
    @Expose
    private float total;
    private final static long serialVersionUID = 148860958359272562L;

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getDelivery() {
        return delivery;
    }

    public void setDelivery(int delivery) {
        this.delivery = delivery;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

}