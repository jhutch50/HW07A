package com.example.hw07a;

import java.io.Serializable;

public class Trip implements Serializable {
    String title,chatroom,photo;
    double locLat,locLong;


    public Trip() {
    }

    public Trip(String title, String chatroom, String photo, double locLat, double locLong) {
        this.title = title;
        this.chatroom = chatroom;
        this.photo = photo;
        this.locLat = locLat;
        this.locLong = locLong;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getChatroom() {
        return chatroom;
    }

    public void setChatroom(String chatroom) {
        this.chatroom = chatroom;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public double getLocLat() {
        return locLat;
    }

    public void setLocLat(double locLat) {
        this.locLat = locLat;
    }

    public double getLocLong() {
        return locLong;
    }

    public void setLocLong(double locLong) {
        this.locLong = locLong;
    }

    public void setLocation(double locLat, double locLong){
        this.locLat = locLat;
        this.locLong = locLong;
    }

    @Override
    public String toString() {
        return "Trip{" +
                "title='" + title + '\'' +
                ", chatroom='" + chatroom + '\'' +
                ", photo='" + photo + '\'' +
                ", locLat=" + locLat +
                ", locLong=" + locLong +
                '}';
    }
}
