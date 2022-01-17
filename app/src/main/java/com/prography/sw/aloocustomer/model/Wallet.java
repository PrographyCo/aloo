package com.prography.sw.aloocustomer.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Wallet implements Serializable {

    @SerializedName("customer_id")
    @Expose
    private String customerId;
    @SerializedName("amount")
    @Expose
    private String amount;
    @SerializedName("reserved")
    @Expose
    private String reserved;
    private final static long serialVersionUID = -4949688120875262020L;

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getReserved() {
        return reserved;
    }

    public void setReserved(String reserved) {
        this.reserved = reserved;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }
}