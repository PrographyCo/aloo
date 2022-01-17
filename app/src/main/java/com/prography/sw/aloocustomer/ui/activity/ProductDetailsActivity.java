package com.prography.sw.aloocustomer.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
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
import com.prography.sw.aloocustomer.adapter.SlidePagerAdapter;
import com.prography.sw.aloocustomer.databinding.ActivityProductDetailsBinding;
import com.prography.sw.aloocustomer.model.VendorItems;

import com.prography.sw.aloocustomer.ui.MainActivity;
import com.prography.sw.aloocustomer.util.SharedPreferencesHelper;
import com.prography.sw.aloocustomer.network.AuthResource;
import com.prography.sw.aloocustomer.util.AppUtils;
import com.prography.sw.aloocustomer.viewmodel.CardViewModel;
import com.prography.sw.aloocustomer.viewmodel.MainViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

public class ProductDetailsActivity extends AppCompatActivity implements CustomListener, View.OnClickListener {
    private ActivityProductDetailsBinding binding;
    private SlidePagerAdapter adapter;
    private List<String> listSlides = new ArrayList<>();
    private List<VendorItems> items = new ArrayList<>();
    private int count = 0;
    private int count_cart_item = 0;
    private int id;

    private int amount;
    private MainViewModel mainViewModel;
    private CardViewModel cardViewModel;
    private CustomRecyclerViewAdapter productAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProductDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initViews();
        cardItem();
        getIntentDate();
        initViewModel();
        initadapter();
        initRestaurantPageAdapter();
        startItemsProcess();
    }

    private void getIntentDate() {
        id = getIntent().getIntExtra("id", -1);
//        image = getIntent().getStringExtra("image");
//        AppUtils.initGlide(this).load(image).into(binding.vendorItemImage);
        Toast.makeText(this, id + "", Toast.LENGTH_SHORT).show();
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

    private void initViewModel() {
        mainViewModel = new ViewModelProvider(this,
                new ViewModelProvider.AndroidViewModelFactory(getApplication())).get(MainViewModel.class);

        cardViewModel = new ViewModelProvider(this,
                new ViewModelProvider.AndroidViewModelFactory(getApplication())).get(CardViewModel.class);

    }

    private void startItemsProcess() {
        mainViewModel.getVendorItem(id).observe(this, resource -> {
            switch (resource.status) {
                case LOADING:
                    showParent(View.GONE);
                    break;
                case AUTHENTICATED:
                    updateUi(resource.data);
                    break;
                case ERROR:
                    showFailed(resource);
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
        binding.itemName.setText(response.getVendorDetilseSuperPhrmasData().getName());
        binding.itemNumber.setText(response.getVendorDetilseSuperPhrmasData().getAmount() + " " + response.getVendorDetilseSuperPhrmasData().getAmountType());
        amount = response.getVendorDetilseSuperPhrmasData().getAmount();
        binding.itemPrice.setText(response.getVendorDetilseSuperPhrmasData().getPrice() + getString(R.string.SR));
        listSlides = response.getVendorDetilseSuperPhrmasData().getImages();
        items = response.getVendorDetilseSuperPhrmasData().getSimilar();
        adapter = new SlidePagerAdapter(listSlides);
        binding.sliderPager.setAdapter(adapter);
        binding.indicator.setupWithViewPager(binding.sliderPager, true);
        productAdapter.setVendorItems(items);
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


    private void startAddToCardProcess() {
        cardViewModel.addToCardSuperPharmacy(id, count).observe(this, resource -> {
            switch (resource.status) {
                case LOADING:
                    showParentAddtoCard(View.GONE);
                    break;
                case AUTHENTICATED:
                    updateUiAddtoCard(resource.data);
                    cardViewModel.removeAddToCardObservers(this);
                    break;
                case ERROR:
                    showFailedAddtoCard(resource);
                    break;
            }

        });
    }

    private void showParentAddtoCard(int visibility) {
        if (visibility == View.VISIBLE) {
            binding.loadingAddToCard.setVisibility(View.GONE);
            binding.btnAddToCart.setVisibility(View.VISIBLE);

        } else {
            binding.loadingAddToCard.setVisibility(View.VISIBLE);
            binding.btnAddToCart.setVisibility(View.GONE);

        }
    }


    private void updateUiAddtoCard(GeneralResponse response) {
        showParentAddtoCard(View.VISIBLE);
        count_cart_item++;
        binding.cartItemCount.setVisibility(View.VISIBLE);
        binding.numberCartItemCount.setVisibility(View.VISIBLE);
        binding.numberCartItemCount.setText(String.valueOf(count_cart_item));
        Toast.makeText(this, response.getMessage(), Toast.LENGTH_SHORT).show();

        startActivity(new Intent(this, MainActivity.class));
        finish();
    }


    private void showFailedAddtoCard(AuthResource<GeneralResponse> errorResponse) {
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
        showParentAddtoCard(View.VISIBLE);
    }


    private void initadapter() {
        adapter = new SlidePagerAdapter(listSlides);
        binding.sliderPager.setAdapter(adapter);
        Timer timer = new Timer();
        // timer.scheduleAtFixedRate(new ResturantsActivity.SliderTimer(), 4000, 3000);
        binding.indicator.setupWithViewPager(binding.sliderPager, true);

    }

    private void initViews() {
        binding.cart.setOnClickListener(this);
        binding.addImg.setOnClickListener(this);
        binding.miMinus.setOnClickListener(this);
        binding.btnAddToCart.setOnClickListener(this);
        binding.backImage.setOnClickListener(this);


    }

    private void initRestaurantPageAdapter() {

        productAdapter = new CustomRecyclerViewAdapter(this, CustomRecyclerViewAdapter.HolderConstants.ITEM_SUPERMARKET_PAGE);
        productAdapter.setVendorItems(items);
        binding.rvSameProdect.setAdapter(productAdapter);
        Log.d("TAG", "initRestaurantPageAdapter: ");
    }

    @Override
    public void onItemClick(int position, String action) {

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
            case R.id.btn_add_to_cart:

                if (SharedPreferencesHelper.getUserToken(this).isEmpty())
                    startActivity(new Intent(this, LoginActivity.class));
                else {
                    startAddToCardProcess();
                }
                break;
            case R.id.back_image:
                this.finish();
                break;
            case R.id.add_img:
                if (count < amount) {
                    count++;
                    binding.number.setText(String.valueOf(count));
                } else {
                    Toast.makeText(this, "the item amount must be less  than one or equels the items amount ^_-", Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.mi_minus:
                if (count > 1) {
                    count--;
                    binding.number.setText(String.valueOf(count));
                } else {
                    Toast.makeText(this, "the item amount must be larger than one or one ^_-", Toast.LENGTH_SHORT).show();
                }
                break;


        }

    }
}