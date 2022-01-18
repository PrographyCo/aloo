package com.prography.sw.alooproduct.adapter;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.prography.sw.alooproduct.R;
import com.prography.sw.alooproduct.model.Notification;

public class NotificationViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private final CustomListener customListener;
    private final CustomRecyclerViewAdapter.HolderConstants holderConstants;
    private TextView tvTitle, tvText, tvNumber, tvTime;

    public NotificationViewHolder(View itemView, CustomListener customListener, CustomRecyclerViewAdapter.HolderConstants holderConstants) {
        super(itemView);
        this.customListener = customListener;
        this.holderConstants = holderConstants;
        initViews();
    }

    private void initViews() {
        tvTitle = itemView.findViewById(R.id.tv_title);
        tvText = itemView.findViewById(R.id.tv_text);
        tvNumber = itemView.findViewById(R.id.tv_number);
        tvTime = itemView.findViewById(R.id.tv_time);

        itemView.setOnClickListener(this);
    }


    void onBind(Notification notification) {
        tvTitle.setText(notification.getTitle());
        tvText.setText(notification.getBody());
    }

    @Override
    public void onClick(View v) {
        customListener.onItemClick(getAdapterPosition(), String.valueOf(getAdapterPosition()));
    }
}