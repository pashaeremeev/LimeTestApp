package com.example.practice;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.media3.common.util.UnstableApi;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

@UnstableApi public class AllChannelFragment extends Fragment {

    private ChannelRepo channelRepo;
    private EpgRepo epgRepo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_all_channel_list, container, false);
        channelRepo = new ChannelRepo(getContext());
        epgRepo = new EpgRepo(getContext());
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        MyNewChannelAdapter adapter = new MyNewChannelAdapter(
                view.getContext(),
                channelRepo.getChannels(),
                epgRepo.getEpgs(),
                channel -> clickOnChannelView(channel)
        );

        DownloadChannels.downloadChannels(channelRepo, epgRepo, isSuccess -> {
            if (!isSuccess) {
                return null;
            }
            adapter.setChannels(channelRepo.getChannels());
            adapter.setEpgs(epgRepo.getEpgs());
            adapter.notifyDataSetChanged();
            return null;
        });

        recyclerView.setAdapter(adapter);
        return view;
    }

    private Void clickOnChannelView(Channel channel) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.videoCntainer, VideoFragment.getInstance(channel.getId()));
        fragmentTransaction.commitAllowingStateLoss();
        return null;
    }
}