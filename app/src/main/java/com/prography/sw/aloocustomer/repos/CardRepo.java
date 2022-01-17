package com.prography.sw.aloocustomer.repos;

import androidx.lifecycle.LiveData;

import com.prography.sw.aloocustomer.data.response.GeneralResponse;
import com.prography.sw.aloocustomer.network.AuthApiResponse;
import com.prography.sw.aloocustomer.network.ServiceGenerator;

import java.util.List;

import retrofit2.http.Field;

public class CardRepo {
    private static volatile CardRepo instance;

    public static CardRepo getInstance() {
        if (instance == null) {
            instance = new CardRepo();
        }
        return instance;
    }


    public LiveData<AuthApiResponse<GeneralResponse>> addToCard(int item_id, int amount, List<String> with, List<String> without,
                                                                String size,
                                                                List<Integer> drinks,
                                                                List<Integer> extras, String token, String acceptLanguage) {
        return ServiceGenerator.getMApi().addToCard(item_id, amount, with, without, size, drinks, extras, token, acceptLanguage);
    }

    public LiveData<AuthApiResponse<GeneralResponse>> addToCardSuperPhrmacy(int item_id, int amount, String token, String acceptLanguage) {
        return ServiceGenerator.getMApi().addToCardSuperPhrmacy(item_id, amount, token, acceptLanguage);
    }

    public LiveData<AuthApiResponse<GeneralResponse>> getAllCardItem(int supported_vendor_id, String token, String acceptLanguage) {
        return ServiceGenerator.getMApi().getAllCardItem(supported_vendor_id, token, acceptLanguage);
    }

    public LiveData<AuthApiResponse<GeneralResponse>> deleteCartItem(int item_id, String token, String acceptLanguage) {
        return ServiceGenerator.getMApi().deleteCartItem(item_id, token, acceptLanguage);
    }

    public LiveData<AuthApiResponse<GeneralResponse>> getOneItem(int item_id, String token, String acceptLanguage) {
        return ServiceGenerator.getMApi().getOneItem(item_id, token, acceptLanguage);
    }

    public LiveData<AuthApiResponse<GeneralResponse>> SaveEdit(int item_id,int amount, List<String> with, List<String> without,
                                                               String size,
                                                               List<Integer> drinks,
                                                               List<Integer> extras, String token, String acceptLanguage) {
        return ServiceGenerator.getMApi().SaveEdit(item_id,amount, with, without, size, drinks, extras, token, acceptLanguage);
    }

    public LiveData<AuthApiResponse<GeneralResponse>> SaveEdit(int item_id, int amount, String token, String acceptLanguage) {
        return ServiceGenerator.getMApi().SaveEdit(item_id, amount, token, acceptLanguage);
    }

    public LiveData<AuthApiResponse<GeneralResponse>> submitCartOrder(int supported_vendor_id, String token, String acceptLanguage) {
        return ServiceGenerator.getMApi().submitCartOrder(supported_vendor_id, token, acceptLanguage);
    }

}
