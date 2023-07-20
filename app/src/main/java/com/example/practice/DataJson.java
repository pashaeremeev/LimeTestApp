package com.example.practice;

import android.widget.ArrayAdapter;

import java.util.ArrayList;

public class DataJson {

    public static ChannelJsonModel CHANNEL_JSON_MODEL = new ChannelJsonModel(new ArrayList<>());
    private static ArrayList<Channel> channels = new ArrayList<>();
    private static ArrayList<Epg> epgs = new ArrayList<>();

    public static ArrayList<Channel> getChannels() {
        ArrayList<ChannelJson> channelJsons = CHANNEL_JSON_MODEL.getChannelJsons();
        for (int i = 0; i < channelJsons.size(); i++) {
            Channel channel = channelJsons.get(i).createChannel();
            channels.add(channel);
        }
        return channels;
    }

    public static Channel getChannelById(int id) {
        channels = getChannels();
        for (int i = 0; i < channels.size(); i++) {
            Channel channel = channels.get(i);
            if (channel.getId() == id) {
                return channel;
            }
        }
        return null;
    }

    public static ArrayList<Epg> getEpgs() {
        ArrayList<ChannelJson> channelJsons = CHANNEL_JSON_MODEL.getChannelJsons();
        for (int i = 0; i < channelJsons.size(); i++) {
            Epg epg = channelJsons.get(i).createEpg();
            epgs.add(epg);
        }
        return epgs;
    }
}
