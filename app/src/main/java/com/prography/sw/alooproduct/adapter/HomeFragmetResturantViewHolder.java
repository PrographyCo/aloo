package com.prography.sw.alooproduct.adapter;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.prography.sw.alooproduct.R;
import com.prography.sw.alooproduct.model.HomeResturant;
import com.prography.sw.alooproduct.model.OrderItem;
import com.prography.sw.alooproduct.util.AppUtils;

public class HomeFragmetResturantViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private final CustomRecyclerViewAdapter.HolderConstants holderConstants;
    private final CustomListener customListener;
    private TextView tv_time, tv_meal_number, tv_order_number, tv_owner_meal, tv_price;
    private Button btn_confirmation;


    public HomeFragmetResturantViewHolder(@NonNull View itemView, CustomListener customListener, CustomRecyclerViewAdapter.HolderConstants holderConstants) {
        super(itemView);

        this.holderConstants = holderConstants;
        this.customListener = customListener;
        initview();
    }

    private void initview() {
        tv_time = itemView.findViewById(R.id.tv_time);
        tv_meal_number = itemView.findViewById(R.id.tv_meal_number);
        tv_order_number = itemView.findViewById(R.id.tv_order_number);
        tv_owner_meal = itemView.findViewById(R.id.tv_owner_meal);
        tv_price = itemView.findViewById(R.id.order_price);
        btn_confirmation = itemView.findViewById(R.id.btn_confirmation);
        itemView.setOnClickListener(this);
        btn_confirmation.setOnClickListener(this);
    }

    public void onBind(OrderItem item) {
        tv_time.setText(AppUtils.setTime((long) item.getDate()));
        String meals = itemView.getResources().getString(R.string.no_items) + " " + item.getItemsSumAmount();
        tv_meal_number.setText(meals);
        String number = itemView.getResources().getString(R.string.order_number) + " " + item.getId();
        tv_order_number.setText(number);
        tv_owner_meal.setText(item.getCustomer().getName());
        String price = item.getTotalPrice() + " " + itemView.getResources().getString(R.string.currency);
        tv_price.setText(price);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == itemView.getId()) {
            customListener.onItemClick(getAdapterPosition(), "HOME_FRAGMET_RESTURANT");
        } else if (view.getId() == R.id.btn_confirmation) {
            Toast.makeText(itemView.getContext(), "btn_confirmation", Toast.LENGTH_SHORT).show();
        }
    }
}
