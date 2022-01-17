package com.prography.sw.aloocustomer.repos;

import androidx.lifecycle.LiveData;

import com.prography.sw.aloocustomer.data.response.GeneralResponse;
import com.prography.sw.aloocustomer.network.AuthApiResponse;
import com.prography.sw.aloocustomer.network.ServiceGenerator;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;

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

    public LiveData<AuthApiResponse<GeneralResponse>> updateProfile(RequestBody name, RequestBody password, RequestBody passwordConfirmation, RequestBody city, RequestBody gender, RequestBody phone, RequestBody email, MultipartBody.Part img, String token, String acceptLanguage) {
        return ServiceGenerator.getMApi().updateProfile(name, password, passwordConfirmation, city, gender, phone, email, img, token, acceptLanguage);
    }

    public LiveData<AuthApiResponse<GeneralResponse>> getWallet(String token, String acceptLanguage) {
        return ServiceGenerator.getMApi().getWallet(token, acceptLanguage);
    }

    public LiveData<AuthApiResponse<GeneralResponse>> setFirebaseToken(String fcmToken, String token, String acceptLanguage) {
        return ServiceGenerator.getMApi().setFirebaseToken(fcmToken, token, acceptLanguage);
    }

    public LiveData<AuthApiResponse<GeneralResponse>> getNotification(int page, String token, String acceptLanguage) {
        return ServiceGenerator.getMApi().getNotification(page, token, acceptLanguage);
    }
}
