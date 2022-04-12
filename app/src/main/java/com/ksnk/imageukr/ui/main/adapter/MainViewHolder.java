package com.ksnk.imageukr.ui.main.adapter;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ksnk.imageukr.R;

public class MainViewHolder extends RecyclerView.ViewHolder{
    public ImageView imageView;
    public MainViewHolder(@NonNull View itemView) {
        super(itemView);
        imageView=itemView.findViewById(R.id.imageView);
    }
}
