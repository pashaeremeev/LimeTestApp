package com.example.practice;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LiveData;
import androidx.media3.common.util.UnstableApi;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

@UnstableApi public class FavChannelFragment extends Fragment {

    private ChannelRepo channelRepo;
    private EpgRepo epgRepo;
    private MyNewChannelAdapter adapter;
    private LiveData<ArrayList<Channel>> liveData;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fav_channel_list, container, false);
        channelRepo = new ChannelRepo(getContext());
        epgRepo = new EpgRepo(getContext());
        liveData = channelRepo.getLiveData();

        RecyclerView recyclerView = view.findViewById(R.id.recyclerFavView);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        adapter = new MyNewChannelAdapter(
                view.getContext(),
                epgRepo.getEpgs(),
                channel -> clickOnChannelView(channel),
                channel -> clickOnFavoriteView(channel, recyclerView)
        );
        liveData.observe(getViewLifecycleOwner(), (list) -> {
            ArrayList<Channel> favChannels = new ArrayList<>();
            ArrayList<Epg> favEpg = new ArrayList<>();
            for (int i = 0; i < list.size(); i++) {
                Channel channel = list.get(i);
                Epg epg = epgRepo.getEpgs().get(i);
                if (channel.isFavorite()) {
                    favChannels.add(channel);
                    favEpg.add(epg);
                }
            }
            adapter.setChannels(favChannels);
            adapter.setEpgs(favEpg);
            adapter.notifyDataSetChanged();
        });


        DownloadChannels.downloadChannels(channelRepo, epgRepo, isSuccess -> {
            if (!isSuccess) {
                return null;
            }
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

    private Void clickOnFavoriteView(Channel channel, RecyclerView recyclerView) {
        ArrayList<Channel> channelList = liveData.getValue();
        for (int i = 0; i < channelList.size(); i++) {
            if (channelList.get(i).getId() == channel.getId()) {
                if (channelList.get(i).isFavorite()) {
                    channelList.get(i).setFavorite(false);
                } else {
                    channelList.get(i).setFavorite(true);
                }
                channelRepo.saveChannels(channelList);
            }
        }
        return null;
    }
}
