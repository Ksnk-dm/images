package com.ksnk.imageukr.adapter;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ksnk.imageukr.R;

public class AdapterViewHolder extends RecyclerView.ViewHolder{
    public ImageView imageView;
    public AdapterViewHolder(@NonNull View itemView) {
        super(itemView);
        imageView=itemView.findViewById(R.id.imageView);
    }
}
