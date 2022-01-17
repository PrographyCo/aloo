package com.prography.sw.aloocustomer.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.prography.sw.aloocustomer.databinding.ActivityClientsServiceBinding;
import com.prography.sw.aloocustomer.viewmodel.GeneralDataViewModel;

public class ClientsServiceActivity extends AppCompatActivity implements View.OnClickListener {
    private ActivityClientsServiceBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityClientsServiceBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initViews();

    }

    private void initViews() {
        binding.technicalSupport.setOnClickListener(this);
        binding.complaint.setOnClickListener(this);
        binding.question.setOnClickListener(this);
        binding.myInquiries.setOnClickListener(this);
        binding.backImage.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        if (view.getId() == binding.myInquiries.getId()) {
            startActivity(new Intent(this, MyInquiriesActivity.class));
        } else if (view.getId() == binding.question.getId()) {
            startActivity(new Intent(this, MyQuestionActivity.class));
        } else if (view.getId() == binding.complaint.getId()) {
            Intent intent = new Intent(this, MyTechnicalSupportActivity.class);
            intent.putExtra("key", "complaint");
            startActivity(intent);
        } else if (view.getId() == binding.technicalSupport.getId()) {
            Intent intent = new Intent(this, MyTechnicalSupportActivity.class);
            intent.putExtra("key", "support");
            startActivity(intent);
        } else if (view.getId() == binding.backImage.getId()) {
            this.finish();
        }
    }
}