package com.example.hw07a;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Trip implements Serializable {
    String title;
    List<String> chatroom = new ArrayList<>();
    String photo;
    String placeId;
    String name;
    String imageUrl;
    String id;
    List<String> users = new ArrayList<>();

    public Trip(String title, List<String> chatroom, String photo, String placeId, String name, String imageUrl, String id, List<String> users, String creator_id, String locLat, String locLong) {
        this.title = title;
        this.chatroom = chatroom;
        this.photo = photo;
        this.placeId = placeId;
        this.name = name;
        this.imageUrl = imageUrl;
        this.id = id;
        this.users = users;
        this.creator_id = creator_id;
        this.locLat = locLat;
        this.locLong = locLong;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public List<String> getUsers() {
        return users;
    }

    public void setUsers(List<String> users) {
        this.users = users;
    }

    String creator_id;
    String locLat,locLong;

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Trip() {
    }

    public String getCreator_id() {
        return creator_id;
    }

    public void setCreator_id(String creator_id) {
        this.creator_id = creator_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getChatroom() {
        return chatroom;
    }

    public void setChatroom(List<String> chatroom) {
        this.chatroom = chatroom;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getLocLat() {
        return locLat;
    }

    public void setLocLat(String locLat) {
        this.locLat = locLat;
    }

    public String getLocLong() {
        return locLong;
    }

    public void setLocLong(String locLong) {
        this.locLong = locLong;
    }

    @Override
    public String toString() {
        return "Trip{" +
                "title='" + title + '\'' +
                ", chatroom='" + chatroom + '\'' +
                ", photo='" + photo + '\'' +
                ", placeId='" + placeId + '\'' +
                ", name='" + name + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", users=" + users +
                ", creator_id='" + creator_id + '\'' +
                ", locLat='" + locLat + '\'' +
                ", locLong='" + locLong + '\'' +
                '}';
    }

    public Map toHashMap() {
        Map<String,Object> tripemap = new HashMap<>();
        tripemap.put("title",this.title);
        tripemap.put("chatroom",this.chatroom);
        tripemap.put("photo",this.photo);
        tripemap.put("placeId",this.placeId);
        tripemap.put("name",this.name);
        tripemap.put("locLat",this.locLat);
        tripemap.put("locLong",this.locLong);
        tripemap.put("creator_id",this.creator_id);
        tripemap.put("imageUrl",this.imageUrl);
        tripemap.put("users",this.users);
        tripemap.put("id",this.id);
        return tripemap;
    }
}
