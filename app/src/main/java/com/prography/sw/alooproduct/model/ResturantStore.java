package com.prography.sw.alooproduct.model;

public class ResturantStore {
    private int restaurant_image;
    private String restaurant_name;
    private String restaurant_description;
    private String restaurant_calories;
    private String restaurant_prise;

    public ResturantStore(int restaurant_image, String restaurant_name, String restaurant_description, String restaurant_calories, String restaurant_prise) {
        this.restaurant_image = restaurant_image;
        this.restaurant_name = restaurant_name;
        this.restaurant_description = restaurant_description;
        this.restaurant_calories = restaurant_calories;
        this.restaurant_prise = restaurant_prise;
    }

    public int getRestaurant_image() {
        return restaurant_image;
    }

    public void setRestaurant_image(int restaurant_image) {
        this.restaurant_image = restaurant_image;
    }

    public String getRestaurant_name() {
        return restaurant_name;
    }

    public void setRestaurant_name(String restaurant_name) {
        this.restaurant_name = restaurant_name;
    }

    public String getRestaurant_description() {
        return restaurant_description;
    }

    public void setRestaurant_description(String restaurant_description) {
        this.restaurant_description = restaurant_description;
    }

    public String getRestaurant_calories() {
        return restaurant_calories;
    }

    public void setRestaurant_calories(String restaurant_calories) {
        this.restaurant_calories = restaurant_calories;
    }

    public String getRestaurant_prise() {
        return restaurant_prise;
    }

    public void setRestaurant_prise(String restaurant_prise) {
        this.restaurant_prise = restaurant_prise;
    }
}

