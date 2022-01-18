package com.prography.sw.alooproduct.adapter;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.prography.sw.alooproduct.R;
import com.prography.sw.alooproduct.model.HomeResturant;
import com.prography.sw.alooproduct.model.HomeSuperPharm;
import com.prography.sw.alooproduct.model.StoreProductItem;
import com.prography.sw.alooproduct.util.AppUtils;

public class HomeFragmetSuperPharmViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private final CustomRecyclerViewAdapter.HolderConstants holderConstants;
    private final CustomListener customListener;
    private ImageView item_image;
    private TextView item_name, item_number, item_price;


    public HomeFragmetSuperPharmViewHolder(@NonNull View itemView, CustomListener customListener, CustomRecyclerViewAdapter.HolderConstants holderConstants) {
        super(itemView);

        this.holderConstants = holderConstants;
        this.customListener = customListener;
        initview();
    }

    private void initview() {
        item_name = itemView.findViewById(R.id.item_name);
        item_number = itemView.findViewById(R.id.item_number);
        item_price = itemView.findViewById(R.id.item_price);
        item_image = itemView.findViewById(R.id.item_image);

    }

    public void onBind(StoreProductItem item) {
        AppUtils.initGlide(itemView.getContext()).load(item.getItem().getImg()).into(item_image);
        item_name.setText(item.getItem().getName());
        item_number.setText(item.getItem().getAmount() + " " + item.getItem().getAmountType());
        item_price.setText(item.getItem().getPrice());

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == itemView.getId()) {
            customListener.onItemClick(getAdapterPosition(), "HOME_FRAGMENT_SUPER_PHARM");
        }
    }
}
