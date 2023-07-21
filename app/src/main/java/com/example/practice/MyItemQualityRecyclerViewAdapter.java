package com.example.practice;

import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

import kotlin.jvm.functions.Function1;

public class MyItemQualityRecyclerViewAdapter extends RecyclerView.Adapter<MyQualityHolder> {

    private Function1<Quality, Void> clickListener;
    private Context context;
    private List<Quality> qualities;

    public MyItemQualityRecyclerViewAdapter(Context context, List<Quality> qualities, Function1<Quality, Void> clickListener) {
        this.clickListener = clickListener;
        this.context = context;
        this.qualities = qualities;
    }

    @Override
    public MyQualityHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new MyQualityHolder(LayoutInflater.from(context)
                .inflate(R.layout.quality_item, parent, false));

    }

    @Override
    public void onBindViewHolder(final MyQualityHolder holder, int position) {
        Quality item = qualities.get(position);
        if (item.getHeight() == - 1) {
            holder.getQualityText().setText("AUTO");
        } else {
            holder.getQualityText().setText(item.getHeight() + "p");
        }
        if (item.isCurrent() != 0) {
            holder.itemView.setBackgroundColor(Color.parseColor("#0077FF"));
            holder.getQualityText().setTextColor(Color.WHITE);
        }
        holder.itemView.setOnClickListener(view -> clickListener.invoke(item));
    }

    @Override
    public int getItemCount() {
        return qualities.size();
    }
}