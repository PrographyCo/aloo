package com.prography.sw.aloodelevery.ui.activity;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.prography.sw.aloodelevery.R;
import com.prography.sw.aloodelevery.custom.BottomSheetCancel;
import com.prography.sw.aloodelevery.custom.BottomSheetDone;
import com.prography.sw.aloodelevery.custom.BottomSheetRate;
import com.prography.sw.aloodelevery.custom.DialogTextButton;
import com.prography.sw.aloodelevery.data.response.GeneralResponse;
import com.prography.sw.aloodelevery.databinding.ActivityMapsBinding;
import com.prography.sw.aloodelevery.network.AuthResource;
import com.prography.sw.aloodelevery.ui.MainActivity;
import com.prography.sw.aloodelevery.util.AppUtils;
import com.prography.sw.aloodelevery.viewmodel.OrdersViewModel;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, View.OnClickListener {

    private SupportMapFragment mapFragment;
    private GoogleMap mMap;
    private int id;
    private OrdersViewModel ordersViewModel;
    private ActivityMapsBinding binding;
    private FusedLocationProviderClient client;
    private BottomSheetRate bottomSheetRate;
    private BottomSheetDone bottomSheetDone;
    private BottomSheetCancel bottomSheetCancel;
    private int step = 0;
    MarkerOptions markerOptions;
    LatLng latLngObj;
    private DialogTextButton dialogPermission;
    private String customerName;
    private String lat;
    private String lon;
    private String userPhone;
    private String userName;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getIntentData();
        initViews();
        initMap();
        initDialogs();
        initViewModel();
        initBottomSheet();
        openGPS(this);
        getCurrentLocation();

    }

    private void getIntentData() {
        id = getIntent().getIntExtra("id", -1);
        lat = getIntent().getStringExtra("lat");
        lon = getIntent().getStringExtra("lon");
        userPhone = getIntent().getStringExtra("phone");
        userName = getIntent().getStringExtra("name");
    }

    private void initViewModel() {
        ordersViewModel = new ViewModelProvider(this,
                new ViewModelProvider.AndroidViewModelFactory(getApplication())).get(OrdersViewModel.class);
    }

    private void initViews() {
        binding.ivCall.setOnClickListener(this);
        binding.ivChat.setOnClickListener(this);
        binding.ivClose.setOnClickListener(this);
        binding.btn.setOnClickListener(this);
        binding.name.setText(userName);
    }

    private void startCancelPrcess() {
        ordersViewModel.cancel(id, bottomSheetCancel.getMessage()).observe(this, cancelResource -> {
            switch (cancelResource.status) {
                case LOADING:
                    bottomSheetCancel.showParent(View.GONE);
                    break;
                case AUTHENTICATED:
                    updateUiCancel(cancelResource.data);
                    break;
                case ERROR:
                    showFailedCancel(cancelResource);
                    break;
            }
        });
    }

    private void updateUiCancel(GeneralResponse response) {
        bottomSheetCancel.showParent(View.VISIBLE);
        Toast.makeText(this, response.getMessage(), Toast.LENGTH_SHORT).show();
        bottomSheetCancel.dismissDialog();
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }


    private void showFailedCancel(AuthResource<GeneralResponse> errorResponse) {
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
        bottomSheetCancel.showParent(View.VISIBLE);
    }


    private void initMap() {

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);

    }

    public void openGPS(Activity activity) {
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        final LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);

        Task<LocationSettingsResponse> result = LocationServices.getSettingsClient(this).checkLocationSettings(builder.build());
        result.addOnCompleteListener(new OnCompleteListener<LocationSettingsResponse>() {
            @Override
            public void onComplete(@NonNull Task<LocationSettingsResponse> task) {

                try {
                    LocationSettingsResponse response = task.getResult(ApiException.class);
                    // All location settings are satisfied. The client can initialize location
                    // requests here.
                } catch (ApiException exception) {
                    switch (exception.getStatusCode()) {
                        case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                            // Location settings are not satisfied. But could be fixed by showing the
                            // user a dialog.
                            try {
                                // Cast to a resolvable exception.
                                ResolvableApiException resolvable = (ResolvableApiException) exception;
                                // Show the dialog by calling startResolutionForResult(),
                                // and check the result in onActivityResult().
                                resolvable.startResolutionForResult(
                                        activity,
                                        LocationRequest.PRIORITY_HIGH_ACCURACY);
                            } catch (IntentSender.SendIntentException e) {
                                // Ignore the error.
                            } catch (ClassCastException e) {
                                // Ignore, should be an impossible error.
                            }
                            break;
                        case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                            // Location settings are not satisfied. However, we have no way to fix the
                            // settings so we won't show the dialog.
                            break;
                    }
                }
            }

        });

    }

    private void getCurrentLocation() {
        client = LocationServices.getFusedLocationProviderClient(this);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 100);
        else {
            client.getLastLocation()
                    .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            // Got last known location. In some rare situations this can be null.
                            if (location != null) {
                                // Logic to handle location object
                                latLngObj = new LatLng(location.getLatitude(), location.getLongitude());
                                markerOptions = new MarkerOptions()
                                        .position(latLngObj)
                                        .icon(BitmapFromVector(getApplicationContext(), R.drawable.ic_marker))
                                        .anchor(0.5f, 0.5f);

                                mMap.addMarker(markerOptions);
                                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLngObj, 12));
                            }
                        }
                    });
        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100 && grantResults.length > 0) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getCurrentLocation();
                if (mMap != null) {
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    }
                    mMap.setMyLocationEnabled(true);
                }
            } else
                dialogPermission.show();
        }

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 100);
        else
            mMap.setMyLocationEnabled(true);


        LatLng pleac = new LatLng(Double.valueOf(lat), Double.valueOf(lon));

        MarkerOptions markerOptions = new MarkerOptions()
                .position(pleac)
                .icon(BitmapFromVector(getApplicationContext(), R.drawable.ic_marker))
                .anchor(0.5f, 0.5f);
        mMap.addMarker(markerOptions);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(pleac, 12));


    }

    private BitmapDescriptor BitmapFromVector(Context context, int vectorResId) {
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorResId);

        vectorDrawable.setBounds(0, 0, vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight());

        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmap);

        vectorDrawable.draw(canvas);

        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }

    private void initDialogs() {
        dialogPermission
                = new DialogTextButton(this,
                (position, action) -> {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 100);
                    dialogPermission.dismiss();
                },
                getString(R.string.permission_text), getString(R.string.yes));
    }

    private void initBottomSheet() {
        bottomSheetDone = new BottomSheetDone(this, (position, action) -> {
            this.finish();
        }, R.drawable.ic_done, getString(R.string.rate_sent), getString(R.string.thank_you), getString(R.string.ok));
        bottomSheetCancel = new BottomSheetCancel(this, (position, action) -> {
            startCancelPrcess();
        });
    }

    private void startRatingProcess() {
        ordersViewModel.rate(id, bottomSheetRate.getRate(), bottomSheetRate.getMessage()).observe(this, resource -> {
            switch (resource.status) {
                case LOADING:
                    bottomSheetRate.showParent(View.GONE);
                    break;
                case AUTHENTICATED:
                    updateUiRate(resource.data);
                    break;
                case ERROR:
                    showRateFailed(resource);
                    break;
            }
        });
    }

    private void updateUiRate(GeneralResponse response) {
        bottomSheetRate.showParent(View.VISIBLE);
        Toast.makeText(this, response.getMessage(), Toast.LENGTH_SHORT).show();
        bottomSheetDone.showDialog();
    }

    private void showRateFailed(AuthResource<GeneralResponse> errorResponse) {
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
        bottomSheetRate.showParent(View.VISIBLE);
    }

    private void startWaitingProcess() {
        ordersViewModel.waiting(id).observe(this, waitingResource -> {
            switch (waitingResource.status) {
                case LOADING:
                    showParent(View.GONE);
                    break;
                case AUTHENTICATED:
                    updateUiWaiting(waitingResource.data);
                    break;
                case ERROR:
                    showFailed(waitingResource);
                    break;
            }
        });
    }

    private void startToCustomerProcess() {
        ordersViewModel.toCustomer(id).observe(this, toCustomerResource -> {
            switch (toCustomerResource.status) {
                case LOADING:
                    showParent(View.GONE);
                    break;
                case AUTHENTICATED:
                    updateUiToCustomer(toCustomerResource.data);
                    break;
                case ERROR:
                    showFailed(toCustomerResource);
                    break;
            }
        });
    }

    private void startArrivedProcess() {
        ordersViewModel.arrived(id).observe(this, arrivedResource -> {
            switch (arrivedResource.status) {
                case LOADING:
                    showParent(View.GONE);
                    break;
                case AUTHENTICATED:
                    updateUiArrived(arrivedResource.data);
                    break;
                case ERROR:
                    showFailed(arrivedResource);
                    break;
            }
        });
    }

    private void startDeliveredProcess() {
        ordersViewModel.delivered(id).observe(this, deliveredResource -> {
            switch (deliveredResource.status) {
                case LOADING:
                    showParent(View.GONE);
                    break;
                case AUTHENTICATED:
                    updateUiDelivered(deliveredResource.data);
                    break;
                case ERROR:
                    showFailed(deliveredResource);
                    break;
            }
        });
    }


    private void showParent(int visibility) {
        if (visibility == View.VISIBLE) {
            binding.loading.setVisibility(View.GONE);
            binding.btn.setVisibility(View.VISIBLE);

        } else {
            binding.loading.setVisibility(View.VISIBLE);
            binding.btn.setVisibility(View.GONE);

        }
    }

    private void updateUiWaiting(GeneralResponse response) {
        showParent(View.VISIBLE);
        Toast.makeText(this, response.getWaitingData().getStatus(), Toast.LENGTH_SHORT).show();
        customerName = response.getWaitingData().getCustomer().getName();
        binding.btn.setText(R.string.to_customer);
        step = 1;
    }

    private void updateUiToCustomer(GeneralResponse response) {
        showParent(View.VISIBLE);
        Toast.makeText(this, response.getToCustomerData().getStatus(), Toast.LENGTH_SHORT).show();

        binding.btn.setText(R.string.arrived);
        step = 2;
    }

    private void updateUiArrived(GeneralResponse response) {
        showParent(View.VISIBLE);
        Toast.makeText(this, response.getMessage(), Toast.LENGTH_SHORT).show();

        binding.btn.setText(R.string.delivered);
        step = 3;
    }

    private void updateUiDelivered(GeneralResponse response) {
        showParent(View.VISIBLE);
        Toast.makeText(this, response.getMessage(), Toast.LENGTH_SHORT).show();
        bottomSheetRate = new BottomSheetRate(this, (position, action) -> {
            startRatingProcess();
        }, customerName);
        bottomSheetRate.showDialog();

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


    private void callIntent() {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + userPhone));
        startActivity(intent);
    }

    private void whatsAppIntent() {
        String contact = "+966 "+userPhone; // use country code with your phone number
        String url = "https://api.whatsapp.com/send?phone=" + contact;
        try {
            PackageManager pm = getPackageManager();
            pm.getPackageInfo("com.whatsapp", PackageManager.GET_ACTIVITIES);
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i);
        } catch (PackageManager.NameNotFoundException e) {
            Toast.makeText(this, "Whatsapp app not installed in your phone", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }


    @Override
    public void onClick(View view) {
        if (view.getId() == binding.ivChat.getId()) {
            whatsAppIntent();
        } else if (view.getId() == binding.ivCall.getId()) {
            callIntent();
        } else if (view.getId() == binding.ivClose.getId()) {
            bottomSheetCancel.showDialog();
        } else if (view.getId() == binding.btn.getId()) {
            switch (step) {
                case 0:
                    startWaitingProcess();
                    break;
                case 1:
                    startToCustomerProcess();
                    break;
                case 2:
                    startArrivedProcess();
                    break;
                case 3:
                    startDeliveredProcess();
                    break;
            }
        }
    }
}