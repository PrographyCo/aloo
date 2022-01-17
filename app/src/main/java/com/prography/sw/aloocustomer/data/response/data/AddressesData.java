package com.prography.sw.aloocustomer.data.response.data;

import java.io.Serializable;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.prography.sw.aloocustomer.model.IAddress;

public class AddressesData implements Serializable {

    @SerializedName("places")
    @Expose
    private List<IAddress> IAddresses = null;
    private final static long serialVersionUID = 9138229609669323599L;

    public List<IAddress> getAddresses() {
        return IAddresses;
    }

    public void setAddresses(List<IAddress> IAddresses) {
        this.IAddresses = IAddresses;
    }

}