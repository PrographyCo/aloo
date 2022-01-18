package com.prography.sw.alooproduct.ui.activity;

import static com.prography.sw.alooproduct.util.AppUtils.unauthorized;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.prography.sw.alooproduct.R;
import com.prography.sw.alooproduct.adapter.CustomListener;
import com.prography.sw.alooproduct.adapter.CustomRecyclerViewAdapter;
import com.prography.sw.alooproduct.custom.BottomSheetListRadio;
import com.prography.sw.alooproduct.data.response.GeneralResponse;
import com.prography.sw.alooproduct.databinding.ActivityStoreProductsBinding;
import com.prography.sw.alooproduct.model.HomeSuperPharm;
import com.prography.sw.alooproduct.model.Radio;
import com.prography.sw.alooproduct.model.StoreProductItem;
import com.prography.sw.alooproduct.network.AuthResource;
import com.prography.sw.alooproduct.util.Constants;
import com.prography.sw.alooproduct.viewmodel.ProfileViewModel;

import java.util.ArrayList;
import java.util.List;

public class StoreProductsSuperPharmActivity extends AppCompatActivity implements CustomListener, View.OnClickListener {
    private ActivityStoreProductsBinding binding;
    private List<StoreProductItem> items = new ArrayList<>();
    private ProfileViewModel profileViewModel;
    private CustomRecyclerViewAdapter adapter;
    private BottomSheetListRadio bottomSheetListRadio;
    private List<Radio> radios = new ArrayList<>();
    private boolean loaded;
    private boolean pulled = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityStoreProductsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initViews();
        initViewModel();
        initList();
        initBottomtsheet();
        initAdapter();
        startProductStoreProcess();
    }

    private void initList() {
        radios.add(new Radio("Offers", false));
        radios.add(new Radio("Value Meals", false));
        radios.add(new Radio("Kids meals", false));
        radios.add(new Radio("Side Orders", false));
        radios.add(new Radio("sweets", false));
    }

    private void initBottomtsheet() {
        bottomSheetListRadio = new BottomSheetListRadio(this, new CustomListener() {
            @Override
            public void onItemClick(int position, String action) {
                for (int i = 0; i < radios.size(); i++)
                    radios.get(i).setCheck(i == position);
                bottomSheetListRadio.notifyList();
                binding.search.setText(radios.get(position).getTitle());
                bottomSheetListRadio.dismissDialog();
                //  loaded = false;
                //    startProductStoreProcess();
            }
        });
        bottomSheetListRadio.setList(getString(R.string.Choose_section), radios);
    }

    private void initViewModel() {
        profileViewModel = new ViewModelProvider(this,
                new ViewModelProvider.AndroidViewModelFactory(getApplication())).get(ProfileViewModel.class);
    }

    private void initViews() {
        binding.backImage.setOnClickListener(this);
        binding.search.setOnClickListener(this);

        binding.swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pulled = true;
                profileViewModel.setPageNumber(1);
                profileViewModel.setFetchingNextPage(false);
                profileViewModel.setFetchingExhausted(false);
                loaded = false;
                startProductStoreProcess();
            }
        });

        binding.rvStoreProducts.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                if (!binding.rvStoreProducts.canScrollVertically(1) && binding.rvStoreProducts.getVisibility() == View.VISIBLE
                        && !profileViewModel.isFetchingExhausted() && !profileViewModel.isFetchingNextPage()) {
                    // search the next page
                    loaded = false;
                    profileViewModel.getNextPage();
                }
            }
        });


    }


    private void initAdapter() {
        adapter = new CustomRecyclerViewAdapter(this, CustomRecyclerViewAdapter.HolderConstants.HOME_FRAGMENT_SUPER_PHARM);
        adapter.setHomeSuperPharm(items);
        binding.rvStoreProducts.setAdapter(adapter);
    }

    private void startProductStoreProcess() {
        profileViewModel.getItems().observe(this, itmesResource -> {
            switch (itmesResource.status) {
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
                        updateUi(itmesResource.data);
                    }
                    loaded = true;
                    break;
                case ERROR:
                    if (!loaded) {
                        showFailed(itmesResource);
                    }
                    loaded = true;
                    break;
            }
        });
    }


    private void showParent(int visibility) {
        if (visibility == View.VISIBLE) {
            binding.loading.setVisibility(View.GONE);
            binding.rvStoreProducts.setVisibility(View.VISIBLE);
        } else {
            binding.loading.setVisibility(View.VISIBLE);
            binding.rvStoreProducts.setVisibility(View.GONE);
        }
    }

    private void updateUi(GeneralResponse response) {
        adapter.hideLoading();
        showParent(View.VISIBLE);
        if (response.getQuestionsData().getQuestions() != null && response.getQuestionsData().getQuestions().size() > 0) {
            int oldPosition = -1;
            if (profileViewModel.isFetchingNextPage()) {
                oldPosition = items.size();
                this.items.addAll(response.getStoreProductData().getItems());
                profileViewModel.setFetchingNextPage(false);
            } else {
                this.items = response.getStoreProductData().getItems();
            }

            if (response.getQuestionsData().getQuestions().size() < Constants.FETCHING_LIMIT)
                profileViewModel.setFetchingExhausted(true);

            adapter.setHomeSuperPharm(items);
            binding.rvStoreProducts.setVisibility(View.VISIBLE);

            if (oldPosition > 0) {
                binding.rvStoreProducts.smoothScrollToPosition(oldPosition);
            }

        } else {
            if (items.size() == 0) {
                Toast.makeText(this, getString(R.string.no_questions_were_found), Toast.LENGTH_SHORT).show();
            } else {
                profileViewModel.setFetchingExhausted(true);
                profileViewModel.setFetchingNextPage(false);
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


    @Override
    public void onItemClick(int position, String action) {

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == binding.backImage.getId()) {
            this.finish();
        } else if (view.getId() == binding.search.getId()) {
            bottomSheetListRadio.showDialog();
        }

    }
}