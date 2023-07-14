package com.example.practice;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import kotlin.jvm.functions.Function1;

public class AllChannelFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_all_channel_list, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(new MyChannelRecyclerViewAdapter(
                view.getContext(),
                new DataChannels(),
                channel -> clickOnChannel(channel)
        ));
        return view;
    }

    private Void clickOnChannel(Channel channel) {
        getActivity().startActivity(new Intent(getActivity(), VideoActivity.class));
        return null;
    }
}