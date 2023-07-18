package com.example.practice;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Quality implements Parcelable {

    private int height;
    private int width;
    private int index;

    public Quality(int width, int height, int index) {
        this.height = height;
        this.width = width;
        this.index = index;
    }

    protected Quality(Parcel in) {
        height = in.readInt();
        width = in.readInt();
        index = in.readInt();
    }

    public static final Creator<Quality> CREATOR = new Creator<Quality>() {
        @Override
        public Quality createFromParcel(Parcel in) {
            return new Quality(in);
        }

        @Override
        public Quality[] newArray(int size) {
            return new Quality[size];
        }
    };

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeStringArray(new String[]{index + "", width+ "", height + ""});
    }
}