package com.prography.sw.aloodelevery.ui.activity;

import static com.github.vipulasri.timelineview.TimelineView.TAG;
import static com.prography.sw.aloodelevery.util.AppUtils.unauthorized;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.prography.sw.aloodelevery.R;
import com.prography.sw.aloodelevery.data.response.GeneralResponse;
import com.prography.sw.aloodelevery.databinding.ActivityAboutAppBinding;
import com.prography.sw.aloodelevery.network.AuthResource;
import com.prography.sw.aloodelevery.viewmodel.GeneralDataViewModel;

public class AboutAppActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityAboutAppBinding binding;
    private GeneralDataViewModel generalDataViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityAboutAppBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initViews();
        intViewMolde();
        startAboutProcess();

    }

    private void intViewMolde() {
        generalDataViewModel = new ViewModelProvider(this,
                new ViewModelProvider.AndroidViewModelFactory(getApplication())).get(GeneralDataViewModel.class);
    }

    private void initViews() {
        binding.backImage.setOnClickListener(this);
        binding.tvContactNumber.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == binding.backImage.getId()) {
            this.finish();
        }else if (view.getId() == binding.tvContactNumber.getId()) {
            startIntent();
        }
    }

    private void startIntent() {
        String contact = "+972 592692503"; // use country code with your phone number
        String url = "https://api.whatsapp.com/send?phone=" + contact;
        try {
            PackageManager pm = getPackageManager();
            pm.getPackageInfo("com.whatsapp", PackageManager.GET_ACTIVITIES);
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i);
        } catch (PackageManager.NameNotFoundException e) {
            Toast.makeText(this, "Whatsapp app not installed in your phone", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    private void startAboutProcess() {
        generalDataViewModel.aboutData().observe(this, aboutResource -> {
            switch (aboutResource.status) {
                case LOADING:
                    showParent(View.GONE);
                    break;
                case AUTHENTICATED:
                    updateUi(aboutResource.data);
                    generalDataViewModel.removeAboutObservers(this);
                    break;
                case ERROR:
                    showAboutFailed(aboutResource);
                    generalDataViewModel.removeAboutObservers(this);
                    break;
            }
        });
    }

    private void showParent(int visibility) {
        if (visibility == View.VISIBLE) {
            binding.loading.setVisibility(View.GONE);
            binding.contEmail.setVisibility(View.VISIBLE);
            binding.contWhatsApp.setVisibility(View.VISIBLE);
            binding.contSocial.setVisibility(View.VISIBLE);
        } else {
            binding.loading.setVisibility(View.VISIBLE);
            binding.contEmail.setVisibility(View.GONE);
            binding.contWhatsApp.setVisibility(View.GONE);
            binding.contSocial.setVisibility(View.GONE);
        }
    }

    private void updateUi(GeneralResponse response) {
        showParent(View.VISIBLE);
        binding.tvEmail.setText(response.getAboutData().getEmail());
        binding.tvContactNumber.setText(response.getAboutData().getWhatsapp());
//        binding.tvInstagram.setText(response.getAboutData().getInstagram());
//        binding.tvTwitter.setText(response.getAboutData().getTwitter());
//        binding.tvLinkedin.setText(response.getAboutData().getLinkedin());

        Log.d(TAG, "updateUi: " + response.getAboutData().getEmail());
        Log.d(TAG, "updateUi: " + response.getAboutData().getWhatsapp());
        Log.d(TAG, "updateUi: " + response.getAboutData().getTwitter());
        Log.d(TAG, "updateUi: " + response.getAboutData().getLinkedin());
        Log.d(TAG, "updateUi: " + response.getAboutData().getInstagram());


    }

    private void showAboutFailed(AuthResource<GeneralResponse> errorResponse) {
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
}