package com.prography.sw.aloocustomer.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class OldItemChoices implements Serializable {

    @SerializedName("size")
    @Expose
    private String size;
    @SerializedName("with")
    @Expose
    private List<String> with = null;
    @SerializedName("without")
    @Expose
    private List<String> without = null;
    @SerializedName("drinks")
    @Expose
    private List<Drink> drinks = null;
    @SerializedName("extras")
    @Expose
    private List<Extra> extras = null;
    private final static long serialVersionUID = 5097629759425633232L;

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public List<String> getWith() {
        return with;
    }

    public void setWith(List<String> with) {
        this.with = with;
    }

    public List<String> getWithout() {
        return without;
    }

    public void setWithout(List<String> without) {
        this.without = without;
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