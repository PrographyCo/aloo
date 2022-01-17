package com.prography.sw.aloodelevery.data.response.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

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
    private int rate;
    @SerializedName("rides")
    @Expose
    private int rides;
    @SerializedName("hours")
    @Expose
    private String hours;
    @SerializedName("income")
    @Expose
    private String income;
    @SerializedName("exchanged")
    @Expose
    private float exchanged;
    private final static long serialVersionUID = 9154541224104716295L;

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

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public int getRides() {
        return rides;
    }

    public void setRides(int rides) {
        this.rides = rides;
    }

    public String getHours() {
        return hours;
    }

    public void setHours(String hours) {
        this.hours = hours;
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