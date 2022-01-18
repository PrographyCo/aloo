package com.prography.sw.alooproduct.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.prography.sw.alooproduct.R;
import com.prography.sw.alooproduct.adapter.CustomListener;
import com.prography.sw.alooproduct.adapter.CustomRecyclerViewAdapter;
import com.prography.sw.alooproduct.data.response.GeneralResponse;
import com.prography.sw.alooproduct.databinding.ActivityPreparingOrderBinding;
import com.prography.sw.alooproduct.model.OrderListTiem;
import com.prography.sw.alooproduct.model.PreparingOrder;
import com.prography.sw.alooproduct.network.AuthResource;
import com.prography.sw.alooproduct.util.AppUtils;
import com.prography.sw.alooproduct.viewmodel.OrdersViewModel;

import java.util.ArrayList;
import java.util.List;

public class PreparingOrderActivity extends AppCompatActivity implements CustomListener, View.OnClickListener {
    private ActivityPreparingOrderBinding binding;
    private List<OrderListTiem> items = new ArrayList<>();
    private OrdersViewModel ordersViewModel;
    private CustomRecyclerViewAdapter adapter;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPreparingOrderBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initview();
        getIntentData();
        initAdapter();
        initViewModel();
        startOrderDataProcess();
    }

    private void initAdapter() {
        adapter = new CustomRecyclerViewAdapter(this, CustomRecyclerViewAdapter.HolderConstants.ITEM_PREPARING_ORDER);
        adapter.setPreparingOrder(items);
        binding.rvPreparingMeal.setAdapter(adapter);
    }

    private void initViewModel() {
        ordersViewModel = new ViewModelProvider(this,
                new ViewModelProvider.AndroidViewModelFactory(getApplication())).get(OrdersViewModel.class);
    }

    private void getIntentData() {
        id = getIntent().getIntExtra("id", -1);
        binding.title.setText("Order number " + id);
    }

    private void startOrderDataProcess() {
        ordersViewModel.getOrdersData(String.valueOf(id)).observe(this, orderDataResource -> {
            switch (orderDataResource.status) {
                case LOADING:
                    showParent(View.GONE);
                    break;
                case AUTHENTICATED:
                    updateUi(orderDataResource.data);
                    break;
                case ERROR:
                    showFailed(orderDataResource);
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

    private void updateUi(GeneralResponse response) {
        showParent(View.VISIBLE);
        binding.tvNameOrderOwner.setText(response.getOrderData().getCustomer().getName());
        binding.tvDateTime.setText(AppUtils.setTime((long) response.getOrderData().getDate()));
        binding.tvMealNumber.setText(String.valueOf(response.getOrderData().getItemsSumAmount()));
        items = response.getOrderData().getItems();
        adapter.setPreparingOrder(items);
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


    private void startReadyProcess() {
        ordersViewModel.ready(id).observe(this, readyResource -> {
            switch (readyResource.status) {
                case LOADING:
                    showParentReady(View.GONE);
                    break;
                case AUTHENTICATED:
                    updateUiReady(readyResource.data);
                    break;
                case ERROR:
                    showFailedReady(readyResource);
                    break;
            }
        });
    }

    private void showParentReady(int visibility) {
        if (visibility == View.VISIBLE) {
            binding.loading.setVisibility(View.GONE);
            binding.btnOrderProcessed.setVisibility(View.VISIBLE);
        } else {
            binding.loading.setVisibility(View.VISIBLE);
            binding.btnOrderProcessed.setVisibility(View.GONE);
        }
    }

    private void updateUiReady(GeneralResponse response) {
        showParentReady(View.VISIBLE);
        Toast.makeText(this, response.getReadyData().getStatus(), Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, MainActivity.class);
//        intent.putExtra("id", id);
        startActivity(intent);
        finish();
    }

    private void showFailedReady(AuthResource<GeneralResponse> errorResponse) {
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
        showParentReady(View.VISIBLE);
    }


    private void initview() {
        binding.btnOrderProcessed.setOnClickListener(this);
        binding.backImage.setOnClickListener(this);
    }

    @Override
    public void onItemClick(int position, String action) {

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == binding.btnOrderProcessed.getId()) {
            startReadyProcess();

        } else if (view.getId() == binding.backImage.getId()) {
            this.finish();
        }
    }
}