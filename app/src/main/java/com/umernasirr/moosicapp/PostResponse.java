package com.umernasirr.moosicapp;
import java.util.ArrayList;

public class PostResponse
{
    private boolean success;

    private ArrayList<DataPost> data;

    public void setSuccess(boolean success){
        this.success = success;
    }
    public boolean getSuccess(){
        return this.success;
    }
    public void setData(ArrayList<DataPost> data){
        this.data = data;
    }
    public ArrayList<DataPost> getData(){
        return this.data;
    }
}

class DataPost
{
    private String _id;

    private String title;

    private String description;

    private String user;

    private String url;

    private String createdAt;

    private int __v;

    public void set_id(String _id){
        this._id = _id;
    }
    public String get_id(){
        return this._id;
    }
    public void setTitle(String title){
        this.title = title;
    }
    public String getTitle(){
        return this.title;
    }
    public void setDescription(String description){
        this.description = description;
    }
    public String getDescription(){
        return this.description;
    }
    public void setUser(String user){
        this.user = user;
    }
    public String getUser(){
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

