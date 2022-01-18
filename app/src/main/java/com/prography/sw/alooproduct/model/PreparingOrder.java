package com.prography.sw.alooproduct.model;

public class PreparingOrder {

    private int image;
    private String product_name;
    private String product_Drink;
    private String product_without;
    private String product_number;
    private String product_price;
    private boolean checkBoxe;

    public PreparingOrder(int image, String product_name, String product_Drink, String product_without, String product_number, String product_price, boolean checkBoxe) {
        this.image = image;
        this.product_name = product_name;
        this.product_Drink = product_Drink;
        this.product_without = product_without;
        this.product_number = product_number;
        this.product_price = product_price;
        this.checkBoxe = checkBoxe;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getProduct_Drink() {
        return product_Drink;
    }

    public void setProduct_Drink(String product_Drink) {
        this.product_Drink = product_Drink;
    }

    public String getProduct_without() {
        return product_without;
    }

    public void setProduct_without(String product_without) {
        this.product_without = product_without;
    }

    public boolean isCheckBoxe() {
        return checkBoxe;
    }

    public void setCheckBoxe(boolean checkBoxe) {
        this.checkBoxe = checkBoxe;
    }

    public String getProduct_number() {
        return product_number;
    }

    public void setProduct_number(String product_number) {
        this.product_number = product_number;
    }

    public String getProduct_price() {
        return product_price;
    }

    public void setProduct_price(String product_price) {
        this.product_price = product_price;
    }
}
