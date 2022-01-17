package com.prography.sw.aloocustomer.repos;

import androidx.lifecycle.LiveData;

import com.prography.sw.aloocustomer.data.response.GeneralResponse;
import com.prography.sw.aloocustomer.network.AuthApiResponse;
import com.prography.sw.aloocustomer.network.ServiceGenerator;

public class GeneralDataRepo {
    private static volatile GeneralDataRepo instance;

    public static GeneralDataRepo getInstance() {
        if (instance == null) {

            instance = new GeneralDataRepo();
        }
        return instance;
    }

    public LiveData<AuthApiResponse<GeneralResponse>> cities(String acceptLanguage) {
        return ServiceGenerator.getMApi().cities(acceptLanguage);
    }

    public LiveData<AuthApiResponse<GeneralResponse>> banks(String acceptLanguage) {
        return ServiceGenerator.getMApi().banks(acceptLanguage);
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

    public LiveData<AuthApiResponse<GeneralResponse>> services(String acceptLanguage) {
        return ServiceGenerator.getMApi().services(acceptLanguage);
    }

    public LiveData<AuthApiResponse<GeneralResponse>> getRestaurantTypes(String acceptLanguage) {
        return ServiceGenerator.getMApi().getRestaurantTypes(acceptLanguage);
    }

    public LiveData<AuthApiResponse<GeneralResponse>> getKitchenTypes(String acceptLanguage) {
        return ServiceGenerator.getMApi().getKitchenTypes(acceptLanguage);
    }

    public LiveData<AuthApiResponse<GeneralResponse>> clientService(String type, String question, String acceptLanguage) {
        return ServiceGenerator.getMApi().clientService(type, question, acceptLanguage);
    }


}
