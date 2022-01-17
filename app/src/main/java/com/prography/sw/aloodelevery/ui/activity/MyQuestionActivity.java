package com.prography.sw.aloodelevery.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.prography.sw.aloodelevery.R;
import com.prography.sw.aloodelevery.databinding.ActivityMyQuestionBinding;
import com.prography.sw.aloodelevery.viewmodel.GeneralDataViewModel;

public class MyQuestionActivity extends AppCompatActivity implements View.OnClickListener {
    private ActivityMyQuestionBinding binding;
    private GeneralDataViewModel generalDataViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMyQuestionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        intviewModel();
        initViews();
    }

    private void intviewModel() {
        generalDataViewModel = new ViewModelProvider(this,
                new ViewModelProvider.AndroidViewModelFactory(getApplication())).get(GeneralDataViewModel.class);
    }

    private void startClientServiceProcess() {
        generalDataViewModel.clientService("question", binding.etQuestion.getmyText().toString()).observe(this, resource -> {
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
            startClientServiceProcess();
        }
    }
}