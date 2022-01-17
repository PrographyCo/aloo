package com.prography.sw.aloocustomer.repos;

import androidx.lifecycle.LiveData;

import com.prography.sw.aloocustomer.data.response.GeneralResponse;
import com.prography.sw.aloocustomer.network.AuthApiResponse;
import com.prography.sw.aloocustomer.network.ServiceGenerator;

public class SignupRepo {

    private static volatile SignupRepo instance;


    public static SignupRepo getInstance() {
        if (instance == null) {
            instance = new SignupRepo();
        }
        return instance;
    }

    public LiveData<AuthApiResponse<GeneralResponse>> signup(String name, String phone,
                                                            String gender, String city, String password,
                                                            String password_confirmation, String acceptLanguage) {
        return ServiceGenerator.getMApi().signup(name, phone, gender, city, password, password_confirmation, acceptLanguage);
    }
}
