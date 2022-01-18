package com.prography.sw.alooproduct.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.prography.sw.alooproduct.R;
import com.prography.sw.alooproduct.custom.BottomSheetDone;
import com.prography.sw.alooproduct.custom.BottomSheetListText;
import com.prography.sw.alooproduct.custom.DialogTextTwoButtons;
import com.prography.sw.alooproduct.databinding.ActivityAddProductBinding;

import java.util.ArrayList;
import java.util.List;

public class AddProductActivity extends AppCompatActivity {

    private ActivityAddProductBinding binding;
    private BottomSheetDone bottomSheetDone;
    private BottomSheetListText sectionBottomSheetListText;
    private List<String> sectionList = new ArrayList<>();
    private DialogTextTwoButtons dialogDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityAddProductBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        generateFakeData();
        initBottomSheet();
        getIntentData();
        setListeners();
    }

    private void generateFakeData() {

        sectionList.add("All");
        sectionList.add("Skin Care");
        sectionList.add("Hair Care");
        sectionList.add("Discounts");
        sectionList.add("Skin Care");
        sectionList.add("Hair Care");
        sectionList.add("Discounts");
        sectionList.add("Skin Care");
        sectionList.add("Hair Care");
        sectionList.add("Discounts");
    }

    private void initBottomSheet() {
        bottomSheetDone = new BottomSheetDone(this, (position, action) -> {
            Toast.makeText(getApplication(), "ok", Toast.LENGTH_SHORT).show();
            finish();
        }, R.drawable.ic_done, getString(R.string.product_deleted), "", getString(R.string.ok));

        dialogDelete
                = new DialogTextTwoButtons(this,
                (position, action) -> {
                    if (action.equals("BUTTON1")) {
                        Toast.makeText(this, "yes : " + action, Toast.LENGTH_SHORT).show();
                        finish();
                    }else if (action.equals("BUTTON2"))
                        Toast.makeText(this, "no : " + action, Toast.LENGTH_SHORT).show();
                    dialogDelete.dismiss();
                },
                getString(R.string.delete_text), getString(R.string.yes), getString(R.string.no));

        sectionBottomSheetListText = new BottomSheetListText(this, (position, action) -> {
            sectionBottomSheetListText.dismissDialog();
            binding.tvSection.setText(sectionList.get(position));
        });
        sectionBottomSheetListText.setList(getString(R.string.section), sectionList);
    }

    private void getIntentData() {
        if (getIntent().getExtras() != null)
            binding.btnDelete.setVisibility(View.VISIBLE);
        else
            binding.btnDelete.setVisibility(View.VISIBLE);
    }

    private void setListeners() {
        binding.backImage.setOnClickListener(view -> {
            this.finish();
        });
        binding.btn.setOnClickListener(view -> {
            bottomSheetDone.showDialog();
        });
        binding.btnDelete.setOnClickListener(v -> {
            dialogDelete.show();
        });
        binding.frameSection.setOnClickListener(view -> {
            sectionBottomSheetListText.showDialog();
        });
    }
}