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
import com.prography.sw.aloocustomer.repos.CardRepo;
import com.prography.sw.aloocustomer.util.SharedPreferencesHelper;

import java.util.List;

public class CardViewModel extends AndroidViewModel {

    private CardRepo cardRepo;

    private MediatorLiveData<AuthResource<GeneralResponse>> addToCardResult = new MediatorLiveData<>();
    private MediatorLiveData<AuthResource<GeneralResponse>> allCardItemResult = new MediatorLiveData<>();
    private MediatorLiveData<AuthResource<GeneralResponse>> deleteResult = new MediatorLiveData<>();
    private MediatorLiveData<AuthResource<GeneralResponse>> viewOneItemResult = new MediatorLiveData<>();
    private MediatorLiveData<AuthResource<GeneralResponse>> saveResult = new MediatorLiveData<>();
    private MediatorLiveData<AuthResource<GeneralResponse>> submitCartOrderResult = new MediatorLiveData<>();

    public CardViewModel(@NonNull Application application) {
        super(application);
        cardRepo = CardRepo.getInstance();
    }


    public LiveData<AuthResource<GeneralResponse>> addToCard(int item_id, int amount, List<String> with, List<String> without,
                                                             String size,
                                                             List<Integer> drinks,
                                                             List<Integer> extras) {
        LiveData<AuthApiResponse<GeneralResponse>> response = cardRepo.addToCard(item_id, amount, with, without, size, drinks, extras, SharedPreferencesHelper.getUserToken(getApplication()), SharedPreferencesHelper.getAppLanguage(getApplication()));
        addToCardResult.setValue(AuthResource.loading());
        addToCardResult.addSource(response, it -> {
            addToCardResult.removeSource(response);
            if (it instanceof AuthApiResponse.AuthApiSuccessResponse) {
                addToCardResult.setValue(AuthResource.authenticated((GeneralResponse) ((AuthApiResponse.AuthApiSuccessResponse) it).getBody()));
            } else {
                if (((AuthApiResponse.AuthApiErrorResponse) it).getBody() instanceof GeneralResponse) {
                    addToCardResult.setValue(AuthResource.error(((AuthApiResponse.AuthApiErrorResponse) it).getErrorMessage(),
                            (GeneralResponse) ((AuthApiResponse.AuthApiErrorResponse) it).getBody(),
                            ((AuthApiResponse.AuthApiErrorResponse) it).getErrorCode()));
                } else {
                    addToCardResult.setValue(AuthResource.error(((AuthApiResponse.AuthApiErrorResponse) it).getErrorMessage(),
                            null, ((AuthApiResponse.AuthApiErrorResponse) it).getErrorCode()));
                }
            }
        });
        return addToCardResult;
    }

    public LiveData<AuthResource<GeneralResponse>> addToCardSuperPharmacy(int item_id, int amount) {
        LiveData<AuthApiResponse<GeneralResponse>> response = cardRepo.addToCardSuperPhrmacy(item_id, amount, SharedPreferencesHelper.getUserToken(getApplication()), SharedPreferencesHelper.getAppLanguage(getApplication()));
        addToCardResult.setValue(AuthResource.loading());
        addToCardResult.addSource(response, it -> {
            addToCardResult.removeSource(response);
            if (it instanceof AuthApiResponse.AuthApiSuccessResponse) {
                addToCardResult.setValue(AuthResource.authenticated((GeneralResponse) ((AuthApiResponse.AuthApiSuccessResponse) it).getBody()));
            } else {
                if (((AuthApiResponse.AuthApiErrorResponse) it).getBody() instanceof GeneralResponse) {
                    addToCardResult.setValue(AuthResource.error(((AuthApiResponse.AuthApiErrorResponse) it).getErrorMessage(),
                            (GeneralResponse) ((AuthApiResponse.AuthApiErrorResponse) it).getBody(),
                            ((AuthApiResponse.AuthApiErrorResponse) it).getErrorCode()));
                } else {
                    addToCardResult.setValue(AuthResource.error(((AuthApiResponse.AuthApiErrorResponse) it).getErrorMessage(),
                            null, ((AuthApiResponse.AuthApiErrorResponse) it).getErrorCode()));
                }
            }
        });
        return addToCardResult;
    }

    public void removeAddToCardObservers(LifecycleOwner lifecycleOwner) {
        addToCardResult.removeObservers(lifecycleOwner);
    }

    public LiveData<AuthResource<GeneralResponse>> getAllCardItem(int item_id) {
        LiveData<AuthApiResponse<GeneralResponse>> response = cardRepo.getAllCardItem(item_id, SharedPreferencesHelper.getUserToken(getApplication()), SharedPreferencesHelper.getAppLanguage(getApplication()));
        allCardItemResult.setValue(AuthResource.loading());
        allCardItemResult.addSource(response, it -> {
            allCardItemResult.removeSource(response);
            if (it instanceof AuthApiResponse.AuthApiSuccessResponse) {
                allCardItemResult.setValue(AuthResource.authenticated((GeneralResponse) ((AuthApiResponse.AuthApiSuccessResponse) it).getBody()));
            } else {
                if (((AuthApiResponse.AuthApiErrorResponse) it).getBody() instanceof GeneralResponse) {
                    allCardItemResult.setValue(AuthResource.error(((AuthApiResponse.AuthApiErrorResponse) it).getErrorMessage(),
                            (GeneralResponse) ((AuthApiResponse.AuthApiErrorResponse) it).getBody(),
                            ((AuthApiResponse.AuthApiErrorResponse) it).getErrorCode()));
                } else {
                    allCardItemResult.setValue(AuthResource.error(((AuthApiResponse.AuthApiErrorResponse) it).getErrorMessage(),
                            null, ((AuthApiResponse.AuthApiErrorResponse) it).getErrorCode()));
                }
            }
        });
        return allCardItemResult;
    }

    public void removeAllCardItem(LifecycleOwner lifecycleOwner) {
        allCardItemResult.removeObservers(lifecycleOwner);
    }

    public LiveData<AuthResource<GeneralResponse>> deleteCartItem(int item_id) {
        LiveData<AuthApiResponse<GeneralResponse>> response = cardRepo.deleteCartItem(item_id, SharedPreferencesHelper.getUserToken(getApplication()), SharedPreferencesHelper.getAppLanguage(getApplication()));
        deleteResult.setValue(AuthResource.loading());
        deleteResult.addSource(response, it -> {
            deleteResult.removeSource(response);
            if (it instanceof AuthApiResponse.AuthApiSuccessResponse) {
                deleteResult.setValue(AuthResource.authenticated((GeneralResponse) ((AuthApiResponse.AuthApiSuccessResponse) it).getBody()));
            } else {
                if (((AuthApiResponse.AuthApiErrorResponse) it).getBody() instanceof GeneralResponse) {
                    deleteResult.setValue(AuthResource.error(((AuthApiResponse.AuthApiErrorResponse) it).getErrorMessage(),
                            (GeneralResponse) ((AuthApiResponse.AuthApiErrorResponse) it).getBody(),
                            ((AuthApiResponse.AuthApiErrorResponse) it).getErrorCode()));
                } else {
                    deleteResult.setValue(AuthResource.error(((AuthApiResponse.AuthApiErrorResponse) it).getErrorMessage(),
                            null, ((AuthApiResponse.AuthApiErrorResponse) it).getErrorCode()));
                }
            }
        });
        return deleteResult;
    }

    public LiveData<AuthResource<GeneralResponse>> getOneItem(int item_id) {
        LiveData<AuthApiResponse<GeneralResponse>> response = cardRepo.getOneItem(item_id, SharedPreferencesHelper.getUserToken(getApplication()), SharedPreferencesHelper.getAppLanguage(getApplication()));
        viewOneItemResult.setValue(AuthResource.loading());
        viewOneItemResult.addSource(response, it -> {
            viewOneItemResult.removeSource(response);
            if (it instanceof AuthApiResponse.AuthApiSuccessResponse) {
                viewOneItemResult.setValue(AuthResource.authenticated((GeneralResponse) ((AuthApiResponse.AuthApiSuccessResponse) it).getBody()));
            } else {
                if (((AuthApiResponse.AuthApiErrorResponse) it).getBody() instanceof GeneralResponse) {
                    viewOneItemResult.setValue(AuthResource.error(((AuthApiResponse.AuthApiErrorResponse) it).getErrorMessage(),
                            (GeneralResponse) ((AuthApiResponse.AuthApiErrorResponse) it).getBody(),
                            ((AuthApiResponse.AuthApiErrorResponse) it).getErrorCode()));
                } else {
                    viewOneItemResult.setValue(AuthResource.error(((AuthApiResponse.AuthApiErrorResponse) it).getErrorMessage(),
                            null, ((AuthApiResponse.AuthApiErrorResponse) it).getErrorCode()));
                }
            }
        });
        return viewOneItemResult;
    }

    public LiveData<AuthResource<GeneralResponse>> saveEdit(int item_id, int amount, List<String> with, List<String> without,
                                                            String size,
                                                            List<Integer> drinks,
                                                            List<Integer> extras) {
        LiveData<AuthApiResponse<GeneralResponse>> response = cardRepo.SaveEdit(item_id, amount, with, without, size, drinks, extras, SharedPreferencesHelper.getUserToken(getApplication()), SharedPreferencesHelper.getAppLanguage(getApplication()));
        saveResult.setValue(AuthResource.loading());
        saveResult.addSource(response, it -> {
            saveResult.removeSource(response);
            if (it instanceof AuthApiResponse.AuthApiSuccessResponse) {
                saveResult.setValue(AuthResource.authenticated((GeneralResponse) ((AuthApiResponse.AuthApiSuccessResponse) it).getBody()));
            } else {
                if (((AuthApiResponse.AuthApiErrorResponse) it).getBody() instanceof GeneralResponse) {
                    saveResult.setValue(AuthResource.error(((AuthApiResponse.AuthApiErrorResponse) it).getErrorMessage(),
                            (GeneralResponse) ((AuthApiResponse.AuthApiErrorResponse) it).getBody(),
                            ((AuthApiResponse.AuthApiErrorResponse) it).getErrorCode()));
                } else {
                    saveResult.setValue(AuthResource.error(((AuthApiResponse.AuthApiErrorResponse) it).getErrorMessage(),
                            null, ((AuthApiResponse.AuthApiErrorResponse) it).getErrorCode()));
                }
            }
        });
        return saveResult;
    }

    public LiveData<AuthResource<GeneralResponse>> saveEdit(int item_id, int amount) {
        LiveData<AuthApiResponse<GeneralResponse>> response = cardRepo.SaveEdit(item_id, amount, SharedPreferencesHelper.getUserToken(getApplication()), SharedPreferencesHelper.getAppLanguage(getApplication()));
        saveResult.setValue(AuthResource.loading());
        saveResult.addSource(response, it -> {
            saveResult.removeSource(response);
            if (it instanceof AuthApiResponse.AuthApiSuccessResponse) {
                saveResult.setValue(AuthResource.authenticated((GeneralResponse) ((AuthApiResponse.AuthApiSuccessResponse) it).getBody()));
            } else {
                if (((AuthApiResponse.AuthApiErrorResponse) it).getBody() instanceof GeneralResponse) {
                    saveResult.setValue(AuthResource.error(((AuthApiResponse.AuthApiErrorResponse) it).getErrorMessage(),
                            (GeneralResponse) ((AuthApiResponse.AuthApiErrorResponse) it).getBody(),
                            ((AuthApiResponse.AuthApiErrorResponse) it).getErrorCode()));
                } else {
                    saveResult.setValue(AuthResource.error(((AuthApiResponse.AuthApiErrorResponse) it).getErrorMessage(),
                            null, ((AuthApiResponse.AuthApiErrorResponse) it).getErrorCode()));
                }
            }
        });
        return saveResult;
    }


    public LiveData<AuthResource<GeneralResponse>> submitCartOrder(int item_id) {
        LiveData<AuthApiResponse<GeneralResponse>> response = cardRepo.submitCartOrder(item_id, SharedPreferencesHelper.getUserToken(getApplication()), SharedPreferencesHelper.getAppLanguage(getApplication()));
        submitCartOrderResult.setValue(AuthResource.loading());
        submitCartOrderResult.addSource(response, it -> {
            submitCartOrderResult.removeSource(response);
            if (it instanceof AuthApiResponse.AuthApiSuccessResponse) {
                submitCartOrderResult.setValue(AuthResource.authenticated((GeneralResponse) ((AuthApiResponse.AuthApiSuccessResponse) it).getBody()));
            } else {
                if (((AuthApiResponse.AuthApiErrorResponse) it).getBody() instanceof GeneralResponse) {
                    submitCartOrderResult.setValue(AuthResource.error(((AuthApiResponse.AuthApiErrorResponse) it).getErrorMessage(),
                            (GeneralResponse) ((AuthApiResponse.AuthApiErrorResponse) it).getBody(),
                            ((AuthApiResponse.AuthApiErrorResponse) it).getErrorCode()));
                } else {
                    submitCartOrderResult.setValue(AuthResource.error(((AuthApiResponse.AuthApiErrorResponse) it).getErrorMessage(),
                            null, ((AuthApiResponse.AuthApiErrorResponse) it).getErrorCode()));
                }
            }
        });
        return submitCartOrderResult;
    }


}
