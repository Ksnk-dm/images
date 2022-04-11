package com.ksnk.imageukr;

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
import com.ksnk.imageukr.adapter.AdapterRecyclerViewAdapter;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    Button button;
    private RecyclerView recyclerView;
    private List<String> items = new ArrayList<>();
    private GridLayoutManager layoutManager;
    private AdapterRecyclerViewAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        items.add("https://www.wallpaperflare.com/static/876/494/344/ukraine-ukrainian-maidan-kyiv-wallpaper.jpg");

        items.add("https://img.wallpapersafari.com/desktop/1920/1080/5/89/6bxdZW.jpg");
        items.add("https://img.wallpapersafari.com/desktop/1920/1080/41/57/1ESrLK.jpg");
        items.add("https://previews.123rf.com/images/rglinsky/rglinsky1201/rglinsky120100188/12336990-vertical-oriented-image-of-famous-eiffel-tower-in-paris-france-.jpg");
        PhotoView photoView =
                findViewById(R.id.photo_view);
        button = findViewById(R.id.button);
        recyclerView = findViewById(R.id.image_recycler_view);

        layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new AdapterRecyclerViewAdapter(items);
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