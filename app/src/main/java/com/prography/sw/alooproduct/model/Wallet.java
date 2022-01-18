package com.prography.sw.alooproduct.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Wallet implements Serializable {

    @SerializedName("branch_id")
    @Expose
    private String branchId;
    @SerializedName("amount")
    @Expose
    private String amount;
    private final static long serialVersionUID = -4949688120875262020L;

    public String getBranchId() {
        return branchId;
    }

    public void setBranchId(String branchId) {
        this.branchId = branchId;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

}