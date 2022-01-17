package com.prography.sw.aloocustomer.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.prography.sw.aloocustomer.R;
import com.prography.sw.aloocustomer.data.response.GeneralResponse;
import com.prography.sw.aloocustomer.databinding.ActivityLoginBinding;
import com.prography.sw.aloocustomer.network.AuthResource;
import com.prography.sw.aloocustomer.ui.MainActivity;
import com.prography.sw.aloocustomer.util.SharedPreferencesHelper;
import com.prography.sw.aloocustomer.viewmodel.LoginViewModel;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityLoginBinding binding;
    private LoginViewModel loginViewModel;
    private static final String TAG = "LoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initViews();
        initViewModel();
    }

    private void initViewModel() {
        loginViewModel = new ViewModelProvider(this,
                new ViewModelProvider.AndroidViewModelFactory(this.getApplication()))
                .get(LoginViewModel.class);
    }

    private void initViews() {
        binding.btnLogin.setOnClickListener(this);
        binding.tvRegister.setOnClickListener(this);
        binding.tvForgotPassword.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == binding.btnLogin.getId()) {
            validateData();
        } else if (v.getId() == binding.tvRegister.getId()) {
            startActivity(new Intent(LoginActivity.this, RegistrationActivity.class));
        } else if (v.getId() == binding.tvForgotPassword.getId()) {
            Toast.makeText(getApplication(), "forgot password", Toast.LENGTH_SHORT).show();
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
                    Log.d(TAG, "data: " + result.data.getLoginData().getName());
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
            binding.layoutRegister.setVisibility(View.VISIBLE);
        } else {
            binding.loading.setVisibility(View.VISIBLE);
            binding.btnLogin.setVisibility(View.GONE);
            binding.layoutRegister.setVisibility(View.GONE);
        }
    }

    private void updateUi(GeneralResponse response) {
        SharedPreferencesHelper.setUserToken(this, response.getLoginData().getToken().getTokenType() + " " + response.getLoginData().getToken().getAccessToken());
        Log.d(TAG, "updateUi: token" + response.getLoginData().getToken().getAccessToken());
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