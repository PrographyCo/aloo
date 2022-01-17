package com.prography.sw.aloodelevery.ui.fragment;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.prography.sw.aloodelevery.databinding.FragmentHelpBinding;
import com.prography.sw.aloodelevery.ui.activity.AboutAppActivity;
import com.prography.sw.aloodelevery.ui.activity.ClientsServiceActivity;
import com.prography.sw.aloodelevery.ui.activity.PrivacyPolicyActivity;
import com.prography.sw.aloodelevery.ui.activity.QuestionsActivity;


public class HelpFragment extends Fragment implements View.OnClickListener {
    private FragmentHelpBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        binding = FragmentHelpBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.aboutApp.setOnClickListener(this);
        binding.privacyPolicyConditions.setOnClickListener(this);
        binding.commonQuestions.setOnClickListener(this);
        binding.rateApp.setOnClickListener(this);
        binding.clientsService.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == binding.aboutApp.getId()) {
            startActivity(new Intent(getActivity(), AboutAppActivity.class));
        } else if (view.getId() == binding.privacyPolicyConditions.getId()) {
            startActivity(new Intent(getActivity(), PrivacyPolicyActivity.class));
        } else if (view.getId() == binding.commonQuestions.getId()) {
            startActivity(new Intent(getActivity(), QuestionsActivity.class));
        } else if (view.getId() == binding.rateApp.getId()) {
            rateApp();
        } else if (view.getId() == binding.clientsService.getId()){
            startActivity(new Intent(getActivity(), ClientsServiceActivity.class));
        }
    }

    public void rateApp() {
        try {
            Intent rateIntent = rateIntentForUrl("market://details");
            startActivity(rateIntent);
        } catch (ActivityNotFoundException e) {
            Intent rateIntent = rateIntentForUrl("https://play.google.com/store/apps/details?id=com.prography.sw.aloodelevery");
            startActivity(rateIntent);
        }
    }

    private Intent rateIntentForUrl(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(String.format("%s?id=%s", url, "com.prography.sw.aloodelevery")));
        int flags = Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_MULTIPLE_TASK;
        if (Build.VERSION.SDK_INT >= 21) {
            flags |= Intent.FLAG_ACTIVITY_NEW_DOCUMENT;
        } else {
            //noinspection deprecation
            flags |= Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET;
        }
        intent.addFlags(flags);
        return intent;
    }

}