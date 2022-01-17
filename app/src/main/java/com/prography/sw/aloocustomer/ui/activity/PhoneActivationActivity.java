package com.prography.sw.aloocustomer.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.prography.sw.aloocustomer.R;
import com.prography.sw.aloocustomer.data.response.GeneralResponse;
import com.prography.sw.aloocustomer.databinding.ActivityPhoneActivationBinding;
import com.prography.sw.aloocustomer.model.Main;
import com.prography.sw.aloocustomer.network.AuthResource;
import com.prography.sw.aloocustomer.ui.MainActivity;
import com.prography.sw.aloocustomer.util.AppUtils;
import com.prography.sw.aloocustomer.util.SharedPreferencesHelper;
import com.prography.sw.aloocustomer.viewmodel.SignupViewModel;
import com.prography.sw.aloocustomer.viewmodel.VerifyViewModel;

public class PhoneActivationActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "PhoneActivationActivity";
    private ActivityPhoneActivationBinding binding;
    private VerifyViewModel verifyViewModel;
    private String phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPhoneActivationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initViews();
        initViewModel();
        getIntentData();

    }

    private void initViews() {
        binding.tvResend.setOnClickListener(this);
        binding.btn.setOnClickListener(this);
    }

    private void initViewModel() {
        verifyViewModel = new ViewModelProvider(this,
                new ViewModelProvider.AndroidViewModelFactory(getApplication())).get(VerifyViewModel.class);

    }

    private void getIntentData() {
        phone = getIntent().getStringExtra("phone");
    }

    private void startVerifyProcess() {
        verifyViewModel.verify(phone, binding.etCode.getmyText().toString().trim()).observe(this, new Observer<AuthResource<GeneralResponse>>() {
            @Override
            public void onChanged(AuthResource<GeneralResponse> verifyResource) {
                switch (verifyResource.status) {
                    case LOADING:
                        showParent(View.GONE);
                        break;
                    case AUTHENTICATED:
                        updateUi(verifyResource.data);
                        showParent(View.VISIBLE);
                        break;
                    case ERROR:
                        showVerifyFailed(verifyResource);
                        break;
                }
            }
        });
    }

    private void startResendCodeProcess() {
        verifyViewModel.resendCode(phone).observe(this, resendCodeResource -> {
            switch (resendCodeResource.status) {
                case LOADING:
                    showParent(View.GONE);
                    break;
                case AUTHENTICATED:
                    Toast.makeText(this, getString(R.string.resend_code), Toast.LENGTH_SHORT).show();
                    showParent(View.VISIBLE);
                    break;
                case ERROR:
                    showVerifyFailed(resendCodeResource);
                    break;
            }
        });
    }


    private void showParent(int visibility) {

        if (visibility == View.VISIBLE) {
            binding.loading.setVisibility(View.GONE);
            binding.contener.setVisibility(View.VISIBLE);
            binding.btn.setVisibility(View.VISIBLE);

        } else {
            binding.loading.setVisibility(View.VISIBLE);
            binding.btn.setVisibility(View.GONE);
            binding.contener.setVisibility(View.GONE);

        }
    }

    private void updateUi(GeneralResponse response) {
        SharedPreferencesHelper.setUserToken(this, response.getVerificationData().getToken().getTokenType() + " " + response.getLoginData().getToken().getAccessToken());
        Log.d(TAG, "updateUi: token" + response.getLoginData().getToken().getAccessToken());
        Intent intent = new Intent(PhoneActivationActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void showVerifyFailed(AuthResource<GeneralResponse> errorResponse) {
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

    @Override
    public void onClick(View view) {

        if (view.getId() == binding.btn.getId()) {
            startVerifyProcess();
        } else if (view.getId() == binding.tvResend.getId()) {
            startResendCodeProcess();
        }

    }


}