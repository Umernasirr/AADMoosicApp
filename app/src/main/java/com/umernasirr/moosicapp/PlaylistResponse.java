package com.umernasirr.moosicapp;


import java.util.ArrayList;
import java.util.List;

class DataPR
{
    private ArrayList<SongModel> song;

    private String _id;

    private String name;

    private String description;

    private User user;

    private String createdAt;

    private int __v;

    public void setSong(ArrayList<SongModel> song){
        this.song = song;
    }
    public ArrayList<SongModel> getSong(){
        return this.song;
    }
    public void set_id(String _id){
        this._id = _id;
    }
    public String get_id(){
        return this._id;
    }
    public void setName(String name){
        this.name = name;
    }
    public String getName(){
        return this.name;
    }
    public void setDescription(String description){
        this.description = description;
    }
    public String getDescription(){
        return this.description;
    }
    public void setUser(User user){
        this.user = user;
    }
    public User getUser(){
        return this.user;
    }
    public void setCreatedAt(String createdAt){
        this.createdAt = createdAt;
    }
    public String getCreatedAt(){
        return this.createdAt;
    }
    public void set__v(int __v){
        this.__v = __v;
    }
    public int get__v(){
        return this.__v;
    }
}

public class PlaylistResponse
{
    private boolean success;

    private ArrayList<DataPR> data;

    public void setSuccess(boolean success){
        this.success = success;
    }
    public boolean getSuccess(){
        return this.success;
    }
    public void setData(ArrayList<DataPR> data){
        this.data = data;
    }
    public ArrayList<DataPR> getData(){
        return this.data;
    }
}