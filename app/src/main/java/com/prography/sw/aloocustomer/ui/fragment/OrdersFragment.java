package com.prography.sw.aloocustomer.ui.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.prography.sw.aloocustomer.R;
import com.prography.sw.aloocustomer.databinding.FragmentOrdersBinding;
import com.prography.sw.aloocustomer.ui.activity.ListOrdersActivity;

public class OrdersFragment extends Fragment implements View.OnClickListener {

    private FragmentOrdersBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentOrdersBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initViews();
    }

    private void initViews() {
        binding.restaurantsOrders.setOnClickListener(this);
        binding.supermarketOrders.setOnClickListener(this);
        binding.pharmaciesOrders.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = -1;
        switch (view.getId()) {
            case R.id.supermarket_orders:
                id = 1;
                break;
            case R.id.pharmacies_orders:
                id = 2;
                break;
            case R.id.restaurants_orders:
                id = 3;
                break;
        }
        Intent intent = new Intent(getActivity(), ListOrdersActivity.class);
        intent.putExtra("id", id);
        startActivity(intent);
    }
}