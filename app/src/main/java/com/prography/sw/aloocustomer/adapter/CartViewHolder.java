package com.prography.sw.aloocustomer.adapter;

import android.telephony.CellSignalStrength;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.prography.sw.aloocustomer.R;
import com.prography.sw.aloocustomer.model.Cart;
import com.prography.sw.aloocustomer.model.CartItem;
import com.prography.sw.aloocustomer.util.AppUtils;

import de.hdodenhof.circleimageview.CircleImageView;

public class CartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private final CustomListener customListener;
    private final CustomRecyclerViewAdapter.HolderConstants holderConstants;
    private CircleImageView meal_image;
    ImageView image_delete, image_add, image_minus;
    private TextView name_meal, tv_with, tv_with_out, tv_drink, tv_extra, meal_prise, tv_number;
    private Button btn_edit;


    public CartViewHolder(@NonNull View itemView, CustomListener customListener, CustomRecyclerViewAdapter.HolderConstants holderConstants) {
        super(itemView);
        this.customListener = customListener;
        this.holderConstants = holderConstants;
        initView();
    }

    public void initView() {
        meal_image = itemView.findViewById(R.id.meal_image);
        image_delete = itemView.findViewById(R.id.im_delete);
        image_add = itemView.findViewById(R.id.add_img);
        image_minus = itemView.findViewById(R.id.mi_minus);
        name_meal = itemView.findViewById(R.id.tv_resturant_name);
        tv_with = itemView.findViewById(R.id.tv_with);
        tv_with_out = itemView.findViewById(R.id.tv_with_out);
        tv_drink = itemView.findViewById(R.id.tv_drink);
        tv_extra = itemView.findViewById(R.id.tv_extra);
        meal_prise = itemView.findViewById(R.id._meal_price);
        tv_number = itemView.findViewById(R.id.number);
        btn_edit = itemView.findViewById(R.id.btn_edit);

        itemView.setOnClickListener(this);
        image_delete.setOnClickListener(this);
        btn_edit.setOnClickListener(this);
    }

    public void onBind(CartItem item) {
        if (item.getItem() != null) {
            AppUtils.initGlide(itemView.getContext()).load(item.getItem().getImg()).into(meal_image);
            name_meal.setText(item.getItem().getName());

        }
        if (item.isOffer()) {
            btn_edit.setVisibility(View.GONE);
        }

        if (item.getData() != null && item.getData().getWith() != null) {
            for (int i = 0; i < item.getData().getWith().size(); i++) {
                tv_with.append(item.getData().getWith().get(i) + " , ");
            }
        } else {
            tv_with.setVisibility(View.GONE);
        }
        if (item.getData() != null && item.getData().getWithout() != null) {
            for (int i = 0; i < item.getData().getWithout().size(); i++) {
                tv_with_out.append(item.getData().getWithout().get(i) + " , ");
            }
        } else {
            tv_with_out.setVisibility(View.GONE);
        }
        if (item.getData() != null && item.getData().getDrinks() != null) {
            for (int i = 0; i < item.getData().getDrinks().size(); i++) {
                tv_drink.append(item.getData().getDrinks().get(i).getName() + " , ");
            }
        } else {
            tv_drink.setVisibility(View.GONE);
        }
        if (item.getData() != null && item.getData().getExtras() != null) {
            for (int i = 0; i < item.getData().getExtras().size(); i++) {
                tv_extra.append(item.getData().getExtras().get(i).getName() + " , ");
            }
        } else {
            tv_extra.setVisibility(View.GONE);
        }
        meal_prise.setText(String.valueOf(item.getUnitPrice()));
        tv_number.setText(String.valueOf(item.getAmount()));


    }

    @Override
    public void onClick(View view) {
        if (view.getId() == itemView.getId()) {
            customListener.onItemClick(getAdapterPosition(), "CART_ITEM");
        } else if (view.getId() == R.id.im_delete) {
            customListener.onItemClick(getAdapterPosition(), "DELETE");
        } else if (view.getId() == R.id.btn_edit) {
            customListener.onItemClick(getAdapterPosition(), "EDIT");
            Toast.makeText(itemView.getContext(), "btn_edit", Toast.LENGTH_SHORT).show();
        }
    }
}
