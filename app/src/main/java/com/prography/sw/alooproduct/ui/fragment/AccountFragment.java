package com.prography.sw.alooproduct.ui.fragment;

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
import androidx.lifecycle.ViewModelProvider;

import com.prography.sw.alooproduct.R;
import com.prography.sw.alooproduct.custom.DialogTextTwoButtons;
import com.prography.sw.alooproduct.data.response.GeneralResponse;
import com.prography.sw.alooproduct.databinding.FragmentAccountBinding;
import com.prography.sw.alooproduct.network.AuthResource;
import com.prography.sw.alooproduct.ui.activity.AddListsProductsActivity;
import com.prography.sw.alooproduct.ui.activity.LoginActivity;
import com.prography.sw.alooproduct.ui.activity.MainActivity;
import com.prography.sw.alooproduct.ui.activity.MyDataActivity;
import com.prography.sw.alooproduct.ui.activity.MyOrdersActivity;
import com.prography.sw.alooproduct.ui.activity.NotificationActivity;
import com.prography.sw.alooproduct.ui.activity.StoreInfoActivity;
import com.prography.sw.alooproduct.ui.activity.StoreProductsResturantActivity;
import com.prography.sw.alooproduct.ui.activity.StoreProductsSuperPharmActivity;
import com.prography.sw.alooproduct.ui.activity.WalletActivity;
import com.prography.sw.alooproduct.util.SharedPreferencesHelper;
import com.prography.sw.alooproduct.viewmodel.LogoutViewModel;

import java.util.Locale;

public class AccountFragment extends Fragment {
    private static final String TAG = AccountFragment.class.getSimpleName();
    private FragmentAccountBinding binding;
    private DialogTextTwoButtons dialogLogout, dialogLanguage;
    private LogoutViewModel logoutViewModel;
    private String key;

    public AccountFragment(String key) {
        this.key = key;
    }

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

        checLang();
        initDialogs();
        initViewModel();

        binding.storeInfo.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), StoreInfoActivity.class));
        });
        binding.alooWallet.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), WalletActivity.class));
        });
        binding.storeProducts.setOnClickListener(v -> {
            if (key.equals("Restaurants")) {
                startActivity(new Intent(getActivity(), StoreProductsResturantActivity.class));
            } else {
                startActivity(new Intent(getActivity(), StoreProductsSuperPharmActivity.class));
            }
        });
        binding.myData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), MyDataActivity.class));
            }
        });
        binding.notification.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), NotificationActivity.class));
        });
        binding.switch1.setOnClickListener(v -> {
            if (binding.switch1.isChecked()) {
                binding.tvArabic.setVisibility(View.INVISIBLE);
                binding.tvEnglish.setVisibility(View.VISIBLE);
            } else {
                binding.tvArabic.setVisibility(View.VISIBLE);
                binding.tvEnglish.setVisibility(View.INVISIBLE);
            }
            dialogLanguage.show();
        });
        binding.logOut.setOnClickListener(v -> {
            dialogLogout.show();
        });
        binding.orders.setOnClickListener(v -> {
            startActivity(new Intent(getContext(), MyOrdersActivity.class));
        });
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
                    else if (action.equals("BUTTON2"))
                        Toast.makeText(getContext(), "no : " + action, Toast.LENGTH_SHORT).show();
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
                    } else if (action.equals("BUTTON2"))
                        binding.switch1.setChecked(SharedPreferencesHelper.getSwitchLang(getContext()));
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


}