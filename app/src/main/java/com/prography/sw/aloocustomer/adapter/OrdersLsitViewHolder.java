package com.prography.sw.aloocustomer.adapter;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.prography.sw.aloocustomer.R;
import com.prography.sw.aloocustomer.model.MyOrder;
import com.prography.sw.aloocustomer.util.AppUtils;

import de.hdodenhof.circleimageview.CircleImageView;

public class OrdersLsitViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private final CustomListener customListener;
    private final CustomRecyclerViewAdapter.HolderConstants holderConstants;
    private CircleImageView item_image;
    private TextView tv_name, tv_time_date, tv_order_number;

    public OrdersLsitViewHolder(@NonNull View itemView, CustomListener customListener, CustomRecyclerViewAdapter.HolderConstants holderConstants) {
        super(itemView);
        this.customListener = customListener;
        this.holderConstants = holderConstants;
        initView();
    }

    public void initView() {
        item_image = itemView.findViewById(R.id.item_order_list_image);
        tv_name = itemView.findViewById(R.id.tv_resturant_name);
        tv_time_date = itemView.findViewById(R.id.tv_time_date);
        tv_order_number = itemView.findViewById(R.id.order_number);

        itemView.setOnClickListener(this);
    }

    public void onBind(MyOrder item) {
        AppUtils.initGlide(itemView.getContext()).load(item.getVendor().getLogo()).into(item_image);
        tv_name.setText(item.getVendor().getName());
        tv_time_date.setText(item.getDate());
        tv_order_number.setText(String.valueOf(item.getId()));
    }

    @Override
    public void onClick(View view) {
        if (holderConstants == CustomRecyclerViewAdapter.HolderConstants.ITEM_ORDERS_LIST) {
            customListener.onItemClick(getAdapterPosition(), "ITEM_ORDERS_LIST");
        }
    }
}
