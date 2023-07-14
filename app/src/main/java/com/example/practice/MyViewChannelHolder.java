package com.example.practice;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyViewChannelHolder extends RecyclerView.ViewHolder {

    private ImageView iconChannel, favoriteView;
    private TextView nameChannel, tvShow;

    public MyViewChannelHolder(@NonNull View itemView) {
        super(itemView);
        iconChannel = itemView.findViewById(R.id.iconChannel);
        favoriteView = itemView.findViewById(R.id.isFavoriteView);
        nameChannel = itemView.findViewById(R.id.nameChannel);
        tvShow = itemView.findViewById(R.id.nameTvShow);
    }

    public ImageView getIconChannel() {
        return iconChannel;
    }

    public ImageView getFavoriteView() {
        return favoriteView;
    }

    public TextView getNameChannel() {
        return nameChannel;
    }

    public TextView getTvShow() {
        return tvShow;
    }
}
