package com.prography.sw.alooproduct.adapter;

import android.view.View;
import android.widget.RadioButton;

import androidx.recyclerview.widget.RecyclerView;

import com.prography.sw.alooproduct.R;
import com.prography.sw.alooproduct.model.Radio;

class BottomSheetItemRadioViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private final CustomListener customListener;
    private final CustomRecyclerViewAdapter.HolderConstants holderConstants;
    private RadioButton rbTitle;

    public BottomSheetItemRadioViewHolder(View itemView, CustomListener customListener, CustomRecyclerViewAdapter.HolderConstants holderConstants) {
        super(itemView);
        this.customListener = customListener;
        this.holderConstants = holderConstants;
        initViews();
    }

    private void initViews() {
        rbTitle = itemView.findViewById(R.id.rb_title);
        itemView.setOnClickListener(this);
    }


    void onBind(Radio itemRadio) {
        rbTitle.setText(itemRadio.getTitle());
        rbTitle.setChecked(itemRadio.getCheck());
    }

    @Override
    public void onClick(View v) {
        if (holderConstants == CustomRecyclerViewAdapter.HolderConstants.BOTTOM_SHEET_ITEM_RADIO_1)
            customListener.onItemClick(getAdapterPosition(), "BOTTOM_SHEET_ITEM_RADIO_1");
        else
            customListener.onItemClick(getAdapterPosition(), "BOTTOM_SHEET_ITEM_RADIO_2");
    }
}
