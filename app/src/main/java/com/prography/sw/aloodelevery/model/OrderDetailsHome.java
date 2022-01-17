package com.prography.sw.aloodelevery.model;

public class OrderDetailsHome {

    private int image;
    private String meal_name;
    private String meal_without;
    private String meals_number;
    private String meal_price;

    public OrderDetailsHome(int image, String meal_name, String meal_without, String meals_number, String meal_price) {
        this.image = image;
        this.meal_name = meal_name;
        this.meal_without = meal_without;
        this.meals_number = meals_number;
        this.meal_price = meal_price;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getMeal_name() {
        return meal_name;
    }

    public void setMeal_name(String meal_name) {
        this.meal_name = meal_name;
    }

    public String getMeal_without() {
        return meal_without;
    }

    public void setMeal_without(String meal_without) {
        this.meal_without = meal_without;
    }

    public String getMeals_number() {
        return meals_number;
    }

    public void setMeals_number(String meals_number) {
        this.meals_number = meals_number;
    }

    public String getMeal_price() {
        return meal_price;
    }

    public void setMeal_price(String meal_price) {
        this.meal_price = meal_price;
    }
}
