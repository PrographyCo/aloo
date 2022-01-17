package com.prography.sw.aloocustomer.adapter;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.prography.sw.aloocustomer.R;
import com.prography.sw.aloocustomer.model.OrderItem;
import com.prography.sw.aloocustomer.util.AppUtils;

import de.hdodenhof.circleimageview.CircleImageView;

public class OrderDetailsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private final CustomListener customListener;
    private final CustomRecyclerViewAdapter.HolderConstants holderConstants;

    private CircleImageView order_image;
    private TextView product_name, product_size, product_number, product_price;

    public OrderDetailsViewHolder(@NonNull View itemView, CustomListener customListener, CustomRecyclerViewAdapter.HolderConstants holderConstants) {
        super(itemView);
        this.customListener = customListener;
        this.holderConstants = holderConstants;
        initView();
    }

    private void initView() {
        order_image = itemView.findViewById(R.id.order_image);
        product_name = itemView.findViewById(R.id.tv_order_name);
        product_size = itemView.findViewById(R.id.tv_order_size);
        product_number = itemView.findViewById(R.id.order_number);
        product_price = itemView.findViewById(R.id.order_price);
    }


    public void onBind(OrderItem item) {
        AppUtils.initGlide(itemView.getContext()).load(item.getInfo().getImg()).into(order_image);
        product_name.setText(item.getInfo().getName());
        product_number.setText(String.valueOf(item.getAmount()));
        product_size.setText(item.getData().getSize());
        product_price.setText(String.valueOf(item.getItemPrice()));
    }

    @Override
    public void onClick(View view) {
        if (holderConstants == CustomRecyclerViewAdapter.HolderConstants.ITEM_ORDERS_DETAILS) {
            customListener.onItemClick(getAdapterPosition(), "ITEM_ORDERS_DETAILS");
        }
    }
}
