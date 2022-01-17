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
import com.prography.sw.aloocustomer.data.request.SignUpFormState;
import com.prography.sw.aloocustomer.data.response.GeneralResponse;
import com.prography.sw.aloocustomer.network.AuthApiResponse;
import com.prography.sw.aloocustomer.network.AuthResource;
import com.prography.sw.aloocustomer.repos.SignupRepo;
import com.prography.sw.aloocustomer.util.SharedPreferencesHelper;

import java.util.regex.Pattern;

public class SignupViewModel extends AndroidViewModel {
    private SignupRepo signupRepo;
    private MediatorLiveData<AuthResource<GeneralResponse>> signupResult = new MediatorLiveData<>();
    private final MutableLiveData<SignUpFormState> signupFormState = new MutableLiveData<>();

    public SignupViewModel(@NonNull Application application) {
        super(application);
        signupRepo = SignupRepo.getInstance();
    }

    public LiveData<AuthResource<GeneralResponse>> signup(String name, String phone,
                                                          String gender, String city, String password,
                                                          String password_confirmation) {
        LiveData<AuthApiResponse<GeneralResponse>> signup = signupRepo.signup(name, phone, gender, city, password, password_confirmation, SharedPreferencesHelper.getAppLanguage(getApplication()));
        signupResult.setValue(AuthResource.loading());
        signupResult.addSource(signup, it -> {
            signupResult.removeSource(signup);
            if (it instanceof AuthApiResponse.AuthApiSuccessResponse) {
                signupResult.setValue(AuthResource.authenticated((GeneralResponse) ((AuthApiResponse.AuthApiSuccessResponse) it).getBody()));
            } else {
                if (((AuthApiResponse.AuthApiErrorResponse) it).getBody() instanceof GeneralResponse) {
                    signupResult.setValue(AuthResource.error(((AuthApiResponse.AuthApiErrorResponse) it).getErrorMessage(),
                            (GeneralResponse) ((AuthApiResponse.AuthApiErrorResponse) it).getBody(),
                            ((AuthApiResponse.AuthApiErrorResponse) it).getErrorCode()));
                } else {
                    signupResult.setValue(AuthResource.error(((AuthApiResponse.AuthApiErrorResponse) it).getErrorMessage(),
                            null, ((AuthApiResponse.AuthApiErrorResponse) it).getErrorCode()));
                }
            }
        });
        return signupResult;
    }

    public LiveData<SignUpFormState> getSignUpFormState() {
        return signupFormState;
    }

    public void signUpDataChanged(String name, String phone, String password, String confirmPassword, String country, String gender) {
        if (TextUtils.isEmpty(name) || name.length() < 3) {
            signupFormState.setValue(new SignUpFormState(R.string.invalid_first_name, null, null, null, null, null));
        } else if (TextUtils.isEmpty(phone) || !isValidMobile(phone)) {
            signupFormState.setValue(new SignUpFormState(null, R.string.invalid_phone, null, null, null, null));
        } else if (!isPasswordValid(password)) {
            signupFormState.setValue(new SignUpFormState(null, null, R.string.invalid_password, null, null, null));
        } else if (!password.equals(confirmPassword)) {
            signupFormState.setValue(new SignUpFormState(null, null, null, R.string.invalid_confirm_password, null, null));
        } else if (TextUtils.isEmpty(country) || country.equals(getApplication().getString(R.string.country))) {
            signupFormState.setValue(new SignUpFormState(null, null, null, null, R.string.invalid_country, null));
        } else if (TextUtils.isEmpty(gender) || country.equals(getApplication().getString(R.string.gender))) {
            signupFormState.setValue(new SignUpFormState(null, null, null, null, null, R.string.invalid_gender));
        } else {
            signupFormState.setValue(new SignUpFormState(true));
        }
    }


    // A placeholder mobile validation check
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

    public void removeSignUpObservers(LifecycleOwner lifecycleOwner) {
        signupResult.removeObservers(lifecycleOwner);
    }

    //    public void removeGenderObservers(LifecycleOwner lifecycleOwner) {
//        genderResult.removeObservers(lifecycleOwner);
//    }
//
//    public void removeCountryObservers(LifecycleOwner lifecycleOwner) {
//        countryResult.removeObservers(lifecycleOwner);
//    }

}
