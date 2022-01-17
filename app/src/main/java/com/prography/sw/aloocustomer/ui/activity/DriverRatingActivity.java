package com.prography.sw.aloocustomer.ui.activity;

import static com.prography.sw.aloocustomer.util.AppUtils.setTime;
import static com.prography.sw.aloocustomer.util.AppUtils.unauthorized;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RatingBar;
import android.widget.Toast;

import com.prography.sw.aloocustomer.R;
import com.prography.sw.aloocustomer.adapter.CustomListener;
import com.prography.sw.aloocustomer.custom.BottomSheetDone;
import com.prography.sw.aloocustomer.data.response.GeneralResponse;
import com.prography.sw.aloocustomer.databinding.ActivityDriverRatingBinding;
import com.prography.sw.aloocustomer.network.AuthResource;
import com.prography.sw.aloocustomer.ui.MainActivity;
import com.prography.sw.aloocustomer.viewmodel.MyOrdersViewModel;

public class DriverRatingActivity extends AppCompatActivity implements View.OnClickListener, CustomListener {
    private ActivityDriverRatingBinding binding;
    private BottomSheetDone bottomSheetDone;
    private MyOrdersViewModel myOrdersViewModel;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDriverRatingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getIntentData();
        initView();
        initViewModel();
    }

    private void getIntentData() {
        id = getIntent().getIntExtra("id", -1);
    }

    private void initView() {
        binding.btnSubmitEvaluation.setOnClickListener(this);
        binding.rate.setOnRatingBarChangeListener((ratingBar, v, b) -> {
            ratingBar.setRating(v);
        });

    }

    private void initViewModel() {
        myOrdersViewModel = new ViewModelProvider(this,
                new ViewModelProvider.AndroidViewModelFactory(this.getApplication()))
                .get(MyOrdersViewModel.class);
    }

    private void btmSheetDone() {
        bottomSheetDone = new BottomSheetDone(this, this, R.drawable.ic_done,
                getString(R.string.rate_done), null, getString(R.string.back_to_main));
        bottomSheetDone.showDialog();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_submit_evaluation:
                rate();
                break;
        }
    }

    @Override
    public void onItemClick(int position, String action) {
        switch (action) {
            case "BottomSheetDone":
                Intent intent = new Intent(this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
                break;

        }
    }

    private void rate() {
        myOrdersViewModel.rate(id, String.valueOf((int) binding.rate.getRating()), binding.etSomeNotes.getmyText().toString()).observe(this, result -> {
            switch (result.status) {
                case LOADING:
                    showParent(View.GONE);
                    break;
                case AUTHENTICATED:
                    updateUi(result.data);
                    myOrdersViewModel.removeOrderDetailsObservers(this);
                    break;
                case ERROR:
                    showFailed(result);
                    myOrdersViewModel.removeOrderDetailsObservers(this);
                    break;
            }
        });
    }

    private void showParent(int visibility) {
        if (visibility == View.VISIBLE) {
            binding.loading.setVisibility(View.GONE);
            binding.btnSubmitEvaluation.setVisibility(View.VISIBLE);
        } else {
            binding.loading.setVisibility(View.VISIBLE);
            binding.btnSubmitEvaluation.setVisibility(View.GONE);
        }
    }

    private void updateUi(GeneralResponse response) {
        showParent(View.VISIBLE);
        btmSheetDone();
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
                unauthorized(this);
                break;
            default:
                Toast.makeText(this, errorResponse.message, Toast.LENGTH_SHORT).show();
                break;
        }
        showParent(View.VISIBLE);
    }

}