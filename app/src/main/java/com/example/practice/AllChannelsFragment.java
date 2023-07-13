package com.example.practice;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.LinkedHashMap;

public class AllChannelsFragment extends Fragment {

    public static final String TITLE = "title";
    private static final ChannelsData CHANNELS_DATA = new ChannelsData();
    private static final int VERTICAL_MARGIN = 10;
    private static final int HORIZONTAL_MARGIN = 20;
    private static final ChannelsIconsData CHANNELS_ICONS_DATA = new ChannelsIconsData();

    private static  final int RIGHT_PADDING_FAV_BUT = 50;
    private static final int HEIGHT_OF_CHANNEL_FIELD = 220;
    private static final int FONT_SIZE_OF_CHANNEL_NAME = 16;
    private static final int PADDING_SIZE_NAME_FIELD = 30;


    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(
                R.layout.all_channels, container, false);
        ScrollView scrollView = new ScrollView(getContext());
        LinearLayout scrollLayout = new LinearLayout(getContext());
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        scrollLayout.setPadding(0, 10, 0, 400);
        scrollLayout.setOrientation(LinearLayout.VERTICAL);
        scrollView.setFillViewport(true);
        scrollView.addView(scrollLayout, params);
        fillPage(CHANNELS_DATA, (ViewGroup) scrollLayout, CHANNELS_ICONS_DATA);
        ((ViewGroup) rootView).addView(scrollView);
        return rootView;
    }
    private void createChannelField(ViewGroup parent, String nameOfChannel, boolean isFav, int idIcon) {
        RelativeLayout field = new RelativeLayout(getContext());
        LinearLayout.LayoutParams paramsOfField = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                HEIGHT_OF_CHANNEL_FIELD);
        paramsOfField.setMargins(HORIZONTAL_MARGIN, VERTICAL_MARGIN,
                HORIZONTAL_MARGIN, VERTICAL_MARGIN);
        field.setBackgroundColor(getResources().getColor(R.color.second, getActivity().getTheme()));

        ImageView icon = loadChannelIcon(field, idIcon);
        icon.setId(parent.generateViewId());

        createChannelName(field, icon.getId(), nameOfChannel);

        createFavButton(field, isFav);

        field.setBackground(getResources().getDrawable(R.drawable.bg_rounded,
                getContext().getTheme()));

        field.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().startActivity(new Intent(getActivity(), VideoActivity.class));
            }
        });

        parent.addView(field, paramsOfField);
    }
    private void fillPage(LinkedHashMap<String, Boolean> data, ViewGroup parent,
                          ChannelsIconsData channelsIconsData) {
        int[] icons = channelsIconsData.getChannelIcons();
        int index = 0;
        for (String nameOfChannel : data.keySet()) {
            createChannelField(parent, nameOfChannel, data.get(nameOfChannel), icons[index]);
            index++;
        }
    }

    private ImageView loadChannelIcon(ViewGroup parent, int channelsIconsDataId) {
        ImageView channelIcon = new ImageView(getContext());
        channelIcon.setImageResource(channelsIconsDataId);
        RelativeLayout.LayoutParams paramsOfIcon = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        channelIcon.setPadding(HORIZONTAL_MARGIN, 0, 0, 0);
        paramsOfIcon.addRule(RelativeLayout.CENTER_VERTICAL);
        paramsOfIcon.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        channelIcon.setLayoutParams(paramsOfIcon);
        parent.addView(channelIcon);
        return channelIcon;
    }

    private void createChannelName(ViewGroup parent, int leftElemId, String nameOfChannel) {
        TextView nameField = new TextView(getContext());
        nameField.setText(nameOfChannel);
        nameField.setTextSize(FONT_SIZE_OF_CHANNEL_NAME);
        nameField.setTextColor(getResources().getColor(R.color.white, getActivity().getTheme()));
        RelativeLayout.LayoutParams paramsOfNameField = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        paramsOfNameField.addRule(RelativeLayout.RIGHT_OF, leftElemId);
        nameField.setPadding(PADDING_SIZE_NAME_FIELD, PADDING_SIZE_NAME_FIELD, 0 , 0);
        nameField.setLayoutParams(paramsOfNameField);
        parent.addView(nameField);
    }
    private void createFavButton(ViewGroup parent, boolean isFav) {
        ImageButton favoriteButton = new ImageButton(getContext());
        favoriteButton.setPadding(0, 0, RIGHT_PADDING_FAV_BUT, 0);
        if (isFav) {
            favoriteButton.setImageResource(R.drawable.star_selected);
        } else {
            favoriteButton.setImageResource(R.drawable.star_unselected);
        }
        favoriteButton.setBackground(null);
        RelativeLayout.LayoutParams paramsOfFavButton = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        paramsOfFavButton.addRule(RelativeLayout.CENTER_VERTICAL);
        paramsOfFavButton.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        favoriteButton.setLayoutParams(paramsOfFavButton);
        parent.addView(favoriteButton);
    }
}