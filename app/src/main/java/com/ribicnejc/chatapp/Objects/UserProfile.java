package com.ribicnejc.chatapp.Objects;

public class UserProfile {

    private String name;
    private boolean online;
    private String email;
    private String uid;

    public UserProfile(String name, String email, String uid, boolean online){
        this.name = name;
        this.online = online;
        this.email = email;
        this.uid = uid;
    }

    public UserProfile(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
