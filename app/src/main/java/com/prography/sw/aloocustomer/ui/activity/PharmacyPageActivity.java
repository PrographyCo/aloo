package com.prography.sw.aloocustomer.ui.activity;

import static com.prography.sw.aloocustomer.util.AppUtils.unauthorized;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
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
import com.prography.sw.aloocustomer.custom.BottomSheetCardList;
import com.prography.sw.aloocustomer.custom.BottomSheetListText;
import com.prography.sw.aloocustomer.data.response.GeneralResponse;
import com.prography.sw.aloocustomer.databinding.ActivityPharmacyPageBinding;

import com.prography.sw.aloocustomer.model.Category;
import com.prography.sw.aloocustomer.model.Radio;
import com.prography.sw.aloocustomer.model.VendorItems;
import com.prography.sw.aloocustomer.network.AuthResource;
import com.prography.sw.aloocustomer.util.AppUtils;
import com.prography.sw.aloocustomer.util.Constants;
import com.prography.sw.aloocustomer.util.SharedPreferencesHelper;
import com.prography.sw.aloocustomer.viewmodel.FavoriteViewModel;
import com.prography.sw.aloocustomer.viewmodel.MainViewModel;

import java.util.ArrayList;
import java.util.List;

public class PharmacyPageActivity extends AppCompatActivity implements CustomListener, View.OnClickListener {
    private ActivityPharmacyPageBinding binding;
    private BottomSheetCardList bottomSheetCardList;
    private BottomSheetListText bottomSheetListText;
    private List<VendorItems> items = new ArrayList<>();
    private CustomRecyclerViewAdapter adapter;
    private MainViewModel mainViewModel;
    List<String> titles = new ArrayList<>();
    List<Integer> titles_Id = new ArrayList<>();
    List<Radio> titleFirstList = new ArrayList<>();

    private int id;
    private int category_id;
    private String order_by;
    private String image, logo;
    private FavoriteViewModel favoriteViewModel;
    private boolean loaded;
    private boolean pulled = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPharmacyPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initViews();
        cardItem();
        inirViewModel();
        categories();
        storBy();
        getIntentData();
        initRestaurantPageAdapter();
        startCategoriesProcess();
        startVendorProcess();
    }

    private void inirViewModel() {
        mainViewModel = new ViewModelProvider(this,
                new ViewModelProvider.AndroidViewModelFactory(getApplication())).get(MainViewModel.class);

        favoriteViewModel = new ViewModelProvider(this,
                new ViewModelProvider.AndroidViewModelFactory(this.getApplication()))
                .get(FavoriteViewModel.class);
    }

    private void getIntentData() {
        id = getIntent().getIntExtra("id", -1);
        logo = getIntent().getStringExtra("logo");
        image = getIntent().getStringExtra("image");

        AppUtils.initGlide(this).load(image).into(binding.itemImage);
        AppUtils.initGlide(this).load(logo).into(binding.vendorImage);
    }

    private void initViews() {
        binding.resturantSpecify.setOnClickListener(this);
        binding.resturantType.setOnClickListener(this);
        binding.cart.setOnClickListener(this);
        binding.backImage.setOnClickListener(this);


        binding.swipeRefresh.setOnRefreshListener(() -> {
            pulled = true;
            mainViewModel.setPageNumber(1);
            mainViewModel.setFetchingNextPage(false);
            mainViewModel.setFetchingExhausted(false);
            loaded = false;
            startVendorProcess();
        });

        binding.rvPharmacyPage.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                if (!binding.rvPharmacyPage.canScrollVertically(1) && binding.rvPharmacyPage.getVisibility() == View.VISIBLE
                        && !mainViewModel.isFetchingExhausted() && !mainViewModel.isFetchingNextPage()) {
                    // search the next page
                    loaded = false;
                    mainViewModel.getNextPageVendor(id, Double.valueOf(SharedPreferencesHelper.getLon(PharmacyPageActivity.this)),
                            Double.valueOf(SharedPreferencesHelper.getLat(PharmacyPageActivity.this)));
                }
            }
        });


    }

    private void categories() {
        bottomSheetListText = new BottomSheetListText(this, this);
        bottomSheetListText.setList(getString(R.string.restaurant_type), titles);

    }

    private void storBy() {
        bottomSheetCardList = new BottomSheetCardList(this, this, false);

    }

    private void startCategoriesProcess() {
        mainViewModel.getCategories(id).observe(this, resource -> {
            switch (resource.status) {
                case LOADING:
                    break;
                case AUTHENTICATED:
                    for (int i = 0; i < resource.data.getCategoriesData().getCategories().size(); i++) {
                        titles.add(resource.data.getCategoriesData().getCategories().get(i).getName());
                        titles_Id.add(resource.data.getCategoriesData().getCategories().get(i).getId());
                    }

                    break;
                case ERROR:
                    showFailed(resource);
                    break;
            }
        });
    }


    private void initRestaurantPageAdapter() {

        adapter = new CustomRecyclerViewAdapter(this, CustomRecyclerViewAdapter.HolderConstants.ITEM_SUPERMARKET_PAGE);
        adapter.setVendorItems(items);
        binding.rvPharmacyPage.setAdapter(adapter);
        Log.d("TAG", "initRestaurantPageAdapter: ");
    }


    private void startVendorProcess() {
        mainViewModel.getVendor(id, order_by, category_id, Double.valueOf(SharedPreferencesHelper.getLon(this)),
                Double.valueOf(SharedPreferencesHelper.getLat(this))).observe(this, resource -> {
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
                        mainViewModel.removeVendorObservers(this);
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
            binding.rvPharmacyPage.setVisibility(View.VISIBLE);

        } else {
            binding.loading.setVisibility(View.VISIBLE);
            binding.rvPharmacyPage.setVisibility(View.GONE);

        }
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


    private void updateUi(GeneralResponse response) {
        adapter.hideLoading();
        showParent(View.VISIBLE);

        if (response.getVendorSuperPharmsData().getItems() != null && response.getVendorSuperPharmsData().getItems().size() > 0) {
            int oldPosition = -1;
            if (mainViewModel.isFetchingNextPage()) {
                oldPosition = items.size();
                this.items.addAll(response.getVendorSuperPharmsData().getItems());
                mainViewModel.setFetchingNextPage(false);
            } else {
                this.items = response.getVendorSuperPharmsData().getItems();
            }
            if (response.getVendorSuperPharmsData().getItems().size() < Constants.FETCHING_LIMIT)
                mainViewModel.setFetchingExhausted(true);

            adapter.setVendorItems(items);
            binding.rvPharmacyPage.setVisibility(View.VISIBLE);

            if (oldPosition > 0) {
                binding.rvPharmacyPage.smoothScrollToPosition(oldPosition);
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
//        items = response.getVendorSuperPharmsData().getItems();
//        adapter.setVendorItems(items);


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
            case "ITEM_SUPERMARKET_PAGE":
                Intent intent = new Intent(PharmacyPageActivity.this, ProductDetailsActivity.class);
                intent.putExtra("id", items.get(position).getId());
                intent.putExtra("image", items.get(position).getImg());
                startActivity(intent);
                break;
            case "highToLow":
                order_by = "price_highest";
                loaded = false;
                startVendorProcess();
                break;
            case "lowToHigh":
                order_by = "price_lowest";
                loaded = false;
                startVendorProcess();
                break;
            case "TEXT":
                category_id = titles_Id.get(position);
                binding.resturantType.setText(titles.get(position));
                loaded = false;
                startVendorProcess();
                bottomSheetListText.dismissDialog();
                break;
            case "FAVORITE":
                if (items.get(position).isFavorite())
                    deleteFavorite(items.get(position).getId());
                else
                    addToFavorite(items.get(position).getId());
        }

    }

    private void addToFavorite(int id) {
        favoriteViewModel.addToFavorite(String.valueOf(id)).observe(this, result -> {
            switch (result.status) {
                case AUTHENTICATED:
                    updateDeleteUi(result.data);
                    break;
                case ERROR:
                    showDeleteFailed(result);
                    break;
            }
        });
    }

    private void deleteFavorite(int id) {
        favoriteViewModel.deleteFavorite(id).observe(this, result -> {
            switch (result.status) {
                case AUTHENTICATED:
                    updateDeleteUi(result.data);
                    break;
                case ERROR:
                    showDeleteFailed(result);
                    break;
            }
        });
    }

    private void updateDeleteUi(GeneralResponse response) {
        Toast.makeText(this, response.getMessage(), Toast.LENGTH_SHORT).show();
        startVendorProcess();
        adapter.notifyDataSetChanged();
    }

    private void showDeleteFailed(AuthResource<GeneralResponse> errorResponse) {
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
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.resturant_type:
                bottomSheetListText.showDialog();
                break;
            case R.id.resturant_specify:
                bottomSheetCardList.showDialog();
                break;
            case R.id.cart:
                if (SharedPreferencesHelper.getUserToken(this).isEmpty())
                    startActivity(new Intent(this, LoginActivity.class));
                else
                    startActivity(new Intent(this, CartActivity.class));

                break;
            case R.id.back_image:
                this.finish();
                break;
        }
    }
}