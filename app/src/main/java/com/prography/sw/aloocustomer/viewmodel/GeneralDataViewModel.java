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
import com.prography.sw.aloocustomer.repos.GeneralDataRepo;
import com.prography.sw.aloocustomer.util.SharedPreferencesHelper;

public class GeneralDataViewModel extends AndroidViewModel {

    private GeneralDataRepo generalDataRepo;
    private MediatorLiveData<AuthResource<GeneralResponse>> citiesResult = new MediatorLiveData<>();
    private MediatorLiveData<AuthResource<GeneralResponse>> banksResult = new MediatorLiveData<>();
    private MediatorLiveData<AuthResource<GeneralResponse>> aboutDataResult = new MediatorLiveData<>();
    private MediatorLiveData<AuthResource<GeneralResponse>> privacyResult = new MediatorLiveData<>();
    private MediatorLiveData<AuthResource<GeneralResponse>> questionsResult = new MediatorLiveData<>();
    private MediatorLiveData<AuthResource<GeneralResponse>> servicesResult = new MediatorLiveData<>();
    private MediatorLiveData<AuthResource<GeneralResponse>> restaurantTypesResult = new MediatorLiveData<>();
    private MediatorLiveData<AuthResource<GeneralResponse>> kitchenTypesResult = new MediatorLiveData<>();
    private MediatorLiveData<AuthResource<GeneralResponse>> clientServiceResult = new MediatorLiveData<>();

    private int pageNumber = 1;
    private boolean isFetchingNextPage, isFetchingExhausted;

    public GeneralDataViewModel(@NonNull Application application) {
        super(application);
        generalDataRepo = GeneralDataRepo.getInstance();
    }

    public LiveData<AuthResource<GeneralResponse>> cities() {
        LiveData<AuthApiResponse<GeneralResponse>> cities = generalDataRepo.cities(SharedPreferencesHelper.getAppLanguage(getApplication()));
        citiesResult.setValue(AuthResource.loading());
        citiesResult.addSource(cities, it -> {
            citiesResult.removeSource(cities);
            if (it instanceof AuthApiResponse.AuthApiSuccessResponse) {
                citiesResult.setValue(AuthResource.authenticated((GeneralResponse) ((AuthApiResponse.AuthApiSuccessResponse) it).getBody()));
            } else {
                if (((AuthApiResponse.AuthApiErrorResponse) it).getBody() instanceof GeneralResponse) {
                    citiesResult.setValue(AuthResource.error(((AuthApiResponse.AuthApiErrorResponse) it).getErrorMessage(),
                            (GeneralResponse) ((AuthApiResponse.AuthApiErrorResponse) it).getBody(),
                            ((AuthApiResponse.AuthApiErrorResponse) it).getErrorCode()));
                } else {
                    citiesResult.setValue(AuthResource.error(((AuthApiResponse.AuthApiErrorResponse) it).getErrorMessage(),
                            null, ((AuthApiResponse.AuthApiErrorResponse) it).getErrorCode()));
                }
            }
        });
        return citiesResult;
    }

    public void removeCitiesObservers(LifecycleOwner lifecycleOwner) {
        citiesResult.removeObservers(lifecycleOwner);
    }

    public LiveData<AuthResource<GeneralResponse>> banks() {
        LiveData<AuthApiResponse<GeneralResponse>> banks = generalDataRepo.banks(SharedPreferencesHelper.getAppLanguage(getApplication()));
        banksResult.setValue(AuthResource.loading());
        banksResult.addSource(banks, it -> {
            banksResult.removeSource(banks);
            if (it instanceof AuthApiResponse.AuthApiSuccessResponse) {
                banksResult.setValue(AuthResource.authenticated((GeneralResponse) ((AuthApiResponse.AuthApiSuccessResponse) it).getBody()));
            } else {
                if (((AuthApiResponse.AuthApiErrorResponse) it).getBody() instanceof GeneralResponse) {
                    banksResult.setValue(AuthResource.error(((AuthApiResponse.AuthApiErrorResponse) it).getErrorMessage(),
                            (GeneralResponse) ((AuthApiResponse.AuthApiErrorResponse) it).getBody(),
                            ((AuthApiResponse.AuthApiErrorResponse) it).getErrorCode()));
                } else {
                    banksResult.setValue(AuthResource.error(((AuthApiResponse.AuthApiErrorResponse) it).getErrorMessage(),
                            null, ((AuthApiResponse.AuthApiErrorResponse) it).getErrorCode()));
                }
            }
        });
        return banksResult;

    }

    public void removeBanksObservers(LifecycleOwner lifecycleOwner) {
        banksResult.removeObservers(lifecycleOwner);
    }

    public LiveData<AuthResource<GeneralResponse>> aboutData() {
        LiveData<AuthApiResponse<GeneralResponse>> aboutData = generalDataRepo.aboutData(SharedPreferencesHelper.getAppLanguage(getApplication()));
        aboutDataResult.setValue(AuthResource.loading());
        aboutDataResult.addSource(aboutData, it -> {
            aboutDataResult.removeSource(aboutData);
            if (it instanceof AuthApiResponse.AuthApiSuccessResponse) {
                aboutDataResult.setValue(AuthResource.authenticated((GeneralResponse) ((AuthApiResponse.AuthApiSuccessResponse) it).getBody()));
            } else {
                if (((AuthApiResponse.AuthApiErrorResponse) it).getBody() instanceof GeneralResponse) {
                    aboutDataResult.setValue(AuthResource.error(((AuthApiResponse.AuthApiErrorResponse) it).getErrorMessage(),
                            (GeneralResponse) ((AuthApiResponse.AuthApiErrorResponse) it).getBody(),
                            ((AuthApiResponse.AuthApiErrorResponse) it).getErrorCode()));
                } else {
                    aboutDataResult.setValue(AuthResource.error(((AuthApiResponse.AuthApiErrorResponse) it).getErrorMessage(),
                            null, ((AuthApiResponse.AuthApiErrorResponse) it).getErrorCode()));
                }
            }
        });
        return aboutDataResult;
    }

    public void removeAboutObservers(LifecycleOwner lifecycleOwner) {
        aboutDataResult.removeObservers(lifecycleOwner);
    }

    public LiveData<AuthResource<GeneralResponse>> privacy() {
        LiveData<AuthApiResponse<GeneralResponse>> privacy = generalDataRepo.privacy(SharedPreferencesHelper.getAppLanguage(getApplication()));
        privacyResult.setValue(AuthResource.loading());
        privacyResult.addSource(privacy, it -> {
            privacyResult.removeSource(privacy);
            if (it instanceof AuthApiResponse.AuthApiSuccessResponse) {
                privacyResult.setValue(AuthResource.authenticated((GeneralResponse) ((AuthApiResponse.AuthApiSuccessResponse) it).getBody()));
            } else {
                if (((AuthApiResponse.AuthApiErrorResponse) it).getBody() instanceof GeneralResponse) {
                    privacyResult.setValue(AuthResource.error(((AuthApiResponse.AuthApiErrorResponse) it).getErrorMessage(),
                            (GeneralResponse) ((AuthApiResponse.AuthApiErrorResponse) it).getBody(),
                            ((AuthApiResponse.AuthApiErrorResponse) it).getErrorCode()));
                } else {
                    privacyResult.setValue(AuthResource.error(((AuthApiResponse.AuthApiErrorResponse) it).getErrorMessage(),
                            null, ((AuthApiResponse.AuthApiErrorResponse) it).getErrorCode()));
                }
            }
        });
        return privacyResult;
    }

    public void removePrivacyObservers(LifecycleOwner lifecycleOwner) {
        privacyResult.removeObservers(lifecycleOwner);
    }

    public LiveData<AuthResource<GeneralResponse>> questions() {
        LiveData<AuthApiResponse<GeneralResponse>> questions = generalDataRepo.questions(pageNumber, SharedPreferencesHelper.getAppLanguage(getApplication()));
        questionsResult.setValue(AuthResource.loading());
        questionsResult.addSource(questions, it -> {
            if (it instanceof AuthApiResponse.AuthApiSuccessResponse) {
                questionsResult.setValue(AuthResource.authenticated((GeneralResponse) ((AuthApiResponse.AuthApiSuccessResponse) it).getBody()));
            } else {
                if (((AuthApiResponse.AuthApiErrorResponse) it).getBody() instanceof GeneralResponse) {
                    questionsResult.setValue(AuthResource.error(((AuthApiResponse.AuthApiErrorResponse) it).getErrorMessage(),
                            (GeneralResponse) ((AuthApiResponse.AuthApiErrorResponse) it).getBody(),
                            ((AuthApiResponse.AuthApiErrorResponse) it).getErrorCode()));
                } else {
                    questionsResult.setValue(AuthResource.error(((AuthApiResponse.AuthApiErrorResponse) it).getErrorMessage(),
                            null, ((AuthApiResponse.AuthApiErrorResponse) it).getErrorCode()));
                }
            }
        });
        return questionsResult;
    }

    public void removeQuestionObservers(LifecycleOwner lifecycleOwner) {
        questionsResult.removeObservers(lifecycleOwner);
    }

    public LiveData<AuthResource<GeneralResponse>> services() {
        LiveData<AuthApiResponse<GeneralResponse>> services = generalDataRepo.services(SharedPreferencesHelper.getAppLanguage(getApplication()));
        servicesResult.setValue(AuthResource.loading());
        servicesResult.addSource(services, it -> {
            if (it instanceof AuthApiResponse.AuthApiSuccessResponse) {
                servicesResult.setValue(AuthResource.authenticated((GeneralResponse) ((AuthApiResponse.AuthApiSuccessResponse) it).getBody()));
            } else {
                if (((AuthApiResponse.AuthApiErrorResponse) it).getBody() instanceof GeneralResponse) {
                    servicesResult.setValue(AuthResource.error(((AuthApiResponse.AuthApiErrorResponse) it).getErrorMessage(),
                            (GeneralResponse) ((AuthApiResponse.AuthApiErrorResponse) it).getBody(),
                            ((AuthApiResponse.AuthApiErrorResponse) it).getErrorCode()));
                } else {
                    servicesResult.setValue(AuthResource.error(((AuthApiResponse.AuthApiErrorResponse) it).getErrorMessage(),
                            null, ((AuthApiResponse.AuthApiErrorResponse) it).getErrorCode()));
                }
            }
        });
        return servicesResult;
    }

    public void removeServicesObservers(LifecycleOwner lifecycleOwner) {
        servicesResult.removeObservers(lifecycleOwner);
    }

    public LiveData<AuthResource<GeneralResponse>> getRestaurantTypes() {
        LiveData<AuthApiResponse<GeneralResponse>> restaurantTypes = generalDataRepo.getRestaurantTypes(SharedPreferencesHelper.getAppLanguage(getApplication()));
        restaurantTypesResult.setValue(AuthResource.loading());
        restaurantTypesResult.addSource(restaurantTypes, it -> {
            restaurantTypesResult.removeSource(restaurantTypes);
            if (it instanceof AuthApiResponse.AuthApiSuccessResponse) {
                restaurantTypesResult.setValue(AuthResource.authenticated((GeneralResponse) ((AuthApiResponse.AuthApiSuccessResponse) it).getBody()));
            } else {
                if (((AuthApiResponse.AuthApiErrorResponse) it).getBody() instanceof GeneralResponse) {
                    restaurantTypesResult.setValue(AuthResource.error(((AuthApiResponse.AuthApiErrorResponse) it).getErrorMessage(),
                            (GeneralResponse) ((AuthApiResponse.AuthApiErrorResponse) it).getBody(),
                            ((AuthApiResponse.AuthApiErrorResponse) it).getErrorCode()));
                } else {
                    restaurantTypesResult.setValue(AuthResource.error(((AuthApiResponse.AuthApiErrorResponse) it).getErrorMessage(),
                            null, ((AuthApiResponse.AuthApiErrorResponse) it).getErrorCode()));
                }
            }
        });
        return restaurantTypesResult;
    }

    public void removeRestaurantTypesObservers(LifecycleOwner lifecycleOwner) {
        restaurantTypesResult.removeObservers(lifecycleOwner);
    }

    public LiveData<AuthResource<GeneralResponse>> getKitchenTypes() {
        LiveData<AuthApiResponse<GeneralResponse>> kitchenTypes = generalDataRepo.getKitchenTypes(SharedPreferencesHelper.getAppLanguage(getApplication()));
        kitchenTypesResult.setValue(AuthResource.loading());
        kitchenTypesResult.addSource(kitchenTypes, it -> {
            kitchenTypesResult.removeSource(kitchenTypes);
            if (it instanceof AuthApiResponse.AuthApiSuccessResponse) {
                kitchenTypesResult.setValue(AuthResource.authenticated((GeneralResponse) ((AuthApiResponse.AuthApiSuccessResponse) it).getBody()));
            } else {
                if (((AuthApiResponse.AuthApiErrorResponse) it).getBody() instanceof GeneralResponse) {
                    kitchenTypesResult.setValue(AuthResource.error(((AuthApiResponse.AuthApiErrorResponse) it).getErrorMessage(),
                            (GeneralResponse) ((AuthApiResponse.AuthApiErrorResponse) it).getBody(),
                            ((AuthApiResponse.AuthApiErrorResponse) it).getErrorCode()));
                } else {
                    kitchenTypesResult.setValue(AuthResource.error(((AuthApiResponse.AuthApiErrorResponse) it).getErrorMessage(),
                            null, ((AuthApiResponse.AuthApiErrorResponse) it).getErrorCode()));
                }
            }
        });
        return kitchenTypesResult;
    }

    public void removeKitchenTypesObservers(LifecycleOwner lifecycleOwner) {
        kitchenTypesResult.removeObservers(lifecycleOwner);
    }

    public LiveData<AuthResource<GeneralResponse>> clientService(String type, String question) {
        LiveData<AuthApiResponse<GeneralResponse>> clientService = generalDataRepo.clientService(type, question, SharedPreferencesHelper.getAppLanguage(getApplication()));
        clientServiceResult.setValue(AuthResource.loading());
        clientServiceResult.addSource(clientService, it -> {
            clientServiceResult.removeSource(clientService);
            if (it instanceof AuthApiResponse.AuthApiSuccessResponse) {
                clientServiceResult.setValue(AuthResource.authenticated((GeneralResponse) ((AuthApiResponse.AuthApiSuccessResponse) it).getBody()));
            } else {
                if (((AuthApiResponse.AuthApiErrorResponse) it).getBody() instanceof GeneralResponse) {
                    clientServiceResult.setValue(AuthResource.error(((AuthApiResponse.AuthApiErrorResponse) it).getErrorMessage(),
                            (GeneralResponse) ((AuthApiResponse.AuthApiErrorResponse) it).getBody(),
                            ((AuthApiResponse.AuthApiErrorResponse) it).getErrorCode()));
                } else {
                    clientServiceResult.setValue(AuthResource.error(((AuthApiResponse.AuthApiErrorResponse) it).getErrorMessage(),
                            null, ((AuthApiResponse.AuthApiErrorResponse) it).getErrorCode()));
                }
            }
        });
        return clientServiceResult;
    }

    public void removeClientServiceObservers(LifecycleOwner lifecycleOwner) {
        clientServiceResult.removeObservers(lifecycleOwner);
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
        questions();
    }

}
