package com.example.practice;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Random;

public class DataChannels extends ArrayList<Channel> {

    public DataChannels() {
        createChannelList();
    }

    private void createChannelList() {
        this.add(new Channel(generateId(), "Первый канал", R.drawable.first_channel, true));
        this.add(new Channel(generateId(), "НТВ", R.drawable.ntv, false));
        this.add(new Channel(generateId(), "ТВ3", R.drawable.tv3, false));
        this.add(new Channel(generateId(), "2x2", R.drawable.two_on_two, true));
        this.add(new Channel(generateId(), "Пятница!", R.drawable.friday, true));
        this.add(new Channel(generateId(), "Суббота!", R.drawable.saturday, false));
        this.add(new Channel(generateId(), "Карусель", R.drawable.carousel, false));
        this.add(new Channel(generateId(), "ТНТ", R.drawable.tnt, false));
    }

    private int generateId() {
        return new SecureRandom().nextInt();
    }
}
