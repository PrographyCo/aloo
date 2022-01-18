package com.prography.sw.alooproduct.ui.activity;

import static com.prography.sw.alooproduct.util.AppUtils.unauthorized;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.prography.sw.alooproduct.R;
import com.prography.sw.alooproduct.adapter.CustomListener;
import com.prography.sw.alooproduct.adapter.CustomRecyclerViewAdapter;
import com.prography.sw.alooproduct.custom.BottomSheetListText;
import com.prography.sw.alooproduct.data.response.GeneralResponse;
import com.prography.sw.alooproduct.data.response.data.MyOrder;
import com.prography.sw.alooproduct.databinding.ActivityMyOrdersBinding;
import com.prography.sw.alooproduct.network.AuthResource;
import com.prography.sw.alooproduct.util.Constants;
import com.prography.sw.alooproduct.viewmodel.OrdersViewModel;

import java.util.ArrayList;
import java.util.List;

public class MyOrdersActivity extends AppCompatActivity implements CustomListener, View.OnClickListener {

    private ActivityMyOrdersBinding binding;
    private List<MyOrder> myOrders = new ArrayList<>();
    private CustomRecyclerViewAdapter adapter;
    private OrdersViewModel ordersViewModel;
    private boolean loaded;
    private boolean pulled = false;
    private BottomSheetListText bottomSheetListText;
    private List<String> text = new ArrayList<>();
    private int position = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMyOrdersBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initBottomSheet();
        initViewModel();
        initViews();
        initAdapter();
        getCurrentOrders();
        setListeners();
    }

    private void setListeners() {
        binding.type.setOnClickListener(view -> {
            bottomSheetListText.showDialog();
        });
        binding.backImage.setOnClickListener(v -> {
            this.finish();
        });
    }

    private void initBottomSheet() {
        text.add(getString(R.string.current));
        text.add(getString(R.string.cancelled));
        bottomSheetListText = new BottomSheetListText(this, (position, action) -> {
            this.position = position;
            pulled = false;
            ordersViewModel.setMyOrderPageNumber(1);
            ordersViewModel.setFetchingMyOrderNextPage(false);
            ordersViewModel.setFetchingMyOrderExhausted(false);
            loaded = false;
            myOrders.clear();
            if (position == 0)
                getCurrentOrders();
            else
                getCancelledOrders();
            binding.type.setText(text.get(position));
            bottomSheetListText.dismissDialog();
        });
        bottomSheetListText.setList(null, text);
    }

    private void initViewModel() {
        ordersViewModel = new ViewModelProvider(this,
                new ViewModelProvider.AndroidViewModelFactory(this.getApplication()))
                .get(OrdersViewModel.class);
    }

    private void initAdapter() {
        adapter = new CustomRecyclerViewAdapter(this, CustomRecyclerViewAdapter.HolderConstants.MY_ORDER);
        adapter.setMyOrders(myOrders);
        binding.rvOrder.setAdapter(adapter);
    }

    private void initViews() {
        binding.backImage.setOnClickListener(this);

        binding.swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pulled = true;
                ordersViewModel.setMyOrderPageNumber(1);
                ordersViewModel.setFetchingMyOrderNextPage(false);
                ordersViewModel.setFetchingMyOrderExhausted(false);
                loaded = false;
                if (position == 0)
                    getCurrentOrders();
                else
                    getCancelledOrders();
            }
        });

        binding.rvOrder.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                if (!binding.rvOrder.canScrollVertically(1) && binding.rvOrder.getVisibility() == View.VISIBLE
                        && !ordersViewModel.isFetchingMyOrderExhausted() && !ordersViewModel.isFetchingMyOrderNextPage()) {
                    // search the next page
                    loaded = false;
                    if (position == 0)
                        ordersViewModel.getMyOrderNextPage(OrdersViewModel.MyOrder.CURRENT);
                    else
                        ordersViewModel.getMyOrderNextPage(OrdersViewModel.MyOrder.CANCELLED);
                }
            }
        });
    }

    @Override
    public void onItemClick(int position, String action) {
        Intent intent = new Intent(this, OrderDetailsActivity.class);
        intent.putExtra("id", myOrders.get(position).getId());
        intent.putExtra("status", myOrders.get(position).getStatus());
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == binding.backImage.getId()) {
            this.finish();
        }
    }


    private void getCurrentOrders() {
        ordersViewModel.getCurrent().observe(this, result -> {
            switch (result.status) {
                case LOADING:
                    if (pulled)
                        binding.swipeRefresh.setEnabled(false);
                    else if (ordersViewModel.getMyOrderPageNumber() > 1) {
                        adapter.displayLoading();
                    } else if (!pulled) {
                        showParent(View.GONE);
                    }
                    break;
                case AUTHENTICATED:
                    if (!loaded) {
                        updateUi(result.data);
                    }
                    loaded = true;
//                    ordersViewModel.removeCurrentObserver(this);
                    break;
                case ERROR:
                    if (!loaded) {
                        showFailed(result);
                    }
                    loaded = true;
//                    ordersViewModel.removeCurrentObserver(this);
                    break;
            }
        });
    }

    private void getCancelledOrders() {
        ordersViewModel.getCancelled().observe(this, result -> {
            switch (result.status) {
                case LOADING:
                    if (pulled)
                        binding.swipeRefresh.setEnabled(false);
                    else if (ordersViewModel.getMyOrderPageNumber() > 1) {
                        adapter.displayLoading();
                    } else if (!pulled) {
                        showParent(View.GONE);
                    }
                    break;
                case AUTHENTICATED:
                    if (!loaded) {
                        updateUi(result.data);
                    }
                    loaded = true;
//                    ordersViewModel.removeCurrentObserver(this);
                    break;
                case ERROR:
                    if (!loaded) {
                        showFailed(result);
                    }
                    loaded = true;
//                    ordersViewModel.removeCurrentObserver(this);
                    break;
            }
        });
    }

    private void showParent(int visibility) {
        if (visibility == View.VISIBLE) {
            binding.loading.setVisibility(View.GONE);
            binding.rvOrder.setVisibility(View.VISIBLE);
        } else {
            binding.loading.setVisibility(View.VISIBLE);
            binding.rvOrder.setVisibility(View.GONE);
        }
    }

    private void updateUi(GeneralResponse response) {
        adapter.hideLoading();
        showParent(View.VISIBLE);

        if (response.getMyOrderData().getItems() != null && response.getMyOrderData().getItems().size() > 0) {
            int oldPosition = -1;
            if (ordersViewModel.isFetchingMyOrderNextPage()) {
                oldPosition = myOrders.size();
                this.myOrders.addAll(response.getMyOrderData().getItems());
                ordersViewModel.setFetchingMyOrderNextPage(false);
            } else {
                this.myOrders = response.getMyOrderData().getItems();
            }

            if (response.getMyOrderData().getItems().size() < Constants.FETCHING_LIMIT)
                ordersViewModel.setFetchingMyOrderExhausted(true);

            adapter.setMyOrders(myOrders);
            binding.rvOrder.setVisibility(View.VISIBLE);

            if (oldPosition > 0) {
                binding.rvOrder.smoothScrollToPosition(oldPosition);
            }

        } else {
            if (myOrders.size() == 0) {
                Toast.makeText(this, getString(R.string.no_orders_were_found), Toast.LENGTH_SHORT).show();
            } else {
                ordersViewModel.setFetchingMyOrderExhausted(true);
                ordersViewModel.setFetchingMyOrderNextPage(false);
            }
        }
        cancelSwipeRefreshLayout();
    }

    private void cancelSwipeRefreshLayout() {
        pulled = false;
        binding.swipeRefresh.setEnabled(true);
        if (binding.swipeRefresh.isRefreshing()) {
            binding.swipeRefresh.setRefreshing(false);
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