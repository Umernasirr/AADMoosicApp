package com.umernasirr.moosicapp;

public class SongModel {
    int id;
    String name;
    String url;
    int user_id;
    String user_name;
    public SongModel(int id, String name, String url, int user_id, String user_name) {
        this.id = id;
        this.name = name;
        this.url = url;
        this.user_id = user_id;
        this.user_name = user_name;



    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public void setUser_name(String user_name){ this.user_name = user_name;}

    public String getUser_name(){
        return user_name;
    }

}
