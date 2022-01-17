package com.prography.sw.aloocustomer.ui.activity;

import static com.prography.sw.aloocustomer.util.AppUtils.unauthorized;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.prography.sw.aloocustomer.R;
import com.prography.sw.aloocustomer.adapter.CustomListener;
import com.prography.sw.aloocustomer.adapter.CustomRecyclerViewAdapter;
import com.prography.sw.aloocustomer.adapter.SlidePagerAdapter;
import com.prography.sw.aloocustomer.data.response.GeneralResponse;
import com.prography.sw.aloocustomer.databinding.ActivityResturantPageBinding;
import com.prography.sw.aloocustomer.model.Offer;
import com.prography.sw.aloocustomer.model.VendorItems;
import com.prography.sw.aloocustomer.model.slide;
import com.prography.sw.aloocustomer.network.AuthResource;
import com.prography.sw.aloocustomer.util.AppUtils;
import com.prography.sw.aloocustomer.util.Constants;
import com.prography.sw.aloocustomer.util.SharedPreferencesHelper;
import com.prography.sw.aloocustomer.viewmodel.FavoriteViewModel;
import com.prography.sw.aloocustomer.viewmodel.MainViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class ResturantPageActivity extends AppCompatActivity implements CustomListener, View.OnClickListener {

    private ActivityResturantPageBinding binding;
    private List<VendorItems> items = new ArrayList<>();
    private MainViewModel mainViewModel;
    private CustomRecyclerViewAdapter adapter;
    private int id, total, number;
    private String name, min, desc, image;
    private FavoriteViewModel favoriteViewModel;
    private SlidePagerAdapter adapterSlide;
    private List<Offer> listSlides = new ArrayList<>();
    private boolean loaded;
    private boolean pulled = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityResturantPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initViews();
        cardItem();
        initadapter();
        inirViewModel();
        getIntentData();
        initRestaurantPageAdapter();
        startVendorProcess();
    }

    private void inirViewModel() {
        mainViewModel = new ViewModelProvider(this,
                new ViewModelProvider.AndroidViewModelFactory(getApplication())).get(MainViewModel.class);

        favoriteViewModel = new ViewModelProvider(this,
                new ViewModelProvider.AndroidViewModelFactory(this.getApplication()))
                .get(FavoriteViewModel.class);
    }

    private void initViews() {
        binding.backImage.setOnClickListener(this);
        binding.cart.setOnClickListener(this);

        binding.swipeRefresh.setOnRefreshListener(() -> {
            pulled = true;
            mainViewModel.setPageNumber(1);
            mainViewModel.setFetchingNextPage(false);
            mainViewModel.setFetchingExhausted(false);
            loaded = false;
            startVendorProcess();
        });
        binding.rvRestaurantPage.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                if (!binding.rvRestaurantPage.canScrollVertically(1) && binding.rvRestaurantPage.getVisibility() == View.VISIBLE
                        && !mainViewModel.isFetchingExhausted() && !mainViewModel.isFetchingNextPage()) {
                    // search the next page
                    loaded = false;
                    mainViewModel.getNextPageVendor(id, Double.valueOf(SharedPreferencesHelper.getLon(ResturantPageActivity.this)),
                            Double.valueOf(SharedPreferencesHelper.getLat(ResturantPageActivity.this)));
                }
            }
        });

    }

    private void getIntentData() {
        id = getIntent().getIntExtra("id", -1);
        total = getIntent().getIntExtra("Total", -1);
        number = getIntent().getIntExtra("Number", -1);
        name = getIntent().getStringExtra("name");
        min = getIntent().getStringExtra("min");
        desc = getIntent().getStringExtra("desc");
        image = getIntent().getStringExtra("image");


        AppUtils.initGlide(this).load(image).into(binding.vendorImage);
        binding.tvVendorName.setText(name);
        binding.tvVendorServis.setText(desc);
        binding.minimum.setText(getString(R.string.minimum) + " " + min);
        binding.resturantRating.setRating(total);
        binding.numberReate.setText(number + "");

    }

    private void initadapter() {
        adapterSlide = new SlidePagerAdapter(listSlides, "offer");
        binding.sliderPager.setAdapter(adapterSlide);
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new ResturantPageActivity.SliderTimer(), 4000, 3000);
        binding.indicator.setupWithViewPager(binding.sliderPager, true);
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

    private void initRestaurantPageAdapter() {
        adapter = new CustomRecyclerViewAdapter(this, CustomRecyclerViewAdapter.HolderConstants.RESTAURANTS_PAGE_ITEM);
        adapter.setVendorItems(items);
        binding.rvRestaurantPage.setAdapter(adapter);
    }


    private void startVendorProcess() {
        mainViewModel.getVendor(id, Double.valueOf(SharedPreferencesHelper.getLon(this)),
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
            binding.rvRestaurantPage.setVisibility(View.VISIBLE);
            binding.indicator.setVisibility(View.VISIBLE);
            binding.sliderPager.setVisibility(View.VISIBLE);

        } else {
            binding.loading.setVisibility(View.VISIBLE);
            binding.rvRestaurantPage.setVisibility(View.GONE);
            binding.indicator.setVisibility(View.GONE);
            binding.sliderPager.setVisibility(View.GONE);

        }
    }

    private void updateUi(GeneralResponse response) {
        adapter.hideLoading();
        showParent(View.VISIBLE);

        if (response.getVendorData().getMain().getItems() != null && response.getVendorData().getMain().getItems().size() > 0) {
            int oldPosition = -1;
            if (mainViewModel.isFetchingNextPage()) {
                oldPosition = items.size();
                this.items.addAll(response.getVendorData().getMain().getItems());
                mainViewModel.setFetchingNextPage(false);
            } else {
                this.items = response.getVendorData().getMain().getItems();
            }
            if (response.getVendorData().getMain().getItems().size() < Constants.FETCHING_LIMIT)
                mainViewModel.setFetchingExhausted(true);

            adapter.setVendorItems(items);
            binding.rvRestaurantPage.setVisibility(View.VISIBLE);

            if (oldPosition > 0) {
                binding.rvRestaurantPage.smoothScrollToPosition(oldPosition);
            }

        }else {
            if (items.size() == 0) {
                Toast.makeText(this, getString(R.string.no_questions_were_found), Toast.LENGTH_SHORT).show();
            } else {
                mainViewModel.setFetchingExhausted(true);
                mainViewModel.setFetchingNextPage(false);
            }
        }


        cancelSwipeRefreshLayout();




        items = response.getVendorData().getMain().getItems();
        adapter.setVendorItems(items);

        listSlides = response.getVendorData().getOffers();
        adapterSlide = new SlidePagerAdapter(listSlides, "offer");
        binding.sliderPager.setAdapter(adapterSlide);

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
            case "RESTAURANT_PAGE_ITEM":
                Intent intent = new Intent(ResturantPageActivity.this, MealDetailsActivity.class);
                intent.putExtra("id", items.get(position).getId());
                intent.putExtra("image", items.get(position).getImg());
                startActivity(intent);
                break;
            case "FAVORITE_RESTAURANT":
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


    //Timer class
    class SliderTimer extends TimerTask {
        @Override
        public void run() {
            ResturantPageActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (binding.sliderPager.getCurrentItem() < listSlides.size() - 1) {
                        binding.sliderPager.setCurrentItem(binding.sliderPager.getCurrentItem() + 1);
                    } else {
                        binding.sliderPager.setCurrentItem(0);
                    }
                }
            });
        }
    }


}