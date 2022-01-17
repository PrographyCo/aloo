package com.prography.sw.aloodelevery.repos;

import androidx.lifecycle.LiveData;

import com.prography.sw.aloodelevery.data.response.GeneralResponse;
import com.prography.sw.aloodelevery.network.AuthApiResponse;
import com.prography.sw.aloodelevery.network.ServiceGenerator;

import okhttp3.MultipartBody;

public class ProfileRepo {
    private static volatile ProfileRepo instance;


    public static ProfileRepo getInstance() {
        if (instance == null) {
            instance = new ProfileRepo();
        }
        return instance;
    }

    public LiveData<AuthApiResponse<GeneralResponse>> getProfile(String token, String acceptLanguage) {
        return ServiceGenerator.getMApi().getProfile(token, acceptLanguage);
    }

    public LiveData<AuthApiResponse<GeneralResponse>> getWallet(String token, String acceptLanguage) {
        return ServiceGenerator.getMApi().getWallet(token, acceptLanguage);
    }

    public LiveData<AuthApiResponse<GeneralResponse>> getMyData(String token, String acceptLanguage) {
        return ServiceGenerator.getMApi().getMyData(token, acceptLanguage);
    }

    public LiveData<AuthApiResponse<GeneralResponse>> setFirebaseToken(String fcmToken, String token, String acceptLanguage) {
        return ServiceGenerator.getMApi().setFirebaseToken(fcmToken, token, acceptLanguage);

    }

    public LiveData<AuthApiResponse<GeneralResponse>> getNotification(int page, String token, String acceptLanguage) {
        return ServiceGenerator.getMApi().getNotification(page, token, acceptLanguage);
    }
}
