package com.example.practice;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class ChannelRepo {

    private static final String KEY_CHANNEL = "channel";
    private PreferencesRepo preferencesRepo;
    private static MutableLiveData<ArrayList<Channel>> channelMutableLiveData = new MutableLiveData<>(new ArrayList<Channel>());


    public ChannelRepo(Context context) {
        this.preferencesRepo = new PreferencesRepo(context);
        getChannels();
    }

    public void saveChannels(ArrayList<Channel> channels) {
        String channelsJson = new Gson().toJson(channels);
        preferencesRepo.save(channelsJson, KEY_CHANNEL);
        channelMutableLiveData.postValue(channels);
    }

    public void getChannels() {
        String listJsonString = preferencesRepo.get(KEY_CHANNEL);
        Type type = new TypeToken<ArrayList<Channel>>() {}.getType();
        ArrayList<Channel> channels = new Gson().fromJson(listJsonString, type);
        channelMutableLiveData.postValue((channels == null) ? new ArrayList<>() : channels);
    }

    public ArrayList<Channel> getFavChannels() {
        String listJsonString = preferencesRepo.get(KEY_CHANNEL);
        Type type = new TypeToken<ArrayList<Channel>>() {}.getType();
        ArrayList<Channel> channels = new Gson().fromJson(listJsonString, type);
        ArrayList<Channel> favChannels = new ArrayList<>();
        for (int i = 0; i < channels.size(); i++) {
            if (channels.get(i).isFavorite()) {
                favChannels.add(channels.get(i));
            }
        }
        return (favChannels == null) ? new ArrayList<>() : channels;
    }

    public Channel getById(int id) {
        ArrayList<Channel> channels = channelMutableLiveData.getValue();
        for (int i = 0; i < channels.size(); i++) {
            Channel channel = channels.get(i);
            if (channel.getId() == id) {
                return channel;
            }
        }
        return null;
    }

    public static LiveData<ArrayList<Channel>> getLiveData() {
        return channelMutableLiveData;
    }
}
