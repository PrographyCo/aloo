package com.prography.sw.aloocustomer.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.prography.sw.aloocustomer.R;
import com.prography.sw.aloocustomer.adapter.CustomRecyclerViewAdapter;
import com.prography.sw.aloocustomer.data.response.GeneralResponse;
import com.prography.sw.aloocustomer.databinding.ActivityEditOrderFromCartBinding;
import com.prography.sw.aloocustomer.model.MealDetails;
import com.prography.sw.aloocustomer.network.AuthResource;
import com.prography.sw.aloocustomer.util.AppUtils;
import com.prography.sw.aloocustomer.viewmodel.CardViewModel;

import java.util.ArrayList;
import java.util.List;

public class EditOrderFromCartActivity extends AppCompatActivity implements View.OnClickListener {
    private ActivityEditOrderFromCartBinding binding;
    private CardViewModel cardViewModel;
    private int id;
    private List<MealDetails> listOptionals = new ArrayList<>();
    private List<MealDetails> listExtras = new ArrayList<>();
    private List<MealDetails> listDrinks = new ArrayList<>();
    CustomRecyclerViewAdapter adapterOptionals;
    CustomRecyclerViewAdapter adapterExtras;
    CustomRecyclerViewAdapter adapterDrinks;
    private List<String> with = new ArrayList<>();
    private List<String> without = new ArrayList<>();
    private List<Integer> extras = new ArrayList<>();
    private List<Integer> drinks = new ArrayList<>();
    boolean isShownOptionals = false;
    boolean isShownExtras = false;
    boolean isShownDrinks = false;
    private String size;
    private int shangeImgebig = 0;
    private int shangeImgemed = 0;
    private int shangeImgesmall = 0;
    private int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditOrderFromCartBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initViews();
        getIntentData();
        initViewModel();
        initEditMealAdapter();
        startItemDataProcess();
    }

    private void initViews() {
        binding.btnSave.setOnClickListener(this);
        binding.layoutOptionals.setOnClickListener(this);
        binding.layoutDrinks.setOnClickListener(this);
        binding.layoutExtras.setOnClickListener(this);
        binding.mealIconBig.setOnClickListener(this);
        binding.mealIconMed.setOnClickListener(this);
        binding.mealIconSmall.setOnClickListener(this);
        binding.miMinus.setOnClickListener(this);
        binding.addImg.setOnClickListener(this);
        binding.backImage.setOnClickListener(this);
    }

    private void getIntentData() {
        id = getIntent().getIntExtra("id", -1);
    }

    private void initViewModel() {
        cardViewModel = new ViewModelProvider(this,
                new ViewModelProvider.AndroidViewModelFactory(getApplication())).get(CardViewModel.class);
    }

    private void startItemDataProcess() {
        Log.d("TAG", "startItemDataProcess: dddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddd");
        cardViewModel.getOneItem(id).observe(this, resource -> {
            switch (resource.status) {
                case LOADING:
                    showParent(View.GONE);
                    break;
                case AUTHENTICATED:
                    upDateUi(resource.data);
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

    private void upDateUi(GeneralResponse response) {
        showParent(View.VISIBLE);

        binding.bigPrice.setText(getString(R.string.big_price) + response.getEditItemData().getItem().getSizes().getBig());
        binding.medPrice.setText(getString(R.string.med_price) + response.getEditItemData().getItem().getSizes().getMedium());
        binding.smallPrice.setText(getString(R.string.small_price) + response.getEditItemData().getItem().getSizes().getSmall());

        listOptionals.clear();
        for (int i = 0; i < response.getEditItemData().getItem().getOptionals().size(); i++)
            listOptionals.add(new MealDetails(response.getEditItemData().getItem().getOptionals().get(i), false));
        adapterOptionals.setMealDetails(listOptionals);
        binding.rvOptionals.setAdapter(adapterOptionals);

        listExtras.clear();
        for (int i = 0; i < response.getEditItemData().getItem().getExtras().size(); i++)
            listExtras.add(new MealDetails(response.getEditItemData().getItem().getExtras().get(i).getId(), response.getEditItemData().getItem().getExtras().get(i).getName(), false));
        adapterExtras.setMealDetails(listExtras);
        binding.rvExtras.setAdapter(adapterExtras);

        listDrinks.clear();
        for (int i = 0; i < response.getEditItemData().getItem().getDrinks().size(); i++)
            listDrinks.add(new MealDetails(response.getEditItemData().getItem().getDrinks().get(i).getId(), response.getEditItemData().getItem().getDrinks().get(i).getName(), false));
        adapterDrinks.setMealDetails(listDrinks);
        binding.rvDrinks.setAdapter(adapterDrinks);

        binding.tvItemName.setText(response.getEditItemData().getItem().getName());
        binding.calories.setText(getString(R.string.calories) + " " + response.getEditItemData().getItem().getCalories());
        binding.price.setText(response.getEditItemData().getItem().getPrice() + " " + getString(R.string.SR));
        binding.descText.setText(response.getEditItemData().getItem().getDescription());
        AppUtils.initGlide(this).load(response.getEditItemData().getItem().getImg()).into(binding.vendorItemImage);

        //**************************Old choses****************************


        binding.number.setText(String.valueOf(response.getEditItemData().getAmount()));
        count = response.getEditItemData().getAmount();

        switch (response.getEditItemData().getData().getSize()) {
            case "Big":
                binding.mealIconBig.setImageResource(R.drawable.ic_meal_icon_big_red);
                binding.mealIconMed.setImageResource(R.drawable.ic_meal_icon_big_white);
                binding.mealIconSmall.setImageResource(R.drawable.ic_meal_icon_big_white);
                size = "B";
                break;
            case "Medium":
                binding.mealIconMed.setImageResource(R.drawable.ic_meal_icon_big_red);
                binding.mealIconBig.setImageResource(R.drawable.ic_meal_icon_big_white);
                binding.mealIconSmall.setImageResource(R.drawable.ic_meal_icon_big_white);
                size = "M";
                break;
            case "Small":
                binding.mealIconSmall.setImageResource(R.drawable.ic_meal_icon_big_red);
                binding.mealIconMed.setImageResource(R.drawable.ic_meal_icon_big_white);
                binding.mealIconBig.setImageResource(R.drawable.ic_meal_icon_big_white);
                size = "S";
                break;
        }
        for (int i = 0; i < response.getEditItemData().getData().getWith().size(); i++) {
            String oldOptionals = response.getEditItemData().getData().getWith().get(i);
            for (int j = 0; j < listOptionals.size(); j++) {
                if (oldOptionals.equals(listOptionals.get(j).getTitle())) {
                    listOptionals.get(j).setChecked(true);
                    with.add(oldOptionals);
                    without.add(oldOptionals);
                }
            }


        }
        for (int i = 0; i < response.getEditItemData().getData().getDrinks().size(); i++) {
            int drink = response.getEditItemData().getData().getDrinks().get(i).getId();
            for (int j = 0; j < listDrinks.size(); j++) {
                if (drink == listDrinks.get(j).getId()) {
                    listDrinks.get(j).setChecked(true);
                    drinks.add(drink);
                }
            }

        }
        for (int i = 0; i < response.getEditItemData().getData().getExtras().size(); i++) {
            int extra = response.getEditItemData().getData().getExtras().get(i).getId();
            for (int j = 0; j < listExtras.size(); j++) {
                if (extra == listExtras.get(j).getId()) {
                    listExtras.get(j).setChecked(true);
                    extras.add(extra);
                }
            }

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


    private void startEditSaveProcess() {
        Log.d("TAG", "startEditSaveProcess: sssssssssssssssssssssssssssssssssssssssssssssssssssssss");
        cardViewModel.saveEdit(id, count, with, without, size, drinks, extras).observe(this, resource -> {
            switch (resource.status) {
                case LOADING:
                    Toast.makeText(this, "LOADING", Toast.LENGTH_SHORT).show();
                    showParentSave(View.GONE);
                    break;
                case AUTHENTICATED:
                    Toast.makeText(this, "AUTHENTICATED", Toast.LENGTH_SHORT).show();
                    upDateUiSave(resource.data);
                    break;
                case ERROR:
                    Toast.makeText(this, "ERROR", Toast.LENGTH_SHORT).show();
                    showFailedSave(resource);
                    break;
            }
        });
    }

    private void showParentSave(int visibility) {
        if (visibility == View.VISIBLE) {
            binding.loadingSave.setVisibility(View.GONE);
            binding.btnSave.setVisibility(View.VISIBLE);
        } else {
            binding.loadingSave.setVisibility(View.VISIBLE);
            binding.btnSave.setVisibility(View.GONE);

        }
    }

    private void upDateUiSave(GeneralResponse response) {
        showParentSave(View.VISIBLE);
        Toast.makeText(this, response.getMessage(), Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this, CartActivity.class));
        finish();
    }

    private void showFailedSave(AuthResource<GeneralResponse> errorResponse) {
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
        showParentSave(View.VISIBLE);
    }


    private void initEditMealAdapter() {
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
    public void onClick(View view) {
        switch (view.getId()) {
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
            case R.id.btn_save:
                startEditSaveProcess();
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
            case R.id.add_img:
                if (count < 10) {
                    count++;
                    binding.number.setText(String.valueOf(count));
                } else {
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
            case R.id.back_image:
                this.finish();
                break;
        }


    }
}