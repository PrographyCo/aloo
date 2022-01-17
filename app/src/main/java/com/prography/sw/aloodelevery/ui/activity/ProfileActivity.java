package com.prography.sw.aloodelevery.ui.activity;

import static com.prography.sw.aloodelevery.util.AppUtils.unauthorized;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.prography.sw.aloodelevery.R;
import com.prography.sw.aloodelevery.data.response.GeneralResponse;
import com.prography.sw.aloodelevery.data.response.data.ProfileData;
import com.prography.sw.aloodelevery.databinding.ActivityProfileBinding;
import com.prography.sw.aloodelevery.network.AuthResource;
import com.prography.sw.aloodelevery.util.AppUtils;
import com.prography.sw.aloodelevery.viewmodel.ProfileViewModel;

public class ProfileActivity extends AppCompatActivity {

    private ActivityProfileBinding binding;
    private ProfileViewModel profileViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initViewModel();
        getProfile();
        setListeners();

    }

    private void setListeners() {
        binding.backImage.setOnClickListener(view -> {
            this.finish();
        });
    }

    private void initViewModel() {
        profileViewModel = new ViewModelProvider(this,
                new ViewModelProvider.AndroidViewModelFactory(this.getApplication()))
                .get(ProfileViewModel.class);
    }

    private void getProfile() {
        profileViewModel.getProfile().observe(this, result -> {
            switch (result.status) {
                case LOADING:
                    showParent(View.GONE);
                    break;
                case AUTHENTICATED:
                    updateUi(result.data);
                    profileViewModel.removeProfileObservers(this);
                    break;
                case ERROR:
                    showFailed(result);
                    profileViewModel.removeProfileObservers(this);
                    break;
            }
        });
    }

    private void showParent(int visibility) {
        if (visibility == View.VISIBLE) {
            binding.loadingLayout.setVisibility(View.GONE);
            binding.layout.setVisibility(View.VISIBLE);
        } else {
            binding.loadingLayout.setVisibility(View.VISIBLE);
            binding.layout.setVisibility(View.GONE);
        }
    }

    private void updateUi(GeneralResponse response) {
        showParent(View.VISIBLE);
        if (response == null || response.getProfileData() == null) {
            Toast.makeText(this, getString(R.string.something_went_wrong_text), Toast.LENGTH_SHORT).show();
            return;
        }

        ProfileData profileDate = response.getProfileData();

        binding.ivImg.setClipToOutline(true);
        AppUtils.initGlide(this).load(profileDate.getImg()).into(binding.ivImg);
        binding.tvName.setText(profileDate.getName());
        binding.tvId.setText(profileDate.getIdNumber());
        binding.tvPhone.setText(profileDate.getPhone());
        binding.tvEmail.setText(profileDate.getEmail());
        binding.tvGender.setText(profileDate.getGender());
        binding.tvCity.setText(profileDate.getCity());

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
                unauthorized(this);
                break;
            default:
                Toast.makeText(this, errorResponse.message, Toast.LENGTH_SHORT).show();
                break;
        }
        showParent(View.VISIBLE);
    }
}