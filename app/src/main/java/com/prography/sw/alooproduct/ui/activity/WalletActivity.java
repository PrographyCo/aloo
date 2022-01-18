package com.prography.sw.alooproduct.ui.activity;

import static com.prography.sw.alooproduct.util.AppUtils.unauthorized;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.prography.sw.alooproduct.R;
import com.prography.sw.alooproduct.adapter.CustomListener;
import com.prography.sw.alooproduct.adapter.CustomRecyclerViewAdapter;
import com.prography.sw.alooproduct.data.response.GeneralResponse;
import com.prography.sw.alooproduct.data.response.data.WalletData;
import com.prography.sw.alooproduct.databinding.ActivityWalletBinding;
import com.prography.sw.alooproduct.model.Transaction;
import com.prography.sw.alooproduct.network.AuthResource;
import com.prography.sw.alooproduct.viewmodel.ProfileViewModel;

import java.util.ArrayList;
import java.util.List;

public class WalletActivity extends AppCompatActivity implements CustomListener {

    private ActivityWalletBinding binding;
    private List<Transaction> transactions = new ArrayList<>();
    private CustomRecyclerViewAdapter adapter;
    private ProfileViewModel profileViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityWalletBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initViewModel();
        initAdapter();
        getWallet();
        setListeners();
    }

    private void initAdapter() {
        adapter = new CustomRecyclerViewAdapter(this, CustomRecyclerViewAdapter.HolderConstants.TRANSACTION);
        adapter.setTransactions(transactions);
        binding.rvTransaction.setAdapter(adapter);
    }

    private void setListeners() {
        binding.backImage.setOnClickListener(view -> {
            this.finish();
        });
    }

    @Override
    public void onItemClick(int position, String action) {

    }

    private void initViewModel() {
        profileViewModel = new ViewModelProvider(this,
                new ViewModelProvider.AndroidViewModelFactory(this.getApplication()))
                .get(ProfileViewModel.class);
    }

    private void getWallet() {
        profileViewModel.getWallet().observe(this, result -> {
            switch (result.status) {
                case LOADING:
                    showParent(View.GONE);
                    break;
                case AUTHENTICATED:
                    updateUi(result.data);
                    profileViewModel.removeWalletObservers(this);
                    break;
                case ERROR:
                    showFailed(result);
                    profileViewModel.removeWalletObservers(this);
                    break;
            }
        });
    }

    private void showParent(int visibility) {
        if (visibility == View.VISIBLE) {
            binding.loadingLayout.setVisibility(View.GONE);
            binding.layout.setVisibility(View.VISIBLE);
        } else {
            binding.loadingLayout.setVisibility(View.VISIBLE);
            binding.layout.setVisibility(View.GONE);
        }
    }

    private void updateUi(GeneralResponse response) {
        showParent(View.VISIBLE);
        if (response == null || response.getWalletData() == null || response.getWalletData().getTransactions() == null) {
            Toast.makeText(this, getString(R.string.something_went_wrong_text), Toast.LENGTH_SHORT).show();
            return;
        }

        WalletData walletData = response.getWalletData();
        binding.tvBalanceText.setText(walletData.getWallet().getAmount());

        if (walletData.getTransactions().isEmpty())
            Toast.makeText(this, getString(R.string.no_transactions_were_found), Toast.LENGTH_SHORT).show();
        transactions = walletData.getTransactions();
        adapter.setTransactions(transactions);
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