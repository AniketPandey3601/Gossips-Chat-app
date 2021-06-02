package com.example.gossips;

public class User {
    private String uid,name,phoneNumber,Profileimage;

    public User(){

    }

    public User(String uid, String phoneNumber, String name, String profileimage) {
        this.uid = uid;
        this.name = name;
        this.phoneNumber = phoneNumber;
        Profileimage = profileimage;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getProfileimage() {
        return Profileimage;
    }

    public void setProfileimage(String profileimage) {
        Profileimage = profileimage;
    }
}
