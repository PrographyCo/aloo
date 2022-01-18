package com.prography.sw.alooproduct.data.response.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.prography.sw.alooproduct.model.Rate;

import java.io.Serializable;

public class MyData implements Serializable {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("img")
    @Expose
    private String img;
    @SerializedName("rate")
    @Expose
    private Rate rate;
    @SerializedName("orders")
    @Expose
    private int orders;
    @SerializedName("app_percentage")
    @Expose
    private int appPercentage;
    @SerializedName("income")
    @Expose
    private String income;
    @SerializedName("exchanged")
    @Expose
    private float exchanged;
    private final static long serialVersionUID = -5610436666334103026L;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public Rate getRate() {
        return rate;
    }

    public void setRate(Rate rate) {
        this.rate = rate;
    }

    public int getOrders() {
        return orders;
    }

    public void setOrders(int orders) {
        this.orders = orders;
    }

    public int getAppPercentage() {
        return appPercentage;
    }

    public void setAppPercentage(int appPercentage) {
        this.appPercentage = appPercentage;
    }

    public String getIncome() {
        return income;
    }

    public void setIncome(String income) {
        this.income = income;
    }

    public float getExchanged() {
        return exchanged;
    }

    public void setExchanged(float exchanged) {
        this.exchanged = exchanged;
    }

}