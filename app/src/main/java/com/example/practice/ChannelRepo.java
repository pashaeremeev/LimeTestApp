package com.example.practice;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class ChannelRepo {

    private static final String KEY_CHANNEL = "channel";
    private PreferencesRepo preferencesRepo;

    public ChannelRepo(Context context) {
        this.preferencesRepo = new PreferencesRepo(context);
    }

    public void saveChannels(ArrayList<Channel> channels) {
        String channelsJson = new Gson().toJson(channels);
        preferencesRepo.save(channelsJson, KEY_CHANNEL);
    }

    public ArrayList<Channel> getChannels() {
        String listJsonString = preferencesRepo.get(KEY_CHANNEL);
        Type type = new TypeToken<ArrayList<Channel>>() {}.getType();
        ArrayList<Channel> channels = new Gson().fromJson(listJsonString, type);
        return (channels == null) ? new ArrayList<>() : channels;
    }

    public Channel getById(int id) {
        ArrayList<Channel> channels = getChannels();
        for (int i = 0; i < channels.size(); i++) {
            Channel channel = channels.get(i);
            if (channel.getId() == id) {
                return channel;
            }
        }
        return null;
    }
}
