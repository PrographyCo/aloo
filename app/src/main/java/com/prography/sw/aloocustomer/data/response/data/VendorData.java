package com.prography.sw.aloocustomer.data.response.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.prography.sw.aloocustomer.model.Main;
import com.prography.sw.aloocustomer.model.Offer;
import com.prography.sw.aloocustomer.model.Rates;

import java.io.Serializable;
import java.util.List;

public class VendorData implements Serializable {

    @SerializedName("offers")
    @Expose
    private List<Offer> offers = null;
    @SerializedName("main")
    @Expose
    private Main main;
    @SerializedName("rates")
    @Expose
    private Rates rates;
    private final static long serialVersionUID = -2098053752122227235L;

    public List<Offer> getOffers() {
        return offers;
    }

    public void setOffers(List<Offer> offers) {
        this.offers = offers;
    }

    public Main getMain() {
        return main;
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public Rates getRates() {
        return rates;
    }

    public void setRates(Rates rates) {
        this.rates = rates;
    }

}