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

    private Function1<ChannelJson, Void> clickListener;
    private Context context;
    private ArrayList<ChannelJson> channels;

    public MyNewChannelAdapter(Context context, ArrayList<ChannelJson> channels, Function1<ChannelJson, Void> clickListener) {
        this.clickListener = clickListener;
        this.context = context;
        this.channels = channels;
    }

    public void setChannels(ArrayList<ChannelJson> channels) {
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
        ChannelJson item = channels.get(position);
        Channel channelItem = item.createChannel();
        Epg epgItem = item.createEpg();
        Uri urlImage = Uri.parse(channelItem.getImage());
        holder.getNameChannel().setText(channelItem.getName());
        holder.getTvShow().setText(epgItem.getTitle());
        Glide.with(context)
                .load(urlImage)
                .error(R.drawable.image_not_supported)
                .into(holder.getIconChannel());
        if (channelItem.isFavorite()) {
            holder.getFavoriteView().setImageResource(R.drawable.star_selected);
        } else {
            holder.getFavoriteView().setImageResource(R.drawable.star_unselected);
        }
        holder.itemView.setOnClickListener(view -> clickListener.invoke(item));
    }

    @Override
    public int getItemCount() {
        return channels.size();
    }
}
