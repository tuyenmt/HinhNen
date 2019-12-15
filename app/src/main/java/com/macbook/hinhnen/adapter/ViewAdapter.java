package com.macbook.hinhnen.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.macbook.hinhnen.R;
import com.macbook.hinhnen.model.Photo;

import java.util.List;

public class ViewAdapter extends RecyclerView.Adapter<ViewHolder> {
    public Context context;
    public List<Photo> photos;
    public AdapterListenner adapterListenner;


    public ViewAdapter(Context context, List<Photo> photos , AdapterListenner adapterListenner) {
        this.context = context;
        this.photos = photos;
        this.adapterListenner = adapterListenner;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        try {
            Photo photo = photos.get(position);
            Glide.with(context).load(photo.getUrlS()).into(holder.imgview);
            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    adapterListenner.onClick(position);
                }
            });
        } catch (Exception e) {

        }
    }

    @Override
    public int getItemCount() {
        return photos.size();
    }


}



