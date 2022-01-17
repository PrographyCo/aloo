package com.prography.sw.aloodelevery.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class MyOrderItem implements Serializable {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("img")
    @Expose
    private String img;
    @SerializedName("amount")
    @Expose
    private int amount;
    @SerializedName("amount_type")
    @Expose
    private String amountType;
    @SerializedName("name")
    @Expose
    private String name;
    private final static long serialVersionUID = -6021386961125651554L;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getAmountType() {
        return amountType;
    }

    public void setAmountType(String amountType) {
        this.amountType = amountType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}