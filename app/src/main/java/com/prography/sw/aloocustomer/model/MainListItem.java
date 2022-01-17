package com.prography.sw.aloocustomer.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class MainListItem implements Serializable {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("logo")
    @Expose
    private String logo;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("min_price")
    @Expose
    private String minPrice;
    @SerializedName("rates")
    @Expose
    private Rates rates;
    @SerializedName("restaurant_types")
    @Expose
    private String restaurantTypes;
    @SerializedName("kitchen_types")
    @Expose
    private String kitchenTypes;
    @SerializedName("city")
    @Expose
    private City city;
    private final static long serialVersionUID = 7525540654640360338L;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(String minPrice) {
        this.minPrice = minPrice;
    }

    public Rates getRates() {
        return rates;
    }

    public void setRates(Rates rates) {
        this.rates = rates;
    }

    public String getRestaurantTypes() {
        return restaurantTypes;
    }

    public void setRestaurantTypes(String restaurantTypes) {
        this.restaurantTypes = restaurantTypes;
    }

    public String getKitchenTypes() {
        return kitchenTypes;
    }

    public void setKitchenTypes(String kitchenTypes) {
        this.kitchenTypes = kitchenTypes;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

}