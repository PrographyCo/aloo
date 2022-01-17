package com.prography.sw.aloodelevery.repos;

import androidx.lifecycle.LiveData;

import com.prography.sw.aloodelevery.data.response.GeneralResponse;
import com.prography.sw.aloodelevery.network.AuthApiResponse;
import com.prography.sw.aloodelevery.network.ServiceGenerator;

public class LoginRepo {
    private static volatile LoginRepo instance;


    public static LoginRepo getInstance() {
        if (instance == null) {
            instance = new LoginRepo();
        }
        return instance;
    }

    public LiveData<AuthApiResponse<GeneralResponse>> login(String type,String phone, String password, String acceptLanguage) {
        return ServiceGenerator.getMApi().login(type,phone, password, acceptLanguage);
    }
}
