package com.prography.sw.aloodelevery.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.prography.sw.aloodelevery.R;
import com.prography.sw.aloodelevery.databinding.ActivityRegistrationConditionsBinding;

public class RegistrationConditionsActivity extends AppCompatActivity {

    private ActivityRegistrationConditionsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityRegistrationConditionsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setListeners();
    }

    private void setListeners() {
        binding.backImage.setOnClickListener(view -> {
            this.finish();
        });
        binding.btn.setOnClickListener(view -> {
            if (binding.rbConditions.isChecked())
                startActivity(new Intent(RegistrationConditionsActivity.this, RegistrationActivity.class));
            else
                Toast.makeText(this, getString(R.string.toast_agree_conditions), Toast.LENGTH_SHORT).show();
        });
    }
}