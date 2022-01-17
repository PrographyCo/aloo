package com.prography.sw.aloocustomer.data.response.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.prography.sw.aloocustomer.model.RestaurantType;

import java.io.Serializable;
import java.util.List;

public class RestaurantTypesData implements Serializable {

    @SerializedName("restaurant_types")
    @Expose
    private List<RestaurantType> restaurantTypes = null;
    private final static long serialVersionUID = 7161459139976433383L;

    public List<RestaurantType> getRestaurantTypes() {
        return restaurantTypes;
    }

    public void setRestaurantTypes(List<RestaurantType> restaurantTypes) {
        this.restaurantTypes = restaurantTypes;
    }

}