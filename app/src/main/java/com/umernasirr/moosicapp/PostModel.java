package com.umernasirr.moosicapp;

public class PostModel {

    String id;
    String title;
    String description;
    String url;
    String user_name;
    String user_id;
    String createdAt;


    public PostModel(String id, String title, String description, String url, String user_name, String user_id, String createdAt) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.url = url;
        this.user_name = user_name;
        this.user_id = user_id;
        this.createdAt = createdAt;
    }


    public PostModel(String title, String description, String user_id, String url ) {
        this.title = title;
        this.description = description;
        this.url = url;
        this.user_id = user_id;
    }




    public String getCreatedAt(){
        return createdAt;
    }

    public void setCreatedAt(String createdAt){
        this.createdAt = createdAt;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }


}
