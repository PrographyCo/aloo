package com.prography.sw.aloocustomer.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.prography.sw.aloocustomer.databinding.ActivityQuestionDetailsBinding;

public class QuestionDetailsActivity extends AppCompatActivity {

    private ActivityQuestionDetailsBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityQuestionDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.imageQuestion.setClipToOutline(true);
        initViews();
    }

    private void initViews() {
        binding.backImage.setOnClickListener(v -> {
            this.finish();
        });
    }
}