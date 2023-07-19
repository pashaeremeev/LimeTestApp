package com.example.practice;

public class Data {

    private int id;
    private String name;
    private String image;
    private String stream;

    public Data(int id, String name, String image, String stream) {
        this.id = id;
        this.name = name;
        this.image = image;
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

    public String getStream() {
        return stream;
    }

    public void setStream(String stream) {
        this.stream = stream;
    }
}
