package com.example.practice;

import java.security.SecureRandom;

public class Epg {

    private String tvShow = "Полночные откровения. 1 Сезон. 1 Серия.";
    private int start = -10;
    private int end = 10;
    private int id;

    public Epg(String tvShow, int start, int end) {
        this.tvShow = tvShow;
        this.start = start;
        this.end = end;
        this.id = generateId();
    }

    private int generateId() {
        return new SecureRandom().nextInt();
    }

    public String getTvShow() {
        return tvShow;
    }

    public void setTvShow(String tvShow) {
        this.tvShow = tvShow;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }
}
