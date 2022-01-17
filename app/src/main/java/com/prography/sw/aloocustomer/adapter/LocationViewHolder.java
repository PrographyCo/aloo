package com.prography.sw.aloocustomer.adapter;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.prography.sw.aloocustomer.R;
import com.prography.sw.aloocustomer.model.IAddress;

public class LocationViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private final CustomListener customListener;
    private final CustomRecyclerViewAdapter.HolderConstants holderConstants;
    private TextView tvTitle, tvDetails;

    public LocationViewHolder(View itemView, CustomListener customListener, CustomRecyclerViewAdapter.HolderConstants holderConstants) {
        super(itemView);
        this.customListener = customListener;
        this.holderConstants = holderConstants;
        initViews();
    }

    private void initViews() {
        tvTitle = itemView.findViewById(R.id.tv_title);
        tvDetails = itemView.findViewById(R.id.tv_details);

        itemView.setOnClickListener(this);
    }


    void onBind(IAddress IAddress) {
        tvTitle.setText(IAddress.getName());
        tvDetails.setText(IAddress.getLocationName());
    }

    @Override
    public void onClick(View v) {
        customListener.onItemClick(getAdapterPosition(), String.valueOf(getAdapterPosition()));
    }
}
