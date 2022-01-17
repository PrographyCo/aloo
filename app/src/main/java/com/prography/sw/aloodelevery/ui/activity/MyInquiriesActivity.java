package com.prography.sw.aloodelevery.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.prography.sw.aloodelevery.databinding.ActivityMyInquiriesBinding;

public class MyInquiriesActivity extends AppCompatActivity {
    private ActivityMyInquiriesBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMyInquiriesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initViews();
    }

    private void initViews() {
        binding.backImage.setOnClickListener(view -> {
            this.finish();
        });
    }
}