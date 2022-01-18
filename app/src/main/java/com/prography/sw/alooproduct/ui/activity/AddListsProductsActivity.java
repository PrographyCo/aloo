package com.prography.sw.alooproduct.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.prography.sw.alooproduct.databinding.ActivityAddListsProductsBinding;

public class AddListsProductsActivity extends AppCompatActivity {

    private ActivityAddListsProductsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddListsProductsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setListeners();
    }

    private void setListeners() {
        binding.backImage.setOnClickListener(view -> {
            this.finish();
        });
        binding.addSections.setOnClickListener(view -> {
            startActivity(new Intent(AddListsProductsActivity.this, AddSectionActivity.class));
        });
        binding.addProducts.setOnClickListener(view -> {
            startActivity(new Intent(AddListsProductsActivity.this, AddProductActivity.class));
        });
    }
}