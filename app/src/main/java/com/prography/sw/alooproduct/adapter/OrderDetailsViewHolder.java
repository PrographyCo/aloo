package com.prography.sw.alooproduct.adapter;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.prography.sw.alooproduct.R;
import com.prography.sw.alooproduct.data.response.data.OrderData;
import com.prography.sw.alooproduct.model.OrderDetails;
import com.prography.sw.alooproduct.model.OrderListTiem;
import com.prography.sw.alooproduct.util.AppUtils;

import de.hdodenhof.circleimageview.CircleImageView;

public class OrderDetailsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private final CustomRecyclerViewAdapter.HolderConstants holderConstants;
    private final CustomListener customListener;
    private CircleImageView circleImageView;
    private TextView tv_meal_name, tv_without, tv_Drink, tv_meal_number, tv_price;

    public OrderDetailsViewHolder(@NonNull View itemView, CustomListener customListener, CustomRecyclerViewAdapter.HolderConstants holderConstants) {
        super(itemView);

        this.holderConstants = holderConstants;
        this.customListener = customListener;
        initview();
    }

    private void initview() {
        tv_meal_name = itemView.findViewById(R.id.tv_meal_name);
        tv_without = itemView.findViewById(R.id.tv_without);
        tv_Drink = itemView.findViewById(R.id.tv_Drink);
        tv_meal_number = itemView.findViewById(R.id.tv_meal_number);
        tv_price = itemView.findViewById(R.id.tv_price);
        circleImageView = itemView.findViewById(R.id.meal_image);
        itemView.setOnClickListener(this);
    }

    public void onBind(OrderListTiem item) {
//        if (!item.getItem().getImage())
        AppUtils.initGlide(itemView.getContext()).load(item.getItem().getImage()).into(circleImageView);
        tv_meal_name.setText(item.getItem().getName());
        tv_without.setText(item.getData().getWithout());
//        tv_Drink.setText(item.getData().getDrinks().get(1));
        tv_meal_number.setText(item.getAmount());
        tv_price.setText(item.getItemPrice());

    }

    @Override
    public void onClick(View view) {
        customListener.onItemClick(getAdapterPosition(), "ITEM_ORDER_DETAILS");
    }
}
