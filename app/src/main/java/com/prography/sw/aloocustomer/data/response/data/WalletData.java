package com.prography.sw.aloocustomer.data.response.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.prography.sw.aloocustomer.model.Transaction;
import com.prography.sw.aloocustomer.model.Wallet;

import java.io.Serializable;
import java.util.List;

public class WalletData implements Serializable {

    @SerializedName("wallet")
    @Expose
    private Wallet wallet;
    @SerializedName("wallet_details")
    @Expose
    private List<Transaction> transactions = null;
    private final static long serialVersionUID = 8919579110378397734L;

    public Wallet getWallet() {
        return wallet;
    }

    public void setWallet(Wallet wallet) {
        this.wallet = wallet;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

}