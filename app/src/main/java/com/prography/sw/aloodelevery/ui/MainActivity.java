package com.prography.sw.aloodelevery.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.messaging.FirebaseMessaging;
import com.prography.sw.aloodelevery.R;
import com.prography.sw.aloodelevery.ui.fragment.HomeFragment;
import com.prography.sw.aloodelevery.ui.fragment.MyRidesFragment;
import com.prography.sw.aloodelevery.ui.fragment.AccountFragment;
import com.prography.sw.aloodelevery.ui.fragment.HelpFragment;
import com.prography.sw.aloodelevery.util.SharedPreferencesHelper;
import com.prography.sw.aloodelevery.viewmodel.ProfileViewModel;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    private FragmentManager mFragmentManager;
    private String currentFragment;
    private ProfileViewModel profileViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        changeLang(SharedPreferencesHelper.getAppLanguage(this));
        bottomNavigationView = findViewById(R.id.btm_nav);
        changeFragment(new HomeFragment(), HomeFragment.class.getName());
        nav();

        initViewMoldel();
        sendNotification();


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


    public void changeLang(String lang) {
        Toast.makeText(this, SharedPreferencesHelper.getAppLanguage(this), Toast.LENGTH_SHORT).show();
        Locale myLocale = new Locale(lang);
        Locale.setDefault(myLocale);
        android.content.res.Configuration config = new android.content.res.Configuration();
        config.locale = myLocale;
        getResources().updateConfiguration(config, getResources().getDisplayMetrics());
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

                    } else if (itemId == R.id.my_rides_fragment) {
                        changeFragment(new MyRidesFragment(), MyRidesFragment.class.getSimpleName());

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
                    Toast.makeText(this, resource.data.getMessage(), Toast.LENGTH_SHORT).show();
                    break;
                case ERROR:
                    break;
            }
        });
    }

    private void sendNotification() {
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
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
                        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });

    }


}