package com.prography.sw.aloocustomer.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;

import com.prography.sw.aloocustomer.data.response.GeneralResponse;
import com.prography.sw.aloocustomer.network.AuthApiResponse;
import com.prography.sw.aloocustomer.network.AuthResource;
import com.prography.sw.aloocustomer.repos.MainRepo;
import com.prography.sw.aloocustomer.util.SharedPreferencesHelper;

public class MainViewModel extends AndroidViewModel {
    private MainRepo mainRepo;

    private MediatorLiveData<AuthResource<GeneralResponse>> mainResult = new MediatorLiveData<>();
    private MediatorLiveData<AuthResource<GeneralResponse>> vendorResult = new MediatorLiveData<>();
    private MediatorLiveData<AuthResource<GeneralResponse>> vendorItemResult = new MediatorLiveData<>();
    private MediatorLiveData<AuthResource<GeneralResponse>> categoriesResult = new MediatorLiveData<>();


    private int pageNumber = 1;
    private boolean isFetchingNextPage, isFetchingExhausted;


    public MainViewModel(@NonNull Application application) {
        super(application);
        mainRepo = MainRepo.getInstance();
    }


    public LiveData<AuthResource<GeneralResponse>> getMain(int id, double lon, double lat,
                                                           int place_id) {
        LiveData<AuthApiResponse<GeneralResponse>> response = mainRepo.getMain(id, pageNumber, lon, lat, place_id,
                SharedPreferencesHelper.getUserToken(getApplication()), SharedPreferencesHelper.getAppLanguage(getApplication()));
        mainResult.setValue(AuthResource.loading());
        mainResult.addSource(response, it -> {
            mainResult.removeSource(response);
            if (it instanceof AuthApiResponse.AuthApiSuccessResponse) {
                mainResult.setValue(AuthResource.authenticated((GeneralResponse) ((AuthApiResponse.AuthApiSuccessResponse) it).getBody()));
            } else {
                if (((AuthApiResponse.AuthApiErrorResponse) it).getBody() instanceof GeneralResponse) {
                    mainResult.setValue(AuthResource.error(((AuthApiResponse.AuthApiErrorResponse) it).getErrorMessage(),
                            (GeneralResponse) ((AuthApiResponse.AuthApiErrorResponse) it).getBody(),
                            ((AuthApiResponse.AuthApiErrorResponse) it).getErrorCode()));
                } else {
                    mainResult.setValue(AuthResource.error(((AuthApiResponse.AuthApiErrorResponse) it).getErrorMessage(),
                            null, ((AuthApiResponse.AuthApiErrorResponse) it).getErrorCode()));
                }
            }
        });
        return mainResult;
    }

    public LiveData<AuthResource<GeneralResponse>> getMain(int id, double lon, double lat,
                                                           int place_id, int kitchen_type, int restaurant_type,
                                                           String order_by) {
        LiveData<AuthApiResponse<GeneralResponse>> response = mainRepo.getMain(id, pageNumber, lon, lat, place_id, kitchen_type, restaurant_type,
                order_by, SharedPreferencesHelper.getUserToken(getApplication()), SharedPreferencesHelper.getAppLanguage(getApplication()));
        mainResult.setValue(AuthResource.loading());
        mainResult.addSource(response, it -> {
            mainResult.removeSource(response);
            if (it instanceof AuthApiResponse.AuthApiSuccessResponse) {
                mainResult.setValue(AuthResource.authenticated((GeneralResponse) ((AuthApiResponse.AuthApiSuccessResponse) it).getBody()));
            } else {
                if (((AuthApiResponse.AuthApiErrorResponse) it).getBody() instanceof GeneralResponse) {
                    mainResult.setValue(AuthResource.error(((AuthApiResponse.AuthApiErrorResponse) it).getErrorMessage(),
                            (GeneralResponse) ((AuthApiResponse.AuthApiErrorResponse) it).getBody(),
                            ((AuthApiResponse.AuthApiErrorResponse) it).getErrorCode()));
                } else {
                    mainResult.setValue(AuthResource.error(((AuthApiResponse.AuthApiErrorResponse) it).getErrorMessage(),
                            null, ((AuthApiResponse.AuthApiErrorResponse) it).getErrorCode()));
                }
            }
        });
        return mainResult;
    }

    public void removeMainObservers(LifecycleOwner lifecycleOwner) {
        mainResult.removeObservers(lifecycleOwner);
    }


    public LiveData<AuthResource<GeneralResponse>> getVendor(int id, double lon, double lat) {
        LiveData<AuthApiResponse<GeneralResponse>> response = mainRepo.getVendor(id, pageNumber, lon, lat,
                SharedPreferencesHelper.getUserToken(getApplication()), SharedPreferencesHelper.getAppLanguage(getApplication()));
        vendorResult.setValue(AuthResource.loading());
        vendorResult.addSource(response, it -> {
            vendorResult.removeSource(response);
            if (it instanceof AuthApiResponse.AuthApiSuccessResponse) {
                vendorResult.setValue(AuthResource.authenticated((GeneralResponse) ((AuthApiResponse.AuthApiSuccessResponse) it).getBody()));
            } else {
                if (((AuthApiResponse.AuthApiErrorResponse) it).getBody() instanceof GeneralResponse) {
                    vendorResult.setValue(AuthResource.error(((AuthApiResponse.AuthApiErrorResponse) it).getErrorMessage(),
                            (GeneralResponse) ((AuthApiResponse.AuthApiErrorResponse) it).getBody(),
                            ((AuthApiResponse.AuthApiErrorResponse) it).getErrorCode()));
                } else {
                    vendorResult.setValue(AuthResource.error(((AuthApiResponse.AuthApiErrorResponse) it).getErrorMessage(),
                            null, ((AuthApiResponse.AuthApiErrorResponse) it).getErrorCode()));
                }
            }
        });
        return vendorResult;
    }

    public LiveData<AuthResource<GeneralResponse>> getVendor(int id, String order_by, int category, double lon, double lat) {
        LiveData<AuthApiResponse<GeneralResponse>> response = mainRepo.getVendor(id, pageNumber, order_by, category, lon, lat,
                SharedPreferencesHelper.getUserToken(getApplication()), SharedPreferencesHelper.getAppLanguage(getApplication()));
        vendorResult.setValue(AuthResource.loading());
        vendorResult.addSource(response, it -> {
            vendorResult.removeSource(response);
            if (it instanceof AuthApiResponse.AuthApiSuccessResponse) {
                vendorResult.setValue(AuthResource.authenticated((GeneralResponse) ((AuthApiResponse.AuthApiSuccessResponse) it).getBody()));
            } else {
                if (((AuthApiResponse.AuthApiErrorResponse) it).getBody() instanceof GeneralResponse) {
                    vendorResult.setValue(AuthResource.error(((AuthApiResponse.AuthApiErrorResponse) it).getErrorMessage(),
                            (GeneralResponse) ((AuthApiResponse.AuthApiErrorResponse) it).getBody(),
                            ((AuthApiResponse.AuthApiErrorResponse) it).getErrorCode()));
                } else {
                    vendorResult.setValue(AuthResource.error(((AuthApiResponse.AuthApiErrorResponse) it).getErrorMessage(),
                            null, ((AuthApiResponse.AuthApiErrorResponse) it).getErrorCode()));
                }
            }
        });
        return vendorResult;
    }

    public void removeVendorObservers(LifecycleOwner lifecycleOwner) {
        vendorResult.removeObservers(lifecycleOwner);
    }

    public LiveData<AuthResource<GeneralResponse>> getVendorItem(int id) {
        LiveData<AuthApiResponse<GeneralResponse>> response = mainRepo.getVendorItem(id,
                SharedPreferencesHelper.getUserToken(getApplication()), SharedPreferencesHelper.getAppLanguage(getApplication()));
        vendorItemResult.setValue(AuthResource.loading());
        vendorItemResult.addSource(response, it -> {
            vendorItemResult.removeSource(response);
            if (it instanceof AuthApiResponse.AuthApiSuccessResponse) {
                vendorItemResult.setValue(AuthResource.authenticated((GeneralResponse) ((AuthApiResponse.AuthApiSuccessResponse) it).getBody()));
            } else {
                if (((AuthApiResponse.AuthApiErrorResponse) it).getBody() instanceof GeneralResponse) {
                    vendorItemResult.setValue(AuthResource.error(((AuthApiResponse.AuthApiErrorResponse) it).getErrorMessage(),
                            (GeneralResponse) ((AuthApiResponse.AuthApiErrorResponse) it).getBody(),
                            ((AuthApiResponse.AuthApiErrorResponse) it).getErrorCode()));
                } else {
                    vendorItemResult.setValue(AuthResource.error(((AuthApiResponse.AuthApiErrorResponse) it).getErrorMessage(),
                            null, ((AuthApiResponse.AuthApiErrorResponse) it).getErrorCode()));
                }
            }
        });
        return vendorItemResult;
    }

    public void removeVendorItemObservers(LifecycleOwner lifecycleOwner) {
        vendorItemResult.removeObservers(lifecycleOwner);
    }

    public LiveData<AuthResource<GeneralResponse>> getCategories(int id) {
        LiveData<AuthApiResponse<GeneralResponse>> response = mainRepo.getCategories(id,
                SharedPreferencesHelper.getUserToken(getApplication()), SharedPreferencesHelper.getAppLanguage(getApplication()));
        categoriesResult.setValue(AuthResource.loading());
        categoriesResult.addSource(response, it -> {
            categoriesResult.removeSource(response);
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

    public void getNextPage(int id, double lon, double lat,
                            int place_id) {
        setFetchingNextPage(true);
        pageNumber++;
        getMain(id, lon, lat, place_id);
    }

    public void getNextPageVendor(int id, double lon, double lat) {
        setFetchingNextPage(true);
        pageNumber++;
        getVendor(id, lon, lat);
    }


}
