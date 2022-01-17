package com.prography.sw.aloocustomer.custom;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.prography.sw.aloocustomer.R;
import com.prography.sw.aloocustomer.adapter.CustomListener;
import com.prography.sw.aloocustomer.adapter.CustomRecyclerViewAdapter;

import java.util.List;

public class BottomSheetListText {

    private Context context;
    private BottomSheetDialog mDialog;
    private CustomListener customListener;
    private CustomRecyclerViewAdapter adapter;
    private TextView tvTitle;
    private RecyclerView rvBtmSheet;
    private ProgressBar loading;

    public BottomSheetListText(Context context, CustomListener customListener) {
        this.context = context;
        this.customListener = customListener;
        createDialog();
    }

    private void createDialog() {
        View root = LayoutInflater.from(context).inflate(R.layout.bottom_sheet_list_text, null);
        mDialog = new BottomSheetDialog(context);

        mDialog.setContentView(root);
        initViews(root);
    }

    private void initViews(View v) {
        tvTitle = v.findViewById(R.id.tv_title);
        rvBtmSheet = v.findViewById(R.id.rv_btm_sheet);
        loading = v.findViewById(R.id.loading);
    }

    public void setList(String title, List<String> titles) {
        if (title == null || title.equals(""))
            tvTitle.setVisibility(View.GONE);
        else
            tvTitle.setVisibility(View.VISIBLE);
        tvTitle.setText(title);
        adapter = new CustomRecyclerViewAdapter(customListener, CustomRecyclerViewAdapter.HolderConstants.BOTTOM_SHEET_ITEM_TEXT);
        adapter.setTitles(titles);
        rvBtmSheet.setAdapter(adapter);
    }

    public void showDialog() {
        mDialog.show();
    }

    public void dismissDialog() {
        mDialog.dismiss();
    }

    public void showParent(int visibility) {
        if (visibility == View.VISIBLE) {
            loading.setVisibility(View.GONE);
            rvBtmSheet.setVisibility(View.VISIBLE);
        } else {
            loading.setVisibility(View.VISIBLE);
            rvBtmSheet.setVisibility(View.GONE);
        }
    }

}
