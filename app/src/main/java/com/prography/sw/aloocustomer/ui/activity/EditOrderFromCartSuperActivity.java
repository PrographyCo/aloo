package com.prography.sw.aloocustomer.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.prography.sw.aloocustomer.R;
import com.prography.sw.aloocustomer.adapter.SlidePagerAdapter;
import com.prography.sw.aloocustomer.data.response.GeneralResponse;
import com.prography.sw.aloocustomer.databinding.ActivityEditOrderFromCartSuperBinding;
import com.prography.sw.aloocustomer.network.AuthResource;
import com.prography.sw.aloocustomer.util.AppUtils;
import com.prography.sw.aloocustomer.viewmodel.CardViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

public class EditOrderFromCartSuperActivity extends AppCompatActivity implements View.OnClickListener {
    private ActivityEditOrderFromCartSuperBinding binding;
    private CardViewModel cardViewModel;
    private int id;
    private SlidePagerAdapter adapter;
    private List<String> listSlides = new ArrayList<>();
    private int count = 0;
    private int count_cart_item = 0;
    private int amount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditOrderFromCartSuperBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getIntentData();
        initView();
        initadapter();
        initViewModel();
        startOneItemProcess();

    }


    private void initView() {
        binding.backImage.setOnClickListener(this);
    }

    private void initViewModel() {
        cardViewModel = new ViewModelProvider(this,
                new ViewModelProvider.AndroidViewModelFactory(getApplication())).get(CardViewModel.class);
    }

    private void initadapter() {
        adapter = new SlidePagerAdapter(listSlides);
        binding.sliderPager.setAdapter(adapter);
        binding.indicator.setupWithViewPager(binding.sliderPager, true);

    }

    private void getIntentData() {
        id = getIntent().getIntExtra("id", -1);
    }

    private void startOneItemProcess() {
        cardViewModel.getOneItem(id).observe(this, resource -> {
            switch (resource.status) {
                case LOADING:
                    showParent(View.GONE);
                    break;
                case AUTHENTICATED:
                    upDateUi(resource.data);
                    break;
                case ERROR:
                    showFailed(resource);
                    break;
            }
        });
    }

    private void showParent(int visibility) {
        if (visibility == View.VISIBLE) {
            binding.loading.setVisibility(View.GONE);
            binding.contener.setVisibility(View.VISIBLE);

        } else {
            binding.loading.setVisibility(View.VISIBLE);
            binding.contener.setVisibility(View.GONE);

        }
    }

    private void upDateUi(GeneralResponse response) {
        showParent(View.VISIBLE);
        Toast.makeText(this, "AUTHENTICATED", Toast.LENGTH_SHORT).show();
        binding.number.setText(String.valueOf(response.getEditItemData().getAmount()));
        amount = response.getEditItemData().getAmount();
        binding.itemNumber.setText(response.getEditItemData().getItem().getAmount() + " " + response.getEditItemData().getItem().getAmount_type());
        listSlides = response.getEditItemData().getItem().getImages();
        adapter = new SlidePagerAdapter(listSlides);
        binding.sliderPager.setAdapter(adapter);
        binding.indicator.setupWithViewPager(binding.sliderPager, true);

    }

    private void showFailed(AuthResource<GeneralResponse> errorResponse) {
        switch (errorResponse.errorCode) {
            case 0:
                Toast.makeText(this, getString(R.string.check_internet_connection_text), Toast.LENGTH_SHORT).show();
                break;
            case 500:
                Toast.makeText(this, getString(R.string.something_went_wrong_text), Toast.LENGTH_SHORT).show();
                break;
            case 401:
                AppUtils.unauthorized(this);
                break;
            default:
                Toast.makeText(this, errorResponse.message, Toast.LENGTH_SHORT).show();
                break;
        }
        showParent(View.VISIBLE);
    }

    private void startSaveEditProcess() {
        cardViewModel.saveEdit(id, amount).observe(this, new Observer<AuthResource<GeneralResponse>>() {
            @Override
            public void onChanged(AuthResource<GeneralResponse> resource) {
                switch (resource.status) {
                    case LOADING:
                        showParentSave(View.GONE);
                        break;
                    case AUTHENTICATED:
                        upDateUiSave(resource.data);
                        break;
                    case ERROR:
                        showFailedSave(resource);
                        break;
                }
            }
        });
    }


    private void showParentSave(int visibility) {
        if (visibility == View.VISIBLE) {
            binding.loadingSave.setVisibility(View.GONE);
            binding.btnSave.setVisibility(View.VISIBLE);
        } else {
            binding.loadingSave.setVisibility(View.VISIBLE);
            binding.btnSave.setVisibility(View.GONE);

        }
    }

    private void upDateUiSave(GeneralResponse response) {
        showParentSave(View.VISIBLE);
        Toast.makeText(this, response.getMessage(), Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this, CartActivity.class));
        finish();
    }

    private void showFailedSave(AuthResource<GeneralResponse> errorResponse) {
        switch (errorResponse.errorCode) {
            case 0:
                Toast.makeText(this, getString(R.string.check_internet_connection_text), Toast.LENGTH_SHORT).show();
                break;
            case 500:
                Toast.makeText(this, getString(R.string.something_went_wrong_text), Toast.LENGTH_SHORT).show();
                break;
            case 401:
                AppUtils.unauthorized(this);
                break;
            default:
                Toast.makeText(this, errorResponse.message, Toast.LENGTH_SHORT).show();
                break;
        }
        showParentSave(View.VISIBLE);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.add_img:
                if (count < amount) {
                    count++;
                    binding.number.setText(String.valueOf(count));
                } else {
                    Toast.makeText(this, "the item amount must be less  than one or equels the items amount ^_-", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.mi_minus:
                if (count > 1) {
                    count--;
                    binding.number.setText(String.valueOf(count));
                } else {
                    Toast.makeText(this, "the item amount must be larger than one or one ^_-", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_save:
                startSaveEditProcess();
                break;
            case R.id.back_image:
                this.finish();
                break;

        }
    }
}