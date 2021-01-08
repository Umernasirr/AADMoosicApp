package com.umernasirr.moosicapp;

import java.util.ArrayList;
import java.io.Serializable;

public class PlaylistModel implements Serializable {

    String id;
    String name;
    String user;
    ArrayList<SongModel> songsList;


    public PlaylistModel(String id, String name, ArrayList<SongModel> songsList) {
        this.user= "5ff77d5a1d18b3001e4d183b";
        this.id = id;
        this.name = name;
        this.songsList = songsList;
    }

    public PlaylistModel(String name, String user) {
        this.name= name;
        this.user = user;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getUser() {
        return user;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<SongModel> getSongsList() {
        return songsList;
    }

    public void setSongsList(ArrayList<SongModel> songsList) {
        this.songsList = songsList;
    }

    public void addSongToPlaylist(SongModel songModel){
        songsList.add(songModel);
    }

    public void deleteSongFromPlaylist(SongModel songModel){
        songsList.remove(songModel);
    }
}
