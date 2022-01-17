package com.prography.sw.aloodelevery.custom;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.prography.sw.aloodelevery.R;
import com.prography.sw.aloodelevery.adapter.CustomListener;

public class BottomSheetRate {
    private Context context;
    private BottomSheetDialog mDialog;
    private CustomListener mListener;

    private String name;
    private TextView tvName;
    private IconEditText etMessage;
    private Button btn;
    private ProgressBar loading;
    private RatingBar ratingBar;

    public BottomSheetRate(Context context, CustomListener mListener, String name) {
        this.context = context;
        this.mListener = mListener;
        this.name = name;
        createDialog();
    }

    private void createDialog() {
        View root = LayoutInflater.from(context).inflate(R.layout.bottom_sheet_rate, null);
        mDialog = new BottomSheetDialog(context);

        mDialog.setContentView(root);
        initViews(root);


    }

    private void initViews(View v) {
        tvName = v.findViewById(R.id.tv_client_name);
        btn = v.findViewById(R.id.btn);
        loading = v.findViewById(R.id.loading);
        etMessage = v.findViewById(R.id.et_some_notes);
        ratingBar = v.findViewById(R.id.driver_rating);

        tvName.setText(name);

        setListeners();
    }


    private void setListeners() {
        btn.setOnClickListener(v -> {
            mListener.onItemClick(0, "BottomSheetRate");
        });
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                Log.d("BOTTOMSHEETRATE", String.valueOf((int) v));
                ratingBar.setRating(v);

            }
        });
        ratingBar.setOnClickListener(view -> {
            Log.d("BOTTOMSHEETRATE", "onRatingChanged: ");
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

    public String getRate() {
        return String.valueOf((int) ratingBar.getRating());
    }

    public String getMessage() {
        return String.valueOf(etMessage.getmyText());
    }
}
