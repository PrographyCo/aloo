package com.prography.sw.aloocustomer.data.response.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.prography.sw.aloocustomer.model.Cart;
import com.prography.sw.aloocustomer.model.CartItem;

import java.io.Serializable;
import java.util.List;

public class AllCardItemsData implements Serializable {


    @SerializedName("cart")
    @Expose
    private Cart cart;
    private final static long serialVersionUID = 6145006285153184980L;

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }


}