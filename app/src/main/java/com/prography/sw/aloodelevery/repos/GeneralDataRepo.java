package com.prography.sw.aloodelevery.repos;

import androidx.lifecycle.LiveData;

import com.prography.sw.aloodelevery.data.response.GeneralResponse;
import com.prography.sw.aloodelevery.network.AuthApiResponse;
import com.prography.sw.aloodelevery.network.ServiceGenerator;

public class GeneralDataRepo {
    private static volatile GeneralDataRepo instance;

    public static GeneralDataRepo getInstance() {
        if (instance == null) {

            instance = new GeneralDataRepo();
        }
        return instance;
    }

    public LiveData<AuthApiResponse<GeneralResponse>> aboutData(String acceptLanguage) {
        return ServiceGenerator.getMApi().aboutData(acceptLanguage);
    }

    public LiveData<AuthApiResponse<GeneralResponse>> privacy(String acceptLanguage) {
        return ServiceGenerator.getMApi().privacy(acceptLanguage);
    }

    public LiveData<AuthApiResponse<GeneralResponse>> questions(int page, String acceptLanguage) {
        return ServiceGenerator.getMApi().questions(page, acceptLanguage);
    }

    public LiveData<AuthApiResponse<GeneralResponse>> clientService(String type, String question, String acceptLanguage) {
        return ServiceGenerator.getMApi().clientService(type, question, acceptLanguage);
    }

}
