package com.prography.sw.alooproduct.data.response.data;

import java.io.Serializable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StatusData implements Serializable {

    @SerializedName("status")
    @Expose
    private boolean status;
    private final static long serialVersionUID = 3328134216842856083L;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

}