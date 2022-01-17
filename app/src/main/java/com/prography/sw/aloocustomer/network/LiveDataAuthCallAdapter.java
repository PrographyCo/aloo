package com.prography.sw.aloocustomer.network;

import android.util.Log;

import androidx.lifecycle.LiveData;


import com.prography.sw.aloocustomer.util.AApp;
import com.prography.sw.aloocustomer.util.AppUtils;

import java.lang.reflect.Type;

import retrofit2.Call;
import retrofit2.CallAdapter;
import retrofit2.Callback;
import retrofit2.Response;

public class LiveDataAuthCallAdapter<R> implements CallAdapter<R, LiveData<AuthApiResponse<R>>> {

    private final Type responseType;

    public LiveDataAuthCallAdapter(Type responseType) {
        this.responseType = responseType;
    }

    @Override
    public Type responseType() {
        return responseType;
    }

    private static final String TAG = "LiveDataAuthCallAdapter";

    @Override
    public LiveData<AuthApiResponse<R>> adapt(final Call<R> call) {
        return new LiveData<AuthApiResponse<R>>() {
            @Override
            protected void onActive() {
                super.onActive();
                final AuthApiResponse apiResponse = new AuthApiResponse();
                call.clone().enqueue(new Callback<R>() {
                    @Override
                    public void onResponse(Call<R> call, Response<R> response) {

                        postValue(apiResponse.create(response));
                    }

                    @Override
                    public void onFailure(Call<R> call, Throwable t) {
                        Log.d(TAG, "onFailure: sw " + t.getMessage());
                        if (AppUtils.isConnectedToInternet(AApp.getContext()))
                            postValue(apiResponse.create("Something Went Wrong"));
                        else
                            postValue(apiResponse.create("No Internet Connection"));
                    }
                });
            }
        };
    }
}