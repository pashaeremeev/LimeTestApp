package com.example.practice;

import java.util.ArrayList;

public class ChannelJson {

    private int id;
    private String name;
    private String image;
    private ArrayList<EpgJson> epg;
    private String stream;

    public ChannelJson(int id, String name, String image, ArrayList<EpgJson> epg, String stream) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.epg = epg;
        this.stream = stream;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public ArrayList<EpgJson> getEpg() {
        return epg;
    }

    public void setEpg(ArrayList<EpgJson> epg) {
        this.epg = epg;
    }

    public String getStream() {
        return stream;
    }

    public void setStream(String stream) {
        this.stream = stream;
    }

    public Channel createChannel() {
        return new Channel(
                id,
                name,
                image,
                false,
                stream);
    }

    public Epg createEpg() {
        EpgJson epgJson = epg.get(0);
        return new Epg(
                epgJson.getId(),
                id,
                epgJson.getTimeStart(),
                epgJson.getTimeStop(),
                epgJson.getTitle());
    }
}
