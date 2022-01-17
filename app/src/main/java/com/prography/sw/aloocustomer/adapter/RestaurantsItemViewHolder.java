package com.prography.sw.aloocustomer.adapter;

import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.prography.sw.aloocustomer.R;
import com.prography.sw.aloocustomer.model.MainListItem;
import com.prography.sw.aloocustomer.util.AppUtils;

import de.hdodenhof.circleimageview.CircleImageView;

public class RestaurantsItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private final CustomListener customListener;
    private final CustomRecyclerViewAdapter.HolderConstants holderConstants;
    private CircleImageView restaurant_image;
    private TextView restaurant_name, restaurant_description, number_reate, restaurant_minimum_price;
    private RatingBar ratingBar;

    public RestaurantsItemViewHolder(@NonNull View itemView, CustomListener customListener, CustomRecyclerViewAdapter.HolderConstants holderConstants) {
        super(itemView);
        this.customListener = customListener;
        this.holderConstants = holderConstants;
        initView();
    }


    public void initView() {
        restaurant_image = itemView.findViewById(R.id.resturant_image);
        restaurant_name = itemView.findViewById(R.id.tv_resturant_name);
        restaurant_description = itemView.findViewById(R.id.tv_resturant_servis);
        number_reate = itemView.findViewById(R.id.number_reate);
        restaurant_minimum_price = itemView.findViewById(R.id.minimum);
        ratingBar = itemView.findViewById(R.id.resturant_rating);
        itemView.setOnClickListener(this);
    }

    public void onBind(MainListItem item) {
        AppUtils.initGlide(itemView.getContext()).load(item.getLogo()).into(restaurant_image);
        restaurant_name.setText(item.getName());
        restaurant_description.setText(item.getDescription());
        restaurant_minimum_price.setText(item.getMinPrice());
        ratingBar.setRating(item.getRates().getTotal());
        number_reate.setText(String.valueOf(item.getRates().getNumber()));
    }

    @Override
    public void onClick(View view) {
        if (holderConstants == CustomRecyclerViewAdapter.HolderConstants.RESTAURANTS_ITEM) {
            customListener.onItemClick(getAdapterPosition(), "RESTAURANT_ITEM");
        }
    }
}
