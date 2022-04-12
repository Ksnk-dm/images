package com.ksnk.imageukr.ui.images;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.annotation.SuppressLint;
import android.app.WallpaperManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.github.chrisbanes.photoview.PhotoView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.ksnk.imageukr.BuildConfig;
import com.ksnk.imageukr.utils.Contains;
import com.ksnk.imageukr.utils.PhotoLoader;
import com.ksnk.imageukr.R;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

public class ShowImageActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    private PhotoView photoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hideStatusBar();
        setContentView(R.layout.activity_show_image);
       // checkPermission();
        init();
        setImage();
    }

    private void setImage() {
        String url = getIntent().getStringExtra(Contains.URL_IMAGE);
        Picasso.get().load(url).into(photoView);
    }

    private final BottomNavigationView.OnNavigationItemSelectedListener bottomNavItemSelectListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            onClickItems(item);
            return false;
        }
    };

    private void init() {
        bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setOnNavigationItemSelectedListener(bottomNavItemSelectListener);
        photoView = findViewById(R.id.photo_view);
    }

    private void hideStatusBar() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    private void checkPermission() {
        if (Build.VERSION.SDK_INT >= 30) {
            if (!Environment.isExternalStorageManager()) {
                Intent getPermission = new Intent();
                getPermission.setAction(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
                startActivity(getPermission);
            }
        }
    }

    private void setWallpaperClick() {
        WallpaperManager wallpaperManager = WallpaperManager.getInstance(getApplicationContext());
        Picasso.get().load(getIntent().getStringExtra(Contains.URL_IMAGE)).into(new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                try {
                    wallpaperManager.setBitmap(bitmap);
                    Toast.makeText(getApplicationContext(), getText(R.string.wallpaper_set), Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onBitmapFailed(Exception e, Drawable errorDrawable) {
                Toast.makeText(getApplicationContext(), getText(R.string.error), Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        });
    }

    private void sharedClick() {
        Picasso.get().load(getIntent().getStringExtra(Contains.URL_IMAGE)).into(new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                Intent share = new Intent(Intent.ACTION_SEND);
                share.putExtra(Intent.EXTRA_STREAM, getTempBitmapUri(bitmap));
                share.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                share.setType("image/*");
                startActivity(Intent.createChooser(share, getString(R.string.share)));
            }

            @Override
            public void onBitmapFailed(Exception e, Drawable errorDrawable) {

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        });
    }

    private void downloadClick() {
        String name = UUID.randomUUID().toString() + ".jpg";
        String url = getIntent().getStringExtra(Contains.URL_IMAGE);
        Picasso.get().load(url).into(new PhotoLoader(name, this));
    }

    @SuppressLint("NonConstantResourceId")
    private void onClickItems(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.set_wallpaper_item:
                setWallpaperClick();
                break;
            case R.id.share_wallpaper_item:
                sharedClick();
                break;
            case R.id.download_item:
                downloadClick();
                break;
        }
    }

    private Uri getTempBitmapUri(Bitmap bitmap) {
        Uri bmuri = null;
        try {
            File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + File.separator + "image.jpg");
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, fileOutputStream);
            fileOutputStream.close();
            bmuri = FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID + ".provider", file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bmuri;
    }
}