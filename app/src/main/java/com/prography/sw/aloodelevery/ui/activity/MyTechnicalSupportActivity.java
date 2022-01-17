package com.prography.sw.aloodelevery.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.prography.sw.aloodelevery.R;
import com.prography.sw.aloodelevery.databinding.ActivityMyTechnicalSupportBinding;
import com.prography.sw.aloodelevery.viewmodel.GeneralDataViewModel;

public class MyTechnicalSupportActivity extends AppCompatActivity implements View.OnClickListener{
    private ActivityMyTechnicalSupportBinding binding;
    private GeneralDataViewModel generalDataViewModel;
    private String key;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMyTechnicalSupportBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initViews();
        intViewModel();
        getIntentData();
    }
    private void getIntentData() {
        key = getIntent().getStringExtra("key");
        if (key.equals("complaint")) {
            binding.btnQuestion.setText(R.string.sendComplaint);
        } else {
            binding.btnQuestion.setText(R.string.sendToSupport);
        }
    }
    private void intViewModel() {
        generalDataViewModel = new ViewModelProvider(this,
                new ViewModelProvider.AndroidViewModelFactory(getApplication())).get(GeneralDataViewModel.class);
    }

    private void startClientServiceProcess() {
        generalDataViewModel.clientService("support", binding.etQuestion.getmyText().toString()).observe(this, resource -> {
            switch (resource.status) {
                case LOADING:
                    showParent(View.GONE);
                    break;
                case AUTHENTICATED:
                    showParent(View.VISIBLE);
                    Toast.makeText(this, getString(R.string.sentSuccesfully), Toast.LENGTH_SHORT).show();
                    break;
                case ERROR:
                    showParent(View.VISIBLE);
                    break;


            }
        });
    }

    private void startClientServiceComplaintProcess() {
        generalDataViewModel.clientService("complaint", binding.etQuestion.getmyText().toString()).observe(this, resource -> {
            switch (resource.status) {
                case LOADING:
                    showParent(View.GONE);
                    break;
                case AUTHENTICATED:
                    showParent(View.VISIBLE);
                    Toast.makeText(this, getString(R.string.sentSuccesfully), Toast.LENGTH_SHORT).show();
                    break;
                case ERROR:
                    showParent(View.VISIBLE);
                    break;


            }
        });
    }
    private void showParent(int visibility) {
        if (visibility == View.VISIBLE) {
            binding.loading.setVisibility(View.GONE);
            binding.btnQuestion.setVisibility(View.VISIBLE);

        } else {
            binding.loading.setVisibility(View.VISIBLE);
            binding.btnQuestion.setVisibility(View.GONE);

        }
    }
    private void initViews() {
        binding.backImage.setOnClickListener(this);
        binding.btnQuestion.setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {
        if (view.getId() == binding.backImage.getId()) {
            this.finish();
        } else if (view.getId() == binding.btnQuestion.getId()) {
            if (key.equals("complaint")) {
                startClientServiceComplaintProcess();
            } else if (key.equals("support")) {
                startClientServiceProcess();
            }
        }
    }
}