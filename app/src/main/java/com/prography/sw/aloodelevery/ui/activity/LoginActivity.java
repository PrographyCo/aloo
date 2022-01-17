package com.prography.sw.aloodelevery.ui.activity;

import static com.github.vipulasri.timelineview.TimelineView.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Toast;

import com.prography.sw.aloodelevery.R;
import com.prography.sw.aloodelevery.data.response.GeneralResponse;
import com.prography.sw.aloodelevery.databinding.ActivityLoginBinding;
import com.prography.sw.aloodelevery.network.AuthResource;
import com.prography.sw.aloodelevery.ui.MainActivity;
import com.prography.sw.aloodelevery.util.AppUtils;
import com.prography.sw.aloodelevery.util.SharedPreferencesHelper;
import com.prography.sw.aloodelevery.viewmodel.LoginViewModel;

import java.util.Locale;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

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
        checkToken();

    }



    public void changeLang(String lang) {
        Toast.makeText(this, SharedPreferencesHelper.getAppLanguage(this), Toast.LENGTH_SHORT).show();
        Locale myLocale = new Locale(lang);
        Locale.setDefault(myLocale);
        android.content.res.Configuration config = new android.content.res.Configuration();
        config.locale = myLocale;
        getResources().updateConfiguration(config, getResources().getDisplayMetrics());
    }


    private void checkToken() {
        if (!SharedPreferencesHelper.getUserToken(this).isEmpty()) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }

    private void initViewModel() {
        loginViewModel = new ViewModelProvider(this,
                new ViewModelProvider.AndroidViewModelFactory(this.getApplication()))
                .get(LoginViewModel.class);
    }

    private void initViews() {
        binding.btnLogin.setOnClickListener(this);
        binding.entryNow.setOnClickListener(this);
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
        Log.d(TAG, "updateUi: token" + response.getLoginData().getToken().getAccessToken());
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
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
                AppUtils.unauthorized(this);
                break;
            default:
                Toast.makeText(this, errorResponse.message, Toast.LENGTH_SHORT).show();
                break;
        }
        showParent(View.VISIBLE);
    }

    private void startLoginProcess() {
        loginViewModel.login(binding.etPhone.getmyText().toString().trim(), binding.etPassword.getmyText().toString()).observe(this, result -> {
            switch (result.status) {
                case LOADING:
                    showParent(View.GONE);
                    break;
                case AUTHENTICATED:
                    Log.d(TAG, "data: " + result.data.getLoginData().getName());
                    updateUi(result.data);
                    break;
                case ERROR:
                    showFailed(result);
                    break;
            }
        });
    }


    @Override
    public void onClick(View view) {
        if (view.getId() == binding.btnLogin.getId()) {
            validateData();
        } else if (view.getId() == binding.entryNow.getId()) {
            WebView myWebView = new WebView(this);
            setContentView(myWebView);
            myWebView.loadUrl("https://aloo-app.prography.co/driver-register");
        }
    }
}