package com.prography.sw.aloocustomer.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.prography.sw.aloocustomer.R;
import com.prography.sw.aloocustomer.adapter.CustomListener;
import com.prography.sw.aloocustomer.custom.BottomSheetCardList;
import com.prography.sw.aloocustomer.custom.BottomSheetListImageText;
import com.prography.sw.aloocustomer.custom.BottomSheetListText;
import com.prography.sw.aloocustomer.custom.BottomSheetTwoList;
import com.prography.sw.aloocustomer.data.response.GeneralResponse;
import com.prography.sw.aloocustomer.databinding.ActivityStartBinding;
import com.prography.sw.aloocustomer.model.ImageText;
import com.prography.sw.aloocustomer.model.Radio;
import com.prography.sw.aloocustomer.network.AuthResource;
import com.prography.sw.aloocustomer.ui.MainActivity;
import com.prography.sw.aloocustomer.util.SharedPreferencesHelper;
import com.prography.sw.aloocustomer.viewmodel.ProfileViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class StartActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityStartBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityStartBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initViews();
        changeLang(SharedPreferencesHelper.getAppLanguage(this));
        checkData();

    }

    public void changeLang(String lang) {
        Toast.makeText(this, SharedPreferencesHelper.getAppLanguage(this), Toast.LENGTH_SHORT).show();
        Locale myLocale = new Locale(lang);
        Locale.setDefault(myLocale);
        android.content.res.Configuration config = new android.content.res.Configuration();
        config.locale = myLocale;
        getResources().updateConfiguration(config, getResources().getDisplayMetrics());
    }

    public void initViews() {
        binding.btnStart.setOnClickListener(this);
        binding.tvJoinUs.setOnClickListener(this);
    }

    private void checkData() {
        if (!SharedPreferencesHelper.getUserToken(this).isEmpty()) {
            Intent intent = new Intent(StartActivity.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == binding.btnStart.getId()) {
            startActivity(new Intent(StartActivity.this, MainActivity.class));
            finish();
        } else if (view.getId() == binding.tvJoinUs.getId()) {
            startActivity(new Intent(StartActivity.this, LoginActivity.class));
            finish();
        }
    }
}