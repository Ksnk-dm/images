package com.ksnk.imageukr.ui.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.ksnk.imageukr.R;
import com.ksnk.imageukr.utils.ImagesStore;
import com.ksnk.imageukr.ui.main.adapter.MainRecyclerViewAdapter;


public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private GridLayoutManager layoutManager;
    private MainRecyclerViewAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        initRecycler();
    }

    private void init() {
        recyclerView = findViewById(R.id.image_recycler_view);
    }

    private void initRecycler() {
        ImagesStore imagesStore = new ImagesStore();
        layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new MainRecyclerViewAdapter(imagesStore.getImagesList());
        recyclerView.setAdapter(mAdapter);
    }
}