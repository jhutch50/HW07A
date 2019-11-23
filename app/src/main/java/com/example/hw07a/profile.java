package com.example.hw07a;

import android.net.Uri;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class profile implements Serializable {
    String fname,lname,gender;
    String image;

    public profile() {
    }

    public profile(String fname, String lname, String gender, String image) {
        this.fname = fname;
        this.lname = lname;
        this.gender = gender;
        this.image = image;
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
        profilemap.put("uri",this.image);
        return profilemap;
    }
}
