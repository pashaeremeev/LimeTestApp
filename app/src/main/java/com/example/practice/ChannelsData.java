package com.example.practice;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class ChannelsData extends LinkedHashMap{
    public ChannelsData() {
        createMap();
    }
    private Map<String, Boolean> createMap() {
        this.put("Первый канал", true);
        this.put("НТВ", false);
        this.put("ТВ3", false);
        this.put("2х2", true);
        this.put("Пятница!", true);
        this.put("Суббота!", false);
        this.put("Карусель", false);
        this.put("ТНТ", false);
        return this;
    }
}
