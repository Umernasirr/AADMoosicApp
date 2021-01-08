package com.umernasirr.moosicapp;


import java.io.Serializable;

public class SongModel implements Serializable {

    private String _id;

    private String description;

    private User user;

    private String url;

    private String createdAt;

    private int __v;

    public SongModel(String _id, String description, User user, String url, String createdAt, int __v) {
        this._id = _id;
        this.description = description;
        this.user = user;
        this.url = url;
        this.createdAt = createdAt;
        this.__v = __v;
    }

    public void set_id(String _id){
        this._id = _id;
    }
    public String get_id(){
        return this._id;
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
    public void setUrl(String url){
        this.url = url;
    }
    public String getUrl(){
        return this.url;
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
