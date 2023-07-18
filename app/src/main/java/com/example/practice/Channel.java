package com.example.practice;

public class Channel {

    private int id;
    private String name;
    private int image;
    private boolean isFavorite;
    private String stream;

    public Channel(int id, String name, int image, boolean isFavorite, String stream) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.isFavorite = isFavorite;
        this.stream = stream;
    }

    public String getStream() {
        return stream;
    }

    public void setStream(String stream) {
        this.stream = stream;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
