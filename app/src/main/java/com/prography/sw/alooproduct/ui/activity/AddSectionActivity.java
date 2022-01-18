package com.prography.sw.alooproduct.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Toast;

import com.prography.sw.alooproduct.R;
import com.prography.sw.alooproduct.adapter.CustomListener;
import com.prography.sw.alooproduct.adapter.CustomRecyclerViewAdapter;
import com.prography.sw.alooproduct.databinding.ActivityAddSectionBinding;
import com.prography.sw.alooproduct.model.Section;

import java.util.ArrayList;
import java.util.List;

public class AddSectionActivity extends AppCompatActivity implements CustomListener {

    private ActivityAddSectionBinding binding;
    private List<Section> sectionsList = new ArrayList<>();
    private CustomRecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityAddSectionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        generateFakeData();
        initAdapter();
        setListeners();
    }

    private void generateFakeData() {
        sectionsList.add(new Section(""));
    }

    private void initAdapter() {
        adapter = new CustomRecyclerViewAdapter(this, CustomRecyclerViewAdapter.HolderConstants.ADD_SECTION);
        adapter.setSections(sectionsList);
        binding.rvSection.setAdapter(adapter);
    }

    private void setListeners() {
        binding.backImage.setOnClickListener(view -> {
            this.finish();
        });
        binding.tvAdd.setOnClickListener(view -> {
            sectionsList.add(new Section(""));
            adapter.notifyDataSetChanged();
        });
        binding.btn.setOnClickListener(view -> {
            Toast.makeText(getApplication(), "save:" + sectionsList.get(0).getName(), Toast.LENGTH_SHORT).show();
            this.finish();
        });
    }

    @Override
    public void onItemClick(int position, String action) {
        Toast.makeText(getApplication(), "section " + action, Toast.LENGTH_SHORT).show();
    }
}