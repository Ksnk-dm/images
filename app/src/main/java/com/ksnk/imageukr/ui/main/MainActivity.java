package com.ksnk.imageukr.ui.main;

import static com.google.android.gms.ads.rewarded.RewardedAd.*;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.OnUserEarnedRewardListener;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;
import com.google.android.gms.ads.rewardedinterstitial.RewardedInterstitialAd;
import com.ksnk.imageukr.R;
import com.ksnk.imageukr.listeners.AdMobClickListener;
import com.ksnk.imageukr.listeners.UpdateRecyclerListener;
import com.ksnk.imageukr.ui.menu.MainPopupMenu;
import com.ksnk.imageukr.utils.Contains;
import com.ksnk.imageukr.utils.ImagesStore;
import com.ksnk.imageukr.ui.main.adapter.MainRecyclerViewAdapter;


public class MainActivity extends AppCompatActivity implements UpdateRecyclerListener, OnUserEarnedRewardListener, AdMobClickListener {
    private RecyclerView recyclerView;
    private GridLayoutManager layoutManager;
    private MainRecyclerViewAdapter mAdapter;
    private ImageButton settingImageButton;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private AdView adView;
    private RewardedAd mRewardedAd;
    private int span;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        initSharedPrefs();
        span = sharedPreferences.getInt(Contains.PREFERENCE_VARIABLE_SETTINGS, 2);
        initRecycler(span);
        initBanner();
        initRewardBanner();
    }

    @Override
    protected void onResume() {
        super.onResume();
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
            mainPopupMenu.showPopupWindow(view, MainActivity.this);
        }
    };

    private void initRecycler(int span) {
        ImagesStore imagesStore = new ImagesStore();
        layoutManager = new GridLayoutManager(this, span);
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new MainRecyclerViewAdapter(imagesStore.getImagesList(), span, this);
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public void updateRecycler() {
        mAdapter.clearListItem();
        span = sharedPreferences.getInt(Contains.PREFERENCE_VARIABLE_SETTINGS, 2);
        initRecycler(span);
    }

    private void initSharedPrefs() {
        sharedPreferences = getSharedPreferences(Contains.PREFERENCE_INIT, 0);
        editor = sharedPreferences.edit();
    }

    @Override
    public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
        Log.d("tag", "tag");
    }

    @Override
    public void clickAdmob() {

        int random = (int) (Math.random() * 3);
        if (random == 1) {
            showAdsInteresting();
            initRewardBanner();
        }
    }
}