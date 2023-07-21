package com.example.practice;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.media3.common.util.Log;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import kotlin.jvm.functions.Function1;

public class MyNewChannelAdapter extends RecyclerView.Adapter<MyViewChannelHolder> {

    private Function1<Channel, Void> clickListener;
    private Context context;
    private ArrayList<Channel> channels;
    private ArrayList<Epg> epgs;

    public MyNewChannelAdapter(Context context, ArrayList<Channel> channels, ArrayList<Epg> epgs, Function1<Channel, Void> clickListener) {
        this.clickListener = clickListener;
        this.context = context;
        this.channels = channels;
        this.epgs = epgs;
    }

    public void setChannels(ArrayList<Channel> channels) {
        this.channels = channels;
    }

    public void setEpgs(ArrayList<Epg> epgs) {
        this.epgs = epgs;
    }

    @NonNull
    @Override
    public MyViewChannelHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewChannelHolder(LayoutInflater.from(context)
                .inflate(R.layout.channel_field, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewChannelHolder holder, int position) {
        Channel channelItem = channels.get(position);
        Epg epgItem = epgs.get(position);
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
        holder.getFavoriteView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (channelItem.isFavorite()) {
                    holder.getFavoriteView().setImageResource(R.drawable.star_unselected);
                    channelItem.setFavorite(false);
                } else {
                    holder.getFavoriteView().setImageResource(R.drawable.star_selected);
                    channelItem.setFavorite(true);
                }
            }
        });
        holder.itemView.setOnClickListener(view -> clickListener.invoke(channelItem));
        //holder.getIconChannel().setOnClickListener(view -> );
    }

    @Override
    public int getItemCount() {
        return channels.size();
    }
}
