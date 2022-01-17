package com.prography.sw.aloocustomer.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.prography.sw.aloocustomer.R;
import com.prography.sw.aloocustomer.model.Question;

public class QuestionViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private final CustomListener customListener;
    private final CustomRecyclerViewAdapter.HolderConstants holderConstants;
    private TextView tvQuestion, tvAnswer;
    private LinearLayout questionLayout;
    private ImageView ivArrow;

    public QuestionViewHolder(View itemView, CustomListener customListener, CustomRecyclerViewAdapter.HolderConstants holderConstants) {
        super(itemView);
        this.customListener = customListener;
        this.holderConstants = holderConstants;
        initViews();
    }

    private void initViews() {
        tvQuestion = itemView.findViewById(R.id.tv_question);
        tvAnswer = itemView.findViewById(R.id.tv_answer);
        questionLayout = itemView.findViewById(R.id.layout_question);
        ivArrow = itemView.findViewById(R.id.iv_arrow);

        questionLayout.setOnClickListener(this);
    }


    void onBind(Question question) {
        tvQuestion.setText(question.getQuestion());
        tvAnswer.setText(question.getAnswer());
        if (!question.getAnswerShown()) {
            ivArrow.setImageResource(R.drawable.ic_arrow_down);
            tvAnswer.setVisibility(View.GONE);
        } else {
            ivArrow.setImageResource(R.drawable.ic_arrow_up);
            tvAnswer.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View v) {
        customListener.onItemClick(getAdapterPosition(), String.valueOf(getAdapterPosition()));
    }
}
