package com.example.practice;

import android.util.Log;

import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import kotlin.jvm.functions.Function1;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DownloadChannels {

    private static final int REFRESH_TIME = 900000;
    private static ScheduledExecutorService scheduler;

    public static void downloadChannels(ChannelRepo channelRepo, EpgRepo epgRepo, Function1<Boolean, Void> callback) {
        scheduler = Executors.newSingleThreadScheduledExecutor();

        scheduler.scheduleAtFixedRate(new Runnable()
        {
            public void run() {
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
                            if (channelRepo.getChannels().toString().equals(channels.toString())) {
                                callback.invoke(false);
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
                Log.d("TAG", "1");
            }
        }, 0, REFRESH_TIME, TimeUnit.MILLISECONDS);
    }
}
