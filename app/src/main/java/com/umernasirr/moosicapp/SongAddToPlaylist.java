package com.umernasirr.moosicapp;


public class SongAddToPlaylist
{
    private String _id;

    private String song;

    public SongAddToPlaylist(String playList_id, String song) {
        this._id = playList_id;
        this.song = song;
    }

    public void set_id(String _id){
        this._id = _id;
    }
    public String get_id(){
        return this._id;
    }
    public void setSong(String song){
        this.song = song;
    }
    public String getSong(){
        return this.song;
    }
}





