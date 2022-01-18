package com.prography.sw.alooproduct.adapter;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.prography.sw.alooproduct.R;
import com.prography.sw.alooproduct.model.ResturantStore;
import com.prography.sw.alooproduct.model.StoreProductItem;
import com.prography.sw.alooproduct.util.AppUtils;

import de.hdodenhof.circleimageview.CircleImageView;

public class RestaurantStoreViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private final CustomListener customListener;
    private final CustomRecyclerViewAdapter.HolderConstants holderConstants;
    private CircleImageView restaurant_Page_image;
    private TextView restaurant_Page_name, restaurant_Page_description, restaurant_Page_calories, restaurant_Page_prise;


    public RestaurantStoreViewHolder(@NonNull View itemView, CustomListener customListener, CustomRecyclerViewAdapter.HolderConstants holderConstants) {
        super(itemView);
        this.customListener = customListener;
        this.holderConstants = holderConstants;
        initView();
    }


    public void initView() {
        restaurant_Page_image = itemView.findViewById(R.id.resturant_page_image);
        restaurant_Page_name = itemView.findViewById(R.id.tv_meal_name);
        restaurant_Page_description = itemView.findViewById(R.id.tv_meal_desc);
        restaurant_Page_calories = itemView.findViewById(R.id.meal_calories);
        restaurant_Page_prise = itemView.findViewById(R.id.meal_price);
        itemView.setOnClickListener(this);
    }

    public void onBind(StoreProductItem item) {
        AppUtils.initGlide(itemView.getContext()).load(item.getItem().getImg()).into(restaurant_Page_image);
        restaurant_Page_name.setText(item.getItem().getName());
        restaurant_Page_description.setText(item.getItem().getDescription());
        String cal = itemView.getResources().getString(R.string.calories);
        restaurant_Page_calories.setText(cal + " " + item.getItem().getCalories());
        restaurant_Page_prise.setText(item.getItem().getPrice());
    }

    @Override
    public void onClick(View view) {
        if (holderConstants == CustomRecyclerViewAdapter.HolderConstants.RESTAURANTS_STORE_ITEM) {
            customListener.onItemClick(getAdapterPosition(), "RESTAURANTS_STORE_ITEM");
        }
    }
}