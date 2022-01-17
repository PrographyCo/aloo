package com.prography.sw.aloocustomer.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.messaging.FirebaseMessaging;
import com.prography.sw.aloocustomer.R;
import com.prography.sw.aloocustomer.custom.DialogTextButton;
import com.prography.sw.aloocustomer.data.response.GeneralResponse;
import com.prography.sw.aloocustomer.network.AuthResource;
import com.prography.sw.aloocustomer.ui.fragment.HomeFragment;
import com.prography.sw.aloocustomer.ui.fragment.OrdersFragment;
import com.prography.sw.aloocustomer.ui.fragment.AccountFragment;
import com.prography.sw.aloocustomer.ui.fragment.HelpFragment;
import com.prography.sw.aloocustomer.util.AppUtils;
import com.prography.sw.aloocustomer.util.SharedPreferencesHelper;
import com.prography.sw.aloocustomer.viewmodel.ProfileViewModel;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    private FragmentManager mFragmentManager;
    private String currentFragment;
    private DialogTextButton dialogPermission;
    private ProfileViewModel profileViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.btm_nav);
        changeFragment(new HomeFragment(), HomeFragment.class.getName());
        nav();
        initViewMoldel();
        initDialogs();
        askPermission();

        if (!SharedPreferencesHelper.getUserToken(this).isEmpty()) {
            sendNotification();
        }


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create channel to show notifications.
            String channelId = getString(R.string.default_notification_channel_id);
            String channelName = getString(R.string.default_notification_channel_name);
            NotificationManager notificationManager =
                    getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(new NotificationChannel(channelId,
                    channelName, NotificationManager.IMPORTANCE_LOW));
        }

        if (getIntent().getExtras() != null) {
            for (String key : getIntent().getExtras().keySet()) {
                Object value = getIntent().getExtras().get(key);
                Log.d("TAG", "Key: " + key + " Value: " + value);
            }
        }
    }

    private void askPermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 100);
    }


    private void initDialogs() {
        dialogPermission
                = new DialogTextButton(this,
                (position, action) -> {
                    askPermission();
                    dialogPermission.dismiss();
                },
                getString(R.string.permission_text), getString(R.string.yes));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100 && grantResults.length > 0) {
            if (grantResults[0] != PackageManager.PERMISSION_GRANTED)
                dialogPermission.show();
        }

    }

    public void nav() {
        bottomNavigationView.setOnNavigationItemSelectedListener(
                item -> {
                    int itemId = item.getItemId();
                    if (itemId == R.id.home_fragment) {
                        changeFragment(new HomeFragment(), HomeFragment.class.getName());

                    } else if (itemId == R.id.help_fragment) {
                        changeFragment(new HelpFragment(), HelpFragment.class.getSimpleName());

                    } else if (itemId == R.id.account_fragment) {
                        changeFragment(new AccountFragment(), AccountFragment.class.getSimpleName());

                    } else if (itemId == R.id.orders_fragment) {
                        changeFragment(new OrdersFragment(), OrdersFragment.class.getSimpleName());

                    }


                    return true;
                });
        bottomNavigationView.setSelectedItemId(R.id.home);
    }


    public void changeFragment(Fragment fragment, String tagFragmentName) {

        mFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();

        Fragment currentFragment = mFragmentManager.getPrimaryNavigationFragment();
        if (currentFragment != null) {
            fragmentTransaction.hide(currentFragment);
        }

        Fragment fragmentTemp = mFragmentManager.findFragmentByTag(tagFragmentName);
        if (fragmentTemp == null) {
            fragmentTemp = fragment;
            fragmentTransaction.add(R.id.nav_host_fragment, fragmentTemp, tagFragmentName);
        } else {
            fragmentTransaction.show(fragmentTemp);
        }

        fragmentTransaction.setPrimaryNavigationFragment(fragmentTemp);
        fragmentTransaction.setReorderingAllowed(true);
        fragmentTransaction.commitNowAllowingStateLoss();
        this.currentFragment = tagFragmentName;
    }

    private void initViewMoldel() {
        profileViewModel = new ViewModelProvider(this,
                new ViewModelProvider.AndroidViewModelFactory(getApplication())).get(ProfileViewModel.class);
    }

    private void startNotificationProcess(String fcmToken) {
        profileViewModel.setFirebaseToken(fcmToken).observe(this, resource -> {
            switch (resource.status) {
                case LOADING:
                    break;
                case AUTHENTICATED:
                    break;
                case ERROR:
                    showFailed(resource);
                    break;
            }
        });
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
    }

    private void sendNotification() {
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        Log.w("TAG", "Fetching FCM registration token failed", task.getException());
                        return;
                    }
                    // Get new FCM registration token
                    String token = task.getResult();

                    startNotificationProcess(token);
                    // Log and toast
                    String msg = getString(R.string.msg_token_fmt, token);
                    Log.d("TAG", msg);

                });

    }

}