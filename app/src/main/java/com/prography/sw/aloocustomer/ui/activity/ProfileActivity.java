package com.prography.sw.aloocustomer.ui.activity;

import static com.prography.sw.aloocustomer.util.AppUtils.unauthorized;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.prography.sw.aloocustomer.R;
import com.prography.sw.aloocustomer.custom.BottomSheetListText;
import com.prography.sw.aloocustomer.data.response.GeneralResponse;
import com.prography.sw.aloocustomer.data.response.data.ProfileData;
import com.prography.sw.aloocustomer.databinding.ActivityProfileBinding;
import com.prography.sw.aloocustomer.network.AuthResource;
import com.prography.sw.aloocustomer.util.AppUtils;
import com.prography.sw.aloocustomer.viewmodel.GeneralDataViewModel;
import com.prography.sw.aloocustomer.viewmodel.ProfileViewModel;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;
import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class ProfileActivity extends AppCompatActivity {

    private ActivityProfileBinding binding;
    private BottomSheetListText cityBottomSheetListText, genderBottomSheetListText;
    private List<String> cityList = new ArrayList<>();
    private List<String> genderList = new ArrayList<>();
    private ProfileViewModel profileViewModel;
    private Uri imageUri;
    private GeneralDataViewModel generalDataViewModel;
    private int cityId;
    private String TAG = "ProfileActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initViewModel();
        getProfile();
        getCities();
        initBottomSheet();
        setListeners();

    }

    private void initBottomSheet() {
        cityBottomSheetListText = new BottomSheetListText(this, (position, action) -> {
            cityBottomSheetListText.dismissDialog();
            binding.tvCity.setText(cityList.get(position));
            cityId = position + 1;
        });
        cityBottomSheetListText.setList(getString(R.string.city), cityList);

        genderList.addAll(Arrays.asList(getResources().getStringArray(R.array.gender_array)));
        genderBottomSheetListText = new BottomSheetListText(this, (position, action) -> {
            genderBottomSheetListText.dismissDialog();
            binding.tvGender.setText(genderList.get(position));
        });
        genderBottomSheetListText.setList(getString(R.string.gender), genderList);
    }

    private void setListeners() {
        binding.backImage.setOnClickListener(view -> {
            this.finish();
        });
        binding.btn.setOnClickListener(view -> {
            if (!binding.loadingLayout.isShown())
                updateProfile();
        });
        binding.tvImg.setOnClickListener(view -> {
            getPhoto();
        });
        binding.ivImg.setOnClickListener(view -> {
            getPhoto();
        });
        binding.frameCity.setOnClickListener(view -> {
            cityBottomSheetListText.showDialog();
        });
        binding.frameGender.setOnClickListener(view -> {
            genderBottomSheetListText.showDialog();
        });
    }

    private void initViewModel() {
        profileViewModel = new ViewModelProvider(this,
                new ViewModelProvider.AndroidViewModelFactory(this.getApplication()))
                .get(ProfileViewModel.class);
        generalDataViewModel = new ViewModelProvider(this,
                new ViewModelProvider.AndroidViewModelFactory(getApplication()))
                .get(GeneralDataViewModel.class);
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

        if (profileDate.getImg() == null) {
            binding.ivImg.setVisibility(View.GONE);
            binding.tvImg.setVisibility(View.VISIBLE);
        } else {
            binding.tvImg.setVisibility(View.GONE);
            binding.ivImg.setVisibility(View.VISIBLE);
            binding.ivImg.setClipToOutline(true);
            AppUtils.initGlide(this).load(profileDate.getImg()).into(binding.ivImg);
        }

        binding.etName.setmyText(profileDate.getName());
        binding.etEmail.setmyText(profileDate.getEmail());
        binding.etPhone.setmyText(profileDate.getPhone());
        binding.tvGender.setText(profileDate.getGender());
        binding.tvCity.setText(profileDate.getCity().getName());
        cityId = profileDate.getCity().getId();

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

    public void getPhoto() {

        CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .setAspectRatio(1, 1)
                .start(this);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                imageUri = result.getUri();
                if (imageUri != null) {
                    binding.tvImg.setVisibility(View.GONE);
                    binding.ivImg.setVisibility(View.VISIBLE);
                    binding.ivImg.setClipToOutline(true);
                    AppUtils.initGlide(this).load(imageUri).into(binding.ivImg);
                }
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Toast.makeText(this, getString(R.string.something_went_wrong_text), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void updateProfile() {

        RequestBody name = RequestBody.create(binding.etName.getmyText().toString().trim(), MediaType.parse("text/plain"));
        RequestBody password = RequestBody.create(binding.etPassword.getmyText().toString().trim(), MediaType.parse("text/plain"));
        RequestBody confirmPassword = RequestBody.create(binding.etConfirmPassword.getmyText().toString().trim(), MediaType.parse("text/plain"));
        RequestBody city = RequestBody.create(String.valueOf(cityId), MediaType.parse("text/plain"));
        RequestBody gender = RequestBody.create(binding.tvGender.getText().toString().trim(), MediaType.parse("text/plain"));
        RequestBody phone = RequestBody.create(binding.etPhone.getmyText().toString().trim(), MediaType.parse("text/plain"));
        RequestBody email = RequestBody.create(binding.etEmail.getmyText().toString().trim(), MediaType.parse("text/plain"));

        MultipartBody.Part filePart = null;
        if (imageUri != null) {
            File file = new File(imageUri.getPath());
            RequestBody myFile = RequestBody.create(MediaType.parse("multipart/*"), file);
            filePart = MultipartBody.Part.createFormData("image", file.getName(), myFile);
            Log.d(TAG, "updateProfile: file path " + file.getPath());
        }

        profileViewModel.updateProfile(name, password, confirmPassword, city, gender, phone, email, filePart)
                .observe(this, result -> {
                    switch (result.status) {
                        case LOADING:
                            showUpdateParent(View.GONE);
                            break;
                        case AUTHENTICATED:
                            updateUpdateUi(result.data);
                            break;
                        case ERROR:
                            showUpdateFailed(result);
                            break;
                    }
                });
    }

    private void showUpdateParent(int visibility) {
        if (visibility == View.VISIBLE) {
            binding.loadingBtn.setVisibility(View.GONE);
            binding.btn.setVisibility(View.VISIBLE);
        } else {
            binding.loadingBtn.setVisibility(View.VISIBLE);
            binding.btn.setVisibility(View.GONE);
        }
    }

    private void updateUpdateUi(GeneralResponse response) {
        showUpdateParent(View.VISIBLE);
        Toast.makeText(this, response.getMessage(), Toast.LENGTH_SHORT).show();
    }

    private void showUpdateFailed(AuthResource<GeneralResponse> errorResponse) {
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
        showUpdateParent(View.VISIBLE);
    }

    private void getCities() {
        generalDataViewModel.cities().observe(this, result -> {
            switch (result.status) {
                case LOADING:
                    cityBottomSheetListText.showParent(View.GONE);
                    break;
                case AUTHENTICATED:
                    updateCitiesUi(result.data);
                    generalDataViewModel.removeCitiesObservers(this);
                    break;
                case ERROR:
                    showCitiesFailed(result);
                    generalDataViewModel.removeCitiesObservers(this);
                    break;
            }
        });
    }

    private void updateCitiesUi(GeneralResponse response) {
        cityBottomSheetListText.showParent(View.VISIBLE);
        if (response == null || response.getCitiesData() == null || response.getCitiesData().getCities().isEmpty()) {
            Toast.makeText(this, getString(R.string.something_went_wrong_text), Toast.LENGTH_SHORT).show();
            return;
        }
        cityList.clear();
        for (int i = 0; i < response.getCitiesData().getCities().size(); i++) {
            cityList.add(response.getCitiesData().getCities().get(i).getName());
        }

    }

    private void showCitiesFailed(AuthResource<GeneralResponse> errorResponse) {
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
        cityBottomSheetListText.showParent(View.VISIBLE);
    }

}