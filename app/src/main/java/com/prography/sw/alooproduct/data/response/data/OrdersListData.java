package com.prography.sw.alooproduct.data.response.data;

import android.content.ClipData;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.prography.sw.alooproduct.model.OrderItem;

import java.io.Serializable;
import java.util.List;

public class OrdersListData implements Serializable {

    @SerializedName("current_page")
    @Expose
    private int currentPage;
    @SerializedName("items")
    @Expose
    private List<OrderItem> items = null;
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

    public List<OrderItem> getItems() {
        return items;
    }

    public void setItems(List<OrderItem> items) {
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