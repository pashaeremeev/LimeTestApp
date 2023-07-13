package com.example.practice.placeholder;

public class Channel {

    private String name;
    private boolean isFavorite;
    private int Image;

    public Channel(String name, boolean isFavorite, int image) {
        this.name = name;
        this.isFavorite = isFavorite;
        Image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    public int getImage() {
        return Image;
    }

    public void setImage(int image) {
        Image = image;
    }
}
