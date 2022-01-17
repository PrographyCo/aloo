package com.prography.sw.aloocustomer.ui.activity;

import static com.prography.sw.aloocustomer.util.AppUtils.unauthorized;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.prography.sw.aloocustomer.R;
import com.prography.sw.aloocustomer.adapter.CustomListener;
import com.prography.sw.aloocustomer.adapter.CustomRecyclerViewAdapter;
import com.prography.sw.aloocustomer.custom.BottomSheetListImageText;
import com.prography.sw.aloocustomer.custom.BottomSheetListText;
import com.prography.sw.aloocustomer.data.response.GeneralResponse;
import com.prography.sw.aloocustomer.databinding.ActivityFavouriteBinding;
import com.prography.sw.aloocustomer.model.ImageText;
import com.prography.sw.aloocustomer.model.VendorItems;
import com.prography.sw.aloocustomer.network.AuthResource;
import com.prography.sw.aloocustomer.util.Constants;
import com.prography.sw.aloocustomer.viewmodel.FavoriteViewModel;

import java.util.ArrayList;
import java.util.List;

public class FavouriteActivity extends AppCompatActivity implements CustomListener {

    private ActivityFavouriteBinding binding;

    private BottomSheetListImageText bottomSheetListImageText;
    private BottomSheetListText bottomSheetListText;
    List<ImageText> imageTexts = new ArrayList<>();
    List<String> vendorsName = new ArrayList<>();
    List<Integer> vendorsId = new ArrayList<>();

    private CustomRecyclerViewAdapter adapter;
    private List<VendorItems> items = new ArrayList<>();

    private int chosenPosition = 1;

    private FavoriteViewModel favoriteViewModel;
    private boolean loaded;
    private boolean pulled = false;
    private int vendorId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityFavouriteBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initViewModel();
        initValues();
        initAdapter(CustomRecyclerViewAdapter.HolderConstants.ITEM_SUPERMARKET_PAGE);
        initBottomSheet();
        setListeners();
        getFavoriteVendors(chosenPosition);
    }

    private void initViewModel() {
        favoriteViewModel = new ViewModelProvider(this,
                new ViewModelProvider.AndroidViewModelFactory(this.getApplication()))
                .get(FavoriteViewModel.class);
    }

    private void getFavoriteVendors(int id) {
        favoriteViewModel.getFavoriteVendors(id).observe(this, result -> {
            switch (result.status) {
                case LOADING:
                    bottomSheetListText.showParent(View.GONE);
                    break;
                case AUTHENTICATED:
                    updateFavoriteVendorsUi(result.data);
                    favoriteViewModel.removeFavoriteVendorsObservers(this);
                    Log.d("TAG", "getFavoriteVendors ");
                    break;
                case ERROR:
                    showFavoriteVendorsFailed(result);
                    favoriteViewModel.removeFavoriteVendorsObservers(this);
                    break;
            }
        });
    }

    private void updateFavoriteVendorsUi(GeneralResponse response) {
        bottomSheetListText.showParent(View.VISIBLE);
        if (response == null || response.getFavoriteVendors() == null || response.getFavoriteVendors().getVendors().isEmpty()) {
            Toast.makeText(this, getString(R.string.something_went_wrong_text), Toast.LENGTH_SHORT).show();
            return;
        }
        vendorsName.clear();
        vendorsId.clear();
        for (int i = 0; i < response.getFavoriteVendors().getVendors().size(); i++) {
            vendorsName.add(response.getFavoriteVendors().getVendors().get(i).getName());
            vendorsId.add(response.getFavoriteVendors().getVendors().get(i).getId());
        }
        binding.tvName.setText(vendorsName.get(0));
        bottomSheetListText.setList(null, vendorsName);
    }

    private void showFavoriteVendorsFailed(AuthResource<GeneralResponse> errorResponse) {
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
        bottomSheetListText.showParent(View.VISIBLE);
    }

    private void initValues() {
        binding.ivEmpty.setImageResource(R.drawable.ic_empty_supermarket);
        binding.tvEmpty.setText(getString(R.string.choose_supermarket));
    }

    private void initAdapter(CustomRecyclerViewAdapter.HolderConstants holderConstant) {
        adapter = new CustomRecyclerViewAdapter(this, holderConstant);
        binding.rvFavourite.setAdapter(adapter);
    }

    private void initBottomSheet() {
        bottomSheetListImageText = new BottomSheetListImageText(this, (position, action) -> {
            chosenPosition = position + 1;
            binding.ivEmpty.setVisibility(View.VISIBLE);
            binding.tvEmpty.setVisibility(View.VISIBLE);
            binding.rvFavourite.setVisibility(View.GONE);
            binding.swipeRefresh.setVisibility(View.GONE);

            switch (position) {
                case 0:
                    binding.tvEmpty.setText(getString(R.string.choose_supermarket));
                    binding.ivEmpty.setImageResource(R.drawable.ic_supermarkets);
                    break;
                case 1:
                    binding.tvEmpty.setText(getString(R.string.choose_pharmacy));
                    binding.ivEmpty.setImageResource(R.drawable.ic_empty_supermarket);
                    break;
                case 2:
                    binding.tvEmpty.setText(getString(R.string.choose_restaurant));
                    binding.ivEmpty.setImageResource(R.drawable.ic_restaurants);
                    break;
            }

            binding.tvType.setText(imageTexts.get(position).getTitle());
            bottomSheetListImageText.dismissDialog();
            getFavoriteVendors(chosenPosition);

        });
        imageTexts.add(new ImageText(R.drawable.ic_supermarkets, getString(R.string.supermarkets)));
        imageTexts.add(new ImageText(R.drawable.ic_cart, getString(R.string.pharmacies)));
        imageTexts.add(new ImageText(R.drawable.ic_restaurants, getString(R.string.restaurants)));
        bottomSheetListImageText.setList(imageTexts);
        binding.tvType.setText(imageTexts.get(chosenPosition - 1).getTitle());

        // list vendors
        bottomSheetListText = new BottomSheetListText(this, (position, action) -> {

            binding.ivEmpty.setVisibility(View.GONE);
            binding.tvEmpty.setVisibility(View.GONE);
            binding.rvFavourite.setVisibility(View.VISIBLE);
            binding.swipeRefresh.setVisibility(View.VISIBLE);

            if (chosenPosition == 3)
                setRestaurantItems();
            else
                setItems();
            adapter.setVendorItems(items);

            binding.tvName.setText(vendorsName.get(position));
            bottomSheetListText.dismissDialog();
            vendorId = vendorsId.get(position);
            loaded = false;
            getFavoriteItems();
        });
        bottomSheetListText.setList(null, vendorsName);

    }

    private void setRestaurantItems() {
        initAdapter(CustomRecyclerViewAdapter.HolderConstants.RESTAURANTS_PAGE_ITEM);
        binding.rvFavourite.setLayoutManager(new LinearLayoutManager(this));
    }

    private void setItems() {
        initAdapter(CustomRecyclerViewAdapter.HolderConstants.ITEM_SUPERMARKET_PAGE);
        binding.rvFavourite.setLayoutManager(new GridLayoutManager(this, 2));
    }

    private void setListeners() {
        binding.backImage.setOnClickListener(view -> {
            this.finish();
        });

        binding.tvType.setOnClickListener(view -> {
            bottomSheetListImageText.showDialog();
        });

        binding.tvName.setOnClickListener(view -> {
            bottomSheetListText.showDialog();
        });

        binding.swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pulled = true;
                favoriteViewModel.setPageNumber(1);
                favoriteViewModel.setFetchingNextPage(false);
                favoriteViewModel.setFetchingExhausted(false);
                loaded = false;
                getFavoriteItems();
            }
        });

        binding.rvFavourite.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                if (!binding.rvFavourite.canScrollVertically(1) && binding.rvFavourite.getVisibility() == View.VISIBLE
                        && !favoriteViewModel.isFetchingExhausted() && !favoriteViewModel.isFetchingNextPage()) {
                    // search the next page
                    loaded = false;
                    favoriteViewModel.getNextPage(vendorId);
                }
            }
        });
    }

    @Override
    public void onItemClick(int position, String action) {
        if (action.equals("FAVORITE") || action.equals("FAVORITE_RESTAURANT"))
            deleteFavorite(items.get(position).getId());
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
        loaded = false;
        getFavoriteItems();
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

    private void getFavoriteItems() {
        favoriteViewModel.getFavoriteItems(vendorId).observe(this, result -> {
            switch (result.status) {
                case LOADING:
                    if (pulled)
                        binding.swipeRefresh.setEnabled(false);
                    else if (favoriteViewModel.getPageNumber() > 1) {
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

                    break;
                case ERROR:
                    if (!loaded) {
                        showFailed(result);
                    }
                    loaded = true;

                    break;
            }
        });
    }

    private void showParent(int visibility) {
        if (visibility == View.VISIBLE) {
            binding.loading.setVisibility(View.GONE);
            binding.rvFavourite.setVisibility(View.VISIBLE);
        } else {
            binding.loading.setVisibility(View.VISIBLE);
            binding.rvFavourite.setVisibility(View.GONE);
        }
    }

    private void updateUi(GeneralResponse response) {
        adapter.hideLoading();
        showParent(View.VISIBLE);

        if (response.getFavoriteItems().getItems() != null && response.getFavoriteItems().getItems().size() > 0) {
            int oldPosition = -1;
            if (favoriteViewModel.isFetchingNextPage()) {
                oldPosition = items.size();
                this.items.addAll(response.getFavoriteItems().getItems());
                favoriteViewModel.setFetchingNextPage(false);
            } else {
                this.items = response.getFavoriteItems().getItems();
            }

            if (response.getFavoriteItems().getItems().size() < Constants.FETCHING_LIMIT)
                favoriteViewModel.setFetchingExhausted(true);

            adapter.setVendorItems(items);
            binding.rvFavourite.setVisibility(View.VISIBLE);

            if (oldPosition > 0) {
                binding.rvFavourite.smoothScrollToPosition(oldPosition);
            }

        } else {
            if (items.size() == 0) {
                Toast.makeText(this, getString(R.string.no_questions_were_found), Toast.LENGTH_SHORT).show();
            } else {
                favoriteViewModel.setFetchingExhausted(true);
                favoriteViewModel.setFetchingNextPage(false);
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