package com.prography.sw.aloocustomer.repos;

import androidx.lifecycle.LiveData;

import com.prography.sw.aloocustomer.data.response.GeneralResponse;
import com.prography.sw.aloocustomer.network.AuthApiResponse;
import com.prography.sw.aloocustomer.network.ServiceGenerator;

public class MyOrdersRepo {
    private static volatile MyOrdersRepo instance;

    public static MyOrdersRepo getInstance() {
        if (instance == null) {
            instance = new MyOrdersRepo();
        }
        return instance;
    }

    public LiveData<AuthApiResponse<GeneralResponse>> getMYOrders(int id, String token, String acceptLanguage) {
        return ServiceGenerator.getMApi().getMYOrders(id, token, acceptLanguage);
    }

    public LiveData<AuthApiResponse<GeneralResponse>> getOrderDetails(int id, String token, String acceptLanguage) {
        return ServiceGenerator.getMApi().getOrderDetails(id, token, acceptLanguage);
    }

    public LiveData<AuthApiResponse<GeneralResponse>> rate(int orderId, String rate, String message, String token, String acceptLanguage) {
        return ServiceGenerator.getMApi().rate(orderId, rate, message, token, acceptLanguage);
    }

}
