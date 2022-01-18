package com.prography.sw.alooproduct.adapter;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.prography.sw.alooproduct.R;
import com.prography.sw.alooproduct.model.Section;

public class SectionViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private final CustomListener customListener;
    private final CustomRecyclerViewAdapter.HolderConstants holderConstants;
    private TextView tvNo;
    private EditText etSectionName;

    public SectionViewHolder(View itemView, CustomListener customListener, CustomRecyclerViewAdapter.HolderConstants holderConstants) {
        super(itemView);
        this.customListener = customListener;
        this.holderConstants = holderConstants;
        initViews();
    }

    private void initViews() {
        tvNo = itemView.findViewById(R.id.tv_no);
        etSectionName = itemView.findViewById(R.id.et_section_name);
    }


    void onBind(Section section) {
        tvNo.setText(String.valueOf(getAdapterPosition() + 1));
        etSectionName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                section.setName(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        customListener.onItemClick(getAdapterPosition(), etSectionName.getText().toString());
    }
}