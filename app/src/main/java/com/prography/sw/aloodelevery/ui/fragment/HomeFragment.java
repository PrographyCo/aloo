package com.prography.sw.aloodelevery.ui.fragment;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.github.vipulasri.timelineview.TimelineView.TAG;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.prography.sw.aloodelevery.R;
import com.prography.sw.aloodelevery.adapter.CustomListener;
import com.prography.sw.aloodelevery.adapter.CustomRecyclerViewAdapter;
import com.prography.sw.aloodelevery.custom.BottomSheetListRadio;
import com.prography.sw.aloodelevery.custom.BottomSheetRate;
import com.prography.sw.aloodelevery.custom.BottomSheetTwoList;
import com.prography.sw.aloodelevery.data.response.GeneralResponse;
import com.prography.sw.aloodelevery.databinding.FragmentHomeBinding;
import com.prography.sw.aloodelevery.model.Order;
import com.prography.sw.aloodelevery.model.Radio;
import com.prography.sw.aloodelevery.network.AuthResource;
import com.prography.sw.aloodelevery.ui.MainActivity;
import com.prography.sw.aloodelevery.ui.activity.DeliveryOrderDetailsActivity;
import com.prography.sw.aloodelevery.ui.activity.LoginActivity;
import com.prography.sw.aloodelevery.util.AppUtils;
import com.prography.sw.aloodelevery.util.Constants;
import com.prography.sw.aloodelevery.util.SharedPreferencesHelper;
import com.prography.sw.aloodelevery.viewmodel.OrdersViewModel;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements View.OnClickListener, CustomListener {

    private FragmentHomeBinding binding;
    private OrdersViewModel ordersViewModel;
    private CustomRecyclerViewAdapter adapter;
    private BottomSheetListRadio bottomSheetList;
    private BottomSheetListRadio bottomSheetListRadio;
    private List<Radio> titleFirstList = new ArrayList<>();
    private List<Radio> titleTherdList = new ArrayList<>();
    private int count = 0;
    private List<Order> items = new ArrayList<>();
    private int id;
    private boolean loaded;
    private boolean pulled = false;
    private String order_by = null;
    private int type = -1;
    private FusedLocationProviderClient client;
    private double lat, lon;

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
        initListSpecify();
        initlistservesType();
        initViewModel();
        initAdpter();
        initBottomSheetTwoList();
        initBottomSheetListRadio();
        getCurrentLocation();
    }


    private void initViewModel() {
        ordersViewModel = new ViewModelProvider(this,
                new ViewModelProvider.AndroidViewModelFactory(getActivity().getApplication())).get(OrdersViewModel.class);
    }

    private void initAdpter() {
        adapter = new CustomRecyclerViewAdapter(this, CustomRecyclerViewAdapter.HolderConstants.HOME_FRAGMENT);
        adapter.setHomeFragment(items);
        binding.rvHomeFragment.setAdapter(adapter);
    }

    private void initListSpecify() {
        titleFirstList.add(new Radio(getString(R.string.closest_to_me), false));
        titleFirstList.add(new Radio(getString(R.string.farthest_to_me), false));
    }

    private void initBottomSheetTwoList() {
        bottomSheetList = new BottomSheetListRadio(getContext(), (position, action) -> {
            for (int i = 0; i < titleFirstList.size(); i++)
                titleFirstList.get(i).setCheck(i == position);
            bottomSheetList.notifyList();
            bottomSheetList.dismissDialog();
            binding.specify.setText(titleFirstList.get(position).getTitle());
            order_by = titleFirstList.get(position).getTitle();
            loaded = false;
            getCurrentLocation();
        });
        bottomSheetList.setList("sort by", titleFirstList);


    }

    private void initlistservesType() {
        titleTherdList.add(new Radio(getString(R.string.rep_supermarket), false));
        titleTherdList.add(new Radio(getString(R.string.rep_pharmacy), false));
        titleTherdList.add(new Radio(getString(R.string.rep_restaurant), false));
    }

    private void initBottomSheetListRadio() {
        bottomSheetListRadio = new BottomSheetListRadio(getContext(), new CustomListener() {
            @Override
            public void onItemClick(int position, String action) {
                for (int i = 0; i < titleTherdList.size(); i++)
                    titleTherdList.get(i).setCheck(i == position);
                bottomSheetListRadio.notifyList();
                bottomSheetListRadio.dismissDialog();
                binding.servesType.setText(titleTherdList.get(position).getTitle());
                type = position + 1;
                loaded = false;
                getCurrentLocation();
            }
        });

        bottomSheetListRadio.setList("service type", titleTherdList);

    }

    private void initView() {
        binding.servesType.setOnClickListener(this);
        binding.specify.setOnClickListener(this);
        binding.imAvailable.setOnClickListener(this);

        binding.swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pulled = true;
                ordersViewModel.setPageNumber(1);
                ordersViewModel.setFetchingNextPage(false);
                ordersViewModel.setFetchingExhausted(false);
                loaded = false;
                getCurrentLocation();
            }
        });
        binding.rvHomeFragment.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                if (!binding.rvHomeFragment.canScrollVertically(1) && binding.rvHomeFragment.getVisibility() == View.VISIBLE
                        && !ordersViewModel.isFetchingExhausted() && !ordersViewModel.isFetchingNextPage()) {
                    // search the next page
                    loaded = false;
                    ordersViewModel.getNextPage(lon, lat, order_by, type);
                }
            }
        });

    }


    private void getCurrentLocation() {
        client = LocationServices.getFusedLocationProviderClient(getActivity());

        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 100);
        else {
            client.getLastLocation()
                    .addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            // Got last known location. In some rare situations this can be null.
                            if (location != null) {
                                Log.d(TAG, "onSuccess: " + location.getLatitude() + "_" + location.getLongitude());
                                startOrdersProcess(location.getLatitude(), location.getLongitude());
                            }
                        }
                    });
        }


    }

    private void startOrdersProcess(double lat, double lon) {
        ordersViewModel.orders(lon, lat, order_by, type).observe(getViewLifecycleOwner(), ordersResource -> {
            switch (ordersResource.status) {
                case LOADING:
                    if (pulled)
                        binding.swipeRefresh.setEnabled(false);
                    else if (ordersViewModel.getPageNumber() > 1) {
                        adapter.displayLoading();
                    } else if (!pulled) {
                        showParent(View.GONE);
                    }
                    break;
                case AUTHENTICATED:
                    if (!loaded) {
                        updateUi(ordersResource.data);
                    }
                    loaded = true;
                    break;
                case ERROR:
                    if (!loaded) {
                        showFailed(ordersResource);
                    }
                    loaded = true;
                    break;
            }
        });
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
        adapter.notifyDataSetChanged();
        adapter.hideLoading();
        showParent(View.VISIBLE);
        if (response.getQuestionsData().getQuestions() != null && response.getQuestionsData().getQuestions().size() > 0) {
            int oldPosition = -1;
            if (ordersViewModel.isFetchingNextPage()) {
                oldPosition = items.size();
                this.items.addAll(response.getOrdersData().getItems());
                ordersViewModel.setFetchingNextPage(false);
            } else {
                this.items = response.getOrdersData().getItems();
            }
            if (response.getQuestionsData().getQuestions().size() < Constants.FETCHING_LIMIT)
                ordersViewModel.setFetchingExhausted(true);

            adapter.setHomeFragment(items);
            binding.rvHomeFragment.setVisibility(View.VISIBLE);

            if (oldPosition > 0) {
                binding.rvHomeFragment.smoothScrollToPosition(oldPosition);
            }

        } else {
            if (items.size() == 0) {
                Toast.makeText(getActivity(), getString(R.string.no_order_were_found), Toast.LENGTH_SHORT).show();
            } else {
                ordersViewModel.setFetchingExhausted(true);
                ordersViewModel.setFetchingNextPage(false);
            }
        }
        cancelSwipeRefreshLayout();

//        items = response.getOrdersData().getItems();
//        adapter.setHomeFragment(items);
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

    @SuppressLint("ResourceAsColor")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.serves_type:
                bottomSheetListRadio.showDialog();
                break;
            case R.id.specify:
                bottomSheetList.showDialog();
                break;
            case R.id.im_available:
                if (count == 0) {
                    count++;
                    binding.imAvailable.setBackground(getResources().getDrawable(R.drawable.oval_100_red));
                    binding.tvAvailable.setText(R.string.available);
                    binding.tvAvailable.setTextColor(getResources().getColor(R.color.white));
                    showAvailable(GONE);
                } else {
                    count--;
                    binding.imAvailable.setBackground(getResources().getDrawable(R.drawable.oval_100_gray));
                    binding.tvAvailable.setText(R.string.Unavailable);
                    binding.tvAvailable.setTextColor(getResources().getColor(R.color.black));
                    showAvailable(VISIBLE);
                }
                break;
        }
    }

    @Override
    public void onItemClick(int position, String action) {
        switch (action) {
            case "HOME_FRAGMENT":
                id = items.get(position).getId();
                Intent intent = new Intent(getContext(), DeliveryOrderDetailsActivity.class);
                intent.putExtra("id", id);
                startActivity(intent);
                break;
        }
    }

    private void showAvailable(int visibility) {
        if (visibility == View.VISIBLE) {
            binding.loAvailabe.setVisibility(View.GONE);
            binding.rvHomeFragment.setVisibility(View.VISIBLE);
        } else {
            binding.loAvailabe.setVisibility(View.VISIBLE);
            binding.rvHomeFragment.setVisibility(View.GONE);

        }
    }
}