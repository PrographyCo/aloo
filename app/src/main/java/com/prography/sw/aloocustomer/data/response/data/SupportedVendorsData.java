package com.prography.sw.aloocustomer.data.response.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.prography.sw.aloocustomer.model.SupportedVendor;

import java.io.Serializable;
import java.util.List;

public class SupportedVendorsData implements Serializable {

    @SerializedName("supported_vendors")
    @Expose
    private List<SupportedVendor> supportedVendors = null;
    private final static long serialVersionUID = -3843395923449501669L;

    public List<SupportedVendor> getSupportedVendors() {
        return supportedVendors;
    }

    public void setSupportedVendors(List<SupportedVendor> supportedVendors) {
        this.supportedVendors = supportedVendors;
    }

}