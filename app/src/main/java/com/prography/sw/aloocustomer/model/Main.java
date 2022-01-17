package com.prography.sw.aloocustomer.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Main implements Serializable
{

@SerializedName("current_page")
@Expose
private int currentPage;
@SerializedName("items")
@Expose
private List<VendorItems> items = null;
@SerializedName("per_page")
@Expose
private int perPage;
@SerializedName("total")
@Expose
private int total;
private final static long serialVersionUID = 8915369771261152930L;

public int getCurrentPage() {
return currentPage;
}

public void setCurrentPage(int currentPage) {
this.currentPage = currentPage;
}

public List<VendorItems> getItems() {
return items;
}

public void setItems(List<VendorItems> items) {
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