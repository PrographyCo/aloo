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
import com.prography.sw.aloocustomer.databinding.ActivityMealDetailsBinding;
import com.prography.sw.aloocustomer.model.MealDetails;
import com.prography.sw.aloocustomer.network.AuthResource;
import com.prography.sw.aloocustomer.ui.MainActivity;
import com.prography.sw.aloocustomer.util.AppUtils;
import com.prography.sw.aloocustomer.util.SharedPreferencesHelper;
import com.prography.sw.aloocustomer.viewmodel.CardViewModel;
import com.prography.sw.aloocustomer.viewmodel.MainViewModel;

import java.util.ArrayList;
import java.util.FormatFlagsConversionMismatchException;
import java.util.List;

public class MealDetailsActivity extends AppCompatActivity implements CustomListener, View.OnClickListener {
    private ActivityMealDetailsBinding binding;
    private List<MealDetails> listOptionals = new ArrayList<>();
    private List<MealDetails> listExtras = new ArrayList<>();
    private List<MealDetails> listDrinks = new ArrayList<>();

    private List<String> with = new ArrayList<>();
    private List<String> without = new ArrayList<>();
    private List<Integer> extras = new ArrayList<>();
    private List<Integer> drinks = new ArrayList<>();
    private String size;


    private MainViewModel mainViewModel;
    private CardViewModel cardViewModel;
    private int count = 0;
    private int count_cart_item = 0;
    private int shangeImgebig = 0;
    private int shangeImgemed = 0;
    private int shangeImgesmall = 0;
    private int id;
    private String image;
    CustomRecyclerViewAdapter adapterOptionals;
    CustomRecyclerViewAdapter adapterExtras;
    CustomRecyclerViewAdapter adapterDrinks;
    boolean isShownOptionals = false;
    boolean isShownExtras = false;
    boolean isShownDrinks = false;


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMealDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initViews();
        cardItem();
        initViewModel();
        getIntentDate();
        initRestaurantAdapter();
        startItemsProcess();
    }

    private void getIntentDate() {
        id = getIntent().getIntExtra("id", -1);
        image = getIntent().getStringExtra("image");
        AppUtils.initGlide(this).load(image).into(binding.vendorItemImage);
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
        binding.tvItemName.setText(response.getVendorDetilseResturantData().getName());
        binding.calories.setText(response.getVendorDetilseResturantData().getAmount() + " " + response.getVendorDetilseResturantData().getAmountType());
        binding.price.setText(response.getVendorDetilseResturantData().getPrice() + getString(R.string.SR));
        binding.descText.setText(response.getVendorDetilseResturantData().getDescription());
        binding.bigPrice.setText(getString(R.string.big_price) + response.getVendorDetilseResturantData().getSizes().getBig());
        binding.medPrice.setText(getString(R.string.med_price) + response.getVendorDetilseResturantData().getSizes().getMedium());
        binding.smallPrice.setText(getString(R.string.small_price) + response.getVendorDetilseResturantData().getSizes().getSmall());

        listOptionals.clear();
        for (int i = 0; i < response.getVendorDetilseResturantData().getOptionals().size(); i++)
            listOptionals.add(new MealDetails(response.getVendorDetilseResturantData().getOptionals().get(i), false));
        adapterOptionals.setMealDetails(listOptionals);
        binding.rvOptionals.setAdapter(adapterOptionals);

        listExtras.clear();
        for (int i = 0; i < response.getVendorDetilseResturantData().getExtras().size(); i++)
            listExtras.add(new MealDetails(response.getVendorDetilseResturantData().getExtras().get(i).getId(), response.getVendorDetilseResturantData().getExtras().get(i).getName(), false));
        adapterExtras.setMealDetails(listExtras);
        binding.rvExtras.setAdapter(adapterExtras);

        listDrinks.clear();
        for (int i = 0; i < response.getVendorDetilseResturantData().getDrinks().size(); i++)
            listDrinks.add(new MealDetails(response.getVendorDetilseResturantData().getDrinks().get(i).getId(), response.getVendorDetilseResturantData().getDrinks().get(i).getName(), false));
        adapterDrinks.setMealDetails(listDrinks);
        binding.rvDrinks.setAdapter(adapterDrinks);

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


    private void StartAddToCardProcess() {
        cardViewModel.addToCard(id, count, with, without, size, drinks, extras).observe(this,
                resource -> {
                    switch (resource.status) {
                        case LOADING:
                            showParentAddToCard(View.GONE);
                            break;
                        case AUTHENTICATED:
                            updateUiAddToCard(resource.data);
                            cardViewModel.removeAddToCardObservers(this);
                            break;
                        case ERROR:
                            showFailedAddToCard(resource);
                            break;
                    }
                });
    }

    private void showParentAddToCard(int visibility) {
        if (visibility == View.VISIBLE) {
            binding.loadingAddToCard.setVisibility(View.GONE);
            binding.btnAddToCart.setVisibility(View.VISIBLE);
        } else {
            binding.loadingAddToCard.setVisibility(View.VISIBLE);
            binding.btnAddToCart.setVisibility(View.GONE);

        }
    }


    private void updateUiAddToCard(GeneralResponse response) {
        showParentAddToCard(View.VISIBLE);
        count_cart_item += SharedPreferencesHelper.getCardItem(this);
        SharedPreferencesHelper.setCardItem(this, count_cart_item);
        binding.cartItemCount.setVisibility(View.VISIBLE);
        binding.numberCartItemCount.setVisibility(View.VISIBLE);
        binding.numberCartItemCount.setText(String.valueOf(count_cart_item));
        Toast.makeText(this, response.getMessage(), Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }


    private void showFailedAddToCard(AuthResource<GeneralResponse> errorResponse) {
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
        showParentAddToCard(View.VISIBLE);
    }


    private void initViews() {
        binding.cart.setOnClickListener(this);
        binding.backImage.setOnClickListener(this);
        binding.addImg.setOnClickListener(this);
        binding.miMinus.setOnClickListener(this);
        binding.btnAddToCart.setOnClickListener(this);
        binding.mealIconBig.setOnClickListener(this);
        binding.mealIconSmall.setOnClickListener(this);
        binding.layoutOptionals.setOnClickListener(this);
        binding.layoutDrinks.setOnClickListener(this);
        binding.layoutExtras.setOnClickListener(this);
        binding.mealIconMed.setOnClickListener(this);
    }

    private void initRestaurantAdapter() {
        adapterOptionals = new CustomRecyclerViewAdapter((position, action) -> {
            if (!listOptionals.get(position).getChecked()) {
                listOptionals.get(position).setChecked(true);
                with.add(listOptionals.get(position).getTitle());
                without.add(listOptionals.get(position).getTitle());
            } else if (listOptionals.get(position).getChecked()) {
                listOptionals.get(position).setChecked(false);
                for (int i = 0; i < with.size(); i++) {
                    if (with.get(i).equals(listOptionals.get(position).getTitle()))
                        with.remove(i);
                }

            }


        }, CustomRecyclerViewAdapter.HolderConstants.MEAL_DETAILS);

        adapterExtras = new CustomRecyclerViewAdapter((position, action) -> {
            if (!listExtras.get(position).getChecked()) {
                listExtras.get(position).setChecked(true);
                extras.add(listExtras.get(position).getId());
                for (int i = 0; i < extras.size(); i++) {
                    Toast.makeText(this, "" + extras.get(i), Toast.LENGTH_SHORT).show();
                }
            } else if (listExtras.get(position).getChecked()) {
                listExtras.get(position).setChecked(false);
                for (int i = 0; i < extras.size(); i++) {
                    if (extras.get(i).equals(listExtras.get(position).getId())) {
                        Toast.makeText(this, "" + extras.get(i), Toast.LENGTH_SHORT).show();
                        extras.remove(i);
                    }


                }

            }
        }, CustomRecyclerViewAdapter.HolderConstants.MEAL_DETAILS);

        adapterDrinks = new CustomRecyclerViewAdapter((position, action) -> {
            if (!listDrinks.get(position).getChecked()) {
                listDrinks.get(position).setChecked(true);
                drinks.add(listDrinks.get(position).getId());
                for (int i = 0; i < drinks.size(); i++) {
                    Toast.makeText(this, "" + drinks.get(i), Toast.LENGTH_SHORT).show();
                }

            } else if (listDrinks.get(position).getChecked()) {
                listDrinks.get(position).setChecked(false);
                for (int i = 0; i < drinks.size(); i++) {
                    if (drinks.get(i).equals(listDrinks.get(position).getId())) {

                        drinks.remove(i);
                    }
                }

            }

        }, CustomRecyclerViewAdapter.HolderConstants.MEAL_DETAILS);

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
            case R.id.back_image:
                this.finish();
                break;
            case R.id.add_img:
                if (count < 10) {
                    count++;
                    binding.number.setText(String.valueOf(count));
                }else{
                    Toast.makeText(this, "the meal amount must be less than ten  ^_-", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.mi_minus:
                if (count > 1) {
                    count--;
                    binding.number.setText(String.valueOf(count));
                } else {
                    Toast.makeText(this, "the meal amount must be larger than one or one ^_-", Toast.LENGTH_SHORT).show();
                }


                break;
            case R.id.btn_add_to_cart:
                if (SharedPreferencesHelper.getUserToken(this).isEmpty())
                    startActivity(new Intent(this, LoginActivity.class));
                else {
                    StartAddToCardProcess();
                }
                break;
            case R.id.meal_icon_big:
                if (shangeImgebig == 0) {
                    shangeImgebig++;
                    size = "B";
                    binding.mealIconBig.setImageResource(R.drawable.ic_meal_icon_big_red);
                    binding.mealIconMed.setImageResource(R.drawable.ic_meal_icon_big_white);
                    binding.mealIconSmall.setImageResource(R.drawable.ic_meal_icon_big_white);

                } else {
                    shangeImgebig--;
                    binding.mealIconBig.setImageResource(R.drawable.ic_meal_icon_big_white);
                }
                break;
            case R.id.meal_icon_med:
                if (shangeImgemed == 0) {
                    shangeImgemed++;
                    size = "M";
                    binding.mealIconMed.setImageResource(R.drawable.ic_meal_icon_big_red);
                    binding.mealIconBig.setImageResource(R.drawable.ic_meal_icon_big_white);
                    binding.mealIconSmall.setImageResource(R.drawable.ic_meal_icon_big_white);
                } else {
                    shangeImgemed--;
                    binding.mealIconMed.setImageResource(R.drawable.ic_meal_icon_big_white);
                }
                break;
            case R.id.meal_icon_small:
                if (shangeImgesmall == 0) {
                    shangeImgesmall++;
                    size = "S";
                    binding.mealIconSmall.setImageResource(R.drawable.ic_meal_icon_big_red);
                    binding.mealIconMed.setImageResource(R.drawable.ic_meal_icon_big_white);
                    binding.mealIconBig.setImageResource(R.drawable.ic_meal_icon_big_white);
                } else {
                    shangeImgesmall--;
                    binding.mealIconSmall.setImageResource(R.drawable.ic_meal_icon_big_white);

                }
                break;
            case R.id.layout_optionals:
                if (isShownOptionals) {
                    binding.rvOptionals.setVisibility(View.GONE);
                    binding.ivArrowOptionals.setImageResource(R.drawable.ic_arrow_down);
                    isShownOptionals = false;
                } else {
                    binding.rvOptionals.setVisibility(View.VISIBLE);
                    binding.ivArrowOptionals.setImageResource(R.drawable.ic_arrow_up);
                    isShownOptionals = true;
                }
                break;
            case R.id.layout_extras:
                if (isShownExtras) {
                    binding.rvExtras.setVisibility(View.GONE);
                    binding.ivArrowExtras.setImageResource(R.drawable.ic_arrow_down);
                    isShownExtras = false;
                } else {
                    binding.rvExtras.setVisibility(View.VISIBLE);
                    binding.ivArrowExtras.setImageResource(R.drawable.ic_arrow_up);
                    isShownExtras = true;
                }
                break;
            case R.id.layout_drinks:
                if (isShownDrinks) {
                    binding.rvDrinks.setVisibility(View.GONE);
                    binding.ivArrowDrinks.setImageResource(R.drawable.ic_arrow_down);
                    isShownDrinks = false;
                } else {
                    binding.rvDrinks.setVisibility(View.VISIBLE);
                    binding.ivArrowDrinks.setImageResource(R.drawable.ic_arrow_up);
                    isShownDrinks = true;
                }
                break;


        }


    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

}