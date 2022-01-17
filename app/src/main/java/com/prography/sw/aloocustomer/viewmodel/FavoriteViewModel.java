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
import com.prography.sw.aloocustomer.repos.FavoriteRepo;
import com.prography.sw.aloocustomer.util.SharedPreferencesHelper;

public class FavoriteViewModel extends AndroidViewModel {

    private final FavoriteRepo favoriteRepo;
    private MediatorLiveData<AuthResource<GeneralResponse>> getFavoriteVendorsResult = new MediatorLiveData<>();
    private MediatorLiveData<AuthResource<GeneralResponse>> getFavoriteItemsResult = new MediatorLiveData<>();
    private MediatorLiveData<AuthResource<GeneralResponse>> addToFavoriteResult = new MediatorLiveData<>();
    private MediatorLiveData<AuthResource<GeneralResponse>> deleteFavoriteResult = new MediatorLiveData<>();

    private int pageNumber = 1;
    private boolean isFetchingNextPage, isFetchingExhausted;

    public FavoriteViewModel(@NonNull Application application) {
        super(application);
        favoriteRepo = FavoriteRepo.getInstance();
    }

    public LiveData<AuthResource<GeneralResponse>> getFavoriteVendors(int id) {
        LiveData<AuthApiResponse<GeneralResponse>> response = favoriteRepo.getFavoriteVendors(id, SharedPreferencesHelper.getUserToken(getApplication()), SharedPreferencesHelper.getAppLanguage(getApplication()));
        getFavoriteVendorsResult.setValue(AuthResource.loading());
        getFavoriteVendorsResult.addSource(response, it -> {
            getFavoriteVendorsResult.removeSource(response);
            if (it instanceof AuthApiResponse.AuthApiSuccessResponse) {
                getFavoriteVendorsResult.setValue(AuthResource.authenticated((GeneralResponse) ((AuthApiResponse.AuthApiSuccessResponse) it).getBody()));
            } else {
                if (((AuthApiResponse.AuthApiErrorResponse) it).getBody() instanceof GeneralResponse) {
                    getFavoriteVendorsResult.setValue(AuthResource.error(((AuthApiResponse.AuthApiErrorResponse) it).getErrorMessage(),
                            (GeneralResponse) ((AuthApiResponse.AuthApiErrorResponse) it).getBody(),
                            ((AuthApiResponse.AuthApiErrorResponse) it).getErrorCode()));
                } else {
                    getFavoriteVendorsResult.setValue(AuthResource.error(((AuthApiResponse.AuthApiErrorResponse) it).getErrorMessage(),
                            null, ((AuthApiResponse.AuthApiErrorResponse) it).getErrorCode()));
                }
            }
        });
        return getFavoriteVendorsResult;
    }

    public void removeFavoriteVendorsObservers(LifecycleOwner lifecycleOwner) {
        getFavoriteVendorsResult.removeObservers(lifecycleOwner);
    }

    public LiveData<AuthResource<GeneralResponse>> getFavoriteItems(int id) {
        LiveData<AuthApiResponse<GeneralResponse>> response = favoriteRepo.getFavoriteItems(id, pageNumber, SharedPreferencesHelper.getUserToken(getApplication()), SharedPreferencesHelper.getAppLanguage(getApplication()));
        getFavoriteItemsResult.setValue(AuthResource.loading());
        getFavoriteItemsResult.addSource(response, it -> {
            if (it instanceof AuthApiResponse.AuthApiSuccessResponse) {
                getFavoriteItemsResult.setValue(AuthResource.authenticated((GeneralResponse) ((AuthApiResponse.AuthApiSuccessResponse) it).getBody()));
            } else {
                if (((AuthApiResponse.AuthApiErrorResponse) it).getBody() instanceof GeneralResponse) {
                    getFavoriteItemsResult.setValue(AuthResource.error(((AuthApiResponse.AuthApiErrorResponse) it).getErrorMessage(),
                            (GeneralResponse) ((AuthApiResponse.AuthApiErrorResponse) it).getBody(),
                            ((AuthApiResponse.AuthApiErrorResponse) it).getErrorCode()));
                } else {
                    getFavoriteItemsResult.setValue(AuthResource.error(((AuthApiResponse.AuthApiErrorResponse) it).getErrorMessage(),
                            null, ((AuthApiResponse.AuthApiErrorResponse) it).getErrorCode()));
                }
            }
        });
        return getFavoriteItemsResult;
    }

    public void removeFavoriteItemsObservers(LifecycleOwner lifecycleOwner) {
        getFavoriteItemsResult.removeObservers(lifecycleOwner);
    }

    public LiveData<AuthResource<GeneralResponse>> addToFavorite(String id) {
        LiveData<AuthApiResponse<GeneralResponse>> response = favoriteRepo.addToFavorite(id, SharedPreferencesHelper.getUserToken(getApplication()), SharedPreferencesHelper.getAppLanguage(getApplication()));
        addToFavoriteResult.setValue(AuthResource.loading());
        addToFavoriteResult.addSource(response, it -> {
            addToFavoriteResult.removeSource(response);
            if (it instanceof AuthApiResponse.AuthApiSuccessResponse) {
                addToFavoriteResult.setValue(AuthResource.authenticated((GeneralResponse) ((AuthApiResponse.AuthApiSuccessResponse) it).getBody()));
            } else {
                if (((AuthApiResponse.AuthApiErrorResponse) it).getBody() instanceof GeneralResponse) {
                    addToFavoriteResult.setValue(AuthResource.error(((AuthApiResponse.AuthApiErrorResponse) it).getErrorMessage(),
                            (GeneralResponse) ((AuthApiResponse.AuthApiErrorResponse) it).getBody(),
                            ((AuthApiResponse.AuthApiErrorResponse) it).getErrorCode()));
                } else {
                    addToFavoriteResult.setValue(AuthResource.error(((AuthApiResponse.AuthApiErrorResponse) it).getErrorMessage(),
                            null, ((AuthApiResponse.AuthApiErrorResponse) it).getErrorCode()));
                }
            }
        });
        return addToFavoriteResult;
    }

    public LiveData<AuthResource<GeneralResponse>> deleteFavorite(int id) {
        LiveData<AuthApiResponse<GeneralResponse>> response = favoriteRepo.deleteFavorite(id, SharedPreferencesHelper.getUserToken(getApplication()), SharedPreferencesHelper.getAppLanguage(getApplication()));
        deleteFavoriteResult.setValue(AuthResource.loading());
        deleteFavoriteResult.addSource(response, it -> {
            deleteFavoriteResult.removeSource(response);
            if (it instanceof AuthApiResponse.AuthApiSuccessResponse) {
                deleteFavoriteResult.setValue(AuthResource.authenticated((GeneralResponse) ((AuthApiResponse.AuthApiSuccessResponse) it).getBody()));
            } else {
                if (((AuthApiResponse.AuthApiErrorResponse) it).getBody() instanceof GeneralResponse) {
                    deleteFavoriteResult.setValue(AuthResource.error(((AuthApiResponse.AuthApiErrorResponse) it).getErrorMessage(),
                            (GeneralResponse) ((AuthApiResponse.AuthApiErrorResponse) it).getBody(),
                            ((AuthApiResponse.AuthApiErrorResponse) it).getErrorCode()));
                } else {
                    deleteFavoriteResult.setValue(AuthResource.error(((AuthApiResponse.AuthApiErrorResponse) it).getErrorMessage(),
                            null, ((AuthApiResponse.AuthApiErrorResponse) it).getErrorCode()));
                }
            }
        });
        return deleteFavoriteResult;
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

    public void getNextPage(int id) {
        setFetchingNextPage(true);
        pageNumber++;
        getFavoriteItems(id);
    }
}
