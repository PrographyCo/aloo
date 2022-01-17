package com.prography.sw.aloocustomer.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Cart {

    @SerializedName("items")
    @Expose
    private List<CartItem> cartItems = null;
    @SerializedName("total")
    @Expose
    private String total;
    @SerializedName("delivery_price_reservation")
    @Expose
    private int deliveryPriceReservation;
    @SerializedName("place")
    @Expose
    private Place place;


    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<CartItem> cartItems) {
        this.cartItems = cartItems;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public int getDeliveryPriceReservation() {
        return deliveryPriceReservation;
    }

    public void setDeliveryPriceReservation(int deliveryPriceReservation) {
        this.deliveryPriceReservation = deliveryPriceReservation;
    }

    public Place getPlace() {
        return place;
    }

    public void setPlace(Place place) {
        this.place = place;
    }
}
