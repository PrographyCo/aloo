package com.prography.sw.aloodelevery.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import com.prography.sw.aloodelevery.data.response.GeneralResponse;
import com.prography.sw.aloodelevery.network.AuthApiResponse;
import com.prography.sw.aloodelevery.network.AuthResource;
import com.prography.sw.aloodelevery.repos.OrdersRepo;
import com.prography.sw.aloodelevery.util.SharedPreferencesHelper;

public class OrdersViewModel extends AndroidViewModel {

    private OrdersRepo ordersRepo;
    private MediatorLiveData<AuthResource<GeneralResponse>> ordersResult = new MediatorLiveData<>();
    private MediatorLiveData<AuthResource<GeneralResponse>> orderDataResult = new MediatorLiveData<>();
    private MediatorLiveData<AuthResource<GeneralResponse>> confirmResult = new MediatorLiveData<>();
    private MediatorLiveData<AuthResource<GeneralResponse>> cancelResult = new MediatorLiveData<>();
    private MediatorLiveData<AuthResource<GeneralResponse>> waitingResult = new MediatorLiveData<>();
    private MediatorLiveData<AuthResource<GeneralResponse>> toCustomerResult = new MediatorLiveData<>();
    private MediatorLiveData<AuthResource<GeneralResponse>> arrivedResult = new MediatorLiveData<>();
    private MediatorLiveData<AuthResource<GeneralResponse>> deliveredResult = new MediatorLiveData<>();
    private MediatorLiveData<AuthResource<GeneralResponse>> ordersCurrentCancelledResult = new MediatorLiveData<>();
    private MediatorLiveData<AuthResource<GeneralResponse>> rateResult = new MediatorLiveData<>();
    private MediatorLiveData<AuthResource<GeneralResponse>> ordersCurrentDetailsResult = new MediatorLiveData<>();


    private int pageNumber = 1;
    private boolean isFetchingNextPage, isFetchingExhausted;


    public OrdersViewModel(@NonNull Application application) {
        super(application);
        ordersRepo = OrdersRepo.getInstance();
    }

    public LiveData<AuthResource<GeneralResponse>> orders(double lon, double lat, String order_by, int type) {
        LiveData<AuthApiResponse<GeneralResponse>> orders = ordersRepo.orders(lon, lat, pageNumber, order_by, type, SharedPreferencesHelper.getUserToken(getApplication()), SharedPreferencesHelper.getAppLanguage(getApplication()));
        ordersResult.setValue(AuthResource.loading());
        ordersResult.addSource(orders, it -> {
            ordersResult.removeSource(orders);
            if (it instanceof AuthApiResponse.AuthApiSuccessResponse) {
                ordersResult.setValue(AuthResource.authenticated((GeneralResponse) ((AuthApiResponse.AuthApiSuccessResponse) it).getBody()));
            } else {
                if (((AuthApiResponse.AuthApiErrorResponse) it).getBody() instanceof GeneralResponse) {
                    ordersResult.setValue(AuthResource.error(((AuthApiResponse.AuthApiErrorResponse) it).getErrorMessage(),
                            (GeneralResponse) ((AuthApiResponse.AuthApiErrorResponse) it).getBody(),
                            ((AuthApiResponse.AuthApiErrorResponse) it).getErrorCode()));
                } else {
                    ordersResult.setValue(AuthResource.error(((AuthApiResponse.AuthApiErrorResponse) it).getErrorMessage(),
                            null, ((AuthApiResponse.AuthApiErrorResponse) it).getErrorCode()));
                }
            }
        });
        return ordersResult;
    }

    public LiveData<AuthResource<GeneralResponse>> orderData(int id) {
        LiveData<AuthApiResponse<GeneralResponse>> orderData = ordersRepo.orderData(id, SharedPreferencesHelper.getUserToken(getApplication()), SharedPreferencesHelper.getAppLanguage(getApplication()));
        orderDataResult.setValue(AuthResource.loading());
        orderDataResult.addSource(orderData, it -> {
            orderDataResult.removeSource(orderData);
            if (it instanceof AuthApiResponse.AuthApiSuccessResponse) {
                orderDataResult.setValue(AuthResource.authenticated((GeneralResponse) ((AuthApiResponse.AuthApiSuccessResponse) it).getBody()));
            } else {
                if (((AuthApiResponse.AuthApiErrorResponse) it).getBody() instanceof GeneralResponse) {
                    orderDataResult.setValue(AuthResource.error(((AuthApiResponse.AuthApiErrorResponse) it).getErrorMessage(),
                            (GeneralResponse) ((AuthApiResponse.AuthApiErrorResponse) it).getBody(),
                            ((AuthApiResponse.AuthApiErrorResponse) it).getErrorCode()));
                } else {
                    orderDataResult.setValue(AuthResource.error(((AuthApiResponse.AuthApiErrorResponse) it).getErrorMessage(),
                            null, ((AuthApiResponse.AuthApiErrorResponse) it).getErrorCode()));
                }
            }
        });
        return orderDataResult;
    }

    public LiveData<AuthResource<GeneralResponse>> confirm(int id, int distance) {
        LiveData<AuthApiResponse<GeneralResponse>> confirm = ordersRepo.confirm(id, distance, SharedPreferencesHelper.getUserToken(getApplication()), SharedPreferencesHelper.getAppLanguage(getApplication()));
        confirmResult.setValue(AuthResource.loading());
        confirmResult.addSource(confirm, it -> {
            confirmResult.removeSource(confirm);
            if (it instanceof AuthApiResponse.AuthApiSuccessResponse) {
                confirmResult.setValue(AuthResource.authenticated((GeneralResponse) ((AuthApiResponse.AuthApiSuccessResponse) it).getBody()));
            } else {
                if (((AuthApiResponse.AuthApiErrorResponse) it).getBody() instanceof GeneralResponse) {
                    confirmResult.setValue(AuthResource.error(((AuthApiResponse.AuthApiErrorResponse) it).getErrorMessage(),
                            (GeneralResponse) ((AuthApiResponse.AuthApiErrorResponse) it).getBody(),
                            ((AuthApiResponse.AuthApiErrorResponse) it).getErrorCode()));
                } else {
                    confirmResult.setValue(AuthResource.error(((AuthApiResponse.AuthApiErrorResponse) it).getErrorMessage(),
                            null, ((AuthApiResponse.AuthApiErrorResponse) it).getErrorCode()));
                }
            }
        });
        return confirmResult;
    }

    public LiveData<AuthResource<GeneralResponse>> cancel(int id, String message) {
        LiveData<AuthApiResponse<GeneralResponse>> cancel = ordersRepo.cancel(id, message, SharedPreferencesHelper.getUserToken(getApplication()), SharedPreferencesHelper.getAppLanguage(getApplication()));
        cancelResult.setValue(AuthResource.loading());
        cancelResult.addSource(cancel, it -> {
            cancelResult.removeSource(cancel);
            if (it instanceof AuthApiResponse.AuthApiSuccessResponse) {
                cancelResult.setValue(AuthResource.authenticated((GeneralResponse) ((AuthApiResponse.AuthApiSuccessResponse) it).getBody()));
            } else {
                if (((AuthApiResponse.AuthApiErrorResponse) it).getBody() instanceof GeneralResponse) {
                    cancelResult.setValue(AuthResource.error(((AuthApiResponse.AuthApiErrorResponse) it).getErrorMessage(),
                            (GeneralResponse) ((AuthApiResponse.AuthApiErrorResponse) it).getBody(),
                            ((AuthApiResponse.AuthApiErrorResponse) it).getErrorCode()));
                } else {
                    cancelResult.setValue(AuthResource.error(((AuthApiResponse.AuthApiErrorResponse) it).getErrorMessage(),
                            null, ((AuthApiResponse.AuthApiErrorResponse) it).getErrorCode()));
                }
            }
        });
        return cancelResult;
    }

    public LiveData<AuthResource<GeneralResponse>> waiting(int id) {
        LiveData<AuthApiResponse<GeneralResponse>> waiting = ordersRepo.waiting(id, SharedPreferencesHelper.getUserToken(getApplication()), SharedPreferencesHelper.getAppLanguage(getApplication()));
        waitingResult.setValue(AuthResource.loading());
        waitingResult.addSource(waiting, it -> {
            waitingResult.removeSource(waiting);
            if (it instanceof AuthApiResponse.AuthApiSuccessResponse) {
                waitingResult.setValue(AuthResource.authenticated((GeneralResponse) ((AuthApiResponse.AuthApiSuccessResponse) it).getBody()));
            } else {
                if (((AuthApiResponse.AuthApiErrorResponse) it).getBody() instanceof GeneralResponse) {
                    waitingResult.setValue(AuthResource.error(((AuthApiResponse.AuthApiErrorResponse) it).getErrorMessage(),
                            (GeneralResponse) ((AuthApiResponse.AuthApiErrorResponse) it).getBody(),
                            ((AuthApiResponse.AuthApiErrorResponse) it).getErrorCode()));
                } else {
                    waitingResult.setValue(AuthResource.error(((AuthApiResponse.AuthApiErrorResponse) it).getErrorMessage(),
                            null, ((AuthApiResponse.AuthApiErrorResponse) it).getErrorCode()));
                }
            }
        });
        return waitingResult;
    }

    public LiveData<AuthResource<GeneralResponse>> toCustomer(int id) {
        LiveData<AuthApiResponse<GeneralResponse>> toCustomer = ordersRepo.toCustomer(id, SharedPreferencesHelper.getUserToken(getApplication()), SharedPreferencesHelper.getAppLanguage(getApplication()));
        toCustomerResult.setValue(AuthResource.loading());
        toCustomerResult.addSource(toCustomer, it -> {
            toCustomerResult.removeSource(toCustomer);
            if (it instanceof AuthApiResponse.AuthApiSuccessResponse) {
                toCustomerResult.setValue(AuthResource.authenticated((GeneralResponse) ((AuthApiResponse.AuthApiSuccessResponse) it).getBody()));
            } else {
                if (((AuthApiResponse.AuthApiErrorResponse) it).getBody() instanceof GeneralResponse) {
                    toCustomerResult.setValue(AuthResource.error(((AuthApiResponse.AuthApiErrorResponse) it).getErrorMessage(),
                            (GeneralResponse) ((AuthApiResponse.AuthApiErrorResponse) it).getBody(),
                            ((AuthApiResponse.AuthApiErrorResponse) it).getErrorCode()));
                } else {
                    toCustomerResult.setValue(AuthResource.error(((AuthApiResponse.AuthApiErrorResponse) it).getErrorMessage(),
                            null, ((AuthApiResponse.AuthApiErrorResponse) it).getErrorCode()));
                }
            }
        });
        return toCustomerResult;
    }

    public LiveData<AuthResource<GeneralResponse>> arrived(int id) {
        LiveData<AuthApiResponse<GeneralResponse>> arrived = ordersRepo.arrived(id, SharedPreferencesHelper.getUserToken(getApplication()), SharedPreferencesHelper.getAppLanguage(getApplication()));
        arrivedResult.setValue(AuthResource.loading());
        arrivedResult.addSource(arrived, it -> {
            arrivedResult.removeSource(arrived);
            if (it instanceof AuthApiResponse.AuthApiSuccessResponse) {
                arrivedResult.setValue(AuthResource.authenticated((GeneralResponse) ((AuthApiResponse.AuthApiSuccessResponse) it).getBody()));
            } else {
                if (((AuthApiResponse.AuthApiErrorResponse) it).getBody() instanceof GeneralResponse) {
                    arrivedResult.setValue(AuthResource.error(((AuthApiResponse.AuthApiErrorResponse) it).getErrorMessage(),
                            (GeneralResponse) ((AuthApiResponse.AuthApiErrorResponse) it).getBody(),
                            ((AuthApiResponse.AuthApiErrorResponse) it).getErrorCode()));
                } else {
                    arrivedResult.setValue(AuthResource.error(((AuthApiResponse.AuthApiErrorResponse) it).getErrorMessage(),
                            null, ((AuthApiResponse.AuthApiErrorResponse) it).getErrorCode()));
                }
            }
        });
        return arrivedResult;
    }

    public LiveData<AuthResource<GeneralResponse>> delivered(int id) {
        LiveData<AuthApiResponse<GeneralResponse>> delivered = ordersRepo.delivered(id, SharedPreferencesHelper.getUserToken(getApplication()), SharedPreferencesHelper.getAppLanguage(getApplication()));
        deliveredResult.setValue(AuthResource.loading());
        deliveredResult.addSource(delivered, it -> {
            deliveredResult.removeSource(delivered);
            if (it instanceof AuthApiResponse.AuthApiSuccessResponse) {
                deliveredResult.setValue(AuthResource.authenticated((GeneralResponse) ((AuthApiResponse.AuthApiSuccessResponse) it).getBody()));
            } else {
                if (((AuthApiResponse.AuthApiErrorResponse) it).getBody() instanceof GeneralResponse) {
                    deliveredResult.setValue(AuthResource.error(((AuthApiResponse.AuthApiErrorResponse) it).getErrorMessage(),
                            (GeneralResponse) ((AuthApiResponse.AuthApiErrorResponse) it).getBody(),
                            ((AuthApiResponse.AuthApiErrorResponse) it).getErrorCode()));
                } else {
                    deliveredResult.setValue(AuthResource.error(((AuthApiResponse.AuthApiErrorResponse) it).getErrorMessage(),
                            null, ((AuthApiResponse.AuthApiErrorResponse) it).getErrorCode()));
                }
            }
        });
        return deliveredResult;
    }

    public LiveData<AuthResource<GeneralResponse>> getOrdersCurrentCancelled(String status) {
        LiveData<AuthApiResponse<GeneralResponse>> ordersCurrentCancelled = ordersRepo.getOrdersCurrentCancelled(status, SharedPreferencesHelper.getUserToken(getApplication()), SharedPreferencesHelper.getAppLanguage(getApplication()));
        ordersCurrentCancelledResult.setValue(AuthResource.loading());
        ordersCurrentCancelledResult.addSource(ordersCurrentCancelled, it -> {
            ordersCurrentCancelledResult.removeSource(ordersCurrentCancelled);
            if (it instanceof AuthApiResponse.AuthApiSuccessResponse) {
                ordersCurrentCancelledResult.setValue(AuthResource.authenticated((GeneralResponse) ((AuthApiResponse.AuthApiSuccessResponse) it).getBody()));
            } else {
                if (((AuthApiResponse.AuthApiErrorResponse) it).getBody() instanceof GeneralResponse) {
                    ordersCurrentCancelledResult.setValue(AuthResource.error(((AuthApiResponse.AuthApiErrorResponse) it).getErrorMessage(),
                            (GeneralResponse) ((AuthApiResponse.AuthApiErrorResponse) it).getBody(),
                            ((AuthApiResponse.AuthApiErrorResponse) it).getErrorCode()));
                } else {
                    ordersCurrentCancelledResult.setValue(AuthResource.error(((AuthApiResponse.AuthApiErrorResponse) it).getErrorMessage(),
                            null, ((AuthApiResponse.AuthApiErrorResponse) it).getErrorCode()));
                }
            }
        });
        return ordersCurrentCancelledResult;
    }

    public LiveData<AuthResource<GeneralResponse>> rate(int orderId, String rate, String message) {
        LiveData<AuthApiResponse<GeneralResponse>> result = ordersRepo.rate(orderId, rate, message, SharedPreferencesHelper.getUserToken(getApplication()), SharedPreferencesHelper.getAppLanguage(getApplication()));
        rateResult.setValue(AuthResource.loading());
        rateResult.addSource(result, it -> {
            rateResult.removeSource(result);
            if (it instanceof AuthApiResponse.AuthApiSuccessResponse) {
                rateResult.setValue(AuthResource.authenticated((GeneralResponse) ((AuthApiResponse.AuthApiSuccessResponse) it).getBody()));
            } else {
                if (((AuthApiResponse.AuthApiErrorResponse) it).getBody() instanceof GeneralResponse) {
                    rateResult.setValue(AuthResource.error(((AuthApiResponse.AuthApiErrorResponse) it).getErrorMessage(),
                            (GeneralResponse) ((AuthApiResponse.AuthApiErrorResponse) it).getBody(),
                            ((AuthApiResponse.AuthApiErrorResponse) it).getErrorCode()));
                } else {
                    rateResult.setValue(AuthResource.error(((AuthApiResponse.AuthApiErrorResponse) it).getErrorMessage(),
                            null, ((AuthApiResponse.AuthApiErrorResponse) it).getErrorCode()));
                }
            }
        });
        return rateResult;
    }

    public LiveData<AuthResource<GeneralResponse>> getOrdersCurrentDetails(int id) {
        LiveData<AuthApiResponse<GeneralResponse>> ordersCurrentDetails = ordersRepo.getOrdersCurrentDetails(id, SharedPreferencesHelper.getUserToken(getApplication()), SharedPreferencesHelper.getAppLanguage(getApplication()));
        ordersCurrentDetailsResult.setValue(AuthResource.loading());
        ordersCurrentDetailsResult.addSource(ordersCurrentDetails, it -> {
            if (it instanceof AuthApiResponse.AuthApiSuccessResponse) {
                ordersCurrentDetailsResult.setValue(AuthResource.authenticated((GeneralResponse) ((AuthApiResponse.AuthApiSuccessResponse) it).getBody()));
            } else {
                if (((AuthApiResponse.AuthApiErrorResponse) it).getBody() instanceof GeneralResponse) {
                    ordersCurrentDetailsResult.setValue(AuthResource.error(((AuthApiResponse.AuthApiErrorResponse) it).getErrorMessage(),
                            (GeneralResponse) ((AuthApiResponse.AuthApiErrorResponse) it).getBody(),
                            ((AuthApiResponse.AuthApiErrorResponse) it).getErrorCode()));
                } else {
                    ordersCurrentDetailsResult.setValue(AuthResource.error(((AuthApiResponse.AuthApiErrorResponse) it).getErrorMessage(),
                            null, ((AuthApiResponse.AuthApiErrorResponse) it).getErrorCode()));
                }
            }
        });
        return ordersCurrentDetailsResult;
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

    public void getNextPage(double lon, double lat, String order_by, int type) {
        setFetchingNextPage(true);
        pageNumber++;
        orders(lat, lon, order_by, type);
    }


}
