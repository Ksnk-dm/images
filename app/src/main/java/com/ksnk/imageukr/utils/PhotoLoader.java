package com.ksnk.imageukr.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.media.MediaScannerConnection;
import android.os.Environment;
import android.widget.Toast;

import com.ksnk.imageukr.R;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.io.FileOutputStream;

public class PhotoLoader implements Target {
    private final String name;
    private Context context;

    public PhotoLoader(String name, Context context) {
        this.name = name;
        this.context = context;
    }

    @Override
    public void onPrepareLoad(Drawable arg0) {
    }

    @Override
    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom arg1) {
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/" + name);
        try {
            file.createNewFile();
            FileOutputStream ostream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 75, ostream);
            ostream.close();
            Toast.makeText(context, context.getString(R.string.done), Toast.LENGTH_SHORT).show();
            MediaScannerConnection.scanFile(context, new String[]{file.getPath()}, new String[]{"image/*"}, null);

        } catch (Exception e) {
            Toast.makeText(context, context.getString(R.string.error), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBitmapFailed(Exception e, Drawable errorDrawable) {

    }
}
