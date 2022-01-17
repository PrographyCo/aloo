package com.prography.sw.aloocustomer.ui.activity;

import static com.prography.sw.aloocustomer.util.AppUtils.unauthorized;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.prography.sw.aloocustomer.R;
import com.prography.sw.aloocustomer.adapter.CustomListener;
import com.prography.sw.aloocustomer.adapter.CustomRecyclerViewAdapter;
import com.prography.sw.aloocustomer.data.response.GeneralResponse;
import com.prography.sw.aloocustomer.databinding.ActivityListOrdersBinding;
import com.prography.sw.aloocustomer.model.MyOrder;
import com.prography.sw.aloocustomer.network.AuthResource;
import com.prography.sw.aloocustomer.viewmodel.MyOrdersViewModel;

import java.util.ArrayList;
import java.util.List;

public class ListOrdersActivity extends AppCompatActivity implements CustomListener {
    private ActivityListOrdersBinding binding;
    private List<MyOrder> items = new ArrayList<>();
    private int id;
    private MyOrdersViewModel myOrdersViewModel;
    private CustomRecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityListOrdersBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getIntentData();
        initView();
        initViewModel();
        initAdapter();
        getMyOrders();
    }


    private void initView() {
        binding.backImage.setOnClickListener(view -> {
            this.finish();
        });
    }

    private void initViewModel() {
        myOrdersViewModel = new ViewModelProvider(this,
                new ViewModelProvider.AndroidViewModelFactory(this.getApplication()))
                .get(MyOrdersViewModel.class);
    }

    private void getIntentData() {
        id = getIntent().getIntExtra("id", -1);
        switch (id) {
            case 1:
                binding.title.setText(getString(R.string.supermarkets));
                break;
            case 2:
                binding.title.setText(getString(R.string.pharmacies));
                break;
            case 3:
                binding.title.setText(getString(R.string.restaurants));
                break;
        }
    }

    private void initAdapter() {
        adapter = new CustomRecyclerViewAdapter(this, CustomRecyclerViewAdapter.HolderConstants.ITEM_ORDERS_LIST);
        binding.rvOrdersList.setAdapter(adapter);
    }

    @Override
    public void onItemClick(int position, String action) {
        Intent intent = new Intent(ListOrdersActivity.this, OrderDetailsActivity.class);
        intent.putExtra("id", items.get(position).getId());
        startActivity(intent);
    }

    private void getMyOrders() {
        myOrdersViewModel.getMYOrders(id).observe(this, result -> {
            switch (result.status) {
                case LOADING:
                    showParent(View.GONE);
                    break;
                case AUTHENTICATED:
                    updateUi(result.data);
                    myOrdersViewModel.removeMyOrdersObservers(this);
                    break;
                case ERROR:
                    showFailed(result);
                    myOrdersViewModel.removeMyOrdersObservers(this);
                    break;
            }
        });
    }

    private void showParent(int visibility) {
        if (visibility == View.VISIBLE) {
            binding.loadingRv.setVisibility(View.GONE);
            binding.rvOrdersList.setVisibility(View.VISIBLE);
        } else {
            binding.loadingRv.setVisibility(View.VISIBLE);
            binding.rvOrdersList.setVisibility(View.GONE);
        }
    }

    private void updateUi(GeneralResponse response) {
        showParent(View.VISIBLE);
        if (response == null || response.getMyOrdersData() == null || response.getMyOrdersData().getMyOrders() == null) {
            Toast.makeText(this, getString(R.string.something_went_wrong_text), Toast.LENGTH_SHORT).show();
            return;
        }
        if (response.getMyOrdersData().getMyOrders().isEmpty())
            Toast.makeText(this, getString(R.string.no_places_were_added), Toast.LENGTH_SHORT).show();
        items = response.getMyOrdersData().getMyOrders();
        adapter.setMyOrders(items);
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