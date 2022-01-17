package com.prography.sw.aloocustomer.ui.fragment;

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

import com.prography.sw.aloocustomer.R;
import com.prography.sw.aloocustomer.custom.DialogTextTwoButtons;
import com.prography.sw.aloocustomer.data.response.GeneralResponse;
import com.prography.sw.aloocustomer.databinding.FragmentAccountBinding;
import com.prography.sw.aloocustomer.network.AuthResource;
import com.prography.sw.aloocustomer.ui.MainActivity;
import com.prography.sw.aloocustomer.ui.activity.CartActivity;
import com.prography.sw.aloocustomer.ui.activity.DeliveryAddressesActivity;
import com.prography.sw.aloocustomer.ui.activity.FavouriteActivity;
import com.prography.sw.aloocustomer.ui.activity.LoginActivity;
import com.prography.sw.aloocustomer.ui.activity.NotificationActivity;
import com.prography.sw.aloocustomer.ui.activity.ProfileActivity;
import com.prography.sw.aloocustomer.ui.activity.WalletActivity;
import com.prography.sw.aloocustomer.util.SharedPreferencesHelper;
import com.prography.sw.aloocustomer.viewmodel.LogoutViewModel;

import java.util.Locale;

public class AccountFragment extends Fragment implements View.OnClickListener {

    private FragmentAccountBinding binding;
    private DialogTextTwoButtons dialogLogout, dialogLanguage;
    private LogoutViewModel logoutViewModel;
    private String TAG = AccountFragment.class.getSimpleName();
    private String lang;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
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
        binding.deliveryAddresses.setOnClickListener(this);
        binding.alooWallet.setOnClickListener(this);
        binding.favourite.setOnClickListener(this);
        binding.logOut.setOnClickListener(this);
        binding.switch1.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        if (view.getId() == binding.profile.getId()) {
            if (!SharedPreferencesHelper.getUserToken(getContext()).isEmpty())
                startActivity(new Intent(getActivity(), ProfileActivity.class));
            else
                startActivity(new Intent(getContext(), LoginActivity.class));
        } else if (view.getId() == binding.notification.getId()) {
            if (!SharedPreferencesHelper.getUserToken(getContext()).isEmpty())
                startActivity(new Intent(getActivity(), NotificationActivity.class));
            else
                startActivity(new Intent(getContext(), LoginActivity.class));
        } else if (view.getId() == binding.deliveryAddresses.getId()) {

            if (!SharedPreferencesHelper.getUserToken(getContext()).isEmpty())
                startActivity(new Intent(getActivity(), DeliveryAddressesActivity.class));
            else
                startActivity(new Intent(getContext(), LoginActivity.class));
        } else if (view.getId() == binding.alooWallet.getId()) {
            if (!SharedPreferencesHelper.getUserToken(getContext()).isEmpty())
                startActivity(new Intent(getActivity(), WalletActivity.class));
            else
                startActivity(new Intent(getContext(), LoginActivity.class));
        } else if (view.getId() == binding.favourite.getId()) {
            if (!SharedPreferencesHelper.getUserToken(getContext()).isEmpty())
                startActivity(new Intent(getActivity(), FavouriteActivity.class));
            else
                startActivity(new Intent(getContext(), LoginActivity.class));
        } else if (view.getId() == binding.logOut.getId()) {
            if (!SharedPreferencesHelper.getUserToken(getContext()).isEmpty())
                dialogLogout.show();
            else
                startActivity(new Intent(getContext(), LoginActivity.class));
        } else if (view.getId() == binding.switch1.getId()) {
            dialogLanguage.show();
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
        startActivity(new Intent(getContext(), LoginActivity.class));
        getActivity().finish();
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
                startActivity(new Intent(getActivity(), LoginActivity.class));
                break;
            default:
                Toast.makeText(getContext(), errorResponse.message, Toast.LENGTH_SHORT).show();
                break;
        }
        dialogLogout.showParent(View.VISIBLE);
    }


}