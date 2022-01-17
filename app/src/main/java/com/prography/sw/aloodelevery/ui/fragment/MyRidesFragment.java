package com.prography.sw.aloodelevery.ui.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.Toast;

import com.prography.sw.aloodelevery.R;
import com.prography.sw.aloodelevery.adapter.CustomListener;
import com.prography.sw.aloodelevery.adapter.CustomRecyclerViewAdapter;
import com.prography.sw.aloodelevery.custom.BottomSheetListRadio;
import com.prography.sw.aloodelevery.data.response.GeneralResponse;
import com.prography.sw.aloodelevery.databinding.FragmentMyRidesBinding;
import com.prography.sw.aloodelevery.model.HomeFragment;
import com.prography.sw.aloodelevery.model.MyOrder;
import com.prography.sw.aloodelevery.model.MyRides;
import com.prography.sw.aloodelevery.model.Radio;
import com.prography.sw.aloodelevery.network.AuthResource;
import com.prography.sw.aloodelevery.ui.activity.MyRiderDetailsOrderActivity;
import com.prography.sw.aloodelevery.util.AppUtils;
import com.prography.sw.aloodelevery.viewmodel.OrdersViewModel;

import java.util.ArrayList;
import java.util.List;

public class MyRidesFragment extends Fragment implements View.OnClickListener, CustomListener {

    private FragmentMyRidesBinding binding;
    private OrdersViewModel ordersViewModel;
    private CustomRecyclerViewAdapter adapter;
    private BottomSheetListRadio bottomSheetListRadio;
    private BottomSheetListRadio bottomSheetListRadio2;
    private List<Radio> radioButtons = new ArrayList<>();
    private List<Radio> radioButtons2 = new ArrayList<>();
    private List<MyOrder> items = new ArrayList<>();
    private int id;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMyRidesBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initListBottomsheet();
        initListBottomsheet2();
        initViews();
        initBottomSheet();
        initBottomSheet2();
        initViewModel();
        initAdpter();
        startMyOrderProcess();
    }

    private void initListBottomsheet() {
        radioButtons.add(new Radio(getString(R.string.all), true));
        radioButtons.add(new Radio(getString(R.string.rep_restaurant), false));
        radioButtons.add(new Radio(getString(R.string.rep_supermarket), false));
        radioButtons.add(new Radio(getString(R.string.rep_pharmacy), false));
    }

    private void initListBottomsheet2() {
        radioButtons2.add(new Radio(getString(R.string.current_previous), true));
        radioButtons2.add(new Radio(getString(R.string.cancel), false));

    }

    private void initBottomSheet() {
        bottomSheetListRadio = new BottomSheetListRadio(getActivity(), (position, action) -> {
            for (int i = 0; i < radioButtons.size(); i++)
                radioButtons.get(i).setCheck(i == position);
            bottomSheetListRadio.notifyList();
            bottomSheetListRadio.dismissDialog();
            binding.servesType.setText(radioButtons.get(position).getTitle());
        });
        bottomSheetListRadio.setList(getString(R.string.service_type), radioButtons);
    }

    private void initBottomSheet2() {
        bottomSheetListRadio2 = new BottomSheetListRadio(getActivity(), (position, action) -> {
            for (int i = 0; i < radioButtons2.size(); i++)
                radioButtons2.get(i).setCheck(i == position);
            bottomSheetListRadio2.notifyList();
            bottomSheetListRadio2.dismissDialog();
            binding.tvCurrentPrevious.setText(radioButtons2.get(position).getTitle());
        });
        bottomSheetListRadio2.setList(null, radioButtons2);
    }

    private void initViewModel() {
        ordersViewModel = new ViewModelProvider(this,
                new ViewModelProvider.AndroidViewModelFactory(getActivity().getApplication())).get(OrdersViewModel.class);
    }

    private void initViews() {
        binding.servesType.setOnClickListener(this);
        binding.tvCurrentPrevious.setOnClickListener(this);
    }


    private void initAdpter() {
        adapter = new CustomRecyclerViewAdapter(this, CustomRecyclerViewAdapter.HolderConstants.MY_RIDES);
        adapter.setMyRides(items);
        binding.rvMtRidesFragment.setAdapter(adapter);
    }

    private void startMyOrderProcess() {
        ordersViewModel.getOrdersCurrentCancelled("current").observe(getViewLifecycleOwner(), myOrdersResource -> {
            switch (myOrdersResource.status) {
                case LOADING:
                    showParent(View.GONE);
                    break;
                case AUTHENTICATED:
                    updateUi(myOrdersResource.data);
                    break;
                case ERROR:
                    showFailed(myOrdersResource);
                    break;
            }
        });
    }

    private void showParent(int visibility) {
        if (visibility == View.VISIBLE) {
            binding.loading.setVisibility(View.GONE);
            binding.rvMtRidesFragment.setVisibility(View.VISIBLE);
        } else {
            binding.loading.setVisibility(View.VISIBLE);
            binding.rvMtRidesFragment.setVisibility(View.GONE);

        }
    }

    private void updateUi(GeneralResponse response) {
        showParent(View.VISIBLE);
        items = response.getMyOrdersData().getItems();
        Toast.makeText(getActivity(), "" + items.size(), Toast.LENGTH_SHORT).show();
        adapter.setMyRides(items);
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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.serves_type:
                bottomSheetListRadio.showDialog();
                break;
            case R.id.tv_current_previous:
                bottomSheetListRadio2.showDialog();
                break;
        }
    }

    @Override
    public void onItemClick(int position, String action) {
        id = items.get(position).getId();
        Intent intent = new Intent(getContext(), MyRiderDetailsOrderActivity.class);
        intent.putExtra("id", id);
        startActivity(intent);
    }
}