package com.example.practice;

import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import kotlin.jvm.functions.Function1;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DataRepo {

    private static DataRepo dataRepo;
    private static ChannelJsonModel channelJsonModel;
    private static ArrayList<ChannelJson> listData;
    private static ArrayList<Channel> channels;
    private static ArrayList<Epg> epgs;

    public DataRepo(Function1<ArrayList<ChannelJson>,Void> callback) {
        createDataRepo(callback);
    }

    private void createDataRepo(Function1<ArrayList<ChannelJson>,Void> callback) {
        RetrofitClient.getInstance().getApi().getData().enqueue(new Callback<ChannelJsonModel>() {
            @Override
            public void onResponse(Call<ChannelJsonModel> call, Response<ChannelJsonModel> response) {
                ArrayList<ChannelJson> newList = new ArrayList<>();
                listData = new ArrayList<>();
                if (response.isSuccessful()) {
                    /*Data body = response.body();
                    newList.add(body);
                    listData.add(body);*/
                    channelJsonModel = response.body();
                    ArrayList<ChannelJson> channelJsons = channelJsonModel.getChannelJsons();
                    for (int i = 0; i < channelJsons.size(); i++) {
                        ChannelJson channelJson = channelJsons.get(i);
                        newList.add(channelJson);
                        listData.add(channelJson);
                    }
                }
                callback.invoke(newList);
                Log.d("request", newList.size() + "");
            }

            @Override
            public void onFailure(Call<ChannelJsonModel> call, Throwable t) {
                callback.invoke(new ArrayList<>());
            }
        });
    }

    public static ArrayList<ChannelJson> get(Function1<ArrayList<ChannelJson>,Void> callback) {
        if (dataRepo == null) {
            dataRepo = new DataRepo(callback);
        }

        return listData;
    }

    public static ArrayList<Channel> getChannelList() {
        channels = new ArrayList<>();
        for (int i = 0; i < listData.size(); i++) {
            Channel channel = listData.get(i).createChannel();
            channels.add(channel);
        }
        return channels;
    }

    public static ArrayList<Epg> getEpgList() {
        epgs = new ArrayList<>();
        for (int i = 0; i < listData.size(); i++) {
            Epg epg = listData.get(i).createEpg();
            epgs.add(epg);
        }
        return epgs;
    }

    public static Channel getById(int id) {
        for (int i = 0; i < channels.size(); i++) {
            Channel channel = channels.get(i);
            if (channel.getId() == id) {
                return channel;
            }
        }
        return null;
    }

}
