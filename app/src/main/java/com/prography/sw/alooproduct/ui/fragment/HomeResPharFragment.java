package com.prography.sw.alooproduct.ui.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.prography.sw.alooproduct.R;
import com.prography.sw.alooproduct.adapter.CustomListener;
import com.prography.sw.alooproduct.adapter.CustomRecyclerViewAdapter;
import com.prography.sw.alooproduct.databinding.FragmentHomeResPharBinding;
import com.prography.sw.alooproduct.model.HomeSuperPharm;

import java.util.ArrayList;
import java.util.List;

public class HomeResPharFragment extends Fragment implements CustomListener {

    private FragmentHomeResPharBinding binding;
    private List<HomeSuperPharm> item = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeResPharBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initList();
        initAdapter();
    }

    private void initList() {
        item.add(new HomeSuperPharm(R.drawable.image_item_supermarket_page, "chicken breasts", "50,10 SAR", "450 grams"));
        item.add(new HomeSuperPharm(R.drawable.image_item_supermarket_page, "chicken breasts", "50,10 SAR", "450 grams"));
        item.add(new HomeSuperPharm(R.drawable.image_item_supermarket_page, "chicken breasts", "50,10 SAR", "450 grams"));
        item.add(new HomeSuperPharm(R.drawable.image_item_supermarket_page, "chicken breasts", "50,10 SAR", "450 grams"));
        item.add(new HomeSuperPharm(R.drawable.image_item_supermarket_page, "chicken breasts", "50,10 SAR", "450 grams"));
    }

    private void initAdapter() {
        CustomRecyclerViewAdapter adapter = new CustomRecyclerViewAdapter(this, CustomRecyclerViewAdapter.HolderConstants.HOME_FRAGMENT_SUPER_PHARM);
        //adapter.setHomeSuperPharm(item);
        binding.rvHomeSuperPharm.setAdapter(adapter);
    }

    @Override
    public void onItemClick(int position, String action) {

    }
}