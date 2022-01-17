package com.prography.sw.aloocustomer.data.response.data;


import java.io.Serializable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.prography.sw.aloocustomer.model.Token;

public class LoginData implements Serializable {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("img")
    @Expose
    private Object img;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("email")
    @Expose
    private Object email;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("city_id")
    @Expose
    private String cityId;
    @SerializedName("FToken")
    @Expose
    private Object fToken;
    @SerializedName("token")
    @Expose
    private Token token;
    private final static long serialVersionUID = 8813975531861792755L;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Object getImg() {
        return img;
    }

    public void setImg(Object img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Object getEmail() {
        return email;
    }

    public void setEmail(Object email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public Object getFToken() {
        return fToken;
    }

    public void setFToken(Object fToken) {
        this.fToken = fToken;
    }

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }

}