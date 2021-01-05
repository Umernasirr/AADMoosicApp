package com.umernasirr.moosicapp;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SongsResponse
{
    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("data")
    @Expose
    private List<Datum> data = null;
    private final static long serialVersionUID = -5056567197126801548L;

    /**
     * No args constructor for use in serialization
     *
     */
    public SongsResponse() {
    }

    /**
     *
     * @param data
     * @param success
     */
    public SongsResponse(Boolean success, List<Datum> data) {
        super();
        this.success = success;
        this.data = data;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public List<Datum> getData() {
        return data;
    }

    public void setData(List<Datum> data) {
        this.data = data;
    }
}

 class Datum implements Serializable
{

    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("user")
    @Expose
    private String user;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;
    @SerializedName("__v")
    @Expose
    private Integer v;
    private final static long serialVersionUID = -8386851159767913677L;

    /**
     * No args constructor for use in serialization
     *
     */
    public Datum() {
    }

    /**
     *
     * @param createdAt
     * @param v
     * @param description
     * @param id
     * @param user
     * @param url
     */
    public Datum(String id, String description, String user, String url, String createdAt, Integer v) {
        super();
        this.id = id;
        this.description = description;
        this.user = user;
        this.url = url;
        this.createdAt = createdAt;
        this.v = v;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getV() {
        return v;
    }

    public void setV(Integer v) {
        this.v = v;
    }

}


