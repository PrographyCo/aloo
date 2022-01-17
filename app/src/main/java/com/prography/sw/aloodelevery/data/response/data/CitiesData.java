package com.prography.sw.aloodelevery.data.response.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.prography.sw.aloodelevery.model.City;

import java.io.Serializable;
import java.util.List;

public class CitiesData implements Serializable {

    @SerializedName("cities")
    @Expose
    private List<City> cities = null;
    private final static long serialVersionUID = -6383712313756905412L;

    public List<City> getCities() {
        return cities;
    }

    public void setCities(List<City> cities) {
        this.cities = cities;
    }

}