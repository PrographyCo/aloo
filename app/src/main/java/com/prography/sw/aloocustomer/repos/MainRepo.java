package com.prography.sw.aloocustomer.repos;

import androidx.lifecycle.LiveData;

import com.prography.sw.aloocustomer.data.response.GeneralResponse;
import com.prography.sw.aloocustomer.network.AuthApiResponse;
import com.prography.sw.aloocustomer.network.ServiceGenerator;

public class MainRepo {
    private static volatile MainRepo instance;

    public static MainRepo getInstance() {
        if (instance == null) {
            instance = new MainRepo();
        }
        return instance;
    }

    public LiveData<AuthApiResponse<GeneralResponse>> getMain(int id, int page, double lon, double lat,
                                                              int place_id, String token, String acceptLanguage) {
        return ServiceGenerator.getMApi().getMain(id, page, lon, lat, place_id, token, acceptLanguage);
    }

    public LiveData<AuthApiResponse<GeneralResponse>> getMain(int id, int page, double lon, double lat,
                                                              int place_id, int kitchen_type, int restaurant_type,
                                                              String order_by, String token, String acceptLanguage) {
        return ServiceGenerator.getMApi().getMain(id, page, lon, lat, place_id, kitchen_type, restaurant_type, order_by, token, acceptLanguage);
    }

    public LiveData<AuthApiResponse<GeneralResponse>> getVendor(int id, int page, double lon, double lat,
                                                                String token, String acceptLanguage) {
        return ServiceGenerator.getMApi().getVendor(id, page, lon, lat, token, acceptLanguage);
    }

    public LiveData<AuthApiResponse<GeneralResponse>> getVendor(int id, int page, String order_by, int category, double lon, double lat,
                                                                String token, String acceptLanguage) {
        return ServiceGenerator.getMApi().getVendor(id, page, order_by, category, lon, lat, token, acceptLanguage);
    }

    public LiveData<AuthApiResponse<GeneralResponse>> getVendorItem(int id, String token, String acceptLanguage) {
        return ServiceGenerator.getMApi().getVendorItem(id, token, acceptLanguage);
    }

    public LiveData<AuthApiResponse<GeneralResponse>> getCategories(int id, String token, String acceptLanguage) {
        return ServiceGenerator.getMApi().getCategories(id, token, acceptLanguage);
    }

}
