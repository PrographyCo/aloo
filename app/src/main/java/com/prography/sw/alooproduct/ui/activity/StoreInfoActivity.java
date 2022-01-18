package com.prography.sw.alooproduct.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.prography.sw.alooproduct.R;
import com.prography.sw.alooproduct.custom.BottomSheetListRadio;
import com.prography.sw.alooproduct.custom.BottomSheetListText;
import com.prography.sw.alooproduct.data.response.GeneralResponse;
import com.prography.sw.alooproduct.data.response.data.ProfileData;
import com.prography.sw.alooproduct.databinding.ActivityStoreInfoBinding;
import com.prography.sw.alooproduct.model.Radio;
import com.prography.sw.alooproduct.network.AuthResource;
import com.prography.sw.alooproduct.util.SharedPreferencesHelper;
import com.prography.sw.alooproduct.viewmodel.GeneralDataViewModel;
import com.prography.sw.alooproduct.viewmodel.ProfileViewModel;

import java.util.ArrayList;
import java.util.List;

public class StoreInfoActivity extends AppCompatActivity {

    private ActivityStoreInfoBinding binding;
    private ProfileViewModel profileViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityStoreInfoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setListeners();
        initViewModel();
        getProfile();
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
                    Log.d("TAG", "data: " + result.data.getLoginData().getName());
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

        binding.tvLegalName.setText(profileDate.getLegalName());
        binding.tvTradeName.setText(profileDate.getBrandName());
        binding.tvCommercialNo.setText(profileDate.getCommercialNumber());
        binding.tvType.setText(profileDate.getType());
        binding.tvCity.setText(profileDate.getCityName());
        if (SharedPreferencesHelper.isRestaurant(this)) {
            binding.layoutRestaurant.setVisibility(View.VISIBLE);
            binding.tvRestaurantType.setText(profileDate.getRestaurantType());
            binding.tvKitchenType.setText(profileDate.getKitchenType());
        } else {
            binding.layoutRestaurant.setVisibility(View.GONE);
        }
        binding.tvStoreDescription.setText(profileDate.getDescription());
        binding.tvMinimumOrder.setText(String.valueOf(profileDate.getMinPrice()));
        binding.tvEmail.setText(profileDate.getEmail());

        binding.tvManagerName.setText(profileDate.getManager());
        binding.tvManagerPosition.setText(profileDate.getManagerPosition());
        binding.tvManagerPhone.setText(profileDate.getManagerPhone());
        binding.tvManagerEmail.setText(profileDate.getManagerEmail());

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

    private void setListeners() {
        binding.backImage.setOnClickListener(view -> {
            this.finish();
        });
    }
}