package com.prography.sw.alooproduct.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.prography.sw.alooproduct.R;
import com.prography.sw.alooproduct.data.response.GeneralResponse;
import com.prography.sw.alooproduct.databinding.ActivityLoginBinding;
import com.prography.sw.alooproduct.network.AuthResource;
import com.prography.sw.alooproduct.util.SharedPreferencesHelper;
import com.prography.sw.alooproduct.viewmodel.LoginViewModel;

import java.util.Locale;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = LoginActivity.class.getSimpleName();
    private ActivityLoginBinding binding;
    private LoginViewModel loginViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initViews();
        changeLang(SharedPreferencesHelper.getAppLanguage(this));
        initViewModel();
        checkData();
    }


    public void changeLang(String lang) {
        Toast.makeText(this, SharedPreferencesHelper.getAppLanguage(this), Toast.LENGTH_SHORT).show();
        Locale myLocale = new Locale(lang);
        Locale.setDefault(myLocale);
        android.content.res.Configuration config = new android.content.res.Configuration();
        config.locale = myLocale;
        getResources().updateConfiguration(config, getResources().getDisplayMetrics());
    }

    private void checkData() {
        if (!SharedPreferencesHelper.getUserToken(this).isEmpty()) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }

    private void initViews() {
        binding.backImage.setOnClickListener(this);
        binding.btnLogin.setOnClickListener(this);
    }

    private void initViewModel() {
        loginViewModel = new ViewModelProvider(this,
                new ViewModelProvider.AndroidViewModelFactory(this.getApplication()))
                .get(LoginViewModel.class);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == binding.backImage.getId()) {
            this.finish();
        } else if (view.getId() == binding.btnLogin.getId()) {
            validateData();
        }
    }

    private void validateData() {
        loginViewModel.loginDataChanged(binding.etPhone.getmyText().toString().trim(), binding.etPassword.getmyText().toString());
        loginViewModel.getLoginFormState().observe(this, loginFormState -> {
            if (loginFormState == null) {
                return;
            }
            if (loginFormState.getPhoneError() != null) {
                Toast.makeText(getApplication(), getString(R.string.invalid_phone), Toast.LENGTH_SHORT).show();
            }
            if (loginFormState.getPasswordError() != null) {
                Toast.makeText(getApplication(), getString(R.string.invalid_password), Toast.LENGTH_SHORT).show();
            }
            if (loginFormState.isDataValid()) {
                startLoginProcess();
            }
        });
    }

    private void startLoginProcess() {
        loginViewModel.login(binding.etPhone.getmyText().toString().trim(), binding.etPassword.getmyText().toString()).observe(this, result -> {
            switch (result.status) {
                case LOADING:
                    showParent(View.GONE);
                    break;
                case AUTHENTICATED:
                    Log.d(TAG, "startLoginProcess: " + result.data.getLoginData().getToken().getAccessToken());
                    updateUi(result.data);
                    break;
                case ERROR:
                    showFailed(result);
                    break;
            }
        });
    }

    private void showParent(int visibility) {
        if (visibility == View.VISIBLE) {
            binding.loading.setVisibility(View.GONE);
            binding.btnLogin.setVisibility(View.VISIBLE);

        } else {
            binding.loading.setVisibility(View.VISIBLE);
            binding.btnLogin.setVisibility(View.GONE);

        }
    }

    private void updateUi(GeneralResponse response) {
        SharedPreferencesHelper.setUserToken(this, response.getLoginData().getToken().getTokenType() + " " + response.getLoginData().getToken().getAccessToken());
        SharedPreferencesHelper.isRestaurant(this, response.getLoginData().getRestaurant());
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void showFailed(AuthResource<GeneralResponse> errorResponse) {
        switch (errorResponse.errorCode) {
            case 0:
                Toast.makeText(this, getString(R.string.check_internet_connection_text), Toast.LENGTH_SHORT).show();
                break;
            case 500:
                Toast.makeText(this, getString(R.string.something_went_wrong_text), Toast.LENGTH_SHORT).show();
                break;
            case 401:
                startActivity(new Intent(this, LoginActivity.class));
                break;
            default:
                Toast.makeText(this, errorResponse.message, Toast.LENGTH_SHORT).show();
                break;
        }
        showParent(View.VISIBLE);
    }

}