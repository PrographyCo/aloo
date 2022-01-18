package com.prography.sw.alooproduct.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.prography.sw.alooproduct.databinding.ActivityOrderDeliveryBinding;


public class OrderDeliveryActivity extends AppCompatActivity implements View.OnClickListener {
    private ActivityOrderDeliveryBinding binding;

    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOrderDeliveryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initView();
        getIntentData();
    }

    private void initView() {
        binding.btn.setOnClickListener(this);
        binding.backImage.setOnClickListener(this);
    }

    private void getIntentData() {
        id = getIntent().getIntExtra("id", -1);
        binding.title.setText("Order number " + id);
    }

    @Override
    public void onClick(View view) {

        if (view.getId() == binding.btn.getId()) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("key", "Restaurants");
            startActivity(intent);
        } else if (view.getId() == binding.backImage.getId()) {
            this.finish();
        }

    }
}