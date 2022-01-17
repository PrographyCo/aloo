package com.prography.sw.aloocustomer.adapter;

import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alespero.expandablecardview.ExpandableCardView;
import com.prography.sw.aloocustomer.R;
import com.prography.sw.aloocustomer.model.MealDetails;
import com.prography.sw.aloocustomer.model.MealDetilsCardList;

import java.util.ArrayList;
import java.util.List;

public class MealDitelsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private final CustomListener customListener;
    private final CustomRecyclerViewAdapter.HolderConstants holderConstants;
    private CheckBox checkBox;

    public MealDitelsViewHolder(@NonNull View itemView, CustomListener customListener, CustomRecyclerViewAdapter.HolderConstants holderConstants) {
        super(itemView);
        this.customListener = customListener;
        this.holderConstants = holderConstants;
        initViews();

    }

    private void initViews() {
        checkBox = itemView.findViewById(R.id.tv_title);
        itemView.setOnClickListener(this);
    }

    public void onBind(MealDetails item) {
        checkBox.setText(item.getTitle());
        checkBox.setChecked(item.getChecked());
    }


    @Override
    public void onClick(View view) {
        if (holderConstants == CustomRecyclerViewAdapter.HolderConstants.MEAL_DETAILS) {
            customListener.onItemClick(getAdapterPosition(), "MEAL_DETAILS");
        }
    }


}
