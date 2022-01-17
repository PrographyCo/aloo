package com.prography.sw.aloocustomer.custom;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.prography.sw.aloocustomer.R;
import com.prography.sw.aloocustomer.adapter.CustomListener;
import com.prography.sw.aloocustomer.adapter.CustomRecyclerViewAdapter;
import com.prography.sw.aloocustomer.model.Radio;

import java.util.List;

public class BottomSheetTwoList {

    private Context context;
    private BottomSheetDialog mDialog;
    private CustomListener customListener;
    private CustomRecyclerViewAdapter firstAdapter;
    private CustomRecyclerViewAdapter secondAdapter;
    private TextView tvFirstTitle, tvSecondTitle;
    private RecyclerView rvFirst, rvSecond;

    public BottomSheetTwoList(Context context, CustomListener customListener) {
        this.context = context;
        this.customListener = customListener;
        createDialog();
    }

    private void createDialog() {
        View root = LayoutInflater.from(context).inflate(R.layout.bottom_sheet_two_list, null);
        mDialog = new BottomSheetDialog(context);

        mDialog.setContentView(root);
        initViews(root);
    }

    private void initViews(View v) {
        tvFirstTitle = v.findViewById(R.id.tv_first_title);
        tvSecondTitle = v.findViewById(R.id.tv_second_title);
        rvFirst = v.findViewById(R.id.rv_first);
        rvSecond = v.findViewById(R.id.rv_second);
    }

    public void setFirstList(String title, List<Radio> titles) {
        tvFirstTitle.setText(title);
        firstAdapter = new CustomRecyclerViewAdapter(customListener, CustomRecyclerViewAdapter.HolderConstants.BOTTOM_SHEET_ITEM_RADIO_1);
        firstAdapter.setRadios(titles);
        rvFirst.setAdapter(firstAdapter);
        rvFirst.setNestedScrollingEnabled(false);
    }

    public void setSecondList(String title, List<Radio> titles) {
        tvSecondTitle.setText(title);
        secondAdapter = new CustomRecyclerViewAdapter(customListener, CustomRecyclerViewAdapter.HolderConstants.BOTTOM_SHEET_ITEM_RADIO_2);
        secondAdapter.setRadios(titles);
        rvSecond.setAdapter(secondAdapter);
    }

    public void showDialog() {
        mDialog.show();
    }

    public void dismissDialog() {
        mDialog.dismiss();
    }

    public void notifyFirstList() {
        firstAdapter.notifyDataSetChanged();
    }

    public void notifySecondList() {
        secondAdapter.notifyDataSetChanged();
    }
}
