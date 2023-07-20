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

public class DownloadChannels {

    public static final String CHANNELS = "CHANNELS";

    private static DownloadChannels downloadChannels;

    public static void downloadChannels(ChannelRepo channelRepo, EpgRepo epgRepo, Function1<Boolean, Void> callback) {
        RetrofitClient.getInstance().getApi().getData().enqueue(new Callback<ChannelJsonModel>() {
            @Override
            public void onResponse(Call<ChannelJsonModel> call, Response<ChannelJsonModel> response) {
                if (response.isSuccessful()) {
                    ArrayList<ChannelJson> channelJsons = response.body().getChannelJsons();
                    ArrayList<Channel> channels = new ArrayList<>();
                    ArrayList<Epg> epgs = new ArrayList<>();
                    for (int i = 0; i < channelJsons.size(); i++) {
                        ChannelJson channelJson = channelJsons.get(i);
                        Channel channel = channelJson.createChannel();
                        channels.add(channel);
                        Epg epg = channelJson.createEpg();
                        epgs.add(epg);
                    }
                    channelRepo.saveChannels(channels);
                    epgRepo.saveEpgs(epgs);
                }
                callback.invoke(true);
            }

            @Override
            public void onFailure(Call<ChannelJsonModel> call, Throwable t) {
                callback.invoke(false);
            }
        });
    }

//    public static void loadData(Context context, Function1<ArrayList<ChannelJson>,Void> callback) {
//        SharedPreferences sPref = context.getSharedPreferences(APP_PREFERENCES, MODE_PRIVATE);
//        String modelJsonString = sPref.getString(CHANNELS, "");
//        Type type = new TypeToken<ChannelJsonModel>(){}.getType();
//        ChannelJsonModel channelJsonModel = new Gson().fromJson(modelJsonString, type);
//        if (channelJsonModel == null) {
//            downloadChannels(context, callback);
//        } else {
//            DataJson.CHANNEL_JSON_MODEL = channelJsonModel;
//            ArrayList<Channel> newList = new ArrayList<>();
//            listData = new ArrayList<>();
//                saveData(context, channelJsonModel);
//                ArrayList<ChannelJson> channelJsons = channelJsonModel.getChannelJsons();
//                for (int i = 0; i < channelJsons.size(); i++) {
//                    ChannelJson channelJson = channelJsons.get(i);
//                    newList.add(channelJson);
//                    listData.add(channelJson);
//                }
//            callback.invoke(newList);
//        }
//    }
//
//    private static void saveData(Context context, ChannelJsonModel channelJsonModel) {
//        SharedPreferences.Editor editor = context.getSharedPreferences(APP_PREFERENCES, MODE_PRIVATE).edit();
//        String modelJsonString = new Gson().toJson(channelJsonModel);
//        editor.putString(CHANNELS, modelJsonString);
//        editor.commit();
//    }
//
//    public static Channel getById(int id) {
//        for (int i = 0; i < channels.size(); i++) {
//            Channel channel = channels.get(i);
//            if (channel.getId() == id) {
//                return channel;
//            }
//        }
//        return null;
//    }

}
