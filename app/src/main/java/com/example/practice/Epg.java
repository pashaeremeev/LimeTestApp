package com.example.practice;

public class Epg {

    private int id;
    private int channelId;
    private int timeStart;
    private int timeStop;
    private String title;

    public Epg(int id, int channelId, int timeStart, int timeStop, String title) {
        this.id = id;
        this.channelId = channelId;
        this.timeStart = timeStart;
        this.timeStop = timeStop;
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getChannelId() {
        return channelId;
    }

    public void setChannelId(int channelId) {
        this.channelId = channelId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(int timeStart) {
        this.timeStart = timeStart;
    }

    public int getTimeStop() {
        return timeStop;
    }

    public void setTimeStop(int timeStop) {
        this.timeStop = timeStop;
    }
}
