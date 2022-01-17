package com.prography.sw.aloocustomer.viewmodel;

import android.app.Application;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;

import com.prography.sw.aloocustomer.R;
import com.prography.sw.aloocustomer.data.request.LoginFormState;
import com.prography.sw.aloocustomer.data.response.GeneralResponse;
import com.prography.sw.aloocustomer.network.AuthApiResponse;
import com.prography.sw.aloocustomer.network.AuthResource;
import com.prography.sw.aloocustomer.repos.LoginRepo;
import com.prography.sw.aloocustomer.util.SharedPreferencesHelper;

import java.util.regex.Pattern;

public class LoginViewModel extends AndroidViewModel {

    private final LoginRepo loginRepo;
    private MediatorLiveData<AuthResource<GeneralResponse>> loginResult = new MediatorLiveData<>();
    private final MutableLiveData<LoginFormState> loginFormState = new MutableLiveData<>();

    public LoginViewModel(@NonNull Application application) {
        super(application);
        loginRepo = LoginRepo.getInstance();
    }

    public LiveData<AuthResource<GeneralResponse>> login(String phone, String password) {
        LiveData<AuthApiResponse<GeneralResponse>> login = loginRepo.login("android",phone, password, SharedPreferencesHelper.getAppLanguage(getApplication()));
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
//    private boolean isValidMobile(String phone) {
//        return android.util.Patterns.PHONE.matcher(phone).matches();
//    }
    private boolean isValidMobile(String phone) {
        if(!Pattern.matches("[a-zA-Z]+", phone)) {
            return phone.length() > 6 && phone.length() <= 13;
        }
        return false;
    }
    // A placeholder password validation check
    private boolean isPasswordValid(String password) {
        return password != null && password.trim().length() > 7;
    }

}
