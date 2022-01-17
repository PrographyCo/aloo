package com.prography.sw.aloodelevery.ui.activity;

import static com.prography.sw.aloodelevery.util.AppUtils.setTime;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.prography.sw.aloodelevery.R;
import com.prography.sw.aloodelevery.adapter.CustomListener;
import com.prography.sw.aloodelevery.adapter.CustomRecyclerViewAdapter;
import com.prography.sw.aloodelevery.data.response.GeneralResponse;
import com.prography.sw.aloodelevery.databinding.ActivityDeliveryOrderDetailsBinding;
import com.prography.sw.aloodelevery.model.OrderDetailsHome;
import com.prography.sw.aloodelevery.model.OrderItem;
import com.prography.sw.aloodelevery.network.AuthResource;
import com.prography.sw.aloodelevery.util.AppUtils;
import com.prography.sw.aloodelevery.viewmodel.OrdersViewModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DeliveryOrderDetailsActivity extends AppCompatActivity implements CustomListener, View.OnClickListener {
    private ActivityDeliveryOrderDetailsBinding binding;
    private CustomRecyclerViewAdapter adapter;
    private List<OrderItem> items = new ArrayList<>();
    private OrdersViewModel ordersViewModel;
    private int id;
    private String userPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDeliveryOrderDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getIntentData();
        initView();
        initViewModel();
        initAdapter();
        startOderDataProcess();

    }

    private void getIntentData() {
        if (getIntent().hasExtra("id")) {
            id = getIntent().getIntExtra("id", -1);
        }
    }

    private void initViewModel() {
        ordersViewModel = new ViewModelProvider(this,
                new ViewModelProvider.AndroidViewModelFactory(getApplication())).get(OrdersViewModel.class);
    }

    private void initView() {
        binding.backImage.setOnClickListener(this);
        binding.btnAcceptance.setOnClickListener(this);
        binding.imCall.setOnClickListener(this);
        binding.imMessages.setOnClickListener(this);
    }

    private void initAdapter() {
        adapter = new CustomRecyclerViewAdapter(this, CustomRecyclerViewAdapter.HolderConstants.ORDER_DETAILS_HOME);
        adapter.setOrderDetailsHome(items);
        binding.rvOrderDetails.setAdapter(adapter);
        binding.rvOrderDetails.setNestedScrollingEnabled(false);
    }

    @Override
    public void onItemClick(int position, String action) {
    }

    private void startOderDataProcess() {
        ordersViewModel.orderData(id).observe(this, orderDataResource -> {
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
        binding.tvNameOrderOwner.setText(response.getOredrDetielsData().getCustomer().getName());
        binding.ratingBar.setRating(response.getOredrDetielsData().getCustomer().getRate());
        userPhone = response.getOredrDetielsData().getCustomer().getPhone();
        binding.tvSentDelivered.setText(setTime((long) response.getOredrDetielsData().getDate()*1000, this));
        binding.tvRequesterAddress.setText(response.getOredrDetielsData().getPlace().getName());
        binding.myAddress.setText(response.getOredrDetielsData().getPlace().getLocationName());
        binding.tvNumber.setText(String.valueOf(response.getOredrDetielsData().getId()));
        binding.tvStatus.setText(response.getOredrDetielsData().getStatus());
        items = response.getOredrDetielsData().getItems();
        binding.tvDetails.setText(String.valueOf(response.getOredrDetielsData().getItems().size()));
        binding.title.setText(String.valueOf(response.getOredrDetielsData().getId()));
        adapter.setOrderDetailsHome(items);

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


    private void startConfirmProcess() {
        ordersViewModel.confirm(id, 16).observe(this, confirmResource -> {
            switch (confirmResource.status) {
                case LOADING:
                    showParentConfirm(View.GONE);
                    break;
                case AUTHENTICATED:
                    updateUiConfirm(confirmResource.data);
                    break;
                case ERROR:
                    showFailedConfirm(confirmResource);
                    break;
            }
        });
    }

    private void showParentConfirm(int visibility) {
        if (visibility == View.VISIBLE) {
            binding.loadingAcceptance.setVisibility(View.GONE);
            binding.btnAcceptance.setVisibility(View.VISIBLE);
        } else {
            binding.loadingAcceptance.setVisibility(View.VISIBLE);
            binding.btnAcceptance.setVisibility(View.GONE);

        }
    }

    private void updateUiConfirm(GeneralResponse response) {
        showParentConfirm(View.VISIBLE);
        Intent intent = new Intent(this, MapsActivity.class);
        intent.putExtra("id", response.getConfirmData().getId());
        intent.putExtra("lat", response.getConfirmData().getPlace().getLat());
        intent.putExtra("lon", response.getConfirmData().getPlace().getLon());
        intent.putExtra("phone", response.getConfirmData().getCustomer().getPhone());
        intent.putExtra("name", response.getConfirmData().getCustomer().getName());
        startActivity(intent);
        finish();
    }

    private void showFailedConfirm(AuthResource<GeneralResponse> errorResponse) {
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
        showParentConfirm(View.VISIBLE);
    }

    private void callIntent() {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + userPhone));
        startActivity(intent);
    }

    private void whatsAppIntent() {
        String contact = "+966 "+userPhone; // use country code with your phone number
        String url = "https://api.whatsapp.com/send?phone=" + contact;
        try {
            PackageManager pm = getPackageManager();
            pm.getPackageInfo("com.whatsapp", PackageManager.GET_ACTIVITIES);
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i);
        } catch (PackageManager.NameNotFoundException e) {
            Toast.makeText(this, "Whatsapp app not installed in your phone", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == binding.btnAcceptance.getId()) {
            startConfirmProcess();
        } else if (view.getId() == binding.backImage.getId()) {
            this.finish();
        } else if (view.getId() == binding.imCall.getId()) {
            callIntent();
        } else if (view.getId() == binding.imMessages.getId()) {
            whatsAppIntent();
        }
    }
}