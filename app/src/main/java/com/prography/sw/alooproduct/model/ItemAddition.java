package com.prography.sw.alooproduct.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class ItemAddition implements Serializable {

    @SerializedName("with")
    @Expose
    private String with ;
    @SerializedName("without")
    @Expose
    private String without ;
    @SerializedName("size")
    @Expose
    private String size;
    @SerializedName("drinks")
    @Expose
    private List<Object> drinks = null;
    @SerializedName("extras")
    @Expose
    private List<Object> extras = null;
    private final static long serialVersionUID = 2937803220705643776L;

    public String getWith() {
        return with;
    }

    public void setWith(String with) {
        this.with = with;
    }

    public String getWithout() {
        return without;
    }

    public void setWithout(String without) {
        this.without = without;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public List<Object> getDrinks() {
        return drinks;
    }

    public void setDrinks(List<Object> drinks) {
        this.drinks = drinks;
    }

    public List<Object> getExtras() {
        return extras;
    }

    public void setExtras(List<Object> extras) {
        this.extras = extras;
    }

}