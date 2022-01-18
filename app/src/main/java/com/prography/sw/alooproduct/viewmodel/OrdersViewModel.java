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
import com.prography.sw.alooproduct.repos.OrdersRepo;
import com.prography.sw.alooproduct.util.SharedPreferencesHelper;

public class OrdersViewModel extends AndroidViewModel {

    private OrdersRepo ordersRepo;
    private MediatorLiveData<AuthResource<GeneralResponse>> orderResult = new MediatorLiveData<>();
    private MediatorLiveData<AuthResource<GeneralResponse>> orderByResult = new MediatorLiveData<>();
    private MediatorLiveData<AuthResource<GeneralResponse>> orderDataResult = new MediatorLiveData<>();
    private MediatorLiveData<AuthResource<GeneralResponse>> confirmResult = new MediatorLiveData<>();
    private MediatorLiveData<AuthResource<GeneralResponse>> cancelResult = new MediatorLiveData<>();
    private MediatorLiveData<AuthResource<GeneralResponse>> readyResult = new MediatorLiveData<>();
    private MediatorLiveData<AuthResource<GeneralResponse>> cancelledResult = new MediatorLiveData<>();
    private MediatorLiveData<AuthResource<GeneralResponse>> currentResult = new MediatorLiveData<>();

    private int pageNumber = 1;
    private int myOrderPage = 1;
    private boolean isFetchingNextPage, isFetchingExhausted;
    private boolean isFetchingMyOrderNextPage, isFetchingMyOrderExhausted;

    public OrdersViewModel(@NonNull Application application) {
        super(application);
        ordersRepo = OrdersRepo.getInstance();
    }


    public LiveData<AuthResource<GeneralResponse>> getOrders(String order_by, String search) {
        LiveData<AuthApiResponse<GeneralResponse>> orders = ordersRepo.getOrders(pageNumber, order_by, search, SharedPreferencesHelper.getUserToken(getApplication()), SharedPreferencesHelper.getAppLanguage(getApplication()));
        orderResult.setValue(AuthResource.loading());
        orderResult.addSource(orders, it -> {
            if (it instanceof AuthApiResponse.AuthApiSuccessResponse) {
                orderResult.setValue(AuthResource.authenticated((GeneralResponse) ((AuthApiResponse.AuthApiSuccessResponse) it).getBody()));
            } else {
                if (((AuthApiResponse.AuthApiErrorResponse) it).getBody() instanceof GeneralResponse) {
                    orderResult.setValue(AuthResource.error(((AuthApiResponse.AuthApiErrorResponse) it).getErrorMessage(),
                            (GeneralResponse) ((AuthApiResponse.AuthApiErrorResponse) it).getBody(),
                            ((AuthApiResponse.AuthApiErrorResponse) it).getErrorCode()));
                } else {
                    orderResult.setValue(AuthResource.error(((AuthApiResponse.AuthApiErrorResponse) it).getErrorMessage(),
                            null, ((AuthApiResponse.AuthApiErrorResponse) it).getErrorCode()));
                }
            }
        });
        return orderResult;
    }


    public LiveData<AuthResource<GeneralResponse>> getOrdersData(String id) {
        LiveData<AuthApiResponse<GeneralResponse>> ordersData = ordersRepo.getordersData(id, SharedPreferencesHelper.getUserToken(getApplication()), SharedPreferencesHelper.getAppLanguage(getApplication()));
        orderDataResult.setValue(AuthResource.loading());
        orderDataResult.addSource(ordersData, it -> {
            orderDataResult.removeSource(ordersData);
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


    public LiveData<AuthResource<GeneralResponse>> confirm(int id) {
        LiveData<AuthApiResponse<GeneralResponse>> confirm = ordersRepo.confirm(id, SharedPreferencesHelper.getUserToken(getApplication()), SharedPreferencesHelper.getAppLanguage(getApplication()));
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

    public LiveData<AuthResource<GeneralResponse>> ready(int id) {
        LiveData<AuthApiResponse<GeneralResponse>> ready = ordersRepo.ready(id, SharedPreferencesHelper.getUserToken(getApplication()), SharedPreferencesHelper.getAppLanguage(getApplication()));
        readyResult.setValue(AuthResource.loading());
        readyResult.addSource(ready, it -> {
            readyResult.removeSource(ready);
            if (it instanceof AuthApiResponse.AuthApiSuccessResponse) {
                readyResult.setValue(AuthResource.authenticated((GeneralResponse) ((AuthApiResponse.AuthApiSuccessResponse) it).getBody()));
            } else {
                if (((AuthApiResponse.AuthApiErrorResponse) it).getBody() instanceof GeneralResponse) {
                    readyResult.setValue(AuthResource.error(((AuthApiResponse.AuthApiErrorResponse) it).getErrorMessage(),
                            (GeneralResponse) ((AuthApiResponse.AuthApiErrorResponse) it).getBody(),
                            ((AuthApiResponse.AuthApiErrorResponse) it).getErrorCode()));
                } else {
                    readyResult.setValue(AuthResource.error(((AuthApiResponse.AuthApiErrorResponse) it).getErrorMessage(),
                            null, ((AuthApiResponse.AuthApiErrorResponse) it).getErrorCode()));
                }
            }
        });
        return readyResult;
    }


    public LiveData<AuthResource<GeneralResponse>> getCancelled() {
        LiveData<AuthApiResponse<GeneralResponse>> cancelled = ordersRepo.getCancelled(myOrderPage, SharedPreferencesHelper.getUserToken(getApplication()), SharedPreferencesHelper.getAppLanguage(getApplication()));
        cancelledResult.setValue(AuthResource.loading());
        cancelledResult.addSource(cancelled, it -> {
            if (it instanceof AuthApiResponse.AuthApiSuccessResponse) {
                cancelledResult.setValue(AuthResource.authenticated((GeneralResponse) ((AuthApiResponse.AuthApiSuccessResponse) it).getBody()));
            } else {
                if (((AuthApiResponse.AuthApiErrorResponse) it).getBody() instanceof GeneralResponse) {
                    cancelledResult.setValue(AuthResource.error(((AuthApiResponse.AuthApiErrorResponse) it).getErrorMessage(),
                            (GeneralResponse) ((AuthApiResponse.AuthApiErrorResponse) it).getBody(),
                            ((AuthApiResponse.AuthApiErrorResponse) it).getErrorCode()));
                } else {
                    cancelledResult.setValue(AuthResource.error(((AuthApiResponse.AuthApiErrorResponse) it).getErrorMessage(),
                            null, ((AuthApiResponse.AuthApiErrorResponse) it).getErrorCode()));
                }
            }
        });
        return cancelledResult;
    }

    public void removeCancelledObserver(LifecycleOwner owner) {
        cancelledResult.removeObservers(owner);
    }

    public LiveData<AuthResource<GeneralResponse>> getCurrent() {
        LiveData<AuthApiResponse<GeneralResponse>> current = ordersRepo.getCurrent(myOrderPage, SharedPreferencesHelper.getUserToken(getApplication()), SharedPreferencesHelper.getAppLanguage(getApplication()));
        currentResult.setValue(AuthResource.loading());
        currentResult.addSource(current, it -> {
            if (it instanceof AuthApiResponse.AuthApiSuccessResponse) {
                currentResult.setValue(AuthResource.authenticated((GeneralResponse) ((AuthApiResponse.AuthApiSuccessResponse) it).getBody()));
            } else {
                if (((AuthApiResponse.AuthApiErrorResponse) it).getBody() instanceof GeneralResponse) {
                    currentResult.setValue(AuthResource.error(((AuthApiResponse.AuthApiErrorResponse) it).getErrorMessage(),
                            (GeneralResponse) ((AuthApiResponse.AuthApiErrorResponse) it).getBody(),
                            ((AuthApiResponse.AuthApiErrorResponse) it).getErrorCode()));
                } else {
                    currentResult.setValue(AuthResource.error(((AuthApiResponse.AuthApiErrorResponse) it).getErrorMessage(),
                            null, ((AuthApiResponse.AuthApiErrorResponse) it).getErrorCode()));
                }
            }
        });
        return currentResult;
    }

    public void removeCurrentObserver(LifecycleOwner owner) {
        currentResult.removeObservers(owner);
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

    public void getNextPage(String order_by, String search) {
        setFetchingNextPage(true);
        pageNumber++;
        getOrders(order_by, search);
    }


    public int getMyOrderPageNumber() {
        return myOrderPage;
    }

    public void setMyOrderPageNumber(int pageNumber) {
        this.myOrderPage = pageNumber;
    }

    public boolean isFetchingMyOrderNextPage() {
        return isFetchingMyOrderNextPage;
    }

    public void setFetchingMyOrderNextPage(boolean fetchingNextPage) {
        isFetchingMyOrderNextPage = fetchingNextPage;
    }

    public boolean isFetchingMyOrderExhausted() {
        return isFetchingMyOrderExhausted;
    }

    public void setFetchingMyOrderExhausted(boolean fetchingExhausted) {
        isFetchingMyOrderExhausted = fetchingExhausted;
    }

    public void getMyOrderNextPage(MyOrder myOrder) {
        setFetchingMyOrderNextPage(true);
        myOrderPage++;
        if (myOrder == MyOrder.CURRENT)
            getCurrent();
        else if (myOrder == MyOrder.CANCELLED)
            getCancelled();
    }


    public enum MyOrder {
        CURRENT,
        CANCELLED
    }
}
