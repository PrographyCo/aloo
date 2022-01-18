package com.prography.sw.alooproduct.data.response.data;

import java.io.Serializable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.prography.sw.alooproduct.model.Token;

public class LoginData implements Serializable {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("manager")
    @Expose
    private String manager;
    @SerializedName("managerEmail")
    @Expose
    private String managerEmail;
    @SerializedName("managerPhone")
    @Expose
    private String managerPhone;
    @SerializedName("managerPosition")
    @Expose
    private String managerPosition;
    @SerializedName("lon")
    @Expose
    private String lon;
    @SerializedName("lat")
    @Expose
    private String lat;
    @SerializedName("is_restaurant")
    @Expose
    private Boolean isRestaurant;
    @SerializedName("token")
    @Expose
    private Token token;
    private final static long serialVersionUID = -9135649562348213406L;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public String getManagerEmail() {
        return managerEmail;
    }

    public void setManagerEmail(String managerEmail) {
        this.managerEmail = managerEmail;
    }

    public String getManagerPhone() {
        return managerPhone;
    }

    public void setManagerPhone(String managerPhone) {
        this.managerPhone = managerPhone;
    }

    public String getManagerPosition() {
        return managerPosition;
    }

    public void setManagerPosition(String managerPosition) {
        this.managerPosition = managerPosition;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public Boolean getRestaurant() {
        return isRestaurant;
    }

    public void setRestaurant(Boolean restaurant) {
        isRestaurant = restaurant;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }

}
