package com.prography.sw.aloocustomer.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.prography.sw.aloocustomer.R;
import com.prography.sw.aloocustomer.model.VendorItems;
import com.prography.sw.aloocustomer.util.AppUtils;

import de.hdodenhof.circleimageview.CircleImageView;

public class RestaurantPageItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private final CustomListener customListener;
    private final CustomRecyclerViewAdapter.HolderConstants holderConstants;
    private CircleImageView restaurant_Page_image;
    private TextView restaurant_Page_name, restaurant_Page_description, restaurant_Page_calories, restaurant_Page_prise;
    private ImageView icon;


    public RestaurantPageItemViewHolder(@NonNull View itemView, CustomListener customListener, CustomRecyclerViewAdapter.HolderConstants holderConstants) {
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
        icon = itemView.findViewById(R.id.favorite);
        itemView.setOnClickListener(this);
        icon.setOnClickListener(this);
    }

    public void onBind(VendorItems item) {
        AppUtils.initGlide(itemView.getContext()).load(item.getImg()).into(restaurant_Page_image);
        restaurant_Page_name.setText(item.getName());
        restaurant_Page_description.setText(item.getDescription());
        restaurant_Page_calories.setText(itemView.getResources().getString(R.string.calories) + " " + item.getCalories());
        restaurant_Page_prise.setText(itemView.getResources().getString(R.string.price) + " " +  item.getPrice());
        if (item.isFavorite())
            icon.setImageResource(R.drawable.ic_favourite);
        else
            icon.setImageResource(R.drawable.ic_favorite_wihte);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.favorite)
            customListener.onItemClick(getAdapterPosition(), "FAVORITE_RESTAURANT");
        else
            customListener.onItemClick(getAdapterPosition(), "RESTAURANT_PAGE_ITEM");
    }
}
