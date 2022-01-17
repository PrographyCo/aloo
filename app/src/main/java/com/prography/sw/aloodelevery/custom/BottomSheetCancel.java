package com.prography.sw.aloodelevery.custom;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.prography.sw.aloodelevery.R;
import com.prography.sw.aloodelevery.adapter.CustomListener;

public class BottomSheetCancel {
    private Context context;
    private BottomSheetDialog mDialog;
    private CustomListener mListener;

    private IconEditText etMessage;
    private Button btn;
    private ProgressBar loading;

    public BottomSheetCancel(Context context, CustomListener mListener) {
        this.context = context;
        this.mListener = mListener;
        createDialog();
    }

    private void createDialog() {
        View root = LayoutInflater.from(context).inflate(R.layout.bottom_sheet_cancel, null);
        mDialog = new BottomSheetDialog(context);

        mDialog.setContentView(root);
        initViews(root);
    }

    private void initViews(View v) {
        btn = v.findViewById(R.id.btn);
        loading = v.findViewById(R.id.loading);
        etMessage = v.findViewById(R.id.et_some_notes);

        setListeners();
    }


    private void setListeners() {
        btn.setOnClickListener(v -> {
            mListener.onItemClick(0, "BottomSheetRate");
        });
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
            btn.setVisibility(View.VISIBLE);
        } else {
            loading.setVisibility(View.VISIBLE);
            btn.setVisibility(View.GONE);
        }
    }

    public String getMessage() {
        return String.valueOf(etMessage.getmyText());
    }
}