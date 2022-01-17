package com.prography.sw.aloocustomer.custom;

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
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.prography.sw.aloocustomer.R;
import com.prography.sw.aloocustomer.adapter.CustomListener;

public class DialogTextButton extends Dialog{
    private Context context;
    private CustomListener mListener;

    private String text1, btnText;
    private TextView tvText1;
    private Button btn;

    public DialogTextButton(Context context, CustomListener mListener, String text1, String btnText) {
        super(context, R.style.Theme_Dialog);
        this.context = context;
        this.mListener = mListener;
        this.text1 = text1;
        this.btnText = btnText;

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_text_button);
        setCancelable(false);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        this.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        initViews();
    }

    private void initViews() {
        tvText1 = findViewById(R.id.tv_tex1);
        btn = findViewById(R.id.btn);

        tvText1.setText(text1);
        btn.setText(btnText);

        setListeners();
    }

    private void setListeners() {
        btn.setOnClickListener(v -> {
            mListener.onItemClick(0,"dialog");
        });
    }

}
