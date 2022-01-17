package com.prography.sw.aloodelevery.adapter;

import static com.prography.sw.aloodelevery.util.AppUtils.setTime;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.prography.sw.aloodelevery.R;
import com.prography.sw.aloodelevery.model.HomeFragment;
import com.prography.sw.aloodelevery.model.MyOrder;
import com.prography.sw.aloodelevery.model.MyRides;
import com.prography.sw.aloodelevery.util.AppUtils;

public class MyRidesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private final CustomListener customListener;
    private final CustomRecyclerViewAdapter.HolderConstants holderConstants;

    private ImageView icon;
    private TextView timeDate, owner_name, address_one, address_tow, paice;


    public MyRidesViewHolder(@NonNull View itemView, CustomListener customListener, CustomRecyclerViewAdapter.HolderConstants holderConstants) {
        super(itemView);
        this.customListener = customListener;
        this.holderConstants = holderConstants;
        initView();
    }

    private void initView() {
        icon = itemView.findViewById(R.id.ic_icon);
        timeDate = itemView.findViewById(R.id.tv_date_time);
        owner_name = itemView.findViewById(R.id.tv_owner_meal);
        address_one = itemView.findViewById(R.id.tv_Requester_address);
        address_tow = itemView.findViewById(R.id.my_address);
        paice = itemView.findViewById(R.id.tv_price);
        itemView.setOnClickListener(this);
    }

    public void onBind(MyOrder item) {

        if (item.getVendorType().getId() == 1) {//سوبرماركت
            AppUtils.initGlide(itemView.getContext()).load(R.drawable.ic_supermarket).into(icon);
        } else if (item.getVendorType().getId() == 2) {//صيدلية
            AppUtils.initGlide(itemView.getContext()).load(R.drawable.ic_icon_restorant).into(icon);
        } else if (item.getVendorType().getId() == 3) {//مطعم
            AppUtils.initGlide(itemView.getContext()).load(R.drawable.ic_icon_restorant).into(icon);
        }

        timeDate.setText(setTime((long) item.getDate()*1000,itemView.getContext()));
        owner_name.setText(item.getCustomerName());
        address_one.setText(item.getPlace().getName());
        address_tow.setText(item.getPlace().getLocationName());
        paice.setText(item.getTotal());
    }

    @Override
    public void onClick(View view) {
        customListener.onItemClick(getAdapterPosition(), "MY_RIDES");
    }
}
