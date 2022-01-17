package com.prography.sw.aloodelevery.model;

public class HomeFragment {

    private int icon;
    private int icne_pay;
    private String number_meal;
    private String owner_name;
    private String address_one;
    private String address_tow;
    private String now;

    public HomeFragment(int icon, int icne_pay, String number_meal, String owner_name, String address_one, String address_tow, String now) {
        this.icon = icon;
        this.icne_pay = icne_pay;
        this.number_meal = number_meal;
        this.owner_name = owner_name;
        this.address_one = address_one;
        this.address_tow = address_tow;
        this.now = now;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public int getIcne_pay() {
        return icne_pay;
    }

    public void setIcne_pay(int icne_pay) {
        this.icne_pay = icne_pay;
    }

    public String getNumber_meal() {
        return number_meal;
    }

    public void setNumber_meal(String number_meal) {
        this.number_meal = number_meal;
    }

    public String getOwner_name() {
        return owner_name;
    }

    public void setOwner_name(String owner_name) {
        this.owner_name = owner_name;
    }

    public String getAddress_one() {
        return address_one;
    }

    public void setAddress_one(String address_one) {
        this.address_one = address_one;
    }

    public String getAddress_tow() {
        return address_tow;
    }

    public void setAddress_tow(String address_tow) {
        this.address_tow = address_tow;
    }

    public String getNow() {
        return now;
    }

    public void setNow(String now) {
        this.now = now;
    }
}
