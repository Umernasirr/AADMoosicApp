package com.umernasirr.moosicapp;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class SongsData {

    public ArrayList<SongModel> songsList;
    public static SongsData songsData;

    private SongsData() {
        songsList = new ArrayList<>();

        SongModel testSong1 = new SongModel(1, "I want it that way- Backstreet Boys", "www.test1.com", 1, "Umer");
        SongModel testSong2 = new SongModel(2, "Yesterday - Beetles ", "www.test2.com", 2, "Talal");
        SongModel testSong3 = new SongModel(3, "Wonderwall - Oasis ", "www.test3.com", 3,"Asbah");


        songsList.add(testSong1);
        songsList.add(testSong2);
        songsList.add(testSong3);

    }

    public static SongsData getInstance() {
        if (songsData == null) {
            songsData = new SongsData();
        }
        return songsData;
    }


}
