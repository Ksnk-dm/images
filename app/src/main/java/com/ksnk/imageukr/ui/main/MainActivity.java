package com.ksnk.imageukr.ui.main;

import static com.google.android.gms.ads.rewarded.RewardedAd.*;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.OnUserEarnedRewardListener;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;
import com.ksnk.imageukr.data.Image;
import com.ksnk.imageukr.R;
import com.ksnk.imageukr.listeners.AdMobClickListener;
import com.ksnk.imageukr.listeners.UpdateUi;
import com.ksnk.imageukr.ui.menu.MainPopupMenu;
import com.ksnk.imageukr.ui.main.adapter.MainRecyclerViewAdapter;

import java.util.List;


public class MainActivity extends AppCompatActivity implements UpdateUi, OnUserEarnedRewardListener, AdMobClickListener {
    private RecyclerView recyclerView;
    private MainViewModel mainViewModel;
    private GridLayoutManager layoutManager;
    private MainRecyclerViewAdapter mAdapter;
    private ImageButton settingImageButton;
    private AdView adView;
    private RewardedAd mRewardedAd;
    private int span;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        checkTheme();
        init();
        span = mainViewModel.getType();
        initRecycler(span);
        initBanner();
        initRewardBanner();
        loadImages();
        mainViewModel.retrofitGet();
    }

    private void loadImages() {
        mainViewModel.getImageLiveData().observe(this, new Observer<List<Image>>() {
            @Override
            public void onChanged(List<Image> images) {
                mAdapter.setImages(images);
                mAdapter.notifyDataSetChanged();
            }
        });
    }

    private void initRewardBanner() {
        AdRequest adRequest = new AdRequest.Builder().build();
        load(this, "ca-app-pub-2981423664535117/4180062168",
                adRequest, new RewardedAdLoadCallback() {
                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error.
                        Log.d("tag", loadAdError.getMessage());
                        mRewardedAd = null;
                    }

                    @Override
                    public void onAdLoaded(@NonNull RewardedAd rewardedAd) {
                        mRewardedAd = rewardedAd;
                        Log.d("tag", "Ad was loaded.");
                    }
                });
    }

    public void showAdsInteresting() {
        if (mRewardedAd != null) {
            mRewardedAd.show(this, this);
        } else {
            Log.d("TAG", "The interstitial ad wasn't ready yet.");
        }
    }

    private void initBanner() {
        adView.loadAd(initAdRequest());
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        adView.loadAd(initAdRequest());
    }

    private AdRequest initAdRequest() {
        return new AdRequest.Builder().build();
    }

    private void init() {
        recyclerView = findViewById(R.id.image_recycler_view);
        settingImageButton = findViewById(R.id.settings_image_button);
        settingImageButton.setOnClickListener(settingImageButtonOnClickListener);
        adView = findViewById(R.id.adView);
    }

    private final View.OnClickListener settingImageButtonOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            MainPopupMenu mainPopupMenu = new MainPopupMenu();
            mainPopupMenu.showPopupWindow(view, MainActivity.this, mainViewModel);
        }
    };

    private void initRecycler(int span) {
        layoutManager = new GridLayoutManager(this, span);
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new MainRecyclerViewAdapter(span, this);
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public void updateRecycler() {
        span = mainViewModel.getType();
        initRecycler(span);
        loadImages();
    }

    private void checkTheme() {
        int theme = mainViewModel.getTheme();
        switch (theme) {
            case 0:
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
                break;
            case 1:
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                break;
            case 2:
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                break;
        }
    }

    @Override
    public void updateTheme(int theme) {
        switch (theme) {
            case 0:
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
                break;
            case 1:
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                break;
            case 2:
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                break;
        }
    }


    @Override
    public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
        Log.d("tag", "tag");
    }

    @Override
    public void clickAdmob() {
        int random = (int) (Math.random() * 2);
        if (random == 1) {
            showAdsInteresting();
            initRewardBanner();
        }
    }
}