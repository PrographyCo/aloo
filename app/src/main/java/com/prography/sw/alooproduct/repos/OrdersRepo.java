package com.prography.sw.alooproduct.repos;

import androidx.lifecycle.LiveData;

import com.prography.sw.alooproduct.data.response.GeneralResponse;
import com.prography.sw.alooproduct.network.AuthApiResponse;
import com.prography.sw.alooproduct.network.ServiceGenerator;

public class OrdersRepo {

    private static volatile OrdersRepo instance;


    public static OrdersRepo getInstance() {
        if (instance == null) {
            instance = new OrdersRepo();
        }
        return instance;
    }


    //------------order--------------

    public LiveData<AuthApiResponse<GeneralResponse>> getOrders(int page, String order_by,  String search, String token, String acceptLanguage) {
        return ServiceGenerator.getMApi().order(page, order_by, search, token, acceptLanguage);
    }


    //------------ordersData--------------
    public LiveData<AuthApiResponse<GeneralResponse>> getordersData(String page,
                                                                    String token, String acceptLanguage) {
        return ServiceGenerator.getMApi().ordersData(page, token, acceptLanguage);
    }


    //------------confirm--------------
    public LiveData<AuthApiResponse<GeneralResponse>> confirm(int id,
                                                              String token, String acceptLanguage) {
        return ServiceGenerator.getMApi().confirm(id, token, acceptLanguage);
    }


    //------------cancel--------------
    public LiveData<AuthApiResponse<GeneralResponse>> cancel(int page, String message,
                                                             String token, String acceptLanguage) {
        return ServiceGenerator.getMApi().cancel(page, message, token, acceptLanguage);
    }


    //------------ready--------------
    public LiveData<AuthApiResponse<GeneralResponse>> ready(int page,
                                                            String token, String acceptLanguage) {
        return ServiceGenerator.getMApi().ready(page, token, acceptLanguage);
    }

    //------------my order--------------

    public LiveData<AuthApiResponse<GeneralResponse>> getCancelled(int page, String token, String acceptLanguage) {
        return ServiceGenerator.getMApi().cancelled(page, token, acceptLanguage);
    }

    public LiveData<AuthApiResponse<GeneralResponse>> getCurrent(int page, String token, String acceptLanguage) {
        return ServiceGenerator.getMApi().current(page, token, acceptLanguage);
    }


}
