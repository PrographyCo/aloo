package com.prography.sw.aloocustomer.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Toast;

import com.prography.sw.aloocustomer.R;
import com.prography.sw.aloocustomer.adapter.CustomListener;
import com.prography.sw.aloocustomer.adapter.CustomRecyclerViewAdapter;
import com.prography.sw.aloocustomer.custom.BottomSheetDone;
import com.prography.sw.aloocustomer.custom.DialogTextButton;
import com.prography.sw.aloocustomer.data.response.GeneralResponse;
import com.prography.sw.aloocustomer.databinding.ActivityCartBinding;
import com.prography.sw.aloocustomer.model.Cart;
import com.prography.sw.aloocustomer.model.CartItem;
import com.prography.sw.aloocustomer.model.MealDetails;
import com.prography.sw.aloocustomer.network.AuthResource;
import com.prography.sw.aloocustomer.ui.MainActivity;
import com.prography.sw.aloocustomer.util.AppUtils;
import com.prography.sw.aloocustomer.util.SharedPreferencesHelper;
import com.prography.sw.aloocustomer.viewmodel.CardViewModel;

import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity implements CustomListener, View.OnClickListener {

    private ActivityCartBinding binding;
    private CustomRecyclerViewAdapter adapter;
    private List<CartItem> items = new ArrayList<>();
    private BottomSheetDone bottomSheetDone;
    private DialogTextButton dialogTextButton;
    private String intent;
    private CardViewModel cardViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCartBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initView();
        initViewModel();
        getInitentdate();
        initCartAdapter();
        startAllCardItem();
    }

    private void initViewModel() {
        cardViewModel = new ViewModelProvider(this,
                new ViewModelProvider.AndroidViewModelFactory(getApplication())).get(CardViewModel.class);
    }

    private void initView() {
        binding.btnConfirmation.setOnClickListener(this);
        binding.backImage.setOnClickListener(this);
    }

    private void initCartAdapter() {
        adapter = new CustomRecyclerViewAdapter(this, CustomRecyclerViewAdapter.HolderConstants.CART_ITEM);
        adapter.setCarts(items);
        binding.rvCartItem.setNestedScrollingEnabled(false);
        binding.rvCartItem.setAdapter(adapter);

    }

    private void startAllCardItem() {
        cardViewModel.getAllCardItem(SharedPreferencesHelper.getVendorId(this)).observe(this, resource -> {
            switch (resource.status) {
                case LOADING:
                    showParent(View.GONE);
                    break;
                case AUTHENTICATED:
                    updateUi(resource.data);
                    cardViewModel.removeAllCardItem(this);
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
        items = response.getAllCardItemsData().getCart().getCartItems();
        SharedPreferencesHelper.setCardItem(this, items.size());
        adapter.setCarts(items);
        binding.askingPriceNumber.setText(response.getAllCardItemsData().getCart().getTotal());
        binding.deliveryChargeNumber.setText(String.valueOf(response.getAllCardItemsData().getCart().getDeliveryPriceReservation()));
        double t = Double.valueOf(response.getAllCardItemsData().getCart().getTotal());
        int d = response.getAllCardItemsData().getCart().getDeliveryPriceReservation();
        double tot = t + d;
        binding.totelNumber.setText(String.valueOf(tot));
        binding.tvAddreses.setText(response.getAllCardItemsData().getCart().getPlace().getName());
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


    private void startDeleteItemCard(int id) {
        cardViewModel.deleteCartItem(id).observe(this, new Observer<AuthResource<GeneralResponse>>() {
            @Override
            public void onChanged(AuthResource<GeneralResponse> resource) {
                switch (resource.status) {
                    case LOADING:
                        showParent(View.GONE);
                        break;
                    case AUTHENTICATED:
                        updateUiDelete(resource.data);
                        break;
                    case ERROR:
                        showFailed(resource);
                        break;
                }
            }
        });
    }


    private void updateUiDelete(GeneralResponse response) {
        showParent(View.VISIBLE);
        Toast.makeText(this, response.getMessage(), Toast.LENGTH_SHORT).show();
        startAllCardItem();
        adapter.notifyDataSetChanged();
    }

    private void startSubmitItemCards() {
        cardViewModel.submitCartOrder(SharedPreferencesHelper.getVendorId(this)).observe(this, resource -> {
            switch (resource.status) {
                case LOADING:
                    showParentSubmit(View.GONE);
                    break;
                case AUTHENTICATED:
                    updateUiSubmit(resource.data);
                    break;
                case ERROR:
                    showFailedSubmit(resource);
                    break;
            }
        });
    }


    private void showParentSubmit(int visibility) {
        if (visibility == View.VISIBLE) {
            binding.loadingSubmit.setVisibility(View.GONE);
            binding.btnConfirmation.setVisibility(View.VISIBLE);

        } else {
            binding.loadingSubmit.setVisibility(View.VISIBLE);
            binding.btnConfirmation.setVisibility(View.GONE);

        }
    }

    private void updateUiSubmit(GeneralResponse response) {
        showParentSubmit(View.VISIBLE);
        Toast.makeText(this, response.getMessage(), Toast.LENGTH_SHORT).show();
        if (response.getSubmitOrderData().getUrl() != null) {
            WebView myWebView = new WebView(this);
            setContentView(myWebView);
            myWebView.loadUrl(response.getSubmitOrderData().getUrl());
        } else {
            if (items.size() == 0) {
                Toast.makeText(this,getString(R.string.no_item_to_submit), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, response.getMessage(), Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, MainActivity.class));
                this.finish();
            }

        }

    }


    private void showFailedSubmit(AuthResource<GeneralResponse> errorResponse) {
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
        showParentSubmit(View.VISIBLE);
    }


    @Override
    public void onItemClick(int position, String action) {
        switch (action) {
            case "CART_ITEM":
                Toast.makeText(CartActivity.this, "CartActivity ", Toast.LENGTH_SHORT).show();
                break;
            case "DELETE":
                startDeleteItemCard(items.get(position).getId());
                break;
            case "EDIT":
                if (SharedPreferencesHelper.getVendorId(this) == 3) {
                    Intent intent = new Intent(this, EditOrderFromCartActivity.class);
                    intent.putExtra("id", items.get(position).getId());
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(this, EditOrderFromCartSuperActivity.class);
                    intent.putExtra("id", items.get(position).getId());
                    startActivity(intent);
                }

                break;
            case "BottomSheetDone":
                Intent intentTracking = new Intent(this, OrderTrackingActivity.class);
                intentTracking.putExtra("id", items.get(position).getId());
                startActivity(intentTracking);
                Toast.makeText(CartActivity.this, "bottomSheetDone ", Toast.LENGTH_SHORT).show();
                break;
            case "dialog":
                startActivity(new Intent(this, RegistrationActivity.class));
                break;
        }

    }

    private void btmsheetDone() {
        bottomSheetDone = new BottomSheetDone(this, this, R.drawable.ic_send_order_done, "Your request has been sent successfully", "The order number is 1180", "order tracking");
        bottomSheetDone.showDialog();
    }

    private void initDialog() {
        dialogTextButton = new DialogTextButton(this, this, "Please register to confirm your order", "Register now");
        dialogTextButton.show();
    }

    private void getInitentdate() {
        intent = getIntent().getStringExtra("data");
        if (intent != null) {
            if (intent.equals("done")) {
                btmsheetDone();
            }
        }
    }


    @Override
    public void onClick(View view) {
        if (view.getId() == binding.btnConfirmation.getId()) {
            startSubmitItemCards();
        } else if (view.getId() == binding.backImage.getId()) {
            this.finish();
        }
    }
}