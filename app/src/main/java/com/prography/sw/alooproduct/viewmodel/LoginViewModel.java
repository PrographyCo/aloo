package com.prography.sw.alooproduct.viewmodel;

import android.app.Application;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;

import com.prography.sw.alooproduct.R;
import com.prography.sw.alooproduct.data.request.LoginFormState;
import com.prography.sw.alooproduct.data.response.GeneralResponse;
import com.prography.sw.alooproduct.network.AuthApiResponse;
import com.prography.sw.alooproduct.network.AuthResource;
import com.prography.sw.alooproduct.repos.LoginRepo;
import com.prography.sw.alooproduct.util.SharedPreferencesHelper;

public class LoginViewModel extends AndroidViewModel {

    private final LoginRepo loginRepo;
    private MediatorLiveData<AuthResource<GeneralResponse>> loginResult = new MediatorLiveData<>();
    private final MutableLiveData<LoginFormState> loginFormState = new MutableLiveData<>();

    public LoginViewModel(@NonNull Application application) {
        super(application);
        loginRepo = LoginRepo.getInstance();
    }

    public LiveData<AuthResource<GeneralResponse>> login(String lohin_number, String password) {
        LiveData<AuthApiResponse<GeneralResponse>> login = loginRepo.login("android",lohin_number, password, SharedPreferencesHelper.getAppLanguage(getApplication()));
        loginResult.setValue(AuthResource.loading());
        loginResult.addSource(login, it -> {
            loginResult.removeSource(login);
            if (it instanceof AuthApiResponse.AuthApiSuccessResponse) {
                loginResult.setValue(AuthResource.authenticated((GeneralResponse) ((AuthApiResponse.AuthApiSuccessResponse) it).getBody()));
            } else {
                if (((AuthApiResponse.AuthApiErrorResponse) it).getBody() instanceof GeneralResponse) {
                    loginResult.setValue(AuthResource.error(((AuthApiResponse.AuthApiErrorResponse) it).getErrorMessage(),
                            (GeneralResponse) ((AuthApiResponse.AuthApiErrorResponse) it).getBody(),
                            ((AuthApiResponse.AuthApiErrorResponse) it).getErrorCode()));
                } else {
                    loginResult.setValue(AuthResource.error(((AuthApiResponse.AuthApiErrorResponse) it).getErrorMessage(),
                            null, ((AuthApiResponse.AuthApiErrorResponse) it).getErrorCode()));
                }
            }
        });
        return loginResult;
    }

    public void removeLoginObservers(LifecycleOwner lifecycleOwner) {
        loginResult.removeObservers(lifecycleOwner);
    }

    public LiveData<LoginFormState> getLoginFormState() {
        return loginFormState;
    }

    public void loginDataChanged(String phone, String password) {
        if (TextUtils.isEmpty(phone) || !isValidMobile(phone)) {
            loginFormState.setValue(new LoginFormState(R.string.invalid_phone, null));
        } else if (!isPasswordValid(password)) {
            loginFormState.setValue(new LoginFormState(null, R.string.invalid_password));
        } else {
            loginFormState.setValue(new LoginFormState(true));
        }
    }

    // A placeholder mobile validation check
    private boolean isValidMobile(String phone) {
        return android.util.Patterns.PHONE.matcher(phone).matches();
    }

    // A placeholder password validation check
    private boolean isPasswordValid(String password) {
        return password != null && password.trim().length() > 7;
    }

}
