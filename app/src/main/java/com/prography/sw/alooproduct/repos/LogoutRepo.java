package com.prography.sw.alooproduct.repos;

import androidx.lifecycle.LiveData;

import com.prography.sw.alooproduct.data.response.GeneralResponse;
import com.prography.sw.alooproduct.network.AuthApiResponse;
import com.prography.sw.alooproduct.network.ServiceGenerator;

public class LogoutRepo {
    private static volatile LogoutRepo instance;

    public static LogoutRepo getInstance() {
        if (instance == null) {
            instance = new LogoutRepo();
        }
        return instance;
    }

    public LiveData<AuthApiResponse<GeneralResponse>> logout(String token, String acceptLanguage) {
        return ServiceGenerator.getMApi().logout(token, acceptLanguage);
    }
}