package com.prography.sw.aloocustomer.ui.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.prography.sw.aloocustomer.R;
import com.prography.sw.aloocustomer.adapter.CustomListener;
import com.prography.sw.aloocustomer.adapter.CustomRecyclerViewAdapter;
import com.prography.sw.aloocustomer.data.response.GeneralResponse;
import com.prography.sw.aloocustomer.databinding.ActivityNotificationBinding;
import com.prography.sw.aloocustomer.model.Notification;
import com.prography.sw.aloocustomer.network.AuthResource;
import com.prography.sw.aloocustomer.ui.MainActivity;
import com.prography.sw.aloocustomer.util.AppUtils;
import com.prography.sw.aloocustomer.util.Constants;
import com.prography.sw.aloocustomer.util.SharedPreferencesHelper;
import com.prography.sw.aloocustomer.viewmodel.ProfileViewModel;

import java.util.ArrayList;
import java.util.List;

public class NotificationActivity extends AppCompatActivity implements CustomListener {

    private ActivityNotificationBinding binding;
    private List<Notification> notificationList = new ArrayList<>();
    private CustomRecyclerViewAdapter adapter;
    private ProfileViewModel profileViewModel;
    private boolean loaded;
    private boolean pulled = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNotificationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initViewModel();
        initAdapter();
        setListeners();
        startNotificationProcess();
    }


    private void initViewModel() {
        profileViewModel = new ViewModelProvider(this,
                new ViewModelProvider.AndroidViewModelFactory(getApplication())).get(ProfileViewModel.class);
    }

    private void startNotificationProcess() {
        profileViewModel.getNotification().observe(this, resource -> {
            switch (resource.status) {
                case LOADING:
                    if (pulled)
                        binding.swipeRefresh.setEnabled(false);
                    else if (profileViewModel.getPageNumber() > 1) {
                        adapter.displayLoading();
                    } else if (!pulled) {
                        showParent(View.GONE);
                    }
                    break;
                case AUTHENTICATED:
                    if (!loaded) {
                        updateUi(resource.data);
                    }
                    loaded = true;
                    profileViewModel.removeNotificationObservers(this);
                    break;
                case ERROR:
                    if (!loaded) {
                        showFailed(resource);
                    }
                    loaded = true;
                    profileViewModel.removeNotificationObservers(this);
                    break;
            }
        });
    }

    private void showParent(int visibility) {
        if (visibility == View.VISIBLE) {
            binding.loading.setVisibility(View.GONE);
            binding.rvNotification.setVisibility(View.VISIBLE);
        } else {
            binding.loading.setVisibility(View.VISIBLE);
            binding.rvNotification.setVisibility(View.GONE);
        }
    }


    private void updateUi(GeneralResponse response) {
        adapter.hideLoading();
        showParent(View.VISIBLE);
        if (response.getNotificationData().getItems() != null && response.getNotificationData().getItems().size() > 0) {
            int oldPosition = -1;
            if (profileViewModel.isFetchingNextPage()) {
                oldPosition = notificationList.size();
                this.notificationList.addAll(response.getNotificationData().getItems());
                profileViewModel.setFetchingNextPage(false);
            } else {
                this.notificationList = response.getNotificationData().getItems();
            }

            if (response.getNotificationData().getItems().size() < Constants.FETCHING_LIMIT)
                profileViewModel.setFetchingExhausted(true);

            adapter.setNotifications(notificationList);
            binding.rvNotification.setVisibility(View.VISIBLE);

            if (oldPosition > 0) {
                binding.rvNotification.smoothScrollToPosition(oldPosition);
            }

        } else {
            if (notificationList.size() == 0) {
                Toast.makeText(this, getString(R.string.no_questions_were_found), Toast.LENGTH_SHORT).show();
            } else {
                profileViewModel.setFetchingExhausted(true);
                profileViewModel.setFetchingNextPage(false);
            }
        }
        cancelSwipeRefreshLayout();

//        notificationList = response.getNotificationData().getItems();
//        adapter.setNotifications(notificationList);
    }

    private void cancelSwipeRefreshLayout() {
        pulled = false;
        binding.swipeRefresh.setEnabled(true);
        if (binding.swipeRefresh.isRefreshing()) {
            binding.swipeRefresh.setRefreshing(false);
        }
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

    private void initAdapter() {
        adapter = new CustomRecyclerViewAdapter(this, CustomRecyclerViewAdapter.HolderConstants.NOTIFICATION);
        adapter.setNotifications(notificationList);
        binding.rvNotification.setAdapter(adapter);
    }

    private void setListeners() {
        binding.backImage.setOnClickListener(view -> {
            this.finish();
        });


        binding.swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pulled = true;
                profileViewModel.setPageNumber(1);
                profileViewModel.setFetchingNextPage(false);
                profileViewModel.setFetchingExhausted(false);
                loaded = false;
                startNotificationProcess();
            }
        });

        binding.rvNotification.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                if (!binding.rvNotification.canScrollVertically(1) && binding.rvNotification.getVisibility() == View.VISIBLE
                        && !profileViewModel.isFetchingExhausted() && !profileViewModel.isFetchingNextPage()) {
                    // search the next page
                    loaded = false;
                    profileViewModel.getNextPage();
                }
            }
        });


    }

    @Override
    public void onItemClick(int position, String action) {
        Toast.makeText(getApplication(), "notification " + action, Toast.LENGTH_SHORT).show();
    }
}