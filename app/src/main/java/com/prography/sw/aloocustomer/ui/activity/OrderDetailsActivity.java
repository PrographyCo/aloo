package com.prography.sw.aloocustomer.ui.activity;

import static com.prography.sw.aloocustomer.util.AppUtils.setTime;
import static com.prography.sw.aloocustomer.util.AppUtils.unauthorized;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.prography.sw.aloocustomer.R;
import com.prography.sw.aloocustomer.adapter.CustomListener;
import com.prography.sw.aloocustomer.adapter.CustomRecyclerViewAdapter;
import com.prography.sw.aloocustomer.custom.BottomSheetListText;
import com.prography.sw.aloocustomer.data.response.GeneralResponse;
import com.prography.sw.aloocustomer.databinding.ActivityOrderDetailsBinding;
import com.prography.sw.aloocustomer.model.OrderItem;
import com.prography.sw.aloocustomer.network.AuthResource;
import com.prography.sw.aloocustomer.viewmodel.MyOrdersViewModel;

import java.util.ArrayList;
import java.util.List;

public class OrderDetailsActivity extends AppCompatActivity implements CustomListener, View.OnClickListener {
    private ActivityOrderDetailsBinding binding;
    private List<OrderItem> items = new ArrayList<>();
    private int id;
    private MyOrdersViewModel myOrdersViewModel;
    private CustomRecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOrderDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getIntentData();
        initViewModel();
        initViews();
        initAdapter();
        getOrderDetails();
    }

    private void getIntentData() {
        id = getIntent().getIntExtra("id", -1);
    }

    private void initViewModel() {
        myOrdersViewModel = new ViewModelProvider(this,
                new ViewModelProvider.AndroidViewModelFactory(this.getApplication()))
                .get(MyOrdersViewModel.class);
    }

    private void initViews() {
        binding.btnOrderTracking.setOnClickListener(this);
        binding.btnRate.setOnClickListener(this);
        binding.backImage.setOnClickListener(this);
    }

    private void initAdapter() {
        adapter = new CustomRecyclerViewAdapter(this, CustomRecyclerViewAdapter.HolderConstants.ITEM_ORDERS_DETAILS);
        binding.rvOrdersDetails.setAdapter(adapter);
    }

    @Override
    public void onItemClick(int position, String action) {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_order_tracking:
                Intent intentTracking = new Intent(this, OrderTrackingActivity.class);
                intentTracking.putExtra("id", id);
                startActivity(intentTracking);
                break;
            case R.id.btn_rate:
                Intent intent = new Intent(this, DriverRatingActivity.class);
                intent.putExtra("id", id);
                startActivity(intent);
                break;
            case R.id.back_image:
                this.finish();
                break;
        }
    }

    private void getOrderDetails() {
        myOrdersViewModel.getOrderDetails(id).observe(this, result -> {
            switch (result.status) {
                case LOADING:
                    showParent(View.GONE);
                    break;
                case AUTHENTICATED:
                    updateUi(result.data);
                    myOrdersViewModel.removeOrderDetailsObservers(this);
                    break;
                case ERROR:
                    showFailed(result);
                    myOrdersViewModel.removeOrderDetailsObservers(this);
                    break;
            }
        });
    }

    private void showParent(int visibility) {
        if (visibility == View.VISIBLE) {
            binding.loading.setVisibility(View.GONE);
            binding.scrollView.setVisibility(View.VISIBLE);
        } else {
            binding.loading.setVisibility(View.VISIBLE);
            binding.scrollView.setVisibility(View.GONE);
        }
    }

    private void updateUi(GeneralResponse response) {
        showParent(View.VISIBLE);
        if (response == null || response.getOrderDetailsData() == null || response.getOrderDetailsData().getOrderItems() == null) {
            Toast.makeText(this, getString(R.string.something_went_wrong_text), Toast.LENGTH_SHORT).show();
            return;
        }
        if (response.getOrderDetailsData().getOrderItems().isEmpty())
            Toast.makeText(this, getString(R.string.no_order_items_were_added), Toast.LENGTH_SHORT).show();
        items = response.getOrderDetailsData().getOrderItems();
        adapter.setOrderItems(items);

        binding.tvTimeDate.setText(setTime(Long.valueOf(response.getOrderDetailsData().getDate())));
        binding.tvOrderNumber.setText(String.valueOf(response.getOrderDetailsData().getId()));
        binding.tvSentDelivered.setText(response.getOrderDetailsData().getStatus());
        binding.tvRequesterAddress.setText(response.getOrderDetailsData().getPlace().getName());
        binding.myAddress.setText(response.getOrderDetailsData().getPlace().getLocationName());
        binding.askingPriceNumber.setText(String.valueOf(response.getOrderDetailsData().getTotal().getPrice()));
        binding.deliveryChargeNumber.setText(String.valueOf(response.getOrderDetailsData().getTotal().getDelivery()));
        binding.totelNumber.setText(String.valueOf(response.getOrderDetailsData().getTotal().getTotal()));

        if (response.getOrderDetailsData().getStatus().equals("delivery confirmed")) {
            binding.btnOrderTracking.setVisibility(View.GONE);
            binding.btnRate.setVisibility(View.VISIBLE);
        } else {
            binding.btnOrderTracking.setVisibility(View.VISIBLE);
            binding.btnRate.setVisibility(View.GONE);
        }
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
                unauthorized(this);
                break;
            default:
                Toast.makeText(this, errorResponse.message, Toast.LENGTH_SHORT).show();
                break;
        }
        showParent(View.VISIBLE);
    }

}