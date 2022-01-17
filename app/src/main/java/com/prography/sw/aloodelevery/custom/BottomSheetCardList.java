package com.prography.sw.aloodelevery.custom;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.prography.sw.aloodelevery.R;
import com.prography.sw.aloodelevery.adapter.CustomListener;
import com.prography.sw.aloodelevery.adapter.CustomRecyclerViewAdapter;
import com.prography.sw.aloodelevery.model.Radio;

import java.util.List;

public class BottomSheetCardList {

    private Context context;
    private BottomSheetDialog mDialog;
    private CustomListener customListener;
    private Boolean containsList;

    private CustomRecyclerViewAdapter adapter;
    private TextView tvTitle;
    private RecyclerView rvRadio;

    private LinearLayout highToLow, lowToHigh;

    public String lastClicked;

    public BottomSheetCardList(Context context, CustomListener customListener, Boolean containsList) {
        this.context = context;
        this.customListener = customListener;
        this.containsList = containsList;
        createDialog();
    }

    private void createDialog() {
        View root = LayoutInflater.from(context).inflate(R.layout.bottom_sheet_card_list, null);
        mDialog = new BottomSheetDialog(context);

        mDialog.setContentView(root);
        initViews(root);
    }

    private void initViews(View v) {
        tvTitle = v.findViewById(R.id.tv_title);
        rvRadio = v.findViewById(R.id.rv_radio);
        highToLow = v.findViewById(R.id.high_to_low);
        lowToHigh = v.findViewById(R.id.low_to_high);

        if (containsList) {
            v.findViewById(R.id.view).setVisibility(View.VISIBLE);
            tvTitle.setVisibility(View.VISIBLE);
            rvRadio.setVisibility(View.VISIBLE);
        } else {
            v.findViewById(R.id.view).setVisibility(View.GONE);
            tvTitle.setVisibility(View.GONE);
            rvRadio.setVisibility(View.GONE);
        }

        setListeners();
    }

    private void setListeners() {
        highToLow.setOnClickListener(v -> {
            lastClicked = "highToLow";
            customListener.onItemClick(-1, "highToLow");
        });
        lowToHigh.setOnClickListener(v -> {
            lastClicked = "lowToHigh";
            customListener.onItemClick(-1, "lowToHigh");
        });
    }

    public void setFirstList(String title, List<Radio> titles) {
        tvTitle.setText(title);
        adapter = new CustomRecyclerViewAdapter(customListener, CustomRecyclerViewAdapter.HolderConstants.BOTTOM_SHEET_ITEM_RADIO_1);
        adapter.setRadios(titles);
        rvRadio.setAdapter(adapter);
    }

    public void showDialog() {
        mDialog.show();
    }

    public void dismissDialog() {
        mDialog.dismiss();
    }

    public void notifyFirstList() {
        adapter.notifyDataSetChanged();
    }
}
