package com.ksnk.imageukr.ui.main.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ksnk.imageukr.R;
import com.ksnk.imageukr.ui.images.ShowImageActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterRecyclerViewAdapter extends RecyclerView.Adapter<AdapterViewHolder> {
    private List<String> items;

    public AdapterRecyclerViewAdapter(List<String> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public AdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater =
                (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        return new AdapterViewHolder(
                layoutInflater.inflate(
                        R.layout.items_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterViewHolder holder, int position) {
        String item = getItem(position);
        Picasso.get().load(item).into(holder.imageView);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ShowImageActivity.class);
                intent.putExtra("url", item);
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items == null ? 0 : items.size();
    }

    private String getItem(int position) {
        return items.get(position);
    }
}
