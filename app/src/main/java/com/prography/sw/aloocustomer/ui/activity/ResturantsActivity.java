package com.prography.sw.aloocustomer.ui.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.prography.sw.aloocustomer.R;
import com.prography.sw.aloocustomer.adapter.CustomListener;
import com.prography.sw.aloocustomer.adapter.CustomRecyclerViewAdapter;
import com.prography.sw.aloocustomer.custom.BottomSheetListText;
import com.prography.sw.aloocustomer.custom.BottomSheetTwoList;
import com.prography.sw.aloocustomer.custom.MapSheetSW;
import com.prography.sw.aloocustomer.data.response.GeneralResponse;
import com.prography.sw.aloocustomer.databinding.ActivityResturantsBinding;
import com.prography.sw.aloocustomer.model.MainListItem;
import com.prography.sw.aloocustomer.model.Radio;
import com.prography.sw.aloocustomer.network.AuthResource;
import com.prography.sw.aloocustomer.util.AppUtils;
import com.prography.sw.aloocustomer.util.Constants;
import com.prography.sw.aloocustomer.util.SharedPreferencesHelper;
import com.prography.sw.aloocustomer.viewmodel.GeneralDataViewModel;
import com.prography.sw.aloocustomer.viewmodel.MainViewModel;
import java.util.ArrayList;
import java.util.List;


public class ResturantsActivity extends AppCompatActivity implements CustomListener, View.OnClickListener {


    private ActivityResturantsBinding binding;
    private MainViewModel mainViewModel;
    private GeneralDataViewModel generalDataViewModel;
    private BottomSheetTwoList bottomSheetTwoList;
    private BottomSheetListText bottomSheetListText;
    private List<MainListItem> items = new ArrayList<>();
    private CustomRecyclerViewAdapter customRecyclerViewAdapter;
    List<Radio> titleFirstList = new ArrayList<>();
    List<Radio> titleSecondList = new ArrayList<>();
    List<String> titles = new ArrayList<>();
    private MapSheetSW mapSheet;
    private int id;
    private boolean loaded;
    private boolean pulled = false;
    private double lat, lon;
    private int AddressId;


    private int kitchen_type_id = -1;
    private int restaurant_type_id = -1;
    private String order_by = "default";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityResturantsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initMapSheet();
        getIntentData();
        cardItem();
        initViews();
        initViewModel();
        resturant_specify();
        restaurantType();
        startRestaurantTypesProcess();
        startKitchenTypesProcess();
        initRestaurantAdapter();
    }

    private void initMapSheet() {
        mapSheet = new MapSheetSW(this);
        mapSheet.show(getSupportFragmentManager(), null);
    }

    private void getIntentData() {
        id = getIntent().getIntExtra("id", -1);
    }

    private void initViews() {
        binding.resturantSpecify.setOnClickListener(this);
        binding.resturantType.setOnClickListener(this);
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

        binding.rvRestaurant.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                if (!binding.rvRestaurant.canScrollVertically(1) && binding.rvRestaurant.getVisibility() == View.VISIBLE
                        && !mainViewModel.isFetchingExhausted() && !mainViewModel.isFetchingNextPage()) {
                    // search the next page
                    loaded = false;
                    mainViewModel.getNextPage(id, lon, lat, AddressId);
                }
            }
        });

    }

    private void initViewModel() {
        mainViewModel = new ViewModelProvider(this,
                new ViewModelProvider.AndroidViewModelFactory(getApplication())).get(MainViewModel.class);

        generalDataViewModel = new ViewModelProvider(this,
                new ViewModelProvider.AndroidViewModelFactory(getApplication())).get(GeneralDataViewModel.class);
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


    private void initRestaurantAdapter() {
        customRecyclerViewAdapter = new CustomRecyclerViewAdapter(this, CustomRecyclerViewAdapter.HolderConstants.RESTAURANTS_ITEM);
        customRecyclerViewAdapter.setRestaurants(items);
        binding.rvRestaurant.setAdapter(customRecyclerViewAdapter);
    }

    private void startMainProcess() {
        mainViewModel.getMain(id, lon, lat, AddressId, kitchen_type_id, restaurant_type_id, order_by).observe(this, resource -> {
            switch (resource.status) {
                case LOADING:
                    if (pulled)
                        binding.swipeRefresh.setEnabled(false);
                    else if (mainViewModel.getPageNumber() > 1) {
                        customRecyclerViewAdapter.displayLoading();
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
            binding.rvRestaurant.setVisibility(View.VISIBLE);
        } else {
            binding.loading.setVisibility(View.VISIBLE);
            binding.rvRestaurant.setVisibility(View.GONE);
        }
    }

    private void updateUi(GeneralResponse response) {

        customRecyclerViewAdapter.hideLoading();
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

            customRecyclerViewAdapter.setRestaurants(items);
            binding.rvRestaurant.setVisibility(View.VISIBLE);

            if (oldPosition > 0) {
                binding.rvRestaurant.smoothScrollToPosition(oldPosition);
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
        switch (action) {
            case "RESTAURANT_ITEM":
                Intent intent = new Intent(ResturantsActivity.this, ResturantPageActivity.class);
                intent.putExtra("id", items.get(position).getId());
                intent.putExtra("name", items.get(position).getName());
                intent.putExtra("image", items.get(position).getLogo());
                intent.putExtra("desc", items.get(position).getDescription());
                intent.putExtra("min", items.get(position).getMinPrice());
                intent.putExtra("Total", items.get(position).getRates().getTotal());
                intent.putExtra("Number", items.get(position).getRates().getNumber());
                startActivity(intent);
                break;
            case "TEXT":
                bottomSheetListText.dismissDialog();
                restaurant_type_id = position + 1;
                binding.resturantSpecify.setText(titles.get(position));
                loaded = false;
                startMainProcess();
                break;
            case "BOTTOM_SHEET_ITEM_RADIO_1":
                for (int i = 0; i < titleFirstList.size(); i++) {
                    titleFirstList.get(i).setCheck(i == position);
                }
                order_by = titleFirstList.get(position).getTitle();
                loaded = false;
                startMainProcess();
                bottomSheetTwoList.notifyFirstList();
                bottomSheetTwoList.dismissDialog();
                break;
            case "BOTTOM_SHEET_ITEM_RADIO_2":

                for (int i = 0; i < titleSecondList.size(); i++) {
                    titleSecondList.get(i).setCheck(i == position);
                }
                kitchen_type_id = position + 1;
                binding.resturantType.setText(titleSecondList.get(position).getTitle());
                loaded = false;
                startMainProcess();
                bottomSheetTwoList.notifySecondList();
                bottomSheetTwoList.dismissDialog();
                break;
            case "MAP_SHEET":
                lon = mapSheet.getLon();
                lat = mapSheet.getLat();
                AddressId = mapSheet.getAddressId();
                startMainProcess();
                break;

        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.resturant_specify:
                bottomSheetTwoList.showDialog();

                break;
            case R.id.resturant_type:
                bottomSheetListText.showDialog();
                break;
            case R.id.cart:
                if (SharedPreferencesHelper.getUserToken(this).isEmpty())
                    startActivity(new Intent(this, LoginActivity.class));
                else
                    startActivity(new Intent(this, CartActivity.class));

        }
    }

    private void resturant_specify() {
        bottomSheetTwoList = new BottomSheetTwoList(this, this);

        bottomSheetTwoList.setFirstList(getString(R.string.sort_by), titleFirstList);
        bottomSheetTwoList.setSecondList(getString(R.string.kitchen_type), titleSecondList);

    }


    private void restaurantType() {
        bottomSheetListText = new BottomSheetListText(this, this);
        bottomSheetListText.setList(getString(R.string.restaurant_type), titles);
    }


    private void startRestaurantTypesProcess() {
        generalDataViewModel.getRestaurantTypes().observe(this, resource -> {
            switch (resource.status) {
                case LOADING:
                    break;
                case AUTHENTICATED:
                    for (int i = 0; i < resource.data.getRestaurantTypesData().getRestaurantTypes().size(); i++) {
                        titles.add(resource.data.getRestaurantTypesData().getRestaurantTypes().get(i).getName());
                    }
                    generalDataViewModel.removeRestaurantTypesObservers(this);
                    break;
                case ERROR:
                    showFailed(resource);
                    break;
            }
        });
    }

    private void startKitchenTypesProcess() {
        generalDataViewModel.getKitchenTypes().observe(this, resource -> {
            switch (resource.status) {
                case LOADING:
                    break;
                case AUTHENTICATED:
                    for (int i = 0; i < resource.data.getKitchenTypesData().getKitchenTypes().size(); i++) {
                        titleSecondList.add(new Radio(resource.data.getKitchenTypesData().getKitchenTypes().get(i).getId(), resource.data.getKitchenTypesData().getKitchenTypes().get(i).getName(), false));
                    }
                    titleFirstList.add(new Radio("min_price_lowest", false));
                    titleFirstList.add(new Radio("min_price_highest", false));
                    titleFirstList.add(new Radio("rate", false));

                    generalDataViewModel.removeKitchenTypesObservers(this);
                    break;
                case ERROR:
                    showFailed(resource);
                    break;
            }
        });
    }


}