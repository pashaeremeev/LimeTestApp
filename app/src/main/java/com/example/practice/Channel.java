package com.example.practice;

import androidx.annotation.Nullable;

import java.util.Objects;

public class Channel {

    private int id;
    private String name;
    private String image;
    private boolean isFavorite;
    private String stream;

    public Channel(int id, String name, String image, boolean isFavorite, String stream) {
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
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

    @Override
    public boolean equals(@Nullable Object obj) {
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, image, stream);
    }
}
