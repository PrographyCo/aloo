package com.prography.sw.aloodelevery.ui.activity;

import static com.prography.sw.aloodelevery.util.AppUtils.setTime;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.widget.Toast;


import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.prography.sw.aloodelevery.R;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.prography.sw.aloodelevery.adapter.CustomListener;
import com.prography.sw.aloodelevery.adapter.CustomRecyclerViewAdapter;
import com.prography.sw.aloodelevery.data.response.GeneralResponse;
import com.prography.sw.aloodelevery.databinding.ActivityMyRiderDetailsOrderBinding;
import com.prography.sw.aloodelevery.model.OrderItem;
import com.prography.sw.aloodelevery.network.AuthResource;
import com.prography.sw.aloodelevery.util.AppUtils;
import com.prography.sw.aloodelevery.viewmodel.OrdersViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MyRiderDetailsOrderActivity extends AppCompatActivity implements OnMapReadyCallback, CustomListener, View.OnClickListener {

    private ActivityMyRiderDetailsOrderBinding binding;
    private SupportMapFragment mapFragment;
    private CustomRecyclerViewAdapter adapter;
    private OrdersViewModel ordersViewModel;
    private GoogleMap mMap;
    private List<OrderItem> items = new ArrayList<>();
    private int id;
    private String lat;
    private String lon;
    private LatLng latLngObj;
    private MarkerOptions markerOptions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMyRiderDetailsOrderBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initViewModel();
        initView();
        getIntentData();
        initAdapter();
        startDetailsProcess();
        initMap();


    }

    private void getIntentData() {
        id = getIntent().getIntExtra("id", -1);
    }

    private void initViewModel() {
        ordersViewModel = new ViewModelProvider(this,
                new ViewModelProvider.AndroidViewModelFactory(getApplication())).get(OrdersViewModel.class);
    }

    private void startDetailsProcess() {
        ordersViewModel.getOrdersCurrentDetails(id).observe(this, detailsResource -> {
            switch (detailsResource.status) {
                case LOADING:
                    showParent(View.GONE);
                    break;
                case AUTHENTICATED:
                    updateUi(detailsResource.data);
                    break;
                case ERROR:
                    showFailed(detailsResource);
                    break;
            }
        });
    }

    private void showParent(int visibility) {
        if (visibility == View.VISIBLE) {
            binding.loading.setVisibility(View.GONE);
            binding.layout.setVisibility(View.VISIBLE);
        } else {
            binding.loading.setVisibility(View.VISIBLE);
            binding.layout.setVisibility(View.GONE);
        }
    }

    private void updateUi(GeneralResponse response) {
        showParent(View.VISIBLE);
        lat = response.getMyOrderDetailsData().getPlace().getLat();
        lon = response.getMyOrderDetailsData().getPlace().getLon();
        latLngObj = new LatLng(Double.valueOf(lat), Double.valueOf(lon));
        markerOptions = new MarkerOptions()
                .position(latLngObj)
                .icon(BitmapFromVector(getApplicationContext(), R.drawable.ic_marker))
                .anchor(0.5f, 0.5f);
        mMap.addMarker(markerOptions);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLngObj, 12));
        binding.ratingBar.setRating(response.getMyOrderDetailsData().getCustomer().getRate());
        binding.tvDateTimeTitel.setText(setTime((long) response.getMyOrderDetailsData().getDate() * 1000, this));
        binding.tvRequesterAddress.setText(response.getMyOrderDetailsData().getPlace().getName());
        binding.myAddress.setText(response.getMyOrderDetailsData().getPlace().getLocationName());
        binding.tvNameOrderOwner.setText(response.getMyOrderDetailsData().getCustomer().getName());
        binding.serviceType.setText(response.getMyOrderDetailsData().getOrderType().getName());
        binding.askingPrice.setText(String.valueOf(response.getMyOrderDetailsData().getTotalPrice()));
        binding.deliveryHarge.setText(String.valueOf(response.getMyOrderDetailsData().getDeliveryPrice()));
        binding.totalDistance.setText(response.getMyOrderDetailsData().getDistance());
        binding.journeyTime.setText(String.valueOf(response.getMyOrderDetailsData().getDeliveryTime()));
        binding.tvNumber.setText(String.valueOf(response.getMyOrderDetailsData().getId()));
        items = response.getMyOrderDetailsData().getItems();
        binding.tvOrderrNumbers.setText(response.getMyOrderDetailsData().getStatus());
        adapter.setOrderDetailsHome(items);
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

    private void initView() {
        binding.btnReportProblem.setOnClickListener(this);
    }

    private void initMap() {
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        googleMap.setMyLocationEnabled(true);

    }

    private BitmapDescriptor BitmapFromVector(Context context, int vectorResId) {
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorResId);

        vectorDrawable.setBounds(0, 0, vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight());

        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmap);

        vectorDrawable.draw(canvas);

        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }


//    private void initList() {
//        items.add(new OrderDetailsHome(R.drawable.img, "Grilled Chicken Meal - Medium", "Without: tomato, mayonnaise", "number1", "24 SAR"));
//        items.add(new OrderDetailsHome(R.drawable.img, "Grilled Chicken Meal - Medium", "Without: tomato, mayonnaise", "number1", "24 SAR"));
//    }

    private void initAdapter() {
        adapter = new CustomRecyclerViewAdapter(this, CustomRecyclerViewAdapter.HolderConstants.ORDER_DETAILS_HOME);
        adapter.setOrderDetailsHome(items);
        binding.rvMyRiderDetails.setAdapter(adapter);
        binding.rvMyRiderDetails.setNestedScrollingEnabled(false);
    }

    @Override
    public void onItemClick(int position, String action) {

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == binding.btnReportProblem.getId()) {
            Toast.makeText(this, "ReportProblem", Toast.LENGTH_SHORT).show();
            //   new MapSheetSW().show(getSupportFragmentManager(), null);
        }
    }
}