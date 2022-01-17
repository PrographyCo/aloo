package com.prography.sw.aloocustomer.data.response.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.prography.sw.aloocustomer.model.Similar;
import com.prography.sw.aloocustomer.model.VendorItems;

import java.io.Serializable;
import java.util.List;

public class VendorDetilseSuperPhrmasData implements Serializable {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("amount")
    @Expose
    private int amount;
    @SerializedName("amount_type")
    @Expose
    private String amountType;
    @SerializedName("images")
    @Expose
    private List<String> images = null;
    @SerializedName("similar")
    @Expose
    private List<VendorItems> similar = null;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("is_favorite")
    @Expose
    private boolean isFavorite;
    private final static long serialVersionUID = -1952347455635063659L;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getAmountType() {
        return amountType;
    }

    public void setAmountType(String amountType) {
        this.amountType = amountType;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public List<VendorItems> getSimilar() {
        return similar;
    }

    public void setSimilar(List<VendorItems> similar) {
        this.similar = similar;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isIsFavorite() {
        return isFavorite;
    }

    public void setIsFavorite(boolean isFavorite) {
        this.isFavorite = isFavorite;
    }

}