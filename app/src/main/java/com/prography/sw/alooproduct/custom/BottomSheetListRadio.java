package com.prography.sw.alooproduct.custom;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.prography.sw.alooproduct.R;
import com.prography.sw.alooproduct.adapter.CustomListener;
import com.prography.sw.alooproduct.adapter.CustomRecyclerViewAdapter;
import com.prography.sw.alooproduct.model.Radio;

import java.util.List;

public class BottomSheetListRadio {

    private Context context;
    private BottomSheetDialog mDialog;
    private CustomListener customListener;
    private CustomRecyclerViewAdapter adapter;
    private TextView tvTitle;
    private RecyclerView rvRadio;

    public BottomSheetListRadio(Context context, CustomListener customListener) {
        this.context = context;
        this.customListener = customListener;
        createDialog();
    }

    private void createDialog() {
        View root = LayoutInflater.from(context).inflate(R.layout.bottom_sheet_list_radio, null);
        mDialog = new BottomSheetDialog(context);

        mDialog.setContentView(root);
        initViews(root);
    }

    private void initViews(View v) {
        tvTitle = v.findViewById(R.id.tv_title);
        rvRadio = v.findViewById(R.id.rv_radio);
    }

    public void setList(String title, List<Radio> titles) {
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

    public void notifyList() {
        adapter.notifyDataSetChanged();
    }
}
