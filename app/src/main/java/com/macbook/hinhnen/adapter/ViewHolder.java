package com.macbook.hinhnen.adapter;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.macbook.hinhnen.R;

public class ViewHolder extends RecyclerView.ViewHolder {
    public CardView cardView;
    public ImageView imgview;

    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        cardView = itemView.findViewById(R.id.cardView);
        imgview = itemView.findViewById(R.id.img);
    }
}
