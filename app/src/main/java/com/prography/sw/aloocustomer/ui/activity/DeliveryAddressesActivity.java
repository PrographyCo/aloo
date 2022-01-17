package com.prography.sw.aloocustomer.ui.activity;

import static com.prography.sw.aloocustomer.util.AppUtils.unauthorized;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
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
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
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
import com.prography.sw.aloocustomer.R;
import com.prography.sw.aloocustomer.adapter.CustomListener;
import com.prography.sw.aloocustomer.adapter.CustomRecyclerViewAdapter;
import com.prography.sw.aloocustomer.custom.DialogTextButton;
import com.prography.sw.aloocustomer.data.response.GeneralResponse;
import com.prography.sw.aloocustomer.data.response.data.AddressesData;
import com.prography.sw.aloocustomer.databinding.ActivityDeliveryAddressesBinding;
import com.prography.sw.aloocustomer.model.IAddress;
import com.prography.sw.aloocustomer.network.AuthResource;
import com.prography.sw.aloocustomer.viewmodel.AddressesViewModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DeliveryAddressesActivity extends AppCompatActivity implements CustomListener, OnMapReadyCallback, GoogleMap.OnMapClickListener {

    private ActivityDeliveryAddressesBinding binding;
    private List<IAddress> IAddressList = new ArrayList<>();
    private CustomRecyclerViewAdapter adapter;

    private SupportMapFragment mapFragment;
    private GoogleMap mMap;
    private FusedLocationProviderClient client;
    MarkerOptions markerOptions;
    LatLng latLngObj;
    private DialogTextButton dialogPermission;
    private AddressesViewModel addressesViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDeliveryAddressesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initViewModel();
        initMap();
        initAdapter();
        setListeners();
        initDialogs();
        openGPS(this);
        getCurrentLocation();
        getAddresses();
    }

    private void initViewModel() {
        addressesViewModel = new ViewModelProvider(this,
                new ViewModelProvider.AndroidViewModelFactory(this.getApplication()))
                .get(AddressesViewModel.class);
    }

    private void initMap() {

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);

    }

    private void initAdapter() {
        adapter = new CustomRecyclerViewAdapter(this, CustomRecyclerViewAdapter.HolderConstants.LOCATION);
        adapter.setLocations(IAddressList);
        binding.rvLocation.setAdapter(adapter);
    }

    private void setListeners() {
        binding.backImage.setOnClickListener(view -> {
            this.finish();
        });

        binding.btn.setOnClickListener(view -> {
            if (binding.rvLocation.getVisibility() == View.VISIBLE) {
                binding.rvLocation.setVisibility(View.GONE);
                binding.layoutLocation.setVisibility(View.VISIBLE);
            } else {
                Toast.makeText(getApplication(), "location : " + binding.etTitle.getmyText(), Toast.LENGTH_SHORT).show();
                if (!binding.etTitle.getmyText().toString().isEmpty() && !binding.etDetails.getmyText().toString().isEmpty()) {
                    addAddress();
                }
            }
        });
    }

    @Override
    public void onItemClick(int position, String action) {
        Toast.makeText(getApplication(), " " + IAddressList.get(position).getName(), Toast.LENGTH_SHORT).show();

        mMap.clear();
        latLngObj = new LatLng(Double.parseDouble(IAddressList.get(position).getLat()), Double.parseDouble(IAddressList.get(position).getLon()));
        markerOptions = new MarkerOptions()
                .position(latLngObj)
                .icon(BitmapFromVector(getApplicationContext(), R.drawable.ic_marker))
                .anchor(0.5f, 0.5f);

        mMap.addMarker(markerOptions);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLngObj, 12));
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
                    .addOnSuccessListener(this, new OnSuccessListener<android.location.Location>() {
                        @Override
                        public void onSuccess(android.location.Location location) {
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


        mMap.setOnMapClickListener(this);

    }

    private BitmapDescriptor BitmapFromVector(Context context, int vectorResId) {
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorResId);

        vectorDrawable.setBounds(0, 0, vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight());

        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmap);

        vectorDrawable.draw(canvas);

        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }

    @Override
    public void onMapClick(@NonNull LatLng latLng) {
        mMap.clear();
        latLngObj = new LatLng(latLng.latitude, latLng.longitude);
        markerOptions = new MarkerOptions()
                .position(latLngObj)
                .icon(BitmapFromVector(this, R.drawable.ic_marker))
                .anchor(0.5f, 0.5f);
        mMap.addMarker(markerOptions);

        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLngObj, 12));

        binding.etDetails.setmyText(geocoderLocation(latLng.latitude, latLng.longitude));

    }

    public String geocoderLocation(double longitude, double latitude) {
        Geocoder geoCoder = new Geocoder(this);
        List<Address> list = null;
        try {
            list = geoCoder.getFromLocation(longitude, latitude, 1);
            if (list != null & list.size() > 0) {
                Address address = list.get(0);
                String result = address.getLocality();
                Toast.makeText(getApplicationContext(), "location : " + result, Toast.LENGTH_SHORT).show();
                return result;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Toast.makeText(getApplicationContext(), "location null", Toast.LENGTH_SHORT).show();
        return null;
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

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    private void addAddress() {
        addressesViewModel.addAddress(
                binding.etTitle.getmyText().toString(),
                binding.etDetails.getmyText().toString(),
                String.valueOf(latLngObj.longitude),
                String.valueOf(latLngObj.latitude)
        ).observe(this, result -> {
            switch (result.status) {
                case LOADING:
                    showAddParent(View.GONE);
                    break;
                case AUTHENTICATED:
                    updateAddUi(result.data);
                    break;
                case ERROR:
                    showAddFailed(result);
                    break;
            }
        });
    }

    private void showAddParent(int visibility) {
        if (visibility == View.VISIBLE) {
            binding.loadingBtn.setVisibility(View.GONE);
            binding.btn.setVisibility(View.VISIBLE);
        } else {
            binding.loadingBtn.setVisibility(View.VISIBLE);
            binding.btn.setVisibility(View.GONE);
        }
    }

    private void updateAddUi(GeneralResponse response) {
        showAddParent(View.VISIBLE);
        Toast.makeText(this, response.getMessage(), Toast.LENGTH_SHORT).show();

        binding.etTitle.setmyText("");
        binding.etDetails.setmyText("");
        binding.rvLocation.setVisibility(View.VISIBLE);
        binding.layoutLocation.setVisibility(View.GONE);
        getAddresses();
    }

    private void showAddFailed(AuthResource<GeneralResponse> errorResponse) {
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
        showAddParent(View.VISIBLE);
    }

    private void getAddresses() {
        addressesViewModel.getAddresses().observe(this, result -> {
            switch (result.status) {
                case LOADING:
                    showParent(View.GONE);
                    break;
                case AUTHENTICATED:
                    Log.d("TAG", "data: " + result.data.getLoginData().getName());
                    updateUi(result.data);
                    addressesViewModel.removeAddressesObservers(this);
                    break;
                case ERROR:
                    showFailed(result);
                    addressesViewModel.removeAddressesObservers(this);
                    break;
            }
        });
    }

    private void showParent(int visibility) {
        if (visibility == View.VISIBLE) {
            binding.loadingRv.setVisibility(View.GONE);
            binding.rvLocation.setVisibility(View.VISIBLE);
        } else {
            binding.loadingRv.setVisibility(View.VISIBLE);
            binding.rvLocation.setVisibility(View.GONE);
        }
    }

    private void updateUi(GeneralResponse response) {
        showParent(View.VISIBLE);
        if (response == null || response.getAddressesData() == null || response.getAddressesData().getAddresses() == null) {
            Toast.makeText(this, getString(R.string.something_went_wrong_text), Toast.LENGTH_SHORT).show();
            return;
        }
        if (response.getAddressesData().getAddresses().isEmpty())
            Toast.makeText(this, getString(R.string.no_places_were_added), Toast.LENGTH_SHORT).show();
        IAddressList = response.getAddressesData().getAddresses();
        adapter.setLocations(IAddressList);
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