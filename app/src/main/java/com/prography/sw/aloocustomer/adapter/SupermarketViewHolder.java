package com.prography.sw.aloocustomer.adapter;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.prography.sw.aloocustomer.R;
import com.prography.sw.aloocustomer.model.MainListItem;
import com.prography.sw.aloocustomer.util.AppUtils;

import de.hdodenhof.circleimageview.CircleImageView;

public class SupermarketViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private final CustomListener customListener;
    private final CustomRecyclerViewAdapter.HolderConstants holderConstants;
    private CircleImageView supermarket_image;
    private ImageView item_image;

    public SupermarketViewHolder(@NonNull View itemView, CustomListener customListener, CustomRecyclerViewAdapter.HolderConstants holderConstants) {
        super(itemView);
        this.customListener = customListener;
        this.holderConstants = holderConstants;
        initView();
    }


    public void initView() {
        supermarket_image = itemView.findViewById(R.id.supermarket_image);
        item_image = itemView.findViewById(R.id.item_image);

        itemView.setOnClickListener(this);
    }

    public void onBind(MainListItem item) {
        AppUtils.initGlide(itemView.getContext()).load(item.getLogo()).into(supermarket_image);
        AppUtils.initGlide(itemView.getContext()).load(item.getImage()).into(item_image);
    }

    @Override
    public void onClick(View view) {
        if (holderConstants == CustomRecyclerViewAdapter.HolderConstants.ITEM_SUPERMARKET) {
            customListener.onItemClick(getAdapterPosition(), "ITEM_SUPERMARKET");
        }
    }
}
