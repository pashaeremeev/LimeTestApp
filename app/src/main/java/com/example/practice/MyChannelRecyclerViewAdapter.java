package com.example.practice;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.practice.placeholder.Channel;

import java.util.List;

public class MyChannelRecyclerViewAdapter extends RecyclerView.Adapter<MyViewChannelHolder> {

    private Context context;
    private List<Channel> channels;

    public MyChannelRecyclerViewAdapter(Context context, List<Channel> channels) {
        this.context = context;
        this.channels = channels;
    }

    @Override
    public MyViewChannelHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewChannelHolder(LayoutInflater.from(context)
                .inflate(R.layout.channel_field, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewChannelHolder holder, int position) {
        holder.getIconChannel().setImageResource(channels.get(position).getImage());
        holder.getNameChannel().setText(channels.get(position).getName());
        holder.getIsFavoriteView().setImageResource(channels.get(position).getImage());
    }

    @Override
    public int getItemCount() {
        return channels.size();
    }
}