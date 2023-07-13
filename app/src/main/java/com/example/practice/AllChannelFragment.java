package com.example.practice;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.example.practice.placeholder.Channel;
import com.example.practice.placeholder.PlaceholderContent;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

/**
 * A fragment representing a list of Items.
 */
public class AllChannelFragment extends Fragment {
    private RecyclerView recyclerView;
    private GestureDetector gestureDetector;
    private static final ChannelsData CHANNELS_DATA = new ChannelsData();
    private static final ChannelsIconsData CHANNELS_ICONS = new ChannelsIconsData();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_all_channel_list, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);

        List<Channel> channels = new ArrayList<>();
        int[] icons = CHANNELS_ICONS.getChannelIcons();

        int index = 0;

        for (String nameOfChannel : CHANNELS_DATA.keySet()) {
            //if (CHANNELS_DATA.get(nameOfChannel)) {
                channels.add(new Channel(nameOfChannel, CHANNELS_DATA.get(nameOfChannel), icons[index]));
            //} else {

            //}
            index++;
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(new MyChannelRecyclerViewAdapter(view.getContext(), channels));
        gestureDetector = new GestureDetector(getActivity(), new GestureListener());
        recyclerView.setOnTouchListener((view1, motionEvent) -> gestureDetector.onTouchEvent(motionEvent));
        return view;
    }

    public class GestureListener extends GestureDetector.SimpleOnGestureListener {
        private final int Y_BUFFER = 10;

        @Override
        public boolean onDown(MotionEvent e) {
            recyclerView.getParent().requestDisallowInterceptTouchEvent(true);
            return super.onDown(e);
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            if (Math.abs(distanceX) > Math.abs(distanceY)) {
                // Detected a horizontal scroll, allow the viewpager from switching tabs
                recyclerView.getParent().requestDisallowInterceptTouchEvent(false);
            } else if (Math.abs(distanceY) > Y_BUFFER) {
                // Detected a vertical scroll prevent the viewpager from switching tabs
                recyclerView.getParent().requestDisallowInterceptTouchEvent(true);
            }
            return super.onScroll(e1, e2, distanceX, distanceY);
        }
    }
}