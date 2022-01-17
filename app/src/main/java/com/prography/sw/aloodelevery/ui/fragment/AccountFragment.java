package com.prography.sw.aloodelevery.ui.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.prography.sw.aloodelevery.R;
import com.prography.sw.aloodelevery.custom.DialogTextTwoButtons;
import com.prography.sw.aloodelevery.data.response.GeneralResponse;
import com.prography.sw.aloodelevery.databinding.FragmentAccountBinding;
import com.prography.sw.aloodelevery.network.AuthResource;
import com.prography.sw.aloodelevery.ui.MainActivity;
import com.prography.sw.aloodelevery.ui.activity.LoginActivity;
import com.prography.sw.aloodelevery.ui.activity.MyDataActivity;
import com.prography.sw.aloodelevery.ui.activity.NotificationActivity;
import com.prography.sw.aloodelevery.ui.activity.ProfileActivity;
import com.prography.sw.aloodelevery.ui.activity.WalletActivity;
import com.prography.sw.aloodelevery.util.AppUtils;
import com.prography.sw.aloodelevery.util.SharedPreferencesHelper;
import com.prography.sw.aloodelevery.viewmodel.LogoutViewModel;

import java.util.Locale;

public class AccountFragment extends Fragment implements View.OnClickListener {

    private FragmentAccountBinding binding;
    private DialogTextTwoButtons dialogLogout, dialogLanguage;
    private LogoutViewModel logoutViewModel;
    private String TAG = AccountFragment.class.getSimpleName();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAccountBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews();
        checLang();
        initDialogs();
        initViewModel();

    }

    private void initViews() {
        binding.profile.setOnClickListener(this);
        binding.notification.setOnClickListener(this);
        binding.myData.setOnClickListener(this);
        binding.alooWallet.setOnClickListener(this);
        binding.switch1.setOnClickListener(this);
        binding.logOut.setOnClickListener(this);
    }

    private void initViewModel() {
        logoutViewModel = new ViewModelProvider(this,
                new ViewModelProvider.AndroidViewModelFactory(getActivity().getApplication()))
                .get(LogoutViewModel.class);
    }

    private void initDialogs() {
        dialogLogout
                = new DialogTextTwoButtons(getContext(),
                (position, action) -> {
                    if (action.equals("BUTTON1"))
                        logout();
                    else
                        dialogLogout.dismiss();
                },
                getString(R.string.log_out_text), getString(R.string.yes), getString(R.string.no));
        dialogLanguage
                = new DialogTextTwoButtons(getContext(),
                (position, action) -> {
                    if (action.equals("BUTTON1")) {
                        if (binding.switch1.isChecked()) {
                            changeLang("ar");
                            SharedPreferencesHelper.setSwitchLang(getContext(), true);
                            Toast.makeText(getContext(), binding.switch1.isChecked() + "", Toast.LENGTH_SHORT).show();
                            binding.tvArabic.setVisibility(View.INVISIBLE);
                            binding.tvEnglish.setVisibility(View.VISIBLE);

                        } else {
                            changeLang("en");
                            SharedPreferencesHelper.setSwitchLang(getContext(), false);

                            Toast.makeText(getContext(), binding.switch1.isChecked() + "", Toast.LENGTH_SHORT).show();
                            binding.tvArabic.setVisibility(View.VISIBLE);
                            binding.tvEnglish.setVisibility(View.INVISIBLE);
                        }
                    } else if (action.equals("BUTTON2")) {
                        binding.switch1.setChecked(SharedPreferencesHelper.getSwitchLang(getContext()));
                    }
                    dialogLanguage.dismiss();
                },
                getString(R.string.language_text), getString(R.string.restart), getString(R.string.stay));
    }

    private void logout() {
        logoutViewModel.logout().observe(getViewLifecycleOwner(), result -> {
            switch (result.status) {
                case LOADING:
                    dialogLogout.showParent(View.GONE);
                    break;
                case AUTHENTICATED:
                    Log.d(TAG, "data: " + result.data.getMessage());
                    updateUi(result.data);
                    logoutViewModel.removeLogoutObservers(getViewLifecycleOwner());
                    break;
                case ERROR:
                    showFailed(result);
                    break;
            }
        });
    }

    private void updateUi(GeneralResponse response) {
        SharedPreferencesHelper.setUserToken(getContext(), "");
        dialogLogout.dismiss();
        if (response.getMessage() != null)
            Toast.makeText(getContext(), response.getMessage(), Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getContext(), LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);

    }

    private void showFailed(AuthResource<GeneralResponse> errorResponse) {
        switch (errorResponse.errorCode) {
            case 0:
                Toast.makeText(getContext(), getString(R.string.check_internet_connection_text), Toast.LENGTH_SHORT).show();
                break;
            case 500:
                Toast.makeText(getContext(), getString(R.string.something_went_wrong_text), Toast.LENGTH_SHORT).show();
                break;
            case 401:
                AppUtils.unauthorized(getActivity());
                break;
            default:
                Toast.makeText(getContext(), errorResponse.message, Toast.LENGTH_SHORT).show();
                break;
        }
        dialogLogout.showParent(View.VISIBLE);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == binding.profile.getId()) {
            startActivity(new Intent(getActivity(), ProfileActivity.class));
        } else if (view.getId() == binding.notification.getId()) {
            startActivity(new Intent(getActivity(), NotificationActivity.class));
        } else if (view.getId() == binding.myData.getId()) {
            startActivity(new Intent(getActivity(), MyDataActivity.class));
        } else if (view.getId() == binding.alooWallet.getId()) {
            startActivity(new Intent(getActivity(), WalletActivity.class));
        } else if (view.getId() == binding.switch1.getId()) {
            dialogLanguage.show();
        } else if (view.getId() == binding.logOut.getId()) {
            dialogLogout.show();
        }
    }

    private void checLang() {
        binding.switch1.setChecked(SharedPreferencesHelper.getSwitchLang(getContext()));
        if (SharedPreferencesHelper.getSwitchLang(getContext())) {
            Toast.makeText(getContext(), "ar", Toast.LENGTH_SHORT).show();
            binding.tvArabic.setVisibility(View.INVISIBLE);
            binding.tvEnglish.setVisibility(View.VISIBLE);

        } else {
            Toast.makeText(getContext(), "en", Toast.LENGTH_SHORT).show();
            binding.tvArabic.setVisibility(View.VISIBLE);
            binding.tvEnglish.setVisibility(View.INVISIBLE);
        }
    }


    public void changeLang(String lang) {
        Toast.makeText(getContext(), SharedPreferencesHelper.getAppLanguage(getContext()), Toast.LENGTH_SHORT).show();
        Locale myLocale = new Locale(lang);
        SharedPreferencesHelper.setAppLanguage(getContext(), lang);
        SharedPreferencesHelper.setFirstTime(getActivity(), false);
        Locale.setDefault(myLocale);
        android.content.res.Configuration config = new android.content.res.Configuration();
        config.locale = myLocale;
        getActivity().getResources().updateConfiguration(config, getActivity().getResources().getDisplayMetrics());
        Intent intent = new Intent(getActivity(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }


}