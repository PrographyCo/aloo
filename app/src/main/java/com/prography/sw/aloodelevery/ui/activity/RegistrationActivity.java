package com.prography.sw.aloodelevery.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.prography.sw.aloodelevery.R;
import com.prography.sw.aloodelevery.custom.BottomSheetDone;
import com.prography.sw.aloodelevery.custom.BottomSheetListText;
import com.prography.sw.aloodelevery.databinding.ActivityRegistrationBinding;

import java.util.ArrayList;
import java.util.List;

public class RegistrationActivity extends AppCompatActivity {

    private ActivityRegistrationBinding binding;
    private BottomSheetListText cityBottomSheetListText, genderBottomSheetListText;
    private List<String> cityList = new ArrayList<>();
    private List<String> genderList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegistrationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        generateFakeData();
        initBottomSheet();
        setListeners();
    }

    private void generateFakeData() {
        cityList.add("Palestine");
        cityList.add("Egypt");
        cityList.add("Turkey");
        cityList.add("Paris");
        cityList.add("Sweden");
        cityList.add("USA");
        cityList.add("UAE");

        genderList.add("Female");
        genderList.add("Male");
        genderList.add("Other");
    }

    private void initBottomSheet() {
        cityBottomSheetListText = new BottomSheetListText(this, (position, action) -> {
            cityBottomSheetListText.dismissDialog();
            binding.tvCity.setText(cityList.get(position));
        });
        cityBottomSheetListText.setList(getString(R.string.city), cityList);
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
            startActivity(new Intent(RegistrationActivity.this, PhoneActivationActivity.class));
        });
        binding.tvImg.setOnClickListener(view -> {
            Toast.makeText(getApplication(), "tvImg", Toast.LENGTH_SHORT).show();
        });
        binding.frameCity.setOnClickListener(view -> {
            cityBottomSheetListText.showDialog();
        });
        binding.frameGender.setOnClickListener(view -> {
            genderBottomSheetListText.showDialog();
        });
    }
}