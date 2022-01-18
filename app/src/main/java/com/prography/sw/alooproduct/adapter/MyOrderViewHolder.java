package com.prography.sw.alooproduct.adapter;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.prography.sw.alooproduct.R;
import com.prography.sw.alooproduct.data.response.data.MyOrder;
import com.prography.sw.alooproduct.util.AppUtils;

public class MyOrderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private final CustomRecyclerViewAdapter.HolderConstants holderConstants;
    private final CustomListener customListener;
    private TextView tv_time, tv_order_status, tv_order_number, tv_price;

    public MyOrderViewHolder(@NonNull View itemView, CustomListener customListener, CustomRecyclerViewAdapter.HolderConstants holderConstants) {
        super(itemView);

        this.holderConstants = holderConstants;
        this.customListener = customListener;
        initView();
    }

    private void initView() {
        tv_time = itemView.findViewById(R.id.tv_time);
        tv_order_status = itemView.findViewById(R.id.tv_order_status);
        tv_order_number = itemView.findViewById(R.id.tv_order_number);
        tv_price = itemView.findViewById(R.id.order_price);
        itemView.setOnClickListener(this);
    }

    public void onBind(MyOrder item) {
        tv_time.setText(AppUtils.setTime((long) item.getDate()));
        String status = itemView.getResources().getString(R.string.order_status) + " " + item.getStatus();
        tv_order_status.setText(status);
        String number = itemView.getResources().getString(R.string.order_number) + " " + item.getId();
        tv_order_number.setText(number);
        String price = item.getTotalPrice() + " " + itemView.getResources().getString(R.string.currency);
        tv_price.setText(price);
    }

    @Override
    public void onClick(View view) {
        customListener.onItemClick(getAdapterPosition(), "MY_ORDER");
    }
}
