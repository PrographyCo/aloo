package com.prography.sw.aloocustomer.data.response.data;

import java.io.Serializable;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.prography.sw.aloocustomer.model.Vendor;

public class FavoriteVendorsData implements Serializable {

    @SerializedName("vendors")
    @Expose
    private List<Vendor> vendors = null;
    private final static long serialVersionUID = 6319220190454129559L;

    public List<Vendor> getVendors() {
        return vendors;
    }

    public void setVendors(List<Vendor> vendors) {
        this.vendors = vendors;
    }

}