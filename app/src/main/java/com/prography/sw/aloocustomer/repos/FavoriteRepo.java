package com.prography.sw.aloocustomer.repos;

import androidx.lifecycle.LiveData;

import com.prography.sw.aloocustomer.data.response.GeneralResponse;
import com.prography.sw.aloocustomer.network.AuthApiResponse;
import com.prography.sw.aloocustomer.network.ServiceGenerator;

public class FavoriteRepo {
    private static volatile FavoriteRepo instance;

    public static FavoriteRepo getInstance() {
        if (instance == null) {
            instance = new FavoriteRepo();
        }
        return instance;
    }

    public LiveData<AuthApiResponse<GeneralResponse>> getFavoriteVendors(int id, String token, String acceptLanguage) {
        return ServiceGenerator.getMApi().getFavoriteVendors(id, token, acceptLanguage);
    }

    public LiveData<AuthApiResponse<GeneralResponse>> getFavoriteItems(int id, int page, String token, String acceptLanguage) {
        return ServiceGenerator.getMApi().getFavoriteItems(id, page, token, acceptLanguage);
    }

    public LiveData<AuthApiResponse<GeneralResponse>> addToFavorite(String id, String token, String acceptLanguage) {
        return ServiceGenerator.getMApi().addToFavorite(id, token, acceptLanguage);
    }

    public LiveData<AuthApiResponse<GeneralResponse>> deleteFavorite(int id, String token, String acceptLanguage) {
        return ServiceGenerator.getMApi().deleteFavorite(id, token, acceptLanguage);
    }
}
