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
import com.prography.sw.aloocustomer.adapter.CustomListener;
import com.prography.sw.aloocustomer.custom.BottomSheetListText;
import com.prography.sw.aloocustomer.data.response.GeneralResponse;
import com.prography.sw.aloocustomer.databinding.ActivityRegistrationBinding;
import com.prography.sw.aloocustomer.databinding.ActivityResturantsBinding;
import com.prography.sw.aloocustomer.network.AuthResource;
import com.prography.sw.aloocustomer.ui.MainActivity;
import com.prography.sw.aloocustomer.util.SharedPreferencesHelper;
import com.prography.sw.aloocustomer.viewmodel.GeneralDataViewModel;
import com.prography.sw.aloocustomer.viewmodel.SignupViewModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RegistrationActivity extends AppCompatActivity implements View.OnClickListener {
    private ActivityRegistrationBinding binding;
    private SignupViewModel signupViewModel;
    private GeneralDataViewModel generalDataViewModel;
    private BottomSheetListText cityBottomSheetListText, genderBottomSheetListText;
    private static final String TAG = "RegistrationActivity";
    private List<String> cityNameList = new ArrayList<>();
    private List<String> Gender = new ArrayList<>();
    private String cityId, name, phone, pass, conrirmPass, gender, city;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegistrationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initViews();
        initViewModel();
        initSheetCity();
        initSheetGender();
        getCities();
    }

    private void initViewModel() {
        signupViewModel = new ViewModelProvider(this,
                new ViewModelProvider.AndroidViewModelFactory(getApplication())).get(SignupViewModel.class);

        generalDataViewModel = new ViewModelProvider(this,
                new ViewModelProvider.AndroidViewModelFactory(getApplication())).get(GeneralDataViewModel.class);
    }

    private void initViews() {
        binding.btnSignup.setOnClickListener(this);
        binding.tvCity.setOnClickListener(this);
        binding.tvGender.setOnClickListener(this);
    }

    private void initSheetCity() {
        cityBottomSheetListText = new BottomSheetListText(this, new CustomListener() {
            @Override
            public void onItemClick(int position, String action) {
                binding.tvCity.setText(cityNameList.get(position));
                cityId = String.valueOf(position + 1);
                cityBottomSheetListText.dismissDialog();
            }
        });
        cityBottomSheetListText.setList(getString(R.string.city), cityNameList);
    }

    private void initSheetGender() {
        Gender.addAll(Arrays.asList(getResources().getStringArray(R.array.gender_array)));
        genderBottomSheetListText = new BottomSheetListText(this, new CustomListener() {
            @Override
            public void onItemClick(int position, String action) {
                binding.tvGender.setText(Gender.get(position));
                genderBottomSheetListText.dismissDialog();
            }
        });
        genderBottomSheetListText.setList(getString(R.string.gender), Gender);
    }


    @Override
    public void onClick(View view) {
        if (view.getId() == binding.btnSignup.getId()) {
            validateData();
            //  startActivity(new Intent(RegistrationActivity.this, PhoneActivationActivity.class));
        } else if (view.getId() == binding.tvCity.getId()) {
            cityBottomSheetListText.showDialog();
        } else if (view.getId() == binding.tvGender.getId()) {
            genderBottomSheetListText.showDialog();
        }
    }


    private void validateData() {
        name = binding.etName.getmyText().toString().trim();
        phone = binding.etPhone.getmyText().toString().trim();
        pass = binding.etPassword.getmyText().toString().trim();
        conrirmPass = binding.etConfirmPassword.getmyText().toString().trim();
        gender = binding.tvGender.getText().toString().trim();
        city = binding.tvCity.getText().toString().trim();

        Log.d(TAG, "validateData: " + name);
        Log.d(TAG, "validateData: " + phone);
        Log.d(TAG, "validateData: " + pass);
        Log.d(TAG, "validateData: " + conrirmPass);
        Log.d(TAG, "validateData: " + cityId);
        Log.d(TAG, "validateData: " + gender);

        signupViewModel.signUpDataChanged(name
                , phone
                , pass
                , conrirmPass
                , city
                , gender);
        signupViewModel.getSignUpFormState().observe(this, signupFormState -> {
            if (signupFormState == null) {
                return;
            }
            if (signupFormState.getFirstNameError() != null) {
                Toast.makeText(getApplication(), getString(R.string.invalid_first_name), Toast.LENGTH_SHORT).show();
            }
            if (signupFormState.getPhoneError() != null) {
                Toast.makeText(getApplication(), getString(R.string.invalid_phone), Toast.LENGTH_SHORT).show();
            }
            if (signupFormState.getPasswordError() != null) {
                Toast.makeText(getApplication(), getString(R.string.invalid_password), Toast.LENGTH_SHORT).show();
            }
            if (signupFormState.getConfirmPasswordError() != null) {
                Toast.makeText(getApplication(), getString(R.string.invalid_confirm_password), Toast.LENGTH_SHORT).show();
            }
            if (signupFormState.getCountryError() != null) {
                Toast.makeText(getApplication(), getString(R.string.invalid_country), Toast.LENGTH_SHORT).show();
            }
            if (signupFormState.getGenderError() != null) {
                Toast.makeText(getApplication(), getString(R.string.invalid_gender), Toast.LENGTH_SHORT).show();
            }
            if (signupFormState.isDataValid()) {
                startSignUpProcess();
            }
        });
    }

    private void startSignUpProcess() {
        signupViewModel.signup(name, phone, gender, cityId, pass, conrirmPass).observe(this,
                signupResult -> {
                    switch (signupResult.status) {
                        case LOADING:
                            showParent(View.GONE);
                            break;
                        case AUTHENTICATED:
                            updateUi(signupResult.data);
                            signupViewModel.removeSignUpObservers(this);
                            break;
                        case ERROR:
                            showSignupFailed(signupResult);
                            signupViewModel.removeSignUpObservers(this);
                            break;
                    }
                });
    }

    private void updateUi(GeneralResponse response) {
        Intent intent = new Intent(RegistrationActivity.this, PhoneActivationActivity.class);
        intent.putExtra("phone", response.getSignupData().getPhone());
        startActivity(intent);
        finish();
    }

    private void showSignupFailed(AuthResource<GeneralResponse> errorResponse) {
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
        cityNameList.clear();
        for (int i = 0; i < response.getCitiesData().getCities().size(); i++) {
            cityNameList.add(response.getCitiesData().getCities().get(i).getName());
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
                startActivity(new Intent(this, LoginActivity.class));
                break;
            default:
                Toast.makeText(this, errorResponse.message, Toast.LENGTH_SHORT).show();
                break;
        }
        cityBottomSheetListText.showParent(View.VISIBLE);
    }

    private void showParent(int visibility) {

        if (visibility == View.VISIBLE) {
            binding.loading.setVisibility(View.GONE);
            binding.btnSignup.setVisibility(View.VISIBLE);

        } else {
            binding.loading.setVisibility(View.VISIBLE);
            binding.btnSignup.setVisibility(View.GONE);

        }
    }


}