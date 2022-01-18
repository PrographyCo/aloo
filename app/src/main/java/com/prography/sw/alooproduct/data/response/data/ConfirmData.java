package com.prography.sw.alooproduct.data.response.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.prography.sw.alooproduct.model.Customer;

import java.io.Serializable;

public class ConfirmData implements Serializable {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("total_price")
    @Expose
    private float totalPrice;
    @SerializedName("customer")
    @Expose
    private Customer customer;
    private final static long serialVersionUID = -4754757114987534406L;

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

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

}