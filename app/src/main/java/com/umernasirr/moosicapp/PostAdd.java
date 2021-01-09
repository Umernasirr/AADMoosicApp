package com.umernasirr.moosicapp;

public class PostAdd
{


    public PostAdd(String title, String description, String user, String url) {
        this.title = title;
        this.description = description;
        this.user = user;
        this.url = url;
    }

    private String title;

    private String description;

    private String user;

    private String url;

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
}

