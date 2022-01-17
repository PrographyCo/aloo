package com.prography.sw.aloocustomer.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Rates implements Serializable {

    @SerializedName("total")
    @Expose
    private int total;
    @SerializedName("number")
    @Expose
    private int number;
    private final static long serialVersionUID = 943892356959326408L;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

}