package com.prography.sw.alooproduct.model;

public class HomeResturant {
     private String tv_time;
     private String tv_meal_number;
     private String tv_order_number;
     private String tv_owner_meal;
     private String tv_price;


    public HomeResturant(String tv_time, String tv_meal_number, String tv_order_number, String tv_owner_meal, String tv_price) {
        this.tv_time = tv_time;
        this.tv_meal_number = tv_meal_number;
        this.tv_order_number = tv_order_number;
        this.tv_owner_meal = tv_owner_meal;
        this.tv_price = tv_price;
    }

    public String getTv_time() {
        return tv_time;
    }

    public void setTv_time(String tv_time) {
        this.tv_time = tv_time;
    }

    public String getTv_meal_number() {
        return tv_meal_number;
    }

    public void setTv_meal_number(String tv_meal_number) {
        this.tv_meal_number = tv_meal_number;
    }

    public String getTv_order_number() {
        return tv_order_number;
    }

    public void setTv_order_number(String tv_order_number) {
        this.tv_order_number = tv_order_number;
    }

    public String getTv_owner_meal() {
        return tv_owner_meal;
    }

    public void setTv_owner_meal(String tv_owner_meal) {
        this.tv_owner_meal = tv_owner_meal;
    }

    public String getTv_price() {
        return tv_price;
    }

    public void setTv_price(String tv_price) {
        this.tv_price = tv_price;
    }
}
