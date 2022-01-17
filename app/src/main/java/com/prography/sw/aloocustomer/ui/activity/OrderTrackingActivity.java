package com.prography.sw.aloocustomer.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.prography.sw.aloocustomer.R;
import com.prography.sw.aloocustomer.adapter.TimeLineAdapter;
import com.prography.sw.aloocustomer.databinding.ActivityOrderTrackingBinding;
import com.prography.sw.aloocustomer.model.OrderStatus;
import com.prography.sw.aloocustomer.model.TimeLine;

import java.util.ArrayList;
import java.util.List;

public class OrderTrackingActivity extends AppCompatActivity implements View.OnClickListener {
    private ActivityOrderTrackingBinding binding;
    private TimeLineAdapter timeLineAdapter;
    private List<TimeLine> items = new ArrayList<>();
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOrderTrackingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getIntentData();
        initViews();
        initList();
        initAdapter();
        firebase();
    }

    private void getIntentData() {
        id = getIntent().getIntExtra("id", -1);
        String text = getString(R.string.order_number) + " " + id;
        binding.tvTex2.setText(text);
    }

    private void firebase() {
        binding.loading.setVisibility(View.VISIBLE);
        binding.rvTimeLine.setVisibility(View.GONE);
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("orders/" + id + "/status");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Integer status = dataSnapshot.getValue(Integer.class);
                trackStatus(status);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), "Tracking Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void trackStatus(int status) {
        binding.loading.setVisibility(View.GONE);
        binding.rvTimeLine.setVisibility(View.VISIBLE);
        if (status <= 6) {
            if (status == 6)
                binding.btnRate.setVisibility(View.VISIBLE);
            for (int i = 0; i < items.size(); i++) {
                if (i + 1 <= status) {
                    items.get(i).setStatus(OrderStatus.ACTIVE);
                    timeLineAdapter.notifyItemChanged(i);
                }
            }
        } else
            Toast.makeText(this, getString(R.string.something_went_wrong_text), Toast.LENGTH_SHORT).show();
    }

    private void initViews() {
        binding.btnRate.setOnClickListener(this);
        binding.backImage.setOnClickListener(this);
    }

    private void initList() {
        items.add(new TimeLine(getString(R.string.order_tracking_1), OrderStatus.ACTIVE));
        items.add(new TimeLine(getString(R.string.order_tracking_2), OrderStatus.INACTIVE));
        items.add(new TimeLine(getString(R.string.order_tracking_3), OrderStatus.INACTIVE));
        items.add(new TimeLine(getString(R.string.order_tracking_4), OrderStatus.INACTIVE));
        items.add(new TimeLine(getString(R.string.order_tracking_5), OrderStatus.INACTIVE));
        items.add(new TimeLine(getString(R.string.order_tracking_6), OrderStatus.INACTIVE));
    }

    private void initAdapter() {
        timeLineAdapter = new TimeLineAdapter(items);
        binding.rvTimeLine.setAdapter(timeLineAdapter);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_rate:
                Intent intent = new Intent(this, DriverRatingActivity.class);
                intent.putExtra("id", id);
                startActivity(intent);
                break;
            case R.id.back_image:
                this.finish();
                break;
        }
    }
}