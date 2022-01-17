package com.prography.sw.aloocustomer.ui.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.prography.sw.aloocustomer.R;
import com.prography.sw.aloocustomer.adapter.CustomListener;
import com.prography.sw.aloocustomer.adapter.CustomRecyclerViewAdapter;
import com.prography.sw.aloocustomer.custom.MapSheetSW;
import com.prography.sw.aloocustomer.data.response.GeneralResponse;
import com.prography.sw.aloocustomer.databinding.ActivitySupermarketBinding;
import com.prography.sw.aloocustomer.model.MainListItem;
import com.prography.sw.aloocustomer.network.AuthResource;
import com.prography.sw.aloocustomer.util.AppUtils;
import com.prography.sw.aloocustomer.util.Constants;
import com.prography.sw.aloocustomer.util.SharedPreferencesHelper;
import com.prography.sw.aloocustomer.viewmodel.MainViewModel;

import java.util.ArrayList;
import java.util.List;

public class SupermarketActivity extends AppCompatActivity implements CustomListener, View.OnClickListener {
    private ActivitySupermarketBinding binding;
    private MainViewModel mainViewModel;
    private List<MainListItem> items = new ArrayList<>();
    private CustomRecyclerViewAdapter adapter;
    private int id;
    private MapSheetSW mapSheet;

    private boolean loaded;
    private boolean pulled = false;
    private double lat, lon;
    private int AddressId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySupermarketBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initMapSheet();
        initView();
        cardItem();
        getIntentData();
        initSupermarketAdapter();
        initViewModel();
    }

    private void initMapSheet() {
        mapSheet = new MapSheetSW(this);
        mapSheet.show(getSupportFragmentManager(), null);
    }

    private void getIntentData() {
        id = getIntent().getIntExtra("id", -1);
    }

    private void initView() {
        binding.cart.setOnClickListener(this);


        binding.swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pulled = true;
                mainViewModel.setPageNumber(1);
                mainViewModel.setFetchingNextPage(false);
                mainViewModel.setFetchingExhausted(false);
                loaded = false;
                startMainProcess();
            }
        });

        binding.rvSupermarket.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                if (!binding.rvSupermarket.canScrollVertically(1) && binding.rvSupermarket.getVisibility() == View.VISIBLE
                        && !mainViewModel.isFetchingExhausted() && !mainViewModel.isFetchingNextPage()) {
                    // search the next page
                    loaded = false;
                    mainViewModel.getNextPage(id, lon, lat, AddressId);
                }
            }
        });
    }


    private void cardItem() {
        if (SharedPreferencesHelper.getCardItem(this) != 0) {
            binding.cartItemCount.setVisibility(View.VISIBLE);
            binding.numberCartItemCount.setVisibility(View.VISIBLE);
            binding.numberCartItemCount.setText(String.valueOf(SharedPreferencesHelper.getCardItem(this)));
        } else {
            binding.cartItemCount.setVisibility(View.GONE);
            binding.numberCartItemCount.setVisibility(View.GONE);
        }
    }


    private void initSupermarketAdapter() {
        adapter = new CustomRecyclerViewAdapter(this, CustomRecyclerViewAdapter.HolderConstants.ITEM_SUPERMARKET);
        adapter.setSupermarkets(items);
        binding.rvSupermarket.setAdapter(adapter);
    }

    private void initViewModel() {
        mainViewModel = new ViewModelProvider(this,
                new ViewModelProvider.AndroidViewModelFactory(getApplication())).get(MainViewModel.class);
    }


    private void startMainProcess() {
        mainViewModel.getMain(id, lon, lat, AddressId).observe(this, resource -> {
            switch (resource.status) {
                case LOADING:
                    if (pulled)
                        binding.swipeRefresh.setEnabled(false);
                    else if (mainViewModel.getPageNumber() > 1) {
                        adapter.displayLoading();
                    } else if (!pulled) {
                        showParent(View.GONE);
                    }
                    break;
                case AUTHENTICATED:
                    if (!loaded) {
                        updateUi(resource.data);
                        mainViewModel.removeMainObservers(this);
                    }
                    loaded = true;
                    break;
                case ERROR:
                    if (!loaded) {
                        showFailed(resource);
                    }
                    loaded = true;
                    break;
            }
        });
    }


    private void showParent(int visibility) {
        if (visibility == View.VISIBLE) {
            binding.loading.setVisibility(View.GONE);
            binding.rvSupermarket.setVisibility(View.VISIBLE);

        } else {
            binding.loading.setVisibility(View.VISIBLE);
            binding.rvSupermarket.setVisibility(View.GONE);

        }
    }

    private void updateUi(GeneralResponse response) {
        adapter.hideLoading();
        showParent(View.VISIBLE);

        if (response.getMainData().getItems() != null && response.getMainData().getItems().size() > 0) {
            int oldPosition = -1;
            if (mainViewModel.isFetchingNextPage()) {
                oldPosition = items.size();
                this.items.addAll(response.getMainData().getItems());
                mainViewModel.setFetchingNextPage(false);
            } else {
                this.items = response.getMainData().getItems();
            }
            if (response.getMainData().getItems().size() < Constants.FETCHING_LIMIT)
                mainViewModel.setFetchingExhausted(true);

            adapter.setSupermarkets(items);
            binding.rvSupermarket.setVisibility(View.VISIBLE);

            if (oldPosition > 0) {
                binding.rvSupermarket.smoothScrollToPosition(oldPosition);
            }

        } else {
            if (items.size() == 0) {
                Toast.makeText(this, getString(R.string.no_questions_were_found), Toast.LENGTH_SHORT).show();
            } else {
                mainViewModel.setFetchingExhausted(true);
                mainViewModel.setFetchingNextPage(false);
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
                AppUtils.unauthorized(this);
                break;
            default:
                Toast.makeText(this, errorResponse.message, Toast.LENGTH_SHORT).show();
                break;
        }
        showParent(View.VISIBLE);
    }


    @Override
    public void onItemClick(int position, String action) {
        if (action.equals("MAP_SHEET")) {
            lon = mapSheet.getLon();
            lat = mapSheet.getLat();
            AddressId = mapSheet.getAddressId();
            startMainProcess();
        } else if (action.equals("ITEM_SUPERMARKET")) {
            Intent intent = new Intent(SupermarketActivity.this, SupermarketPageActivity.class);
            intent.putExtra("id", items.get(position).getId());
            intent.putExtra("image", items.get(position).getImage());
            intent.putExtra("logo", items.get(position).getLogo());
            startActivity(intent);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cart:
                if (SharedPreferencesHelper.getUserToken(this).isEmpty())
                    startActivity(new Intent(this, LoginActivity.class));
                else
                    startActivity(new Intent(this, CartActivity.class));
                break;
        }
    }
}