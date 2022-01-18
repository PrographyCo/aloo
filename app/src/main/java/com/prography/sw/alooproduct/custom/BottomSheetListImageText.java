package com.prography.sw.alooproduct.custom;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.prography.sw.alooproduct.R;
import com.prography.sw.alooproduct.adapter.CustomListener;
import com.prography.sw.alooproduct.adapter.CustomRecyclerViewAdapter;
import com.prography.sw.alooproduct.model.ImageText;

import java.util.List;

public class BottomSheetListImageText {

    private Context context;
    private BottomSheetDialog mDialog;
    private CustomListener customListener;
    private CustomRecyclerViewAdapter adapter;
    private RecyclerView rvBtmSheet;

    public BottomSheetListImageText(Context context, CustomListener customListener) {
        this.context = context;
        this.customListener = customListener;
        createDialog();
    }

    private void createDialog() {
        View root = LayoutInflater.from(context).inflate(R.layout.bottom_sheet_list_img_text, null);
        mDialog = new BottomSheetDialog(context);

        mDialog.setContentView(root);
        initViews(root);
    }

    private void initViews(View v) {
        rvBtmSheet = v.findViewById(R.id.rv_btm_sheet);
    }

    public void setList(List<ImageText> imageTexts) {
        adapter = new CustomRecyclerViewAdapter(customListener, CustomRecyclerViewAdapter.HolderConstants.BOTTOM_SHEET_ITEM_IMG_TEXT);
        adapter.setItemImageText(imageTexts);
        rvBtmSheet.setAdapter(adapter);
    }

    public void showDialog() {
        mDialog.show();
    }

    public void dismissDialog() {
        mDialog.dismiss();
    }

}
