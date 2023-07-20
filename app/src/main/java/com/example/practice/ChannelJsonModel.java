package com.example.practice;

import java.util.ArrayList;

public class ChannelJsonModel {

    private ArrayList<ChannelJson> channels;

    public ChannelJsonModel(ArrayList<ChannelJson> channelJsons) {
        this.channels = channelJsons;
    }

    public ArrayList<ChannelJson> getChannelJsons() {
        return channels;
    }

    public void setChannelJsons(ArrayList<ChannelJson> channelJsons) {
        this.channels = channelJsons;
    }
}
