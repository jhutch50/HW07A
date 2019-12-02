package com.example.hw07a;

import android.net.Uri;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class profile implements Serializable {
    String fname,lname,gender;
    String image,id;

    public profile() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public profile(String fname, String lname, String gender, String image, String id) {
        this.fname = fname;
        this.lname = lname;
        this.gender = gender;
        this.image = image;
        this.id = id;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getImage() {
        return image;
    }

    public String getName(){
        return this.fname+" "+this.lname;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "profile{" +
                "fname='" + fname + '\'' +
                ", lname='" + lname + '\'' +
                ", gender='" + gender + '\'' +
                ", image=" + image +
                '}';
    }

    public Map toHashMap() {
        Map<String,Object> profilemap = new HashMap<>();
        profilemap.put("fname",this.fname);
        profilemap.put("lname",this.lname);
        profilemap.put("gender",this.gender);
        profilemap.put("image",this.image);
        profilemap.put("id",this.id);
        return profilemap;
    }
}
