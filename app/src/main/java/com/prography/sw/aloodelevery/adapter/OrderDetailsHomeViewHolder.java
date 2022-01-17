package com.prography.sw.aloodelevery.adapter;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.prography.sw.aloodelevery.R;
import com.prography.sw.aloodelevery.model.OrderDetailsHome;
import com.prography.sw.aloodelevery.model.OrderItem;
import com.prography.sw.aloodelevery.util.AppUtils;

import org.w3c.dom.Text;

import de.hdodenhof.circleimageview.CircleImageView;

public class OrderDetailsHomeViewHolder extends RecyclerView.ViewHolder {
    private final CustomListener customListener;
    private final CustomRecyclerViewAdapter.HolderConstants holderConstants;
    private CircleImageView crCircleImageView;
    private TextView tv_order_name, tv_order_without, order_number, order_price;

    public OrderDetailsHomeViewHolder(@NonNull View itemView, CustomListener customListener, CustomRecyclerViewAdapter.HolderConstants holderConstants) {
        super(itemView);
        this.customListener = customListener;
        this.holderConstants = holderConstants;
        initView();
    }

    private void initView() {
        crCircleImageView = itemView.findViewById(R.id.order_image);
        tv_order_name = itemView.findViewById(R.id.tv_order_name);
        tv_order_without = itemView.findViewById(R.id.tv_order_without);
        order_number = itemView.findViewById(R.id.order_number);
        order_price = itemView.findViewById(R.id.order_price);
    }

    public void onBind(OrderItem item) {
        AppUtils.initGlide(itemView.getContext()).load(item.getItem().getImg()).into(crCircleImageView);
        tv_order_name.setText(item.getItem().getName());
        tv_order_without.setText(item.getData().getWithout());
        order_number.setText(item.getItem().getAmount() + item.getItem().getAmountType());
        order_price.setText(item.getItemPrice() + "");
    }


}
