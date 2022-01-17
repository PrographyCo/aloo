package com.prography.sw.aloodelevery.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Supplements implements Serializable {

    @SerializedName("with")
    @Expose
    private String with;
    @SerializedName("without")
    @Expose
    private String without;
    @SerializedName("size")
    @Expose
    private String size;
    @SerializedName("drinks")
    @Expose
    private List<Drink> drinks = null;
    @SerializedName("extras")
    @Expose
    private List<Extra> extras = null;
    private final static long serialVersionUID = -8021973497830199979L;

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

    public List<Drink> getDrinks() {
        return drinks;
    }

    public void setDrinks(List<Drink> drinks) {
        this.drinks = drinks;
    }

    public List<Extra> getExtras() {
        return extras;
    }

    public void setExtras(List<Extra> extras) {
        this.extras = extras;
    }

}