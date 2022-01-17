package com.prography.sw.aloodelevery.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import com.prography.sw.aloodelevery.data.response.GeneralResponse;
import com.prography.sw.aloodelevery.network.AuthApiResponse;
import com.prography.sw.aloodelevery.network.AuthResource;
import com.prography.sw.aloodelevery.repos.ProfileRepo;
import com.prography.sw.aloodelevery.util.SharedPreferencesHelper;

import okhttp3.MultipartBody;

public class ProfileViewModel extends AndroidViewModel {

    private final ProfileRepo profileRepo;
    private MediatorLiveData<AuthResource<GeneralResponse>> getProfileResult = new MediatorLiveData<>();
    private MediatorLiveData<AuthResource<GeneralResponse>> getWalletResult = new MediatorLiveData<>();
    private MediatorLiveData<AuthResource<GeneralResponse>> getMyDataResult = new MediatorLiveData<>();
    private MediatorLiveData<AuthResource<GeneralResponse>> firebaseTokenResult = new MediatorLiveData<>();
    private MediatorLiveData<AuthResource<GeneralResponse>> notificationResult = new MediatorLiveData<>();


    private int pageNumber = 1;
    private boolean isFetchingNextPage, isFetchingExhausted;


    public ProfileViewModel(@NonNull Application application) {
        super(application);
        profileRepo = ProfileRepo.getInstance();
    }

    public LiveData<AuthResource<GeneralResponse>> getProfile() {
        LiveData<AuthApiResponse<GeneralResponse>> response = profileRepo.getProfile(SharedPreferencesHelper.getUserToken(getApplication()), SharedPreferencesHelper.getAppLanguage(getApplication()));
        getProfileResult.setValue(AuthResource.loading());
        getProfileResult.addSource(response, it -> {
            if (it instanceof AuthApiResponse.AuthApiSuccessResponse) {
                getProfileResult.setValue(AuthResource.authenticated((GeneralResponse) ((AuthApiResponse.AuthApiSuccessResponse) it).getBody()));
            } else {
                if (((AuthApiResponse.AuthApiErrorResponse) it).getBody() instanceof GeneralResponse) {
                    getProfileResult.setValue(AuthResource.error(((AuthApiResponse.AuthApiErrorResponse) it).getErrorMessage(),
                            (GeneralResponse) ((AuthApiResponse.AuthApiErrorResponse) it).getBody(),
                            ((AuthApiResponse.AuthApiErrorResponse) it).getErrorCode()));
                } else {
                    getProfileResult.setValue(AuthResource.error(((AuthApiResponse.AuthApiErrorResponse) it).getErrorMessage(),
                            null, ((AuthApiResponse.AuthApiErrorResponse) it).getErrorCode()));
                }
            }
        });
        return getProfileResult;
    }

    public void removeProfileObservers(LifecycleOwner lifecycleOwner) {
        getProfileResult.removeObservers(lifecycleOwner);
    }

    public LiveData<AuthResource<GeneralResponse>> getWallet() {
        LiveData<AuthApiResponse<GeneralResponse>> response = profileRepo.getWallet(SharedPreferencesHelper.getUserToken(getApplication()), SharedPreferencesHelper.getAppLanguage(getApplication()));
        getWalletResult.setValue(AuthResource.loading());
        getWalletResult.addSource(response, it -> {
            if (it instanceof AuthApiResponse.AuthApiSuccessResponse) {
                getWalletResult.setValue(AuthResource.authenticated((GeneralResponse) ((AuthApiResponse.AuthApiSuccessResponse) it).getBody()));
            } else {
                if (((AuthApiResponse.AuthApiErrorResponse) it).getBody() instanceof GeneralResponse) {
                    getWalletResult.setValue(AuthResource.error(((AuthApiResponse.AuthApiErrorResponse) it).getErrorMessage(),
                            (GeneralResponse) ((AuthApiResponse.AuthApiErrorResponse) it).getBody(),
                            ((AuthApiResponse.AuthApiErrorResponse) it).getErrorCode()));
                } else {
                    getWalletResult.setValue(AuthResource.error(((AuthApiResponse.AuthApiErrorResponse) it).getErrorMessage(),
                            null, ((AuthApiResponse.AuthApiErrorResponse) it).getErrorCode()));
                }
            }
        });
        return getWalletResult;
    }

    public void removeWalletObservers(LifecycleOwner lifecycleOwner) {
        getWalletResult.removeObservers(lifecycleOwner);
    }

    public LiveData<AuthResource<GeneralResponse>> getMyData() {
        LiveData<AuthApiResponse<GeneralResponse>> myData = profileRepo.getMyData(SharedPreferencesHelper.getUserToken(getApplication()), SharedPreferencesHelper.getAppLanguage(getApplication()));
        getMyDataResult.setValue(AuthResource.loading());
        getMyDataResult.addSource(myData, it -> {
            if (it instanceof AuthApiResponse.AuthApiSuccessResponse) {
                getMyDataResult.setValue(AuthResource.authenticated((GeneralResponse) ((AuthApiResponse.AuthApiSuccessResponse) it).getBody()));
            } else {
                if (((AuthApiResponse.AuthApiErrorResponse) it).getBody() instanceof GeneralResponse) {
                    getWalletResult.setValue(AuthResource.error(((AuthApiResponse.AuthApiErrorResponse) it).getErrorMessage(),
                            (GeneralResponse) ((AuthApiResponse.AuthApiErrorResponse) it).getBody(),
                            ((AuthApiResponse.AuthApiErrorResponse) it).getErrorCode()));
                } else {
                    getMyDataResult.setValue(AuthResource.error(((AuthApiResponse.AuthApiErrorResponse) it).getErrorMessage(),
                            null, ((AuthApiResponse.AuthApiErrorResponse) it).getErrorCode()));
                }
            }
        });
        return getMyDataResult;
    }
    public void removeMyDataObservers(LifecycleOwner lifecycleOwner) {
        getMyDataResult.removeObservers(lifecycleOwner);
    }




    public LiveData<AuthResource<GeneralResponse>> setFirebaseToken(String fcmToken) {
        LiveData<AuthApiResponse<GeneralResponse>> response = profileRepo.setFirebaseToken(fcmToken,SharedPreferencesHelper.getUserToken(getApplication()), SharedPreferencesHelper.getAppLanguage(getApplication()));
        firebaseTokenResult.setValue(AuthResource.loading());
        firebaseTokenResult.addSource(response, it -> {
            if (it instanceof AuthApiResponse.AuthApiSuccessResponse) {
                firebaseTokenResult.setValue(AuthResource.authenticated((GeneralResponse) ((AuthApiResponse.AuthApiSuccessResponse) it).getBody()));
            } else {
                if (((AuthApiResponse.AuthApiErrorResponse) it).getBody() instanceof GeneralResponse) {
                    firebaseTokenResult.setValue(AuthResource.error(((AuthApiResponse.AuthApiErrorResponse) it).getErrorMessage(),
                            (GeneralResponse) ((AuthApiResponse.AuthApiErrorResponse) it).getBody(),
                            ((AuthApiResponse.AuthApiErrorResponse) it).getErrorCode()));
                } else {
                    firebaseTokenResult.setValue(AuthResource.error(((AuthApiResponse.AuthApiErrorResponse) it).getErrorMessage(),
                            null, ((AuthApiResponse.AuthApiErrorResponse) it).getErrorCode()));
                }
            }
        });
        return firebaseTokenResult;
    }

    public void removeFirebaseTokenObservers(LifecycleOwner lifecycleOwner) {
        firebaseTokenResult.removeObservers(lifecycleOwner);
    }


    public LiveData<AuthResource<GeneralResponse>> getNotification() {
        LiveData<AuthApiResponse<GeneralResponse>> response = profileRepo.getNotification(pageNumber, SharedPreferencesHelper.getUserToken(getApplication()), SharedPreferencesHelper.getAppLanguage(getApplication()));
        notificationResult.setValue(AuthResource.loading());
        notificationResult.addSource(response, it -> {
            if (it instanceof AuthApiResponse.AuthApiSuccessResponse) {
                notificationResult.setValue(AuthResource.authenticated((GeneralResponse) ((AuthApiResponse.AuthApiSuccessResponse) it).getBody()));
            } else {
                if (((AuthApiResponse.AuthApiErrorResponse) it).getBody() instanceof GeneralResponse) {
                    notificationResult.setValue(AuthResource.error(((AuthApiResponse.AuthApiErrorResponse) it).getErrorMessage(),
                            (GeneralResponse) ((AuthApiResponse.AuthApiErrorResponse) it).getBody(),
                            ((AuthApiResponse.AuthApiErrorResponse) it).getErrorCode()));
                } else {
                    notificationResult.setValue(AuthResource.error(((AuthApiResponse.AuthApiErrorResponse) it).getErrorMessage(),
                            null, ((AuthApiResponse.AuthApiErrorResponse) it).getErrorCode()));
                }
            }
        });
        return notificationResult;
    }

    public void removeNotificationObservers(LifecycleOwner lifecycleOwner) {
        notificationResult.removeObservers(lifecycleOwner);
    }

    public void setFetchingNextPage(boolean fetchingNextPage) {
        isFetchingNextPage = fetchingNextPage;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public boolean isFetchingNextPage() {
        return isFetchingNextPage;
    }

    public boolean isFetchingExhausted() {
        return isFetchingExhausted;
    }

    public void setFetchingExhausted(boolean fetchingExhausted) {
        isFetchingExhausted = fetchingExhausted;
    }

    public void getNextPage() {
        setFetchingNextPage(true);
        pageNumber++;
        getNotification();
    }


}
