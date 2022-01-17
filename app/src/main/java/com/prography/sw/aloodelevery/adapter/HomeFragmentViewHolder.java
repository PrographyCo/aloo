package com.prography.sw.aloodelevery.adapter;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.prography.sw.aloodelevery.R;
import com.prography.sw.aloodelevery.model.HomeFragment;
import com.prography.sw.aloodelevery.model.Order;
import com.prography.sw.aloodelevery.util.AppUtils;

public class HomeFragmentViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private final CustomListener customListener;
    private final CustomRecyclerViewAdapter.HolderConstants holderConstants;

    private ImageView icon, pay_icon;
    private TextView number_meal, owner_name, address_one, address_tow, now;
    private Button btn_accept;

    public HomeFragmentViewHolder(@NonNull View itemView, CustomListener customListener, CustomRecyclerViewAdapter.HolderConstants holderConstants) {
        super(itemView);
        this.customListener = customListener;
        this.holderConstants = holderConstants;
        initView();
    }

    private void initView() {
        icon = itemView.findViewById(R.id.ic_icon);
        number_meal = itemView.findViewById(R.id.tv_meal_number);
        owner_name = itemView.findViewById(R.id.tv_owner_meal);
        address_one = itemView.findViewById(R.id.tv_Requester_address);
        address_tow = itemView.findViewById(R.id.my_address);
        btn_accept = itemView.findViewById(R.id.btn_accept);
        itemView.setOnClickListener(this);
        btn_accept.setOnClickListener(this);
    }

    public void onBind(Order item) {
        if (item.getVendorType().getId() == 1) {
            number_meal.setText("Order: " + item.getVendorType().getName());
            AppUtils.initGlide(itemView.getContext()).load(R.drawable.ic_supermarket).into(icon);
        } else if (item.getVendorType().getId() == 2) {
            number_meal.setText("Order: " + item.getVendorType().getName());
            AppUtils.initGlide(itemView.getContext()).load(R.drawable.ic_icon_restorant).into(icon);
        } else if (item.getVendorType().getId() == 3) {
            number_meal.setText("Order: " + item.getVendorType().getName());
            AppUtils.initGlide(itemView.getContext()).load(R.drawable.ic_icon_restorant).into(icon);
        }
        owner_name.setText(item.getCustomerName());
        address_one.setText(item.getPlace().getName());
        address_tow.setText(item.getPlace().getLocationName());

    }


    @Override
    public void onClick(View view) {
        if (view.getId() == itemView.getId()) {
            customListener.onItemClick(getAdapterPosition(), "HOME_FRAGMENT");
        } else if (view.getId() == R.id.btn_accept) {
            customListener.onItemClick(getAdapterPosition(), "HOME_FRAGMENT");
        }

    }
}
