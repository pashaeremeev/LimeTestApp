package com.example.practice;

public class Channel {

    private int id;
    private String name;
    private int image;
    private boolean isFavorite;

    public Channel(int id, String name, int image, boolean isFavorite) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.isFavorite = isFavorite;
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
