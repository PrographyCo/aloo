package com.prography.sw.alooproduct.data.response.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.prography.sw.alooproduct.model.Bank;

import java.io.Serializable;
import java.util.List;

public class BanksData implements Serializable {

    @SerializedName("banks")
    @Expose
    private List<Bank> banks = null;
    private final static long serialVersionUID = -8129478594409775073L;

    public List<Bank> getBanks() {
        return banks;
    }

    public void setBanks(List<Bank> banks) {
        this.banks = banks;
    }

}