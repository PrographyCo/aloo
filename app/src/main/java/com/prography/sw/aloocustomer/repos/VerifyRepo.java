package com.prography.sw.aloocustomer.repos;

import androidx.lifecycle.LiveData;

import com.prography.sw.aloocustomer.data.response.GeneralResponse;
import com.prography.sw.aloocustomer.network.AuthApiResponse;
import com.prography.sw.aloocustomer.network.ServiceGenerator;

public class VerifyRepo {
    private static volatile VerifyRepo instance;


    public static VerifyRepo getInstance() {
        if (instance == null) {
            instance = new VerifyRepo();
        }
        return instance;
    }

    public LiveData<AuthApiResponse<GeneralResponse>> verify(String phone, String code_verify, String acceptLanguage) {
        return ServiceGenerator.getMApi().verify(phone, code_verify, acceptLanguage);
    }

    public LiveData<AuthApiResponse<GeneralResponse>> resendCode(String phone, String acceptLanguage) {
        return ServiceGenerator.getMApi().resendCode(phone, acceptLanguage);
    }
}
