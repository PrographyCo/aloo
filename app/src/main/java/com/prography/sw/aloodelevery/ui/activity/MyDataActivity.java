package com.prography.sw.aloodelevery.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.prography.sw.aloodelevery.R;
import com.prography.sw.aloodelevery.custom.BottomSheetListRadio;
import com.prography.sw.aloodelevery.data.response.GeneralResponse;
import com.prography.sw.aloodelevery.databinding.ActivityMyDataBinding;
import com.prography.sw.aloodelevery.model.Radio;
import com.prography.sw.aloodelevery.network.AuthResource;
import com.prography.sw.aloodelevery.util.AppUtils;
import com.prography.sw.aloodelevery.viewmodel.ProfileViewModel;

import java.util.ArrayList;
import java.util.List;

public class MyDataActivity extends AppCompatActivity {

    private ActivityMyDataBinding binding;
    private BottomSheetListRadio showBottomSheetListText;
    private List<Radio> showList = new ArrayList<>();
    private ProfileViewModel profileViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMyDataBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        generateFakeData();
        initBottomSheet();
        setListeners();
        initViewModel();
        startMyDataProcess();
    }

    private void initViewModel() {
        profileViewModel = new ViewModelProvider(this,
                new ViewModelProvider.AndroidViewModelFactory(getApplication())).get(ProfileViewModel.class);

    }

    private void startMyDataProcess() {
        profileViewModel.getMyData().observe(this, myDataResource -> {
            switch (myDataResource.status) {
                case LOADING:
                    showParent(View.GONE);
                    break;
                case AUTHENTICATED:
                    upDateUi(myDataResource.data);
                    break;
                case ERROR:
                    showFailed(myDataResource);
                    break;
            }
        });
    }

    private void showParent(int visibility) {
        if (visibility == View.VISIBLE) {
            binding.loading.setVisibility(View.GONE);
            binding.myData.setVisibility(View.VISIBLE);
        } else {
            binding.loading.setVisibility(View.VISIBLE);
            binding.myData.setVisibility(View.GONE);
        }
    }

    private void upDateUi(GeneralResponse response) {
        showParent(View.VISIBLE);
        AppUtils.initGlide(this).load(response.getMyData().getImg()).into(binding.driverImage);
        binding.driverName.setText(response.getMyData().getName());
        binding.driverRating.setRating(response.getMyData().getRate());
        binding.tvRidesNo.setText(String.valueOf(response.getMyData().getRides()));
        binding.tvHoursNo.setText(String.valueOf(response.getMyData().getHours()));
        binding.tvIncomeAvg.setText(String.valueOf(response.getMyData().getIncome()));
        binding.tvAppRatio.setText(String.valueOf(response.getMyData().getExchanged()));
        binding.tvTransferAmount.setText(String.valueOf(response.getMyData().getExchanged()));
    }

    private void showFailed(AuthResource<GeneralResponse> errorResponse) {
        switch (errorResponse.errorCode) {
            case 0:
                Toast.makeText(this, getString(R.string.check_internet_connection_text), Toast.LENGTH_SHORT).show();
                break;
            case 500:
                Toast.makeText(this, getString(R.string.something_went_wrong_text), Toast.LENGTH_SHORT).show();
                break;
            case 401:
                AppUtils.unauthorized(this);
                break;
            default:
                Toast.makeText(this, errorResponse.message, Toast.LENGTH_SHORT).show();
                break;
        }
        showParent(View.VISIBLE);
    }

    private void generateFakeData() {
        showList.add(new Radio(getString(R.string.all), true));
        showList.add(new Radio(getString(R.string.today), false));
        showList.add(new Radio(getString(R.string.week), false));
        showList.add(new Radio(getString(R.string.month), false));
        showList.add(new Radio(getString(R.string.year), false));
    }

    private void initBottomSheet() {
        showBottomSheetListText = new BottomSheetListRadio(this, (position, action) -> {
            for (int i = 0; i < showList.size(); i++)
                showList.get(i).setCheck(i == position);
            showBottomSheetListText.notifyList();
            showBottomSheetListText.dismissDialog();
            binding.tvShowAccording.setText(showList.get(position).getTitle());
        });
        showBottomSheetListText.setList(getString(R.string.show_according), showList);
    }

    private void setListeners() {
        binding.backImage.setOnClickListener(view -> {
            this.finish();
        });
        binding.tvShowAccording.setOnClickListener(view -> {
            showBottomSheetListText.showDialog();
        });
    }
}