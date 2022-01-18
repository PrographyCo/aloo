package com.prography.sw.alooproduct.ui.fragment;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.prography.sw.alooproduct.R;
import com.prography.sw.alooproduct.adapter.CustomListener;
import com.prography.sw.alooproduct.adapter.CustomRecyclerViewAdapter;
import com.prography.sw.alooproduct.custom.BottomSheetListText;
import com.prography.sw.alooproduct.data.response.GeneralResponse;
import com.prography.sw.alooproduct.databinding.FragmentHomeBinding;
import com.prography.sw.alooproduct.model.HomeResturant;
import com.prography.sw.alooproduct.model.OrderDetails;
import com.prography.sw.alooproduct.model.OrderItem;
import com.prography.sw.alooproduct.network.AuthResource;
import com.prography.sw.alooproduct.ui.activity.LoginActivity;
import com.prography.sw.alooproduct.ui.activity.MainActivity;
import com.prography.sw.alooproduct.ui.activity.OrderDetailsActivity;
import com.prography.sw.alooproduct.ui.activity.StartActivity;
import com.prography.sw.alooproduct.util.AppUtils;
import com.prography.sw.alooproduct.util.Constants;
import com.prography.sw.alooproduct.util.SharedPreferencesHelper;
import com.prography.sw.alooproduct.viewmodel.OrdersViewModel;
import com.prography.sw.alooproduct.viewmodel.ProfileViewModel;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements View.OnClickListener, CustomListener {
    private static final String TAG = HomeFragment.class.getSimpleName();
    private FragmentHomeBinding binding;
    private OrdersViewModel ordersViewModel;
    private ProfileViewModel profileViewModel;
    private BottomSheetListText bottomSheetListText;
    private List<String> text = new ArrayList<>();
    private List<OrderItem> item = new ArrayList<>();
    private CustomRecyclerViewAdapter customRecyclerViewAdapter;

    private int count = 0;
    private int id;
    private String order_by = null;

    private boolean loaded;
    private boolean pulled = false;
    private boolean isAvailable = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        initAdapter();
        initViewModel();
        initList();
        initButtomShetTextList();
        startGetStatusProcess();
    }

    public void initView() {
        binding.imAvailable.setOnClickListener(this);
        binding.specify.setOnClickListener(this);

        binding.swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (isAvailable) {
                    pulled = true;
                    ordersViewModel.setPageNumber(1);
                    ordersViewModel.setFetchingNextPage(false);
                    ordersViewModel.setFetchingExhausted(false);
                    loaded = false;
                    startOrderLisProcess();
                } else
                    cancelSwipeRefreshLayout();
            }
        });

        binding.rvHomeFragment.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                if (!binding.rvHomeFragment.canScrollVertically(1) && binding.rvHomeFragment.getVisibility() == View.VISIBLE
                        && !ordersViewModel.isFetchingExhausted() && !ordersViewModel.isFetchingNextPage()) {
                    // search the next page
                    loaded = false;
                    ordersViewModel.getNextPage(order_by, "sohaib");
                }
            }
        });
    }

    private void initViewModel() {
        ordersViewModel = new ViewModelProvider(this,
                new ViewModelProvider.AndroidViewModelFactory(getActivity().getApplication())).get(OrdersViewModel.class);
        profileViewModel = new ViewModelProvider(this,
                new ViewModelProvider.AndroidViewModelFactory(getActivity().getApplication())).get(ProfileViewModel.class);
    }


    private void initAdapter() {
        customRecyclerViewAdapter = new CustomRecyclerViewAdapter(this, CustomRecyclerViewAdapter.HolderConstants.HOME_FRAGMET_RESTURANT);
        customRecyclerViewAdapter.setHomeResturant(item);
        binding.rvHomeFragment.setAdapter(customRecyclerViewAdapter);
    }

    private void initList() {
        text.add(getString(R.string.price_lowest));
        text.add(getString(R.string.price_highest));
        text.add(getString(R.string.item_count_lowest));
        text.add(getString(R.string.item_count_highest));
    }

    private void initButtomShetTextList() {
        bottomSheetListText = new BottomSheetListText(getContext(), new CustomListener() {
            @Override
            public void onItemClick(int position, String action) {
                binding.specify.setText(text.get(position));
                order_by = text.get(position);
                bottomSheetListText.dismissDialog();
                loaded = false;
                if (isAvailable) {
                    startOrderLisProcess();
                }
            }
        });

        bottomSheetListText.setList("sort by", text);

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.im_available:
                startStatusProcess();
                break;
            case R.id.specify:
                bottomSheetListText.showDialog();
                break;
        }
    }

    @Override
    public void onItemClick(int position, String action) {
        Intent intent = new Intent(getActivity(), OrderDetailsActivity.class);
        id = item.get(position).getId();
        intent.putExtra("id", id);
        intent.putExtra("status", item.get(position).getStatus());
        startActivity(intent);
    }


    private void startOrderLisProcess() {
        ordersViewModel.getOrders(order_by, "sohaib").observe(getViewLifecycleOwner(),
                orderResource -> {
                    switch (orderResource.status) {
                        case LOADING:
                            if (pulled)
                                binding.swipeRefresh.setEnabled(false);
                            else if (ordersViewModel.getPageNumber() > 1) {
                                customRecyclerViewAdapter.displayLoading();
                            } else if (!pulled) {
                                showParent(View.GONE);
                            }
                            break;
                        case AUTHENTICATED:
                            if (!loaded) {
                                updateUi(orderResource.data);
                            }
                            loaded = true;
                            break;
                        case ERROR:
                            if (!loaded) {
                                showFailed(orderResource);
                            }
                            loaded = true;
                            break;
                    }
                });
    }


    private void startStatusProcess() {
        profileViewModel.changeAvailableStatus().observe(getViewLifecycleOwner(), new Observer<AuthResource<GeneralResponse>>() {
            @Override
            public void onChanged(AuthResource<GeneralResponse> statusResource) {
                switch (statusResource.status) {
                    case LOADING:
                        showParentStatus(View.GONE);
                        break;
                    case AUTHENTICATED:
                        upDateUiStatus(statusResource.data);
                        profileViewModel.removechangeAvailableStatusResultObservers(getViewLifecycleOwner());
                        break;
                    case ERROR:
                        showParentStatus(statusResource);
                        break;

                }
            }
        });
    }

    private void startGetStatusProcess() {
        profileViewModel.getAvailableStatus().observe(getViewLifecycleOwner(), getAvailableStatusResource -> {
            switch (getAvailableStatusResource.status) {
                case LOADING:
                    showParentStatus(View.GONE);
                    break;
                case AUTHENTICATED:
                    upDateUiGetStatus(getAvailableStatusResource.data);
                    profileViewModel.removeAvailableStatusObservers(getViewLifecycleOwner());
                    break;
                case ERROR:
                    showParentStatus(getAvailableStatusResource);
                    break;

            }
        });
    }


    private void showParentStatus(int visibility) {
        if (visibility == View.VISIBLE) {
            binding.loadingStatus.setVisibility(View.GONE);
            binding.tvAvailable.setVisibility(View.VISIBLE);
        } else {
            binding.loadingStatus.setVisibility(View.VISIBLE);
            binding.tvAvailable.setVisibility(View.GONE);
        }
    }


    private void upDateUiStatus(GeneralResponse response) {
        showParentStatus(View.VISIBLE);
        if (response.getGetStatusData().isStatus())
            showAvailable(GONE);
        else
            showAvailable(VISIBLE);

    }

    private void upDateUiGetStatus(GeneralResponse response) {
        showParentStatus(View.VISIBLE);
        if (response.getGetStatusData().isStatus())
            showAvailable(GONE);
        else
            showAvailable(VISIBLE);

    }


    private void showParentStatus(AuthResource<GeneralResponse> errorResponse) {
        switch (errorResponse.errorCode) {
            case 0:
                Toast.makeText(getActivity(), getString(R.string.check_internet_connection_text), Toast.LENGTH_SHORT).show();
                break;
            case 500:
                Toast.makeText(getActivity(), getString(R.string.something_went_wrong_text), Toast.LENGTH_SHORT).show();
                break;
            case 401:
                AppUtils.unauthorized(getActivity());
                break;
            default:
                Toast.makeText(getActivity(), errorResponse.message, Toast.LENGTH_SHORT).show();
                break;
        }
        showParentStatus(View.VISIBLE);
    }


    private void showParent(int visibility) {
        if (visibility == View.VISIBLE) {
            binding.loading.setVisibility(View.GONE);
            binding.rvHomeFragment.setVisibility(View.VISIBLE);

        } else {
            binding.loading.setVisibility(View.VISIBLE);
            binding.rvHomeFragment.setVisibility(View.GONE);

        }
    }

    private void updateUi(GeneralResponse response) {
        customRecyclerViewAdapter.hideLoading();
        showParent(View.VISIBLE);

        if (response.getOrdersListData().getItems() != null && response.getOrdersListData().getItems().size() > 0) {
            int oldPosition = -1;
            if (ordersViewModel.isFetchingNextPage()) {
                oldPosition = item.size();
                this.item.addAll(response.getOrdersListData().getItems());
                ordersViewModel.setFetchingNextPage(false);
            } else {
                this.item = response.getOrdersListData().getItems();
            }

            if (response.getOrdersListData().getItems().size() < Constants.FETCHING_LIMIT)
                ordersViewModel.setFetchingExhausted(true);

            customRecyclerViewAdapter.setHomeResturant(item);
            binding.rvHomeFragment.setVisibility(View.VISIBLE);

            if (oldPosition > 0) {
                binding.rvHomeFragment.smoothScrollToPosition(oldPosition);
            }

        } else {
            if (item.size() == 0) {
                Toast.makeText(getContext(), getString(R.string.no_items_were_found), Toast.LENGTH_SHORT).show();
            } else {
                ordersViewModel.setFetchingExhausted(true);
                ordersViewModel.setFetchingNextPage(false);
            }
        }
        cancelSwipeRefreshLayout();
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
                Toast.makeText(getActivity(), getString(R.string.check_internet_connection_text), Toast.LENGTH_SHORT).show();
                break;
            case 500:
                Toast.makeText(getActivity(), getString(R.string.something_went_wrong_text), Toast.LENGTH_SHORT).show();
                break;
            case 401:
                AppUtils.unauthorized(getActivity());
                break;
            default:
                Toast.makeText(getActivity(), errorResponse.message, Toast.LENGTH_SHORT).show();
                break;
        }
        showParent(View.VISIBLE);
    }

    private void showAvailable(int visibility) {
        if (visibility == View.VISIBLE) {
            binding.loAvailabe.setVisibility(View.GONE);
            binding.rvHomeFragment.setVisibility(View.VISIBLE);
            binding.imAvailable.setBackground(getResources().getDrawable(R.drawable.oval_100_gray));
            binding.tvAvailable.setText(R.string.Unavailable);
            binding.tvAvailable.setTextColor(getResources().getColor(R.color.black));
            loaded = false;
            isAvailable = true;
            startOrderLisProcess();
        } else {
            binding.loAvailabe.setVisibility(View.VISIBLE);
            binding.rvHomeFragment.setVisibility(View.GONE);
            binding.imAvailable.setBackground(getResources().getDrawable(R.drawable.oval_100_red));
            binding.tvAvailable.setText(R.string.available);
            binding.tvAvailable.setTextColor(getResources().getColor(R.color.white));
            isAvailable = false;
        }
    }
}