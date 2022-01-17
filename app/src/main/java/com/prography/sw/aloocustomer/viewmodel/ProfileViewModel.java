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
import com.prography.sw.aloocustomer.repos.LoginRepo;
import com.prography.sw.aloocustomer.repos.ProfileRepo;
import com.prography.sw.aloocustomer.util.SharedPreferencesHelper;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class ProfileViewModel extends AndroidViewModel {

    private final ProfileRepo profileRepo;
    private MediatorLiveData<AuthResource<GeneralResponse>> getProfileResult = new MediatorLiveData<>();
    private MediatorLiveData<AuthResource<GeneralResponse>> updateProfileResult = new MediatorLiveData<>();
    private MediatorLiveData<AuthResource<GeneralResponse>> getWalletResult = new MediatorLiveData<>();
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

    public LiveData<AuthResource<GeneralResponse>> updateProfile(RequestBody name, RequestBody password, RequestBody passwordConfirmation, RequestBody city, RequestBody gender, RequestBody phone, RequestBody email, MultipartBody.Part img) {
        LiveData<AuthApiResponse<GeneralResponse>> response = profileRepo.updateProfile(name, password, passwordConfirmation, city, gender, phone, email, img, SharedPreferencesHelper.getUserToken(getApplication()), SharedPreferencesHelper.getAppLanguage(getApplication()));
        updateProfileResult.setValue(AuthResource.loading());
        updateProfileResult.addSource(response, it -> {
            updateProfileResult.removeSource(response);
            if (it instanceof AuthApiResponse.AuthApiSuccessResponse) {
                updateProfileResult.setValue(AuthResource.authenticated((GeneralResponse) ((AuthApiResponse.AuthApiSuccessResponse) it).getBody()));
            } else {
                if (((AuthApiResponse.AuthApiErrorResponse) it).getBody() instanceof GeneralResponse) {
                    updateProfileResult.setValue(AuthResource.error(((AuthApiResponse.AuthApiErrorResponse) it).getErrorMessage(),
                            (GeneralResponse) ((AuthApiResponse.AuthApiErrorResponse) it).getBody(),
                            ((AuthApiResponse.AuthApiErrorResponse) it).getErrorCode()));
                } else {
                    updateProfileResult.setValue(AuthResource.error(((AuthApiResponse.AuthApiErrorResponse) it).getErrorMessage(),
                            null, ((AuthApiResponse.AuthApiErrorResponse) it).getErrorCode()));
                }
            }
        });
        return updateProfileResult;
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

    public LiveData<AuthResource<GeneralResponse>> setFirebaseToken(String fcmToken) {
        LiveData<AuthApiResponse<GeneralResponse>> response = profileRepo.setFirebaseToken(fcmToken, SharedPreferencesHelper.getUserToken(getApplication()), SharedPreferencesHelper.getAppLanguage(getApplication()));
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
