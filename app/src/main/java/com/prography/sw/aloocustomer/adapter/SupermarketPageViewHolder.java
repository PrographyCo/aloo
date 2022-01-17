package com.prography.sw.aloocustomer.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.prography.sw.aloocustomer.R;
import com.prography.sw.aloocustomer.model.VendorItems;
import com.prography.sw.aloocustomer.util.AppUtils;

public class SupermarketPageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private final CustomListener customListener;
    private final CustomRecyclerViewAdapter.HolderConstants holderConstants;
    private ImageView item_image, icon;
    private TextView item_name, item_price, item_number;


    public SupermarketPageViewHolder(@NonNull View itemView, CustomListener customListener, CustomRecyclerViewAdapter.HolderConstants holderConstants) {
        super(itemView);
        this.customListener = customListener;
        this.holderConstants = holderConstants;
        initViews();
    }

    public void initViews() {
        item_image = itemView.findViewById(R.id.item_image);
        item_name = itemView.findViewById(R.id.item_name);
        item_price = itemView.findViewById(R.id.item_price);
        item_number = itemView.findViewById(R.id.item_number);
        icon = itemView.findViewById(R.id.favorite);
        itemView.setOnClickListener(this);
        icon.setOnClickListener(this);
    }

    public void onBind(VendorItems item) {
        AppUtils.initGlide(itemView.getContext()).load(item.getImg()).into(item_image);
        item_name.setText(item.getName());
        item_price.setText(item.getPrice());

        item_number.setText(item.getAmount() + " " + item.getAmountType());
        if (item.isFavorite())
            icon.setImageResource(R.drawable.ic_favourite);
        else
            icon.setImageResource(R.drawable.ic_favorite_wihte);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.favorite)
            customListener.onItemClick(getAdapterPosition(), "FAVORITE");
        else
            customListener.onItemClick(getAdapterPosition(), "ITEM_SUPERMARKET_PAGE");

    }
}
