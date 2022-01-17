package com.prography.sw.aloodelevery.model;

public class MyRides {
    private int icon;
    private String timeDate;
    private String name;
    private String address_1;
    private String address_2;
    private String price;

    public MyRides(int icon, String timeDate, String name, String address_1, String address_2, String price) {
        this.icon = icon;
        this.timeDate = timeDate;
        this.name = name;
        this.address_1 = address_1;
        this.address_2 = address_2;
        this.price = price;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getTimeDate() {
        return timeDate;
    }

    public void setTimeDate(String timeDate) {
        this.timeDate = timeDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress_1() {
        return address_1;
    }

    public void setAddress_1(String address_1) {
        this.address_1 = address_1;
    }

    public String getAddress_2() {
        return address_2;
    }

    public void setAddress_2(String address_2) {
        this.address_2 = address_2;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
