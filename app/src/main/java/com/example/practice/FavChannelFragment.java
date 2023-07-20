package com.example.practice;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class FavChannelFragment extends Fragment {

    private ChannelRepo channelRepo;
    private EpgRepo epgRepo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fav_channel_list, container, false);
        channelRepo = new ChannelRepo(getContext());
        epgRepo = new EpgRepo(getContext());
        RecyclerView recyclerView = view.findViewById(R.id.recyclerFavView);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        MyNewChannelAdapter adapter = new MyNewChannelAdapter(
                view.getContext(),
                channelRepo.getChannels(),
                epgRepo.getEpgs(),
                channel -> clickOnChannelView(channel)
        );

        ArrayList<Channel> favChannels = new ArrayList<>();
        ArrayList<Epg> favEpg = new ArrayList<>();
        for (int i = 0; i < channelRepo.getChannels().size(); i++) {
            Channel channel = channelRepo.getChannels().get(i);
            Epg epg = epgRepo.getEpgs().get(i);
            if (channel.isFavorite()) {
                favChannels.add(channel);
                favEpg.add(epg);
            }
        }

        DownloadChannels.downloadChannels(channelRepo, epgRepo, isSuccess -> {
            if (!isSuccess) {
                return null;
            }
            adapter.setChannels(favChannels);
            adapter.setEpgs(favEpg);
            adapter.notifyDataSetChanged();
            return null;
        });

        recyclerView.setAdapter(adapter);
        return view;
    }

    private Void clickOnChannelView(Channel channel) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction =  fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.videoCntainer, VideoFragment.getInstance(channel.getId()));
        fragmentTransaction.commitAllowingStateLoss();
        return null;
    }
}
