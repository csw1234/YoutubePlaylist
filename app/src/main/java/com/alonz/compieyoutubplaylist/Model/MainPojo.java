package com.alonz.compieyoutubplaylist.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by alonz on 15/01/2018.
 */
//A list of our PlayLists names
public class MainPojo {
    @SerializedName("Playlists")
    @Expose
    private List<Playlist> playlists = null;

    public List<Playlist> getPlaylists(){
        return playlists;
    }
}
