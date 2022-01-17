package com.prography.sw.aloocustomer.data.response.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.prography.sw.aloocustomer.model.Category;

import java.io.Serializable;
import java.util.List;

public class CategoriesData implements Serializable {

    @SerializedName("categories")
    @Expose
    private List<Category> categories = null;
    private final static long serialVersionUID = 4070459790377737009L;

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

}