package com.prography.sw.alooproduct.model;

public class HomeSuperPharm {
    private int image;
    private String tv_name;
    private String tv_price;
    private String tv_order_number;


    public HomeSuperPharm(int image, String tv_name, String tv_price, String tv_order_number) {
        this.image = image;
        this.tv_name = tv_name;
        this.tv_price = tv_price;
        this.tv_order_number = tv_order_number;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getTv_name() {
        return tv_name;
    }

    public void setTv_name(String tv_name) {
        this.tv_name = tv_name;
    }

    public String getTv_price() {
        return tv_price;
    }

    public void setTv_price(String tv_price) {
        this.tv_price = tv_price;
    }

    public String getTv_order_number() {
        return tv_order_number;
    }

    public void setTv_order_number(String tv_order_number) {
        this.tv_order_number = tv_order_number;
    }
}
