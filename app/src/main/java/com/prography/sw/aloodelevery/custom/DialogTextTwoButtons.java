package com.prography.sw.aloodelevery.custom;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.prography.sw.aloodelevery.R;
import com.prography.sw.aloodelevery.adapter.CustomListener;

public class DialogTextTwoButtons extends Dialog {
    private Context context;
    private CustomListener mListener;

    private String text1, btnText1, btnText2;
    private TextView tvText1;
    private Button btn1, btn2;
    private ProgressBar loading;
    private LinearLayout layoutButtons;

    public DialogTextTwoButtons(Context context, CustomListener mListener, String text1, String btnText1, String btnText2) {
        super(context, R.style.Theme_Dialog);
        this.context = context;
        this.mListener = mListener;
        this.text1 = text1;
        this.btnText1 = btnText1;
        this.btnText2 = btnText2;

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_text_two_buttons);
        setCancelable(false);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        this.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        initViews();
    }

    private void initViews() {
        tvText1 = findViewById(R.id.tv_tex1);
        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        loading = findViewById(R.id.loading);
        layoutButtons = findViewById(R.id.layout_buttons);

        tvText1.setText(text1);
        btn1.setText(btnText1);
        btn2.setText(btnText2);

        setListeners();
    }

    private void setListeners() {
        btn1.setOnClickListener(v -> {
            mListener.onItemClick(0, "BUTTON1");
        });
        btn2.setOnClickListener(v -> {
            mListener.onItemClick(0, "BUTTON2");
        });
    }

    public void showParent(int visibility) {
        if (visibility == View.VISIBLE) {
            loading.setVisibility(View.GONE);
            layoutButtons.setVisibility(View.VISIBLE);
        } else {
            loading.setVisibility(View.VISIBLE);
            layoutButtons.setVisibility(View.GONE);
        }
    }

}
