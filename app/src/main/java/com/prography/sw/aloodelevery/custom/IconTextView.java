package com.prography.sw.aloodelevery.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.prography.sw.aloodelevery.R;

public class IconTextView extends ConstraintLayout {
    private TextView tvText;
    private ImageView ivImg;
    private String mText;
    private int mIcon;
    private int mStyle;

    public IconTextView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.IconTextView
                , 0, 0);
        mText = typedArray.getString(R.styleable.IconTextView_tv_text);
        mIcon = typedArray.getResourceId(R.styleable.IconTextView_tv_image_rsc, -1);
        mStyle = typedArray.getResourceId(R.styleable.IconTextView_tv_style, -1);

        View view = LayoutInflater.from(context).inflate(R.layout.icon_text_view, this);
        tvText = view.findViewById(R.id.et_text);
        ivImg = view.findViewById(R.id.iv_icon);

        tvText.setTextAppearance(context, mStyle);
        tvText.setText(mText);
        ivImg.setImageResource(mIcon);
    }

    public CharSequence getText() {
        CharSequence text = tvText.getText();
        return text;
    }

    public void setText(String text) {
        tvText.setText(text);
    }


    public void setStatus(boolean enabled) {
        tvText.setEnabled(enabled);
    }

}
