package com.ribicnejc.chatapp.Objects;

public class UserProfile {

    private String name;
    private boolean online;
    private String email;

    public UserProfile(String name, String email, boolean online){
        this.name = name;
        this.online = online;
        this.email = email;
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
}
