package com.prography.sw.aloodelevery.custom;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.prography.sw.aloodelevery.R;
import com.prography.sw.aloodelevery.adapter.CustomListener;

public class BottomSheetDone {
    private Context context;
    private BottomSheetDialog mDialog;
    private CustomListener mListener;

    private int icon;
    private String text1, text2, btnText;
    private ImageView ivIcon;
    private TextView tvText1, tvText2;
    private Button btn;

    public BottomSheetDone(Context context, CustomListener mListener, int icon, String text1, String text2, String btnText) {
        this.context = context;
        this.mListener = mListener;
        this.icon = icon;
        this.text1 = text1;
        this.text2 = text2;
        this.btnText = btnText;
        createDialog();
    }

    private void createDialog() {
        View root = LayoutInflater.from(context).inflate(R.layout.bottom_sheet_done, null);
        mDialog = new BottomSheetDialog(context);

        mDialog.setContentView(root);
        initViews(root);
    }

    private void initViews(View v) {
        ivIcon = v.findViewById(R.id.iv_icon);
        tvText1 = v.findViewById(R.id.tv_tex1);
        tvText2 = v.findViewById(R.id.tv_tex2);
        btn = v.findViewById(R.id.btn);

        ivIcon.setImageResource(icon);
        tvText1.setText(text1);
        tvText2.setText(text2);
        btn.setText(btnText);

        setListeners();
    }

    private void setListeners() {
        btn.setOnClickListener(v -> {
            mListener.onItemClick(0,"BottomSheetDone");
        });
    }

    public void showDialog() {
        mDialog.show();
    }

    public void dismissDialog() {
        mDialog.dismiss();
    }

}
