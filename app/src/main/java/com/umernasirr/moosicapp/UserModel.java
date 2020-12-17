package com.umernasirr.moosicapp;

public class UserModel {

    public UserModel(int id, String username, String password, String gender, String email, String[] playlist) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.gender = gender;
        this.email = email;
        this.playlist = playlist;
    }

    private int id;
    private String username;
    private String password;
    private String gender;
    private String email;
    private String[] playlist;
    
    public String[] getPlaylist() {
        return playlist;
    }

    public void setPlaylist(String[] playlist) {
        this.playlist = playlist;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
