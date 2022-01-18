package com.prography.sw.alooproduct.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.prography.sw.alooproduct.R;
import com.prography.sw.alooproduct.databinding.ActivityStartBinding;
import com.prography.sw.alooproduct.util.SharedPreferencesHelper;

public class StartActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityStartBinding binding;
    private String data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityStartBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (SharedPreferencesHelper.isLogin(this))
            startActivity(new Intent(this, MainActivity.class));

        initView();
    }

    private void initView() {
        binding.tvCat1Titel.setOnClickListener(this);
        binding.tvCat1Desc.setOnClickListener(this);
        binding.imageCar1.setOnClickListener(this);

        binding.tvCat2Titel.setOnClickListener(this);
        binding.tvCat2Desc.setOnClickListener(this);
        binding.imageCar2.setOnClickListener(this);

        binding.tvCat3Titel.setOnClickListener(this);
        binding.tvCat3Desc.setOnClickListener(this);
        binding.imageCar3.setOnClickListener(this);
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_cat_1_titel:
            case R.id.tv_cat_1_desc:
            case R.id.image_car_1:
                setData("Restaurants");
                Intent intent = new Intent(StartActivity.this, MainActivity.class);
                intent.putExtra("key", "Restaurants");
                startActivity(intent);

                break;
            case R.id.tv_cat_2_titel:
            case R.id.tv_cat_2_desc:
            case R.id.image_car_2:
                setData("Supermarket");
                Intent intent1 = new Intent(StartActivity.this, MainActivity.class);
                intent1.putExtra("key", "Supermarket");
                startActivity(intent1);

                break;
            case R.id.tv_cat_3_titel:
            case R.id.tv_cat_3_desc:
            case R.id.image_car_3:
                setData("Pharmacies");
                Intent intent2 = new Intent(StartActivity.this, MainActivity.class);
                intent2.putExtra("key", "Pharmacies");
                startActivity(intent2);

                break;

        }
    }
}