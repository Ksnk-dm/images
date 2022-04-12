package com.ksnk.imageukr.ui.main.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import com.ksnk.imageukr.R;
import com.ksnk.imageukr.ui.images.ShowImageActivity;
import com.ksnk.imageukr.utils.Contains;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MainRecyclerViewAdapter extends RecyclerView.Adapter<MainViewHolder> {
    private List<String> items;

    public MainRecyclerViewAdapter(List<String> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public MainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater =
                (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        return new MainViewHolder(
                layoutInflater.inflate(
                        R.layout.items_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MainViewHolder holder, int position) {
        String item = getItem(position);
        loadPicasso(item, holder);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ShowImageActivity.class);
                intent.putExtra(Contains.URL_IMAGE, item);
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

    private void loadPicasso(String item, MainViewHolder holder) {
        CircularProgressDrawable circularProgressDrawable = new CircularProgressDrawable(holder.imageView.getContext());
        circularProgressDrawable.setStrokeWidth(5f);
        circularProgressDrawable.setCenterRadius(30f);
        circularProgressDrawable.start();
        Picasso.get().load(item).placeholder(circularProgressDrawable).into(holder.imageView);
    }
}
