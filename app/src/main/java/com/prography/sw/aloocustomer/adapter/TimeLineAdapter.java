package com.prography.sw.aloocustomer.adapter;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.github.vipulasri.timelineview.TimelineView;
import com.prography.sw.aloocustomer.R;
import com.prography.sw.aloocustomer.model.TimeLine;

import java.util.List;

public class TimeLineAdapter extends RecyclerView.Adapter<TimeLineAdapter.MyHolder> {

    private List<TimeLine> timeLines;

    public TimeLineAdapter(List<TimeLine> timeLines) {
        this.timeLines = timeLines;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//
        View view = View.inflate(parent.getContext(), R.layout.item_time_line, null);
        return new MyHolder(view, viewType);
//        return new MyHolder(LayoutInflater.from(parent.getContext())
//                .inflate(R.layout.item_time_line, parent, false), viewType);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        TimeLine timeLine = timeLines.get(position);
        switch (timeLine.getStatus()) {
            case ACTIVE:
                setMarker(holder, R.drawable.time_line_marker_done, R.color.white);
                break;
            case INACTIVE:
                setMarker(holder, R.drawable.time_line_marker_review, R.color.purple_700);
                break;
        }
        holder.tv_text.setText(timeLine.getMessage());
        int count = position + 1;
        holder.tv_time_line_number.setText(count + "");

    }


    @Override
    public int getItemCount() {
        return timeLines.size();
    }

    @Override
    public int getItemViewType(int position) {
        return TimelineView.getTimeLineViewType(position, getItemCount());
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void setMarker(MyHolder holder, int drawableResId, int colorFilter) {
        holder.mTimelineView.setMarker(holder.itemView.getContext().getDrawable(drawableResId));
        holder.tv_time_line_number.setTextColor(holder.itemView.getContext().getColor(colorFilter));
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        public TimelineView mTimelineView;
        public TextView tv_text, tv_time_line_number;

        public MyHolder(@NonNull View itemView, int viewType) {
            super(itemView);
            mTimelineView = itemView.findViewById(R.id.timeline);
            tv_text = itemView.findViewById(R.id.tv_text_time_line);
            tv_time_line_number = itemView.findViewById(R.id.time_line_number);
            mTimelineView.initLine(viewType);
        }
    }
}
