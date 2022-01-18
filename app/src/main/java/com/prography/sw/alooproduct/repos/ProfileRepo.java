package com.prography.sw.alooproduct.repos;

import androidx.lifecycle.LiveData;

import com.prography.sw.alooproduct.data.response.GeneralResponse;
import com.prography.sw.alooproduct.network.AuthApiResponse;
import com.prography.sw.alooproduct.network.ServiceGenerator;

public class ProfileRepo {

    private static volatile ProfileRepo instance;

    public static ProfileRepo getInstance() {
        if (instance == null) {
            instance = new ProfileRepo();
        }
        return instance;
    }

    public LiveData<AuthApiResponse<GeneralResponse>> changeAvailableStatus(String token, String acceptLanguage) {
        return ServiceGenerator.getMApi().changeAvailableStatus(token, acceptLanguage);
    }

    public LiveData<AuthApiResponse<GeneralResponse>> getAvailableStatus(String token, String acceptLanguage) {
        return ServiceGenerator.getMApi().getAvailableStatus(token, acceptLanguage);
    }

    public LiveData<AuthApiResponse<GeneralResponse>> getCategories(String token, String acceptLanguage) {
        return ServiceGenerator.getMApi().getCategories(token, acceptLanguage);
    }

    public LiveData<AuthApiResponse<GeneralResponse>> getItems(int page, String token, String acceptLanguage) {
        return ServiceGenerator.getMApi().getItems(page, token, acceptLanguage);
    }

    public LiveData<AuthApiResponse<GeneralResponse>> getProfile(String token, String acceptLanguage) {
        return ServiceGenerator.getMApi().getProfile(token, acceptLanguage);
    }

    public LiveData<AuthApiResponse<GeneralResponse>> getMyData(String token, String acceptLanguage) {
        return ServiceGenerator.getMApi().getMyData(token, acceptLanguage);
    }

    public LiveData<AuthApiResponse<GeneralResponse>> getWallet(String token, String acceptLanguage) {
        return ServiceGenerator.getMApi().getWallet(token, acceptLanguage);
    }
    public LiveData<AuthApiResponse<GeneralResponse>> setFirebaseToken(String fcmToken,String token, String acceptLanguage) {
        return ServiceGenerator.getMApi().setFirebaseToken(fcmToken,token, acceptLanguage);
    }
    public LiveData<AuthApiResponse<GeneralResponse>> getNotification(int page, String token, String acceptLanguage) {
        return ServiceGenerator.getMApi().getNotification(page, token, acceptLanguage);
    }
}
