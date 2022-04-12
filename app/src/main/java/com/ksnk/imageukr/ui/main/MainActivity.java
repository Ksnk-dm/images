package com.ksnk.imageukr.ui.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.ksnk.imageukr.R;
import com.ksnk.imageukr.TestListenersDemo;
import com.ksnk.imageukr.ui.menu.MainPopupMenu;
import com.ksnk.imageukr.utils.ImagesStore;
import com.ksnk.imageukr.ui.main.adapter.MainRecyclerViewAdapter;


public class MainActivity extends AppCompatActivity implements TestListenersDemo {
    private RecyclerView recyclerView;
    private GridLayoutManager layoutManager;
    private MainRecyclerViewAdapter mAdapter;
    private ImageButton settingImageButton;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    int span;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        initSharedPrefs();
        span = sharedPreferences.getInt("settings", 2);
        initRecycler(span);
    }

    private void init() {
        recyclerView = findViewById(R.id.image_recycler_view);
        settingImageButton = findViewById(R.id.settings_image_button);
        settingImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainPopupMenu mainPopupMenu = new MainPopupMenu();
                mainPopupMenu.showPopupWindow(view, MainActivity.this);
            }
        });
    }

    private void initRecycler(int span) {
        ImagesStore imagesStore = new ImagesStore();
        layoutManager = new GridLayoutManager(this, span);
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new MainRecyclerViewAdapter(imagesStore.getImagesList());
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public void updateRecycler() {
        mAdapter.clearListItem();
        span = sharedPreferences.getInt("settings", 2);
        initRecycler(span);
    }

    private void initSharedPrefs() {
        sharedPreferences = getSharedPreferences("mypref", 0);
        editor = sharedPreferences.edit();
    }
}