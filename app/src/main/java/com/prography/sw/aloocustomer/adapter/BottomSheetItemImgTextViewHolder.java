package com.prography.sw.aloocustomer.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.prography.sw.aloocustomer.R;
import com.prography.sw.aloocustomer.model.ImageText;

class BottomSheetItemImgTextViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private final CustomListener customListener;
    private final CustomRecyclerViewAdapter.HolderConstants holderConstants;
    private TextView tvTitle;
    private ImageView imageView;

    public BottomSheetItemImgTextViewHolder(View itemView, CustomListener customListener, CustomRecyclerViewAdapter.HolderConstants holderConstants) {
        super(itemView);
        this.customListener = customListener;
        this.holderConstants = holderConstants;
        initViews();
    }

    private void initViews() {
        tvTitle = itemView.findViewById(R.id.tv_title);
        imageView = itemView.findViewById(R.id.iv_icon);
        itemView.setOnClickListener(this);
    }

    void onBind(ImageText imageText) {
        tvTitle.setText(imageText.getTitle());
        imageView.setImageResource(imageText.getImg());
    }

    @Override
    public void onClick(View v) {
        customListener.onItemClick(getAdapterPosition(), "IMAGE_TEXT");
    }
}
