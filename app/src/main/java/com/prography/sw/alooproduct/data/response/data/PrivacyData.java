package com.prography.sw.alooproduct.data.response.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class PrivacyData implements Serializable {

    @SerializedName("data")
    @Expose
    private String data;
    private final static long serialVersionUID = -7493597639244024956L;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

}