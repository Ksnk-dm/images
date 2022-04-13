package com.ksnk.imageukr.ui.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.ksnk.imageukr.R;
import com.ksnk.imageukr.listeners.UpdateRecyclerListener;
import com.ksnk.imageukr.ui.menu.MainPopupMenu;
import com.ksnk.imageukr.utils.Contains;
import com.ksnk.imageukr.utils.ImagesStore;
import com.ksnk.imageukr.ui.main.adapter.MainRecyclerViewAdapter;


public class MainActivity extends AppCompatActivity implements UpdateRecyclerListener {
    private RecyclerView recyclerView;
    private GridLayoutManager layoutManager;
    private MainRecyclerViewAdapter mAdapter;
    private ImageButton settingImageButton;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private AdView adView;
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
        mAdapter = new MainRecyclerViewAdapter(imagesStore.getImagesList(), span);
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
}