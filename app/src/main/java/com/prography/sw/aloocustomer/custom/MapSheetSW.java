package com.prography.sw.aloocustomer.custom;

import static com.prography.sw.aloocustomer.util.AppUtils.unauthorized;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

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
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.prography.sw.aloocustomer.R;
import com.prography.sw.aloocustomer.adapter.CustomListener;
import com.prography.sw.aloocustomer.adapter.CustomRecyclerViewAdapter;
import com.prography.sw.aloocustomer.data.response.GeneralResponse;
import com.prography.sw.aloocustomer.model.IAddress;
import com.prography.sw.aloocustomer.network.AuthResource;
import com.prography.sw.aloocustomer.util.SharedPreferencesHelper;
import com.prography.sw.aloocustomer.viewmodel.AddressesViewModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class MapSheetSW extends BottomSheetDialogFragment implements OnMapReadyCallback, CustomListener, View.OnClickListener, GoogleMap.OnMapClickListener {
    private GoogleMap mMap;
    private CustomRecyclerViewAdapter adapter;
    private List<IAddress> IAddressList = new ArrayList<>();
    private FusedLocationProviderClient client;
    private MarkerOptions markerOptions;
    private LatLng latLngObj;
    private AddressesViewModel addressesViewModel;

    private RecyclerView rvLocation;
    private ScrollView layoutLocation;
    private IconEditText etTitle, etDetails;
    private Button btn, btnChoose;
    private ProgressBar loadingBtn;
    private ProgressBar loadingRv;
    private int position;
    private CustomListener mListener;

    public MapSheetSW(CustomListener mListener) {
        this.mListener = mListener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.bottom_sheet_map, container, false);
        setCancelable(false);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        initView(rootView);

        initViewModel();
        initAdapter();
        openGPS(getActivity());
        getCurrentLocation();
        checkGuest();

        return rootView;
    }

    private void checkGuest() {
        if (SharedPreferencesHelper.getUserToken(getContext()).isEmpty()) {
            btnChoose.setVisibility(View.VISIBLE);
            rvLocation.setVisibility(View.GONE);
            btn.setVisibility(View.GONE);
        } else {
            btnChoose.setVisibility(View.GONE);
            rvLocation.setVisibility(View.VISIBLE);
            btn.setVisibility(View.VISIBLE);
            getAddresses();
        }
    }

    private void initViewModel() {
        addressesViewModel = new ViewModelProvider(this,
                new ViewModelProvider.AndroidViewModelFactory(getActivity().getApplication()))
                .get(AddressesViewModel.class);
    }

    private void initView(View root) {
        btn = root.findViewById(R.id.btn);
        btnChoose = root.findViewById(R.id.btn_choose);
        rvLocation = root.findViewById(R.id.rv_location);
        layoutLocation = root.findViewById(R.id.layout_location);
        etTitle = root.findViewById(R.id.et_title);
        etDetails = root.findViewById(R.id.et_details);
        loadingBtn = root.findViewById(R.id.loading_btn);
        loadingRv = root.findViewById(R.id.loading_rv);
        btn.setOnClickListener(this);
        btnChoose.setOnClickListener(this);
    }

    private void initAdapter() {
        adapter = new CustomRecyclerViewAdapter(this, CustomRecyclerViewAdapter.HolderConstants.LOCATION);
        adapter.setLocations(IAddressList);
        rvLocation.setAdapter(adapter);
    }

    @Override
    public void onItemClick(int position, String action) {
        this.position = position;
        mListener.onItemClick(0, "MAP_SHEET");
        this.dismiss();
    }


    public int getAddressId() {
        if (SharedPreferencesHelper.getUserToken(getContext()).isEmpty()) {
            SharedPreferencesHelper.setId(getContext(), -1);
            return -1;
        } else {
            SharedPreferencesHelper.setId(getContext(), IAddressList.get(position).getId());
            return IAddressList.get(position).getId();
        }
    }

    public double getLon() {
        if (SharedPreferencesHelper.getUserToken(getContext()).isEmpty()) {
            SharedPreferencesHelper.setLon(getContext(), String.valueOf(latLngObj.longitude));
            return latLngObj.longitude;
        } else {
            SharedPreferencesHelper.setLon(getContext(), String.valueOf(-1));
            return -1;
        }
    }

    public double getLat() {
        if (SharedPreferencesHelper.getUserToken(getContext()).isEmpty()) {
            SharedPreferencesHelper.setLat(getContext(),String.valueOf(latLngObj.latitude));
            return latLngObj.latitude;
        } else {
            SharedPreferencesHelper.setLat(getContext(),String.valueOf(-1));
            return -1;
        }
    }

    public void openGPS(Activity activity) {
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        final LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);

        Task<LocationSettingsResponse> result = LocationServices.getSettingsClient(getActivity()).checkLocationSettings(builder.build());
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
        client = LocationServices.getFusedLocationProviderClient(getActivity());

        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            client.getLastLocation()
                    .addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(android.location.Location location) {
                            // Got last known location. In some rare situations this can be null.
                            if (location != null) {
                                // Logic to handle location object
                                latLngObj = new LatLng(location.getLatitude(), location.getLongitude());
                                markerOptions = new MarkerOptions()
                                        .position(latLngObj)
                                        .icon(BitmapFromVector(getContext(), R.drawable.ic_marker))
                                        .anchor(0.5f, 0.5f);

                                mMap.addMarker(markerOptions);
                                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLngObj, 12));
                            }
                        }
                    });
        }


    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;

        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED)
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
                .icon(BitmapFromVector(getContext(), R.drawable.ic_marker))
                .anchor(0.5f, 0.5f);
        mMap.addMarker(markerOptions);

        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLngObj, 12));

        if (!SharedPreferencesHelper.getUserToken(getContext()).isEmpty()) {
            etDetails.setmyText(geocoderLocation(latLng.latitude, latLng.longitude));
        }
    }

    public String geocoderLocation(double longitude, double latitude) {
        Geocoder geoCoder = new Geocoder(getContext());
        List<Address> list = null;
        try {
            list = geoCoder.getFromLocation(longitude, latitude, 1);
            if (list != null & list.size() > 0) {
                Address address = list.get(0);
                String result = address.getLocality();
                return result;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_choose) {
            mListener.onItemClick(0, "MAP_SHEET");
            this.dismiss();
        } else {
            if (rvLocation.getVisibility() == View.VISIBLE) {
                rvLocation.setVisibility(View.GONE);
                layoutLocation.setVisibility(View.VISIBLE);
            } else {
                Toast.makeText(getContext(), "location : " + etTitle.getmyText(), Toast.LENGTH_SHORT).show();
                if (!etTitle.getmyText().toString().isEmpty() && !etDetails.getmyText().toString().isEmpty())
                    addAddress();
            }
        }
    }

    private void addAddress() {
        addressesViewModel.addAddress(
                etTitle.getmyText().toString(),
                etDetails.getmyText().toString(),
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
            loadingBtn.setVisibility(View.GONE);
            btn.setVisibility(View.VISIBLE);
        } else {
            loadingBtn.setVisibility(View.VISIBLE);
            btn.setVisibility(View.GONE);
        }
    }

    private void updateAddUi(GeneralResponse response) {
        showAddParent(View.VISIBLE);
        Toast.makeText(getContext(), response.getMessage(), Toast.LENGTH_SHORT).show();

        etTitle.setmyText("");
        etDetails.setmyText("");
        rvLocation.setVisibility(View.VISIBLE);
        layoutLocation.setVisibility(View.GONE);
        getAddresses();
    }

    private void showAddFailed(AuthResource<GeneralResponse> errorResponse) {
        switch (errorResponse.errorCode) {
            case 0:
                Toast.makeText(getContext(), getString(R.string.check_internet_connection_text), Toast.LENGTH_SHORT).show();
                break;
            case 500:
                Toast.makeText(getContext(), getString(R.string.something_went_wrong_text), Toast.LENGTH_SHORT).show();
                break;
            case 401:
                unauthorized(getActivity());
                break;
            default:
                Toast.makeText(getContext(), errorResponse.message, Toast.LENGTH_SHORT).show();
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
            loadingRv.setVisibility(View.GONE);
            rvLocation.setVisibility(View.VISIBLE);
        } else {
            loadingRv.setVisibility(View.VISIBLE);
            rvLocation.setVisibility(View.GONE);
        }
    }

    private void updateUi(GeneralResponse response) {
        showParent(View.VISIBLE);
        if (response == null || response.getAddressesData() == null || response.getAddressesData().getAddresses() == null) {
            Toast.makeText(getContext(), getString(R.string.something_went_wrong_text), Toast.LENGTH_SHORT).show();
            return;
        }
        if (response.getAddressesData().getAddresses().isEmpty())
            Toast.makeText(getContext(), getString(R.string.no_places_were_added), Toast.LENGTH_SHORT).show();
        IAddressList = response.getAddressesData().getAddresses();
        adapter.setLocations(IAddressList);
    }

    private void showFailed(AuthResource<GeneralResponse> errorResponse) {
        switch (errorResponse.errorCode) {
            case 0:
                Toast.makeText(getContext(), getString(R.string.check_internet_connection_text), Toast.LENGTH_SHORT).show();
                break;
            case 500:
                Toast.makeText(getContext(), getString(R.string.something_went_wrong_text), Toast.LENGTH_SHORT).show();
                break;
            case 401:
                unauthorized(getActivity());
                break;
            default:
                Toast.makeText(getContext(), errorResponse.message, Toast.LENGTH_SHORT).show();
                break;
        }
        showParent(View.VISIBLE);
    }
}
