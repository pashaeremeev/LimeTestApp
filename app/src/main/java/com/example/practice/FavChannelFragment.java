package com.example.practice;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class FavChannelFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fav_channel_list, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerFavView);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        List<Channel> favChannels = new ArrayList<>();
        List<Channel> channels = DataChannels.get();
        for (int i = 0; i < channels.size(); i++) {
            Channel item = channels.get(i);
            if (item.isFavorite()) {
                favChannels.add(item);
            }
        }

        recyclerView.setAdapter(new MyChannelRecyclerViewAdapter(
                view.getContext(),
                favChannels,
                channel -> clickOnChannel(channel)
        ));
        return view;
    }

    private Void clickOnChannel(Channel channel) {
        getActivity().startActivity(new Intent(getActivity(), VideoActivity.class));
        return null;
    }
}
