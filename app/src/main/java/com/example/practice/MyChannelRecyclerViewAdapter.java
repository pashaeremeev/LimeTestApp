package com.example.practice;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import kotlin.jvm.functions.Function1;

public class MyChannelRecyclerViewAdapter extends RecyclerView.Adapter<MyViewChannelHolder> {

    private Function1<Channel, Void> clickListener;
    private Context context;
    private List<Channel> channels;

    public MyChannelRecyclerViewAdapter(Context context, List<Channel> channels, Function1<Channel, Void> clickListener) {
        this.context = context;
        this.channels = channels;
        this.clickListener = clickListener;
    }

    @Override
    public MyViewChannelHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewChannelHolder(LayoutInflater.from(context)
                .inflate(R.layout.channel_field, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewChannelHolder holder, int position) {
        Channel item = channels.get(position);
        Drawable icon = new BitmapDrawable(context.getResources(), item.getImage());
        holder.getIconChannel().setImageDrawable(icon);
        holder.getNameChannel().setText(item.getName());
        if (item.isFavorite()) {
            holder.getFavoriteView().setImageResource(R.drawable.star_selected);
        } else {
            holder.getFavoriteView().setImageResource(R.drawable.star_unselected);
        }
        holder.getTvShow().setText(item.getId() + "");
        holder.itemView.setOnClickListener(view -> clickListener.invoke(item));
    }

    @Override
    public int getItemCount() {
        return channels.size();
    }
}