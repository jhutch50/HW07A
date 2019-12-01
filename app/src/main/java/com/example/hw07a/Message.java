package com.example.hw07a;

import com.google.firebase.firestore.Exclude;

import java.util.HashMap;
import java.util.Map;

public class Message {
     String text, firstName, lastName, time, image;

    public Message() {
    }

    public Message(String text, String firstName, String lastName, String time, String image) {
        this.text = text;
        this.firstName = firstName;
        this.lastName = lastName;
        this.time = time;
        this.image = image;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
    @Override
    public String toString() {
        return "Message{" +
                "text='" + text + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", time='" + time + '\'' +
                ", image='" + image + '\'' +
                '}';
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("firstName", firstName);
        result.put("lastName",lastName);
        result.put("text",text);
        result.put("time",time);
        result.put("image",image);
        return result;
    }
}
