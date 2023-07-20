package com.example.practice;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.media3.common.util.UnstableApi;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

@UnstableApi public class AllChannelFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_all_channel_list, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        MyNewChannelAdapter adapter = new MyNewChannelAdapter(
                view.getContext(),
                new ArrayList<ChannelJson>(),
                channel -> clickOnChannelView(channel)
        );
        recyclerView.setAdapter(adapter);
        DataRepo.get(list -> {
            adapter.setChannels(list);
            adapter.notifyDataSetChanged();
            return null;
        });
        return view;
    }

    private Void clickOnChannelView(ChannelJson channel) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.videoCntainer, VideoFragment.getInstance(channel.getId()));
        fragmentTransaction.commitAllowingStateLoss();
        return null;
    }

    private Void clickOnChannel(Channel channel) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction =  fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.videoCntainer, VideoFragment.getInstance(channel.getId()));
        fragmentTransaction.commitAllowingStateLoss();
        return null;
    }
}