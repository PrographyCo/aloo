package com.prography.sw.aloocustomer.data.response.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.prography.sw.aloocustomer.model.KitchenType;

import java.io.Serializable;
import java.util.List;

public class KitchenTypesData implements Serializable {

    @SerializedName("kitchen_types")
    @Expose
    private List<KitchenType> kitchenTypes = null;
    private final static long serialVersionUID = 6563945749477744369L;

    public List<KitchenType> getKitchenTypes() {
        return kitchenTypes;
    }

    public void setKitchenTypes(List<KitchenType> kitchenTypes) {
        this.kitchenTypes = kitchenTypes;
    }

}