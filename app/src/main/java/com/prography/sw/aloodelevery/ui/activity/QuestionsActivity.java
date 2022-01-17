package com.prography.sw.aloodelevery.ui.activity;

import static com.prography.sw.aloodelevery.util.AppUtils.unauthorized;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.prography.sw.aloodelevery.R;
import com.prography.sw.aloodelevery.adapter.CustomListener;
import com.prography.sw.aloodelevery.adapter.CustomRecyclerViewAdapter;
import com.prography.sw.aloodelevery.data.response.GeneralResponse;
import com.prography.sw.aloodelevery.databinding.ActivityQuestionsBinding;
import com.prography.sw.aloodelevery.model.Question;
import com.prography.sw.aloodelevery.network.AuthResource;
import com.prography.sw.aloodelevery.util.Constants;
import com.prography.sw.aloodelevery.viewmodel.GeneralDataViewModel;

import java.util.ArrayList;
import java.util.List;

public class QuestionsActivity extends AppCompatActivity implements CustomListener, View.OnClickListener {

    private ActivityQuestionsBinding binding;
    private List<Question> questionList = new ArrayList<>();
    private CustomRecyclerViewAdapter adapter;
    private GeneralDataViewModel generalDataViewModel;
    private boolean loaded;
    private boolean pulled = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityQuestionsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initViewModel();
        initAdapter();
        initViews();
        getQuestions();
    }

    private void initViewModel() {
        generalDataViewModel = new ViewModelProvider(this,
                new ViewModelProvider.AndroidViewModelFactory(this.getApplication()))
                .get(GeneralDataViewModel.class);
    }

    private void initAdapter() {
        adapter = new CustomRecyclerViewAdapter(this, CustomRecyclerViewAdapter.HolderConstants.QUESTION);
        binding.rvQuestion.setAdapter(adapter);
    }

    private void initViews() {
        binding.backImage.setOnClickListener(this);

        binding.swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pulled = true;
                generalDataViewModel.setPageNumber(1);
                generalDataViewModel.setFetchingNextPage(false);
                generalDataViewModel.setFetchingExhausted(false);
                loaded = false;
                getQuestions();
            }
        });

        binding.rvQuestion.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                if (!binding.rvQuestion.canScrollVertically(1) && binding.rvQuestion.getVisibility() == View.VISIBLE
                        && !generalDataViewModel.isFetchingExhausted() && !generalDataViewModel.isFetchingNextPage()) {
                    // search the next page
                    loaded = false;
                    generalDataViewModel.getNextPage();
                }
            }
        });
    }

    @Override
    public void onItemClick(int position, String action) {
        for (int i = 0; i < questionList.size(); i++)
            if (i == position)
                questionList.get(i).setAnswerShown(!questionList.get(i).getAnswerShown());
        adapter.notifyDataSetChanged();
    }

    private void getQuestions() {
        generalDataViewModel.questions().observe(this, result -> {
            switch (result.status) {
                case LOADING:
                    if (pulled)
                        binding.swipeRefresh.setEnabled(false);
                    else if (generalDataViewModel.getPageNumber() > 1) {
                        adapter.displayLoading();
                    } else if (!pulled) {
                        showParent(View.GONE);
                    }
                    break;
                case AUTHENTICATED:
                    if (!loaded) {
                        updateUi(result.data);
                    }
                    loaded = true;
//                    generalDataViewModel.removeQuestionObservers(this);
                    break;
                case ERROR:
                    if (!loaded) {
                        showFailed(result);
                    }
                    loaded = true;
//                    generalDataViewModel.removeQuestionObservers(this);
                    break;
            }
        });
    }

    private void showParent(int visibility) {
        if (visibility == View.VISIBLE) {
            binding.loading.setVisibility(View.GONE);
            binding.rvQuestion.setVisibility(View.VISIBLE);
        } else {
            binding.loading.setVisibility(View.VISIBLE);
            binding.rvQuestion.setVisibility(View.GONE);
        }
    }

    private void updateUi(GeneralResponse response) {
        adapter.hideLoading();
        showParent(View.VISIBLE);
//        if (response == null || response.getQuestionsData() == null || response.getQuestionsData().getQuestions() == null) {
//            Toast.makeText(this, getString(R.string.something_went_wrong_text), Toast.LENGTH_SHORT).show();
//            return;
//        }
//        if (response.getQuestionsData().getQuestions().isEmpty())
//            Toast.makeText(this, getString(R.string.no_questions_were_found), Toast.LENGTH_SHORT).show();
//        questionList = response.getQuestionsData().getQuestions();
//        adapter.setQuestions(questionList);

        if (response.getQuestionsData().getQuestions() != null && response.getQuestionsData().getQuestions().size() > 0) {
            int oldPosition = -1;
            if (generalDataViewModel.isFetchingNextPage()) {
                oldPosition = questionList.size();
                this.questionList.addAll(response.getQuestionsData().getQuestions());
                generalDataViewModel.setFetchingNextPage(false);
            } else {
                this.questionList = response.getQuestionsData().getQuestions();
            }

            if (response.getQuestionsData().getQuestions().size() < Constants.FETCHING_LIMIT)
                generalDataViewModel.setFetchingExhausted(true);

            adapter.setQuestions(questionList);
            binding.rvQuestion.setVisibility(View.VISIBLE);

            if (oldPosition > 0) {
                binding.rvQuestion.smoothScrollToPosition(oldPosition);
            }

        } else {
            if (questionList.size() == 0) {
                Toast.makeText(this, getString(R.string.no_questions_were_found), Toast.LENGTH_SHORT).show();
            } else {
                generalDataViewModel.setFetchingExhausted(true);
                generalDataViewModel.setFetchingNextPage(false);
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
    public void onClick(View view) {
        this.finish();
    }
}