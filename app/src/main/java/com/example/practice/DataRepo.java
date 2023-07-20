package com.example.practice;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;

import android.util.Log;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.ArrayList;

import kotlin.jvm.functions.Function1;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DataRepo {

    public static final String APP_PREFERENCES = "APP_PREFERENCES";

    private static DataRepo dataRepo;
    private static ChannelJsonModel channelJsonModel;
    private static ArrayList<ChannelJson> listData;
    private static ArrayList<Channel> channels;
    private static ArrayList<Epg> epgs;

    private static void downloadChannels(Function1<ArrayList<ChannelJson>, Void> callback) {
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

    public static void loadData(Context context, Function1<ArrayList<ChannelJson>,Void> callback) {
        SharedPreferences sPref = context.getSharedPreferences(APP_PREFERENCES, MODE_PRIVATE);
        String modelJsonString = sPref.getString(APP_PREFERENCES, "");
        Type type = new TypeToken<ChannelJsonModel>(){}.getType();
        ChannelJsonModel channelJsonModel = new Gson().fromJson(modelJsonString, type);
        if (channelJsonModel == null) {
            downloadChannels(callback);
        } else {
            DataJson.CHANNEL_JSON_MODEL = channelJsonModel;
        }
    }

    public static void saveData(Context context) {
        SharedPreferences.Editor editor = context.getSharedPreferences(APP_PREFERENCES, MODE_PRIVATE).edit();
        String modelJsonString = new Gson().toJson(DataJson.CHANNEL_JSON_MODEL);
        editor.putString(APP_PREFERENCES, modelJsonString);
        editor.commit();
    }

    public static ChannelJsonModel getChannelJsonModel() {
        return channelJsonModel;
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
