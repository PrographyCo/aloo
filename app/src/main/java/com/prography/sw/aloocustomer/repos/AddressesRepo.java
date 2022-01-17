package com.prography.sw.aloocustomer.repos;

import androidx.lifecycle.LiveData;

import com.prography.sw.aloocustomer.data.response.GeneralResponse;
import com.prography.sw.aloocustomer.network.AuthApiResponse;
import com.prography.sw.aloocustomer.network.ServiceGenerator;

import okhttp3.MultipartBody;

public class AddressesRepo {
    private static volatile AddressesRepo instance;


    public static AddressesRepo getInstance() {
        if (instance == null) {
            instance = new AddressesRepo();
        }
        return instance;
    }

    public LiveData<AuthApiResponse<GeneralResponse>> getAddresses(String token, String acceptLanguage) {
        return ServiceGenerator.getMApi().getAddresses(token, acceptLanguage);
    }

    public LiveData<AuthApiResponse<GeneralResponse>> addAddress(String name, String locationName, String lon, String lat, String token, String acceptLanguage) {
        return ServiceGenerator.getMApi().addAddress(name, locationName, lon, lat, token, acceptLanguage);
    }

    public LiveData<AuthApiResponse<GeneralResponse>> deleteAddress(int id, String token, String acceptLanguage) {
        return ServiceGenerator.getMApi().deleteAddress(id, token, acceptLanguage);
    }
}
