package com.umernasirr.moosicapp;

import java.util.ArrayList;

public class PlaylistData {


    public ArrayList<PlaylistModel> playlistList;
    public static PlaylistData playlistData;

    private PlaylistData() {
        playlistList = new ArrayList<>();

        SongModel testSong1 = new SongModel(1, "I want it that way- Backstreet Boys", "www.test1.com", 1, "Umer");
        SongModel testSong2 = new SongModel(2, "Yesterday - Beetles ", "www.test2.com", 2, "Talal");
        SongModel testSong3 = new SongModel(3, "Wonderwall - Oasis ", "www.test3.com", 3,"Asbah");

        PlaylistModel playlistModel = new PlaylistModel(1,"Favourite Songs",new ArrayList<SongModel>());

        playlistModel.addSongToPlaylist(testSong1);
        playlistModel.addSongToPlaylist(testSong2);
        playlistModel.addSongToPlaylist(testSong3);

        playlistList.add(playlistModel);
    }

    public static PlaylistData getInstance() {
        if (playlistData == null) {
            playlistData = new PlaylistData();
        }
        return playlistData;
    }



}
