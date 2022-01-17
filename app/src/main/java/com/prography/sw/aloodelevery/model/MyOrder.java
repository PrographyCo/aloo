package com.prography.sw.aloodelevery.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class MyOrder implements Serializable {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("total")
    @Expose
    private String total;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("vendor_type")
    @Expose
    private VendorType vendorType;
    @SerializedName("customer_name")
    @Expose
    private String customerName;
    @SerializedName("date")
    @Expose
    private int date;
    @SerializedName("place")
    @Expose
    private Place place;
    private final static long serialVersionUID = 1748001139807186963L;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public VendorType getVendorType() {
        return vendorType;
    }

    public void setVendorType(VendorType vendorType) {
        this.vendorType = vendorType;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public Place getPlace() {
        return place;
    }

    public void setPlace(Place place) {
        this.place = place;
    }

}