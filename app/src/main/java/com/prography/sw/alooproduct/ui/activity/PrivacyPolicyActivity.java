package com.prography.sw.alooproduct.ui.activity;

import static com.prography.sw.alooproduct.util.AppUtils.unauthorized;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.prography.sw.alooproduct.R;
import com.prography.sw.alooproduct.data.response.GeneralResponse;
import com.prography.sw.alooproduct.databinding.ActivityPrivacyPolicyBinding;
import com.prography.sw.alooproduct.network.AuthResource;
import com.prography.sw.alooproduct.viewmodel.GeneralDataViewModel;

public class PrivacyPolicyActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityPrivacyPolicyBinding binding;
    private GeneralDataViewModel generalDataViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPrivacyPolicyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initViews();
        intViewModel();
        startPrivacyProcess();
    }

    private void initViews() {
        binding.backImage.setOnClickListener(this);
    }

    private void intViewModel() {
        generalDataViewModel = new ViewModelProvider(this,
                new ViewModelProvider.AndroidViewModelFactory(getApplication())).get(GeneralDataViewModel.class);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == binding.backImage.getId()) {
            this.finish();
        }
    }

    private void startPrivacyProcess() {
        generalDataViewModel.privacy().observe(this, privacyResource -> {
            switch (privacyResource.status) {
                case LOADING:
                    showParent(View.GONE);
                    break;
                case AUTHENTICATED:
                    updateUi(privacyResource.data);
                    generalDataViewModel.removePrivacyObservers(this);
                    break;
                case ERROR:
                    showPrivacyFailed(privacyResource);
                    generalDataViewModel.removePrivacyObservers(this);
                    break;
            }
        });
    }

    private void showParent(int visibility) {
        if (visibility == View.VISIBLE) {
            binding.loading.setVisibility(View.GONE);
            binding.tvPrivacyPolicy.setVisibility(View.VISIBLE);
        } else {
            binding.loading.setVisibility(View.VISIBLE);
            binding.tvPrivacyPolicy.setVisibility(View.GONE);
        }
    }

    private void updateUi(GeneralResponse response) {
        showParent(View.VISIBLE);
        binding.tvPrivacyPolicy.setText(response.getPrivacyData().getData());
    }

    private void showPrivacyFailed(AuthResource<GeneralResponse> errorResponse) {
        switch (errorResponse.errorCode) {
            case 0:
                Toast.makeText(this, getString(R.string.check_internet_connection_text), Toast.LENGTH_SHORT).show();
                break;
            case 500:
                Toast.makeText(this, getString(R.string.something_went_wrong_text), Toast.LENGTH_SHORT).show();
                break;
            case 401:
                unauthorized(this);
                break;
            default:
                Toast.makeText(this, errorResponse.message, Toast.LENGTH_SHORT).show();
                break;
        }
        showParent(View.VISIBLE);
    }

}