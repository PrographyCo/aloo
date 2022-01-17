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
import com.prography.sw.aloodelevery.repos.GeneralDataRepo;
import com.prography.sw.aloodelevery.util.SharedPreferencesHelper;

public class GeneralDataViewModel extends AndroidViewModel {

    private GeneralDataRepo generalDataRepo;

    private MediatorLiveData<AuthResource<GeneralResponse>> aboutDataResult = new MediatorLiveData<>();
    private MediatorLiveData<AuthResource<GeneralResponse>> privacyResult = new MediatorLiveData<>();
    private MediatorLiveData<AuthResource<GeneralResponse>> questionsResult = new MediatorLiveData<>();
    private MediatorLiveData<AuthResource<GeneralResponse>> clientServiceResult = new MediatorLiveData<>();


    private int pageNumber = 1;
    private boolean isFetchingNextPage, isFetchingExhausted;

    public GeneralDataViewModel(@NonNull Application application) {
        super(application);
        generalDataRepo = GeneralDataRepo.getInstance();
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
