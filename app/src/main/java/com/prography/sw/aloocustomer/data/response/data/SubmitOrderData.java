package com.prography.sw.aloocustomer.data.response.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class SubmitOrderData implements Serializable {

    @SerializedName("url")
    @Expose
    private String url;
    private final static long serialVersionUID = 5609113827951380441L;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}