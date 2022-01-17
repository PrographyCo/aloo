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
import com.prography.sw.aloocustomer.repos.AddressesRepo;
import com.prography.sw.aloocustomer.util.SharedPreferencesHelper;

public class AddressesViewModel extends AndroidViewModel {

    private final AddressesRepo addressesRepo;
    private MediatorLiveData<AuthResource<GeneralResponse>> getAddressesResult = new MediatorLiveData<>();
    private MediatorLiveData<AuthResource<GeneralResponse>> addAddressResult = new MediatorLiveData<>();
    private MediatorLiveData<AuthResource<GeneralResponse>> deleteAddressResult = new MediatorLiveData<>();

    public AddressesViewModel(@NonNull Application application) {
        super(application);
        addressesRepo = AddressesRepo.getInstance();
    }

    public LiveData<AuthResource<GeneralResponse>> getAddresses() {
        LiveData<AuthApiResponse<GeneralResponse>> response = addressesRepo.getAddresses(SharedPreferencesHelper.getUserToken(getApplication()), SharedPreferencesHelper.getAppLanguage(getApplication()));
        getAddressesResult.setValue(AuthResource.loading());
        getAddressesResult.addSource(response, it -> {
            if (it instanceof AuthApiResponse.AuthApiSuccessResponse) {
                getAddressesResult.setValue(AuthResource.authenticated((GeneralResponse) ((AuthApiResponse.AuthApiSuccessResponse) it).getBody()));
            } else {
                if (((AuthApiResponse.AuthApiErrorResponse) it).getBody() instanceof GeneralResponse) {
                    getAddressesResult.setValue(AuthResource.error(((AuthApiResponse.AuthApiErrorResponse) it).getErrorMessage(),
                            (GeneralResponse) ((AuthApiResponse.AuthApiErrorResponse) it).getBody(),
                            ((AuthApiResponse.AuthApiErrorResponse) it).getErrorCode()));
                } else {
                    getAddressesResult.setValue(AuthResource.error(((AuthApiResponse.AuthApiErrorResponse) it).getErrorMessage(),
                            null, ((AuthApiResponse.AuthApiErrorResponse) it).getErrorCode()));
                }
            }
        });
        return getAddressesResult;
    }

    public void removeAddressesObservers(LifecycleOwner lifecycleOwner) {
        getAddressesResult.removeObservers(lifecycleOwner);
    }

    public LiveData<AuthResource<GeneralResponse>> addAddress(String name, String locationName, String lon, String lat) {
        LiveData<AuthApiResponse<GeneralResponse>> response = addressesRepo.addAddress(name, locationName, lon, lat, SharedPreferencesHelper.getUserToken(getApplication()), SharedPreferencesHelper.getAppLanguage(getApplication()));
        addAddressResult.setValue(AuthResource.loading());
        addAddressResult.addSource(response, it -> {
            addAddressResult.removeSource(response);
            if (it instanceof AuthApiResponse.AuthApiSuccessResponse) {
                addAddressResult.setValue(AuthResource.authenticated((GeneralResponse) ((AuthApiResponse.AuthApiSuccessResponse) it).getBody()));
            } else {
                if (((AuthApiResponse.AuthApiErrorResponse) it).getBody() instanceof GeneralResponse) {
                    addAddressResult.setValue(AuthResource.error(((AuthApiResponse.AuthApiErrorResponse) it).getErrorMessage(),
                            (GeneralResponse) ((AuthApiResponse.AuthApiErrorResponse) it).getBody(),
                            ((AuthApiResponse.AuthApiErrorResponse) it).getErrorCode()));
                } else {
                    addAddressResult.setValue(AuthResource.error(((AuthApiResponse.AuthApiErrorResponse) it).getErrorMessage(),
                            null, ((AuthApiResponse.AuthApiErrorResponse) it).getErrorCode()));
                }
            }
        });
        return addAddressResult;
    }

    public LiveData<AuthResource<GeneralResponse>> deleteAddress(int id) {
        LiveData<AuthApiResponse<GeneralResponse>> response = addressesRepo.deleteAddress(id, SharedPreferencesHelper.getUserToken(getApplication()), SharedPreferencesHelper.getAppLanguage(getApplication()));
        deleteAddressResult.setValue(AuthResource.loading());
        deleteAddressResult.addSource(response, it -> {
            deleteAddressResult.removeSource(response);
            if (it instanceof AuthApiResponse.AuthApiSuccessResponse) {
                deleteAddressResult.setValue(AuthResource.authenticated((GeneralResponse) ((AuthApiResponse.AuthApiSuccessResponse) it).getBody()));
            } else {
                if (((AuthApiResponse.AuthApiErrorResponse) it).getBody() instanceof GeneralResponse) {
                    deleteAddressResult.setValue(AuthResource.error(((AuthApiResponse.AuthApiErrorResponse) it).getErrorMessage(),
                            (GeneralResponse) ((AuthApiResponse.AuthApiErrorResponse) it).getBody(),
                            ((AuthApiResponse.AuthApiErrorResponse) it).getErrorCode()));
                } else {
                    deleteAddressResult.setValue(AuthResource.error(((AuthApiResponse.AuthApiErrorResponse) it).getErrorMessage(),
                            null, ((AuthApiResponse.AuthApiErrorResponse) it).getErrorCode()));
                }
            }
        });
        return deleteAddressResult;
    }

}
