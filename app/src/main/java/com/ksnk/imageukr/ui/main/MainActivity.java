package com.ksnk.imageukr.ui.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.WallpaperManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.github.chrisbanes.photoview.PhotoView;
import com.ksnk.imageukr.R;
import com.ksnk.imageukr.instruments.ImagesStore;
import com.ksnk.imageukr.ui.main.adapter.AdapterRecyclerViewAdapter;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    Button button;
    private RecyclerView recyclerView;
    // private List<String> items = new ArrayList<>();
    private GridLayoutManager layoutManager;
    private AdapterRecyclerViewAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PhotoView photoView =
                findViewById(R.id.photo_view);
        button = findViewById(R.id.button);
        recyclerView = findViewById(R.id.image_recycler_view);
        ImagesStore imagesStore = new ImagesStore();
        layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new AdapterRecyclerViewAdapter(imagesStore.getImagesList());
        recyclerView.setAdapter(mAdapter);


        WallpaperManager wallpaperManager = WallpaperManager.getInstance(getApplicationContext());
        Picasso.get().load("https://i.imgur.com/DvpvklR.png").into(photoView);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Picasso.get().load("https://i.imgur.com/DvpvklR.png").into(new Target() {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                        try {
                            wallpaperManager.setBitmap(bitmap);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onBitmapFailed(Exception e, Drawable errorDrawable) {

                    }

                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable) {

                    }
                });


            }
        });
    }
}