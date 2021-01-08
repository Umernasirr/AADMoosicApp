package com.umernasirr.moosicapp;


import java.util.ArrayList;

public class PlaylistResponseById {
    private boolean success;

    private ArrayList<DataId> data;

    public boolean getSuccess() {
        return this.success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public ArrayList<DataId> getData() {
        return this.data;
    }

    public void setData(ArrayList<DataId> data) {
        this.data = data;
    }
}


class DataId {
    private ArrayList<SongModel> song;

    private String _id;

    private String name;

    private String description;

    private User user;

    private String createdAt;

    private int __v;

    public ArrayList<SongModel> getSong() {
        return this.song;
    }

    public void setSong(ArrayList<SongModel> song) {
        this.song = song;
    }

    public String get_id() {
        return this._id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getCreatedAt() {
        return this.createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public int get__v() {
        return this.__v;
    }

    public void set__v(int __v) {
        this.__v = __v;
    }
}
