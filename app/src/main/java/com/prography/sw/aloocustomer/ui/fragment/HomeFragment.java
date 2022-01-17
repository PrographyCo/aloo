package com.prography.sw.aloocustomer.ui.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.prography.sw.aloocustomer.R;
import com.prography.sw.aloocustomer.data.response.GeneralResponse;
import com.prography.sw.aloocustomer.databinding.FragmentHomeBinding;
import com.prography.sw.aloocustomer.network.AuthResource;
import com.prography.sw.aloocustomer.repos.GeneralDataRepo;
import com.prography.sw.aloocustomer.ui.activity.LoginActivity;
import com.prography.sw.aloocustomer.ui.activity.PharmacyActivity;
import com.prography.sw.aloocustomer.ui.activity.ResturantsActivity;
import com.prography.sw.aloocustomer.ui.activity.SupermarketActivity;
import com.prography.sw.aloocustomer.util.AppUtils;
import com.prography.sw.aloocustomer.util.SharedPreferencesHelper;
import com.prography.sw.aloocustomer.viewmodel.GeneralDataViewModel;

public class HomeFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = HomeFragment.class.getSimpleName();
    private FragmentHomeBinding binding;
    private GeneralDataViewModel generalDataViewModel;
    private int idSupermarket;
    private int idPharmacy;
    private int idResturants;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        initViewMolde();
        startServicesProcess();
    }

    private void initViewMolde() {
        generalDataViewModel = new ViewModelProvider(this,
                new ViewModelProvider.AndroidViewModelFactory(getActivity().getApplication())).get(GeneralDataViewModel.class);
    }

    private void initView() {
        binding.tvCat1Titel.setOnClickListener(this);
        binding.tvCat1Desc.setOnClickListener(this);
        binding.imageCar1.setOnClickListener(this);

        binding.tvCat2Titel.setOnClickListener(this);
        binding.tvCat2Desc.setOnClickListener(this);
        binding.imageCar2.setOnClickListener(this);

        binding.tvCat3Titel.setOnClickListener(this);
        binding.tvCat3Desc.setOnClickListener(this);
        binding.imageCar3.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_cat_1_titel:
            case R.id.tv_cat_1_desc:
            case R.id.image_car_1:
                Intent intentResturants = new Intent(getActivity(), ResturantsActivity.class);
                intentResturants.putExtra("id", idResturants);
                SharedPreferencesHelper.setVendorId(getActivity(), idResturants);
                startActivity(intentResturants);
                break;
            case R.id.tv_cat_2_titel:
            case R.id.tv_cat_2_desc:
            case R.id.image_car_2:
                Intent intentPharmacy = new Intent(getActivity(), PharmacyActivity.class);
                intentPharmacy.putExtra("id", idPharmacy);
                SharedPreferencesHelper.setVendorId(getActivity(), idPharmacy);
                startActivity(intentPharmacy);
                break;
            case R.id.tv_cat_3_titel:
            case R.id.tv_cat_3_desc:
            case R.id.image_car_3:
                Intent intentSupermarket = new Intent(getActivity(), SupermarketActivity.class);
                intentSupermarket.putExtra("id", idSupermarket);
                SharedPreferencesHelper.setVendorId(getActivity(), idSupermarket);
                startActivity(intentSupermarket);
                break;

        }
    }


    private void startServicesProcess() {
        generalDataViewModel.services().observe(getViewLifecycleOwner(), new Observer<AuthResource<GeneralResponse>>() {
            @Override
            public void onChanged(AuthResource<GeneralResponse> servicesResource) {
                switch (servicesResource.status) {
                    case LOADING:
                        showParent(View.GONE);
                        break;
                    case AUTHENTICATED:
                        updateUi(servicesResource.data);
                        generalDataViewModel.removeServicesObservers(getViewLifecycleOwner());
                        break;
                    case ERROR:
                        showServicesFailed(servicesResource);
                        generalDataViewModel.removeServicesObservers(getViewLifecycleOwner());
                        break;
                }
            }
        });
    }


    private void showParent(int visibility) {
        if (visibility == View.VISIBLE) {
            binding.loading.setVisibility(View.GONE);
            binding.tvCat1Desc.setVisibility(View.VISIBLE);
            binding.tvCat1Titel.setVisibility(View.VISIBLE);
            binding.imageCar1.setVisibility(View.VISIBLE);
            binding.tvCat2Desc.setVisibility(View.VISIBLE);
            binding.tvCat2Titel.setVisibility(View.VISIBLE);
            binding.imageCar2.setVisibility(View.VISIBLE);
            binding.tvCat3Desc.setVisibility(View.VISIBLE);
            binding.tvCat3Titel.setVisibility(View.VISIBLE);
            binding.imageCar3.setVisibility(View.VISIBLE);
        } else {
            binding.loading.setVisibility(View.VISIBLE);
            binding.tvCat1Desc.setVisibility(View.GONE);
            binding.tvCat1Titel.setVisibility(View.GONE);
            binding.imageCar1.setVisibility(View.GONE);
            binding.tvCat2Desc.setVisibility(View.GONE);
            binding.tvCat2Titel.setVisibility(View.GONE);
            binding.imageCar2.setVisibility(View.GONE);
            binding.tvCat3Desc.setVisibility(View.GONE);
            binding.tvCat3Titel.setVisibility(View.GONE);
            binding.imageCar3.setVisibility(View.GONE);
        }
    }

    private void updateUi(GeneralResponse response) {
        showParent(View.VISIBLE);

        binding.tvCat1Titel.setText(response.getSupportedVendorsData().getSupportedVendors().get(2).getName());
        binding.tvCat1Desc.setText(response.getSupportedVendorsData().getSupportedVendors().get(2).getDescription());
        AppUtils.initGlide(getActivity()).load(response.getSupportedVendorsData().getSupportedVendors().get(2).getImg()).into(binding.imageCar1);
        idResturants = response.getSupportedVendorsData().getSupportedVendors().get(2).getId();

        binding.tvCat2Titel.setText(response.getSupportedVendorsData().getSupportedVendors().get(1).getName());
        binding.tvCat2Desc.setText(response.getSupportedVendorsData().getSupportedVendors().get(1).getDescription());
        AppUtils.initGlide(getActivity()).load(response.getSupportedVendorsData().getSupportedVendors().get(1).getImg()).into(binding.imageCar2);
        idPharmacy = response.getSupportedVendorsData().getSupportedVendors().get(1).getId();

        binding.tvCat3Titel.setText(response.getSupportedVendorsData().getSupportedVendors().get(0).getName());
        binding.tvCat3Desc.setText(response.getSupportedVendorsData().getSupportedVendors().get(0).getDescription());
        AppUtils.initGlide(getActivity()).load(response.getSupportedVendorsData().getSupportedVendors().get(0).getImg()).into(binding.imageCar3);
        idSupermarket = response.getSupportedVendorsData().getSupportedVendors().get(0).getId();

    }

    private void showServicesFailed(AuthResource<GeneralResponse> errorResponse) {
        switch (errorResponse.errorCode) {
            case 0:
                Toast.makeText(getContext(), getString(R.string.check_internet_connection_text), Toast.LENGTH_SHORT).show();
                break;
            case 500:
                Toast.makeText(getContext(), getString(R.string.something_went_wrong_text), Toast.LENGTH_SHORT).show();
                break;
            case 401:
                AppUtils.unauthorized(getActivity());
                break;
            default:
                Toast.makeText(getContext(), errorResponse.message, Toast.LENGTH_SHORT).show();
                break;
        }
        showParent(View.VISIBLE);
    }
}