package com.prography.sw.alooproduct.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import com.prography.sw.alooproduct.data.response.GeneralResponse;
import com.prography.sw.alooproduct.network.AuthApiResponse;
import com.prography.sw.alooproduct.network.AuthResource;
import com.prography.sw.alooproduct.repos.LogoutRepo;
import com.prography.sw.alooproduct.util.SharedPreferencesHelper;


public class LogoutViewModel extends AndroidViewModel {

    private final LogoutRepo logoutRepo;
    private MediatorLiveData<AuthResource<GeneralResponse>> logoutResult = new MediatorLiveData<>();

    public LogoutViewModel(@NonNull Application application) {
        super(application);
        logoutRepo = LogoutRepo.getInstance();
    }

    public LiveData<AuthResource<GeneralResponse>> logout() {
        LiveData<AuthApiResponse<GeneralResponse>> logout = logoutRepo.logout(SharedPreferencesHelper.getUserToken(getApplication()), SharedPreferencesHelper.getAppLanguage(getApplication()));
        logoutResult.setValue(AuthResource.loading());
        logoutResult.addSource(logout, it -> {
            if (it instanceof AuthApiResponse.AuthApiSuccessResponse) {
                logoutResult.setValue(AuthResource.authenticated((GeneralResponse) ((AuthApiResponse.AuthApiSuccessResponse) it).getBody()));
            } else {
                if (((AuthApiResponse.AuthApiErrorResponse) it).getBody() instanceof GeneralResponse) {
                    logoutResult.setValue(AuthResource.error(((AuthApiResponse.AuthApiErrorResponse) it).getErrorMessage(),
                            (GeneralResponse) ((AuthApiResponse.AuthApiErrorResponse) it).getBody(),
                            ((AuthApiResponse.AuthApiErrorResponse) it).getErrorCode()));
                } else {
                    logoutResult.setValue(AuthResource.error(((AuthApiResponse.AuthApiErrorResponse) it).getErrorMessage(),
                            null, ((AuthApiResponse.AuthApiErrorResponse) it).getErrorCode()));
                }
            }
        });
        return logoutResult;
    }

    public void removeLogoutObservers(LifecycleOwner lifecycleOwner) {
        logoutResult.removeObservers(lifecycleOwner);
    }

}
