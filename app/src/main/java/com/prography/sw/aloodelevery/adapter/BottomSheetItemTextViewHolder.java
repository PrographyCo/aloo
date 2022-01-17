package com.prography.sw.aloodelevery.adapter;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.prography.sw.aloodelevery.R;

class BottomSheetItemTextViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private final CustomListener customListener;
    private final CustomRecyclerViewAdapter.HolderConstants holderConstants;
    private TextView tvTitle;

    public BottomSheetItemTextViewHolder(View itemView, CustomListener customListener, CustomRecyclerViewAdapter.HolderConstants holderConstants) {
        super(itemView);
        this.customListener = customListener;
        this.holderConstants = holderConstants;
        initViews();
    }

    private void initViews() {
        tvTitle = itemView.findViewById(R.id.tv_title);
        itemView.setOnClickListener(this);
    }


    void onBind(String title) {
        tvTitle.setText(title);
    }

    @Override
    public void onClick(View v) {
        customListener.onItemClick(getAdapterPosition(), "TEXT");
    }
}
