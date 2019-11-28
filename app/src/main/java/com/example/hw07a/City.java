package com.example.hw07a;

import java.io.Serializable;

public class City implements Serializable {
    String id, description,place_id;
    double lat,longi;

    public City() {
    }

    public City(String id, String description, String place_id, double lat, double longi) {
        this.id = id;
        this.description = description;
        this.place_id = place_id;
        this.lat = lat;
        this.longi = longi;
    }

    public City(String id, String description, String place_id) {
        this.id = id;
        this.description = description;
        this.place_id = place_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPlace_id() {
        return place_id;
    }

    public void setPlace_id(String place_id) {
        this.place_id = place_id;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLongi() {
        return longi;
    }

    public void setLongi(double longi) {
        this.longi = longi;
    }

    @Override
    public String toString() {
        return "City{" +
                "id='" + id + '\'' +
                ", description='" + description + '\'' +
                ", place_id='" + place_id + '\'' +
                ", lat=" + lat +
                ", longi=" + longi +
                '}';
    }
}