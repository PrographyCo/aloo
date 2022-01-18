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
import com.prography.sw.alooproduct.repos.ProfileRepo;
import com.prography.sw.alooproduct.util.SharedPreferencesHelper;

public class ProfileViewModel extends AndroidViewModel {

    private final ProfileRepo profileRepo;
    private MediatorLiveData<AuthResource<GeneralResponse>> changeAvailableStatusResult = new MediatorLiveData<>();
    private MediatorLiveData<AuthResource<GeneralResponse>> availableStatusResult = new MediatorLiveData<>();
    private MediatorLiveData<AuthResource<GeneralResponse>> categoriesResult = new MediatorLiveData<>();
    private MediatorLiveData<AuthResource<GeneralResponse>> itemsResult = new MediatorLiveData<>();
    private MediatorLiveData<AuthResource<GeneralResponse>> profileResult = new MediatorLiveData<>();
    private MediatorLiveData<AuthResource<GeneralResponse>> myDataResult = new MediatorLiveData<>();
    private MediatorLiveData<AuthResource<GeneralResponse>> getWalletResult = new MediatorLiveData<>();
    private MediatorLiveData<AuthResource<GeneralResponse>> firebaseTokenResult = new MediatorLiveData<>();
    private MediatorLiveData<AuthResource<GeneralResponse>> notificationResult = new MediatorLiveData<>();

    private int pageNumber = 1;
    private boolean isFetchingNextPage, isFetchingExhausted;


    public ProfileViewModel(@NonNull Application application) {
        super(application);
        profileRepo = ProfileRepo.getInstance();
    }

    public LiveData<AuthResource<GeneralResponse>> changeAvailableStatus() {
        LiveData<AuthApiResponse<GeneralResponse>> response = profileRepo.changeAvailableStatus(SharedPreferencesHelper.getUserToken(getApplication()), SharedPreferencesHelper.getAppLanguage(getApplication()));
        changeAvailableStatusResult.setValue(AuthResource.loading());
        changeAvailableStatusResult.addSource(response, it -> {
            changeAvailableStatusResult.removeSource(response);
            if (it instanceof AuthApiResponse.AuthApiSuccessResponse) {
                changeAvailableStatusResult.setValue(AuthResource.authenticated((GeneralResponse) ((AuthApiResponse.AuthApiSuccessResponse) it).getBody()));
            } else {
                if (((AuthApiResponse.AuthApiErrorResponse) it).getBody() instanceof GeneralResponse) {
                    changeAvailableStatusResult.setValue(AuthResource.error(((AuthApiResponse.AuthApiErrorResponse) it).getErrorMessage(),
                            (GeneralResponse) ((AuthApiResponse.AuthApiErrorResponse) it).getBody(),
                            ((AuthApiResponse.AuthApiErrorResponse) it).getErrorCode()));
                } else {
                    changeAvailableStatusResult.setValue(AuthResource.error(((AuthApiResponse.AuthApiErrorResponse) it).getErrorMessage(),
                            null, ((AuthApiResponse.AuthApiErrorResponse) it).getErrorCode()));
                }
            }
        });
        return changeAvailableStatusResult;
    }


    public void removechangeAvailableStatusResultObservers(LifecycleOwner lifecycleOwner) {
        changeAvailableStatusResult.removeObservers(lifecycleOwner);
    }


    public LiveData<AuthResource<GeneralResponse>> getAvailableStatus() {
        LiveData<AuthApiResponse<GeneralResponse>> response = profileRepo.getAvailableStatus(SharedPreferencesHelper.getUserToken(getApplication()), SharedPreferencesHelper.getAppLanguage(getApplication()));
        availableStatusResult.setValue(AuthResource.loading());
        availableStatusResult.addSource(response, it -> {
            if (it instanceof AuthApiResponse.AuthApiSuccessResponse) {
                availableStatusResult.setValue(AuthResource.authenticated((GeneralResponse) ((AuthApiResponse.AuthApiSuccessResponse) it).getBody()));
            } else {
                if (((AuthApiResponse.AuthApiErrorResponse) it).getBody() instanceof GeneralResponse) {
                    availableStatusResult.setValue(AuthResource.error(((AuthApiResponse.AuthApiErrorResponse) it).getErrorMessage(),
                            (GeneralResponse) ((AuthApiResponse.AuthApiErrorResponse) it).getBody(),
                            ((AuthApiResponse.AuthApiErrorResponse) it).getErrorCode()));
                } else {
                    availableStatusResult.setValue(AuthResource.error(((AuthApiResponse.AuthApiErrorResponse) it).getErrorMessage(),
                            null, ((AuthApiResponse.AuthApiErrorResponse) it).getErrorCode()));
                }
            }
        });
        return availableStatusResult;
    }

    public void removeAvailableStatusObservers(LifecycleOwner lifecycleOwner) {
        availableStatusResult.removeObservers(lifecycleOwner);
    }

    public LiveData<AuthResource<GeneralResponse>> getCategories() {
        LiveData<AuthApiResponse<GeneralResponse>> response = profileRepo.getCategories(SharedPreferencesHelper.getUserToken(getApplication()), SharedPreferencesHelper.getAppLanguage(getApplication()));
        categoriesResult.setValue(AuthResource.loading());
        categoriesResult.addSource(response, it -> {
            if (it instanceof AuthApiResponse.AuthApiSuccessResponse) {
                categoriesResult.setValue(AuthResource.authenticated((GeneralResponse) ((AuthApiResponse.AuthApiSuccessResponse) it).getBody()));
            } else {
                if (((AuthApiResponse.AuthApiErrorResponse) it).getBody() instanceof GeneralResponse) {
                    categoriesResult.setValue(AuthResource.error(((AuthApiResponse.AuthApiErrorResponse) it).getErrorMessage(),
                            (GeneralResponse) ((AuthApiResponse.AuthApiErrorResponse) it).getBody(),
                            ((AuthApiResponse.AuthApiErrorResponse) it).getErrorCode()));
                } else {
                    categoriesResult.setValue(AuthResource.error(((AuthApiResponse.AuthApiErrorResponse) it).getErrorMessage(),
                            null, ((AuthApiResponse.AuthApiErrorResponse) it).getErrorCode()));
                }
            }
        });
        return categoriesResult;
    }

    public void removeCategoriesObservers(LifecycleOwner lifecycleOwner) {
        categoriesResult.removeObservers(lifecycleOwner);
    }

    public LiveData<AuthResource<GeneralResponse>> getItems() {
        LiveData<AuthApiResponse<GeneralResponse>> response = profileRepo.getItems(pageNumber, SharedPreferencesHelper.getUserToken(getApplication()), SharedPreferencesHelper.getAppLanguage(getApplication()));
        itemsResult.setValue(AuthResource.loading());
        itemsResult.addSource(response, it -> {
            if (it instanceof AuthApiResponse.AuthApiSuccessResponse) {
                itemsResult.setValue(AuthResource.authenticated((GeneralResponse) ((AuthApiResponse.AuthApiSuccessResponse) it).getBody()));
            } else {
                if (((AuthApiResponse.AuthApiErrorResponse) it).getBody() instanceof GeneralResponse) {
                    itemsResult.setValue(AuthResource.error(((AuthApiResponse.AuthApiErrorResponse) it).getErrorMessage(),
                            (GeneralResponse) ((AuthApiResponse.AuthApiErrorResponse) it).getBody(),
                            ((AuthApiResponse.AuthApiErrorResponse) it).getErrorCode()));
                } else {
                    itemsResult.setValue(AuthResource.error(((AuthApiResponse.AuthApiErrorResponse) it).getErrorMessage(),
                            null, ((AuthApiResponse.AuthApiErrorResponse) it).getErrorCode()));
                }
            }
        });
        return itemsResult;
    }

    public void removeItemsObservers(LifecycleOwner lifecycleOwner) {
        itemsResult.removeObservers(lifecycleOwner);
    }

    public LiveData<AuthResource<GeneralResponse>> getProfile() {
        LiveData<AuthApiResponse<GeneralResponse>> response = profileRepo.getProfile(SharedPreferencesHelper.getUserToken(getApplication()), SharedPreferencesHelper.getAppLanguage(getApplication()));
        profileResult.setValue(AuthResource.loading());
        profileResult.addSource(response, it -> {
            if (it instanceof AuthApiResponse.AuthApiSuccessResponse) {
                profileResult.setValue(AuthResource.authenticated((GeneralResponse) ((AuthApiResponse.AuthApiSuccessResponse) it).getBody()));
            } else {
                if (((AuthApiResponse.AuthApiErrorResponse) it).getBody() instanceof GeneralResponse) {
                    profileResult.setValue(AuthResource.error(((AuthApiResponse.AuthApiErrorResponse) it).getErrorMessage(),
                            (GeneralResponse) ((AuthApiResponse.AuthApiErrorResponse) it).getBody(),
                            ((AuthApiResponse.AuthApiErrorResponse) it).getErrorCode()));
                } else {
                    profileResult.setValue(AuthResource.error(((AuthApiResponse.AuthApiErrorResponse) it).getErrorMessage(),
                            null, ((AuthApiResponse.AuthApiErrorResponse) it).getErrorCode()));
                }
            }
        });
        return profileResult;
    }

    public void removeProfileObservers(LifecycleOwner lifecycleOwner) {
        profileResult.removeObservers(lifecycleOwner);
    }


    public LiveData<AuthResource<GeneralResponse>> getMyData() {
        LiveData<AuthApiResponse<GeneralResponse>> response = profileRepo.getMyData(SharedPreferencesHelper.getUserToken(getApplication()), SharedPreferencesHelper.getAppLanguage(getApplication()));
        myDataResult.setValue(AuthResource.loading());
        myDataResult.addSource(response, it -> {
            if (it instanceof AuthApiResponse.AuthApiSuccessResponse) {
                myDataResult.setValue(AuthResource.authenticated((GeneralResponse) ((AuthApiResponse.AuthApiSuccessResponse) it).getBody()));
            } else {
                if (((AuthApiResponse.AuthApiErrorResponse) it).getBody() instanceof GeneralResponse) {
                    myDataResult.setValue(AuthResource.error(((AuthApiResponse.AuthApiErrorResponse) it).getErrorMessage(),
                            (GeneralResponse) ((AuthApiResponse.AuthApiErrorResponse) it).getBody(),
                            ((AuthApiResponse.AuthApiErrorResponse) it).getErrorCode()));
                } else {
                    myDataResult.setValue(AuthResource.error(((AuthApiResponse.AuthApiErrorResponse) it).getErrorMessage(),
                            null, ((AuthApiResponse.AuthApiErrorResponse) it).getErrorCode()));
                }
            }
        });
        return myDataResult;
    }

    public void removeMyDataObservers(LifecycleOwner lifecycleOwner) {
        myDataResult.removeObservers(lifecycleOwner);
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
        getItems();
    }

    public void getNextPageN() {
        setFetchingNextPage(true);
        pageNumber++;
        getNotification();
    }


}
