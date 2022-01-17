package com.prography.sw.aloodelevery.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.prography.sw.aloodelevery.R;
import com.prography.sw.aloodelevery.custom.BottomSheetDone;
import com.prography.sw.aloodelevery.databinding.ActivityPhoneActivationBinding;
import com.prography.sw.aloodelevery.ui.MainActivity;

public class PhoneActivationActivity extends AppCompatActivity {

    private ActivityPhoneActivationBinding binding;
    private BottomSheetDone bottomSheetDone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPhoneActivationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initBottomSheet();
        setListeners();
    }

    private void initBottomSheet() {
        bottomSheetDone = new BottomSheetDone(this, (position, action) -> {
            Intent i = new Intent(PhoneActivationActivity.this, MainActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
        }, R.drawable.ic_done, getString(R.string.data_sent), getString(R.string.data_verify), getString(R.string.ok));
    }

    private void setListeners() {
        binding.backImage.setOnClickListener(view -> {
            this.finish();
        });
        binding.btn.setOnClickListener(view -> {
            bottomSheetDone.showDialog();
        });
        binding.tvResend.setOnClickListener(view -> {
            Toast.makeText(this, getString(R.string.activation_sent), Toast.LENGTH_SHORT).show();
        });
    }
}