package com.prography.sw.aloodelevery.model;

import java.io.Serializable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Wallet implements Serializable {

    @SerializedName("car_id")
    @Expose
    private String carId;
    @SerializedName("amount")
    @Expose
    private String amount;
    private final static long serialVersionUID = -4949688120875262020L;

    public String getCarId() {
        return carId;
    }

    public void setCarId(String carId) {
        this.carId = carId;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

}