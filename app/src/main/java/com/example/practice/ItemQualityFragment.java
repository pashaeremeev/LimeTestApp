package com.example.practice;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentKt;
import androidx.media3.common.Player;
import androidx.media3.common.TrackSelectionOverride;
import androidx.media3.common.TrackSelectionParameters;
import androidx.media3.common.util.UnstableApi;
import androidx.media3.exoplayer.ExoPlayer;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.List;

@UnstableApi /**
 * A fragment representing a list of Items.
 */
public class ItemQualityFragment extends DialogFragment {

    public static final String BUNDLE_KEY =  "qualityDialogKey";
    public static final String REQUEST_KEY =  "qualityRequestKey";
    public static final String LIST_BUNDLE_KEY = "LIST_BUNDLE_KEY";



    public static ItemQualityFragment getInstance(ArrayList<Quality> list) {
        ItemQualityFragment itemQualityFragment = new ItemQualityFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(LIST_BUNDLE_KEY, list);
        itemQualityFragment.setArguments(bundle);
        return itemQualityFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.quality_list, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.qualityList);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        ArrayList<Quality> qualities = (getArguments() != null) ? getArguments().getParcelableArrayList(LIST_BUNDLE_KEY) : new ArrayList<>();

        recyclerView.setAdapter(new MyItemQualityRecyclerViewAdapter(
                view.getContext(),
                qualities,
                quality -> clickOnQuality(quality)
        ));
        return view;
    }

    private Void clickOnQuality(Quality quality) {
        Bundle result = new Bundle();
        result.putInt(BUNDLE_KEY, quality.getIndex());
        requireActivity().getSupportFragmentManager().setFragmentResult(REQUEST_KEY, result);
        dismiss();
        return null;
    }

    @Override
    public void onStart() {
        super.onStart();
        getDialog().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        getDialog().getWindow().setGravity(Gravity.RIGHT | Gravity.BOTTOM);
        getDialog().getWindow().setBackgroundDrawable(
                getResources().getDrawable(R.drawable.bg_quality, getContext().getTheme()));
    }
}