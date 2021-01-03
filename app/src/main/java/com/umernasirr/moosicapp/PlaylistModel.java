package com.umernasirr.moosicapp;

import java.util.ArrayList;

public class PlaylistModel {

    int id;
    String name;
    ArrayList<SongModel> songsList;

    public PlaylistModel(int id, String name, ArrayList<SongModel> songsList) {
        this.id = id;
        this.name = name;
        this.songsList = songsList;
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
