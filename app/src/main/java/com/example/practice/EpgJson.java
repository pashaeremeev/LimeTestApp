package com.example.practice;

public class EpgJson {

    private int id;
    private int timeStart;
    private int timeStop;
    private String title;

    public EpgJson(int id, int timeStart, int timeStop, String title) {
        this.id = id;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
