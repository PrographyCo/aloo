package com.prography.sw.aloocustomer.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.Editable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.prography.sw.aloocustomer.R;


public class IconEditText extends ConstraintLayout {
    private EditText ed_Text;
    private ImageView imageView;
    private String mText;
    private String hint;
    private int mIcon;
    private int inputTypes;
    private int mStyle;


    public IconEditText(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.IconEditText
                , 0, 0);
        mText = typedArray.getString(R.styleable.IconEditText_new_text);
        mIcon = typedArray.getResourceId(R.styleable.IconEditText_new_image_rsc, -1);
        mStyle = typedArray.getResourceId(R.styleable.IconEditText_style, -1);
        inputTypes = typedArray.getInt(R.styleable.IconEditText_android_inputType, 0);
        hint = typedArray.getString(R.styleable.IconEditText_android_hint);

        View view = LayoutInflater.from(context).inflate(R.layout.icon_edit_text, this);
        ed_Text = view.findViewById(R.id.et_text);
        imageView = view.findViewById(R.id.iv_icon);

        ed_Text.setHint(mText);
        ed_Text.setInputType(inputTypes);
        ed_Text.setHint(hint);
        imageView.setImageResource(mIcon);
        ed_Text.setTextAppearance(context,mStyle);
    }


    public Editable getmyText() {
        Editable textmy = ed_Text.getText();
        //Log.e("ttttt",textmy.toString());
        return textmy;
    }

    public void setmyText(String editable) {
        ed_Text.setText(editable);
    }


    public void setmyststus(boolean editable) {
        ed_Text.setEnabled(editable);
    }

}




