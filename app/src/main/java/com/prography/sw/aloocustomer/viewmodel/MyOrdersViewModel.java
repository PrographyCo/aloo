package com.prography.sw.aloocustomer.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import com.prography.sw.aloocustomer.data.response.GeneralResponse;
import com.prography.sw.aloocustomer.network.AuthApiResponse;
import com.prography.sw.aloocustomer.network.AuthResource;
import com.prography.sw.aloocustomer.repos.MyOrdersRepo;
import com.prography.sw.aloocustomer.util.SharedPreferencesHelper;

public class MyOrdersViewModel extends AndroidViewModel {

    private final MyOrdersRepo myOrdersRepo;
    private MediatorLiveData<AuthResource<GeneralResponse>> getMyOrdersResult = new MediatorLiveData<>();
    private MediatorLiveData<AuthResource<GeneralResponse>> getOrderDetailsResult = new MediatorLiveData<>();
    private MediatorLiveData<AuthResource<GeneralResponse>> rateResult = new MediatorLiveData<>();

    public MyOrdersViewModel(@NonNull Application application) {
        super(application);
        myOrdersRepo = MyOrdersRepo.getInstance();
    }

    public LiveData<AuthResource<GeneralResponse>> getMYOrders(int id) {
        LiveData<AuthApiResponse<GeneralResponse>> response = myOrdersRepo.getMYOrders(id, SharedPreferencesHelper.getUserToken(getApplication()), SharedPreferencesHelper.getAppLanguage(getApplication()));
        getMyOrdersResult.setValue(AuthResource.loading());
        getMyOrdersResult.addSource(response, it -> {
            if (it instanceof AuthApiResponse.AuthApiSuccessResponse) {
                getMyOrdersResult.setValue(AuthResource.authenticated((GeneralResponse) ((AuthApiResponse.AuthApiSuccessResponse) it).getBody()));
            } else {
                if (((AuthApiResponse.AuthApiErrorResponse) it).getBody() instanceof GeneralResponse) {
                    getMyOrdersResult.setValue(AuthResource.error(((AuthApiResponse.AuthApiErrorResponse) it).getErrorMessage(),
                            (GeneralResponse) ((AuthApiResponse.AuthApiErrorResponse) it).getBody(),
                            ((AuthApiResponse.AuthApiErrorResponse) it).getErrorCode()));
                } else {
                    getMyOrdersResult.setValue(AuthResource.error(((AuthApiResponse.AuthApiErrorResponse) it).getErrorMessage(),
                            null, ((AuthApiResponse.AuthApiErrorResponse) it).getErrorCode()));
                }
            }
        });
        return getMyOrdersResult;
    }

    public void removeMyOrdersObservers(LifecycleOwner lifecycleOwner) {
        getMyOrdersResult.removeObservers(lifecycleOwner);
    }

    public LiveData<AuthResource<GeneralResponse>> getOrderDetails(int id) {
        LiveData<AuthApiResponse<GeneralResponse>> response = myOrdersRepo.getOrderDetails(id, SharedPreferencesHelper.getUserToken(getApplication()), SharedPreferencesHelper.getAppLanguage(getApplication()));
        getOrderDetailsResult.setValue(AuthResource.loading());
        getOrderDetailsResult.addSource(response, it -> {
            if (it instanceof AuthApiResponse.AuthApiSuccessResponse) {
                getOrderDetailsResult.setValue(AuthResource.authenticated((GeneralResponse) ((AuthApiResponse.AuthApiSuccessResponse) it).getBody()));
            } else {
                if (((AuthApiResponse.AuthApiErrorResponse) it).getBody() instanceof GeneralResponse) {
                    getOrderDetailsResult.setValue(AuthResource.error(((AuthApiResponse.AuthApiErrorResponse) it).getErrorMessage(),
                            (GeneralResponse) ((AuthApiResponse.AuthApiErrorResponse) it).getBody(),
                            ((AuthApiResponse.AuthApiErrorResponse) it).getErrorCode()));
                } else {
                    getOrderDetailsResult.setValue(AuthResource.error(((AuthApiResponse.AuthApiErrorResponse) it).getErrorMessage(),
                            null, ((AuthApiResponse.AuthApiErrorResponse) it).getErrorCode()));
                }
            }
        });
        return getOrderDetailsResult;
    }

    public void removeOrderDetailsObservers(LifecycleOwner lifecycleOwner) {
        getOrderDetailsResult.removeObservers(lifecycleOwner);
    }

    public LiveData<AuthResource<GeneralResponse>> rate(int orderId, String rate, String message) {
        LiveData<AuthApiResponse<GeneralResponse>> result = myOrdersRepo.rate(orderId, rate, message, SharedPreferencesHelper.getUserToken(getApplication()), SharedPreferencesHelper.getAppLanguage(getApplication()));
        rateResult.setValue(AuthResource.loading());
        rateResult.addSource(result, it -> {
            rateResult.removeSource(result);
            if (it instanceof AuthApiResponse.AuthApiSuccessResponse) {
                rateResult.setValue(AuthResource.authenticated((GeneralResponse) ((AuthApiResponse.AuthApiSuccessResponse) it).getBody()));
            } else {
                if (((AuthApiResponse.AuthApiErrorResponse) it).getBody() instanceof GeneralResponse) {
                    rateResult.setValue(AuthResource.error(((AuthApiResponse.AuthApiErrorResponse) it).getErrorMessage(),
                            (GeneralResponse) ((AuthApiResponse.AuthApiErrorResponse) it).getBody(),
                            ((AuthApiResponse.AuthApiErrorResponse) it).getErrorCode()));
                } else {
                    rateResult.setValue(AuthResource.error(((AuthApiResponse.AuthApiErrorResponse) it).getErrorMessage(),
                            null, ((AuthApiResponse.AuthApiErrorResponse) it).getErrorCode()));
                }
            }
        });
        return rateResult;
    }

}
