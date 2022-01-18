package com.prography.sw.alooproduct.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.prography.sw.alooproduct.R;
import com.prography.sw.alooproduct.adapter.CustomListener;
import com.prography.sw.alooproduct.adapter.CustomRecyclerViewAdapter;
import com.prography.sw.alooproduct.custom.BottomSheetCancel;
import com.prography.sw.alooproduct.data.response.GeneralResponse;
import com.prography.sw.alooproduct.data.response.data.OrderData;
import com.prography.sw.alooproduct.databinding.ActivityOrderDetailsBinding;
import com.prography.sw.alooproduct.model.OrderDetails;
import com.prography.sw.alooproduct.model.OrderListTiem;
import com.prography.sw.alooproduct.network.AuthResource;
import com.prography.sw.alooproduct.util.AppUtils;
import com.prography.sw.alooproduct.viewmodel.OrdersViewModel;

import java.util.ArrayList;
import java.util.List;

public class OrderDetailsActivity extends AppCompatActivity implements CustomListener, View.OnClickListener {
    private ActivityOrderDetailsBinding binding;
    private List<OrderListTiem> items = new ArrayList<>();
    private OrdersViewModel ordersViewModel;
    private CustomRecyclerViewAdapter adapter;
    private BottomSheetCancel bottomSheetCancel;
    private int id;
    private String status;
    private String userphone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOrderDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getIntentData();
        initView();
        initBottomSheet();
        initAdapter();
        initViewModel();
        startOrderDataProcess();
    }

    public void initView() {
        binding.btnConfirmation.setOnClickListener(this);
        binding.backImage.setOnClickListener(this);
        binding.btnReject.setOnClickListener(this);
        binding.imCall.setOnClickListener(this);
        binding.imMessages.setOnClickListener(this);
    }

    private void initViewModel() {
        ordersViewModel = new ViewModelProvider(this,
                new ViewModelProvider.AndroidViewModelFactory(getApplication())).get(OrdersViewModel.class);
    }

    private void initAdapter() {
        adapter = new CustomRecyclerViewAdapter(this, CustomRecyclerViewAdapter.HolderConstants.ITEM_ORDER_DETAILS);
        adapter.setOrderDetails(items);
        binding.rvOrderMeal.setAdapter(adapter);
        binding.rvOrderMeal.setNestedScrollingEnabled(false);
    }


    private void getIntentData() {
        Bundle extras = getIntent().getExtras();
        if (extras == null)
            return;
        status = extras.getString("status");
        id = extras.getInt("id");
        if (!status.equals("unconfirmed")) {
            binding.btnConfirmation.setVisibility(View.GONE);
            binding.btnReject.setVisibility(View.GONE);
        }
    }

    @Override
    public void onItemClick(int position, String action) {

    }

    private void initBottomSheet() {
        bottomSheetCancel = new BottomSheetCancel(this, new CustomListener() {
            @Override
            public void onItemClick(int position, String action) {
                switch (action) {
                    case "BottomSheetCancel":
                        startCancelProcess(bottomSheetCancel.getMessage());
                        break;
                }
            }
        });

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


    private void startConfirmProcess() {
        ordersViewModel.confirm(id).observe(this, confirmResource -> {
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

    private void startCancelProcess(String message) {
        ordersViewModel.cancel(id, message).observe(this, cancelResource -> {
            switch (cancelResource.status) {
                case LOADING:
                    showParentConfirm(View.GONE);
                    break;
                case AUTHENTICATED:
                    updateUiCancel(cancelResource.data);
                    break;
                case ERROR:
                    showFailedConfirm(cancelResource);
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
        userphone = response.getOrderData().getCustomer().getPhone();
        String orderNumber = getString(R.string.order_number) + " " + response.getOrderData().getId();
        binding.title.setText(orderNumber);
        binding.tvTineNumber.setText(AppUtils.setTime((long) response.getOrderData().getDate()));
        binding.tvOrderPrice.setText(String.valueOf(response.getOrderData().getTotalPrice()));
        binding.tvMealNumber.setText(String.valueOf(response.getOrderData().getItemsSumAmount()));
        items = response.getOrderData().getItems();
        adapter.setOrderDetails(items);
    }

    private void updateUiConfirm(GeneralResponse response) {
        showParentConfirm(View.VISIBLE);
        Toast.makeText(this, response.getMessage(), Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, PreparingOrderActivity.class);
        intent.putExtra("id", id);
        startActivity(intent);
        finish();
    }

    private void updateUiCancel(GeneralResponse response) {
        showParentConfirm(View.VISIBLE);
        Toast.makeText(this, response.getMessage(), Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    private void showParentConfirm(int visibility) {
        if (visibility == View.VISIBLE) {
            binding.loadingConfirm.setVisibility(View.GONE);
            binding.btnConfirmation.setVisibility(View.VISIBLE);
            binding.btnReject.setVisibility(View.VISIBLE);
        } else {
            binding.loadingConfirm.setVisibility(View.VISIBLE);
            binding.btnConfirmation.setVisibility(View.GONE);
            binding.btnReject.setVisibility(View.GONE);
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
                AppUtils.unauthorized(this);
                break;
            default:
                Toast.makeText(this, errorResponse.message, Toast.LENGTH_SHORT).show();
                break;
        }
        showParent(View.VISIBLE);
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
        intent.setData(Uri.parse("tel:" + userphone));
        startActivity(intent);
    }

    private void whatsAppIntent() {
        String contact = "+972 592692503"; // use country code with your phone number
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
        switch (view.getId()) {
            case R.id.btn_confirmation:
                startConfirmProcess();
                break;
            case R.id.btn_reject:
                bottomSheetCancel.showDialog();
                break;
            case R.id.back_image:
                this.finish();
                break;
            case R.id.im_call:
                callIntent();
                break;
            case R.id.im_messages:
                whatsAppIntent();
                break;
        }
    }
}