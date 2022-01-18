package com.prography.sw.alooproduct.adapter;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.prography.sw.alooproduct.R;
import com.prography.sw.alooproduct.model.Transaction;

public class TransactionViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private final CustomListener customListener;
    private final CustomRecyclerViewAdapter.HolderConstants holderConstants;
    private TextView tvTime, tvTransNo, tvName, tvPrice, tvStatus, tvReason;

    public TransactionViewHolder(View itemView, CustomListener customListener, CustomRecyclerViewAdapter.HolderConstants holderConstants) {
        super(itemView);
        this.customListener = customListener;
        this.holderConstants = holderConstants;
        initViews();
    }

    private void initViews() {
        tvTime = itemView.findViewById(R.id.tv_time);
        tvTransNo = itemView.findViewById(R.id.tv_trans_no);
        tvName = itemView.findViewById(R.id.tv_name);
        tvPrice = itemView.findViewById(R.id.tv_price);
        tvStatus = itemView.findViewById(R.id.tv_status);
        tvReason = itemView.findViewById(R.id.tv_reason);

        itemView.setOnClickListener(this);
    }


    void onBind(Transaction transaction) {
        tvTime.setText(transaction.getDate());
        tvTransNo.setText(String.valueOf(transaction.getId()));
        tvName.setText(transaction.getMessage());
        if (transaction.getPrice() >= 0)
            tvPrice.setTextColor(itemView.getResources().getColor(R.color.green));
        else
            tvPrice.setTextColor(itemView.getResources().getColor(R.color.text_color_red));
        String price = transaction.getPrice() + " " + itemView.getResources().getString(R.string.currency);
        tvPrice.setText(price);
        tvStatus.setText(transaction.getOrderType());
        tvReason.setText(transaction.getReason());
    }

    @Override
    public void onClick(View v) {
        customListener.onItemClick(getAdapterPosition(), String.valueOf(getAdapterPosition()));
    }
}
