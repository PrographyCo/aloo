package com.prography.sw.alooproduct.repos;

import androidx.lifecycle.LiveData;

import com.prography.sw.alooproduct.data.response.GeneralResponse;
import com.prography.sw.alooproduct.network.AuthApiResponse;
import com.prography.sw.alooproduct.network.ServiceGenerator;

public class LoginRepo {
    private static volatile LoginRepo instance;


    public static LoginRepo getInstance() {
        if (instance == null) {
            instance = new LoginRepo();
        }
        return instance;
    }

    public LiveData<AuthApiResponse<GeneralResponse>> login(String type,String login_number, String password, String acceptLanguage) {
        return ServiceGenerator.getMApi().login(type,login_number, password, acceptLanguage);

    }
}
