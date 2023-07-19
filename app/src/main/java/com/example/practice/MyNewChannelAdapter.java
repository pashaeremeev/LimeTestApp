package com.example.practice;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import kotlin.jvm.functions.Function1;

public class MyNewChannelAdapter extends RecyclerView.Adapter<MyViewChannelHolder> {

    private Function1<Data, Void> clickListener;
    private Context context;
    private List<Data> channels;

    public MyNewChannelAdapter(Context context, ArrayList<Data> channels, Function1<Data, Void> clickListener) {
        this.clickListener = clickListener;
        this.context = context;
        this.channels = channels;
    }

    public void setChannels(ArrayList<Data> channels) {
        this.channels = channels;
    }

    @NonNull
    @Override
    public MyViewChannelHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewChannelHolder(LayoutInflater.from(context)
                .inflate(R.layout.channel_field, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewChannelHolder holder, int position) {
        Data item = channels.get(position);
        Uri urlImage = Uri.parse(item.getImage());
        holder.getNameChannel().setText(item.getName());
        holder.getTvShow().setText(item.getId() + "");
        Glide.with(context)
                .load(urlImage)
                .error(R.drawable.image_not_supported)
                .into(holder.getIconChannel());
        holder.getFavoriteView().setImageResource(R.drawable.star_unselected);
    }

    @Override
    public int getItemCount() {
        return channels.size();
    }
}
