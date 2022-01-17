package com.prography.sw.aloocustomer.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import com.prography.sw.aloocustomer.data.response.GeneralResponse;
import com.prography.sw.aloocustomer.network.AuthApiResponse;
import com.prography.sw.aloocustomer.network.AuthResource;
import com.prography.sw.aloocustomer.repos.VerifyRepo;
import com.prography.sw.aloocustomer.util.SharedPreferencesHelper;

public class VerifyViewModel extends AndroidViewModel {
    private VerifyRepo verifyRepo;
    private MediatorLiveData<AuthResource<GeneralResponse>> verifyResult = new MediatorLiveData<>();
    private MediatorLiveData<AuthResource<GeneralResponse>> resendCodeResult = new MediatorLiveData<>();

    public VerifyViewModel(@NonNull Application application) {
        super(application);
        verifyRepo = VerifyRepo.getInstance();
    }

    public LiveData<AuthResource<GeneralResponse>> verify(String phone, String code_verify) {
        LiveData<AuthApiResponse<GeneralResponse>> verify = verifyRepo.verify(phone, code_verify, SharedPreferencesHelper.getAppLanguage(getApplication()));
        verifyResult.setValue(AuthResource.loading());
        verifyResult.addSource(verify, it -> {
            verifyResult.removeSource(verify);
            if (it instanceof AuthApiResponse.AuthApiSuccessResponse) {
                verifyResult.setValue(AuthResource.authenticated((GeneralResponse) ((AuthApiResponse.AuthApiSuccessResponse) it).getBody()));
            } else {
                if (((AuthApiResponse.AuthApiErrorResponse) it).getBody() instanceof GeneralResponse) {
                    verifyResult.setValue(AuthResource.error(((AuthApiResponse.AuthApiErrorResponse) it).getErrorMessage(),
                            (GeneralResponse) ((AuthApiResponse.AuthApiErrorResponse) it).getBody(),
                            ((AuthApiResponse.AuthApiErrorResponse) it).getErrorCode()));
                } else {
                    verifyResult.setValue(AuthResource.error(((AuthApiResponse.AuthApiErrorResponse) it).getErrorMessage(),
                            null, ((AuthApiResponse.AuthApiErrorResponse) it).getErrorCode()));
                }
            }
        });
        return verifyResult;
    }

    public LiveData<AuthResource<GeneralResponse>> resendCode(String phone) {
        LiveData<AuthApiResponse<GeneralResponse>> resendCode = verifyRepo.resendCode(phone, SharedPreferencesHelper.getAppLanguage(getApplication()));
        resendCodeResult.setValue(AuthResource.loading());
        resendCodeResult.addSource(resendCode, it -> {
            resendCodeResult.removeSource(resendCode);
            if (it instanceof AuthApiResponse.AuthApiSuccessResponse) {
                resendCodeResult.setValue(AuthResource.authenticated((GeneralResponse) ((AuthApiResponse.AuthApiSuccessResponse) it).getBody()));
            } else {
                if (((AuthApiResponse.AuthApiErrorResponse) it).getBody() instanceof GeneralResponse) {
                    resendCodeResult.setValue(AuthResource.error(((AuthApiResponse.AuthApiErrorResponse) it).getErrorMessage(),
                            (GeneralResponse) ((AuthApiResponse.AuthApiErrorResponse) it).getBody(),
                            ((AuthApiResponse.AuthApiErrorResponse) it).getErrorCode()));
                } else {
                    resendCodeResult.setValue(AuthResource.error(((AuthApiResponse.AuthApiErrorResponse) it).getErrorMessage(),
                            null, ((AuthApiResponse.AuthApiErrorResponse) it).getErrorCode()));
                }
            }
        });
        return resendCodeResult;
    }
}
