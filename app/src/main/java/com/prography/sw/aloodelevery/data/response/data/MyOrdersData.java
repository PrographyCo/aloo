package com.prography.sw.aloodelevery.data.response.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.prography.sw.aloodelevery.model.MyOrder;

import java.io.Serializable;
import java.util.List;

public class MyOrdersData implements Serializable {

    @SerializedName("current_page")
    @Expose
    private int currentPage;
    @SerializedName("items")
    @Expose
    private List<MyOrder> items = null;
    @SerializedName("per_page")
    @Expose
    private int perPage;
    @SerializedName("total")
    @Expose
    private int total;
    private final static long serialVersionUID = 3096557740441279747L;

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public List<MyOrder> getItems() {
        return items;
    }

    public void setItems(List<MyOrder> items) {
        this.items = items;
    }

    public int getPerPage() {
        return perPage;
    }

    public void setPerPage(int perPage) {
        this.perPage = perPage;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

}