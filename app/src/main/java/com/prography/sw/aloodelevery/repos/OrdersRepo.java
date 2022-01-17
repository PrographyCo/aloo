package com.prography.sw.aloodelevery.repos;

import androidx.lifecycle.LiveData;

import com.prography.sw.aloodelevery.data.response.GeneralResponse;
import com.prography.sw.aloodelevery.network.AuthApiResponse;
import com.prography.sw.aloodelevery.network.ServiceGenerator;

import retrofit2.http.Field;

public class OrdersRepo {

    private static volatile OrdersRepo instance;

    public static OrdersRepo getInstance() {
        if (instance == null) {
            instance = new OrdersRepo();
        }
        return instance;
    }

    public LiveData<AuthApiResponse<GeneralResponse>> orders(double lon, double lat, int page, String order_by, int type, String token, String acceptLanguage) {
        return ServiceGenerator.getMApi().orders(lon, lat, page, order_by, type, token, acceptLanguage);
    }

    public LiveData<AuthApiResponse<GeneralResponse>> orderData(int id, String token, String acceptLanguage) {
        return ServiceGenerator.getMApi().orderData(id, token, acceptLanguage);
    }

    public LiveData<AuthApiResponse<GeneralResponse>> confirm(int id, int distance, String token, String acceptLanguage) {
        return ServiceGenerator.getMApi().confirm(id, distance, token, acceptLanguage);
    }

    public LiveData<AuthApiResponse<GeneralResponse>> cancel(int id, String message, String token, String acceptLanguage) {
        return ServiceGenerator.getMApi().cancel(id, message, token, acceptLanguage);
    }

    public LiveData<AuthApiResponse<GeneralResponse>> waiting(int id, String token, String acceptLanguage) {
        return ServiceGenerator.getMApi().waiting(id, token, acceptLanguage);
    }

    public LiveData<AuthApiResponse<GeneralResponse>> toCustomer(int id, String token, String acceptLanguage) {
        return ServiceGenerator.getMApi().toCustomer(id, token, acceptLanguage);
    }

    public LiveData<AuthApiResponse<GeneralResponse>> arrived(int id, String token, String acceptLanguage) {
        return ServiceGenerator.getMApi().arrived(id, token, acceptLanguage);
    }

    public LiveData<AuthApiResponse<GeneralResponse>> delivered(int id, String token, String acceptLanguage) {
        return ServiceGenerator.getMApi().delivered(id, token, acceptLanguage);
    }

    public LiveData<AuthApiResponse<GeneralResponse>> getOrdersCurrentCancelled(String status, String token, String acceptLanguage) {
        return ServiceGenerator.getMApi().getOrdersCurrentCancelled(status, token, acceptLanguage);
    }

    public LiveData<AuthApiResponse<GeneralResponse>> getOrdersCurrentDetails(int id, String token, String acceptLanguage) {
        return ServiceGenerator.getMApi().getOrdersCurrentDetails(id, token, acceptLanguage);
    }

    public LiveData<AuthApiResponse<GeneralResponse>> rate(int orderId, String rate, String message, String token, String acceptLanguage) {
        return ServiceGenerator.getMApi().rate(orderId, rate, message, token, acceptLanguage);
    }
}
