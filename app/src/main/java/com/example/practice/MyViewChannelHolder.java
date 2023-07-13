package com.example.practice;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyViewChannelHolder extends RecyclerView.ViewHolder {

    private ImageView iconChannel, isFavoriteView;
    private TextView nameChannel;

    public MyViewChannelHolder(@NonNull View itemView) {
        super(itemView);
        iconChannel = itemView.findViewById(R.id.iconChannel);
        isFavoriteView = itemView.findViewById(R.id.isFavoriteView);
        nameChannel = itemView.findViewById(R.id.nameChannel);
    }

    public ImageView getIconChannel() {
        return iconChannel;
    }

    public ImageView getIsFavoriteView() {
        return isFavoriteView;
    }

    public TextView getNameChannel() {
        return nameChannel;
    }
}
