package com.example.practice;

import java.security.SecureRandom;
import java.util.ArrayList;

public class DataChannels extends ArrayList<Channel> {

    private static DataChannels map;
    private static final String URL = "https://mhd.iptv2022.com/p/5tzYJRkx_8x4VIGmmym0KA,1689751804/streaming/1kanalott/324/1/index.m3u8";

    public DataChannels() {
        createChannelList();
    }

    private void createChannelList() {
        this.add(new Channel(generateId(), "Первый канал", R.drawable.first_channel, true, URL));
        this.add(new Channel(generateId(), "НТВ", R.drawable.ntv, false, URL));
        this.add(new Channel(generateId(), "ТВ3", R.drawable.tv3, false, URL));
        this.add(new Channel(generateId(), "2x2", R.drawable.two_on_two, true, URL));
        this.add(new Channel(generateId(), "Пятница!", R.drawable.friday, true, URL));
        this.add(new Channel(generateId(), "Суббота!", R.drawable.saturday, false, URL));
        this.add(new Channel(generateId(), "Карусель", R.drawable.carousel, false, URL));
        this.add(new Channel(generateId(), "ТНТ", R.drawable.tnt, false, URL));
    }

    private int generateId() {
        return new SecureRandom().nextInt();
    }

    public static DataChannels get() {
        if (map == null) {
            map = new DataChannels();
        }
        return map;
    }

    public Channel getById(int id) {
        for (int i = 0; i < this.size(); i++) {
            Channel channel = this.get(i);
            if (channel.getId() == id) {
                return channel;
            }
        }
        return null;
    }
}
