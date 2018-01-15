package com.alonz.compieyoutubplaylist.Service;
import com.alonz.compieyoutubplaylist.Model.MainPojo;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

/**
 * Created by alonz on 15/01/2017.
 */

public class ServiceGenerator {
    //Define BASE_URL to load from
    static final String BASE_URL = "http://www.leado.co.il";
    //Queue to get from our BASE_URL
    public interface PlayListsService {
        @GET("/clients/shahak/json.json")
        Call<MainPojo> getListTitle();
    }
    //Read from Playlist (MainPojo)
    public PlayListsService getList(){
        Retrofit retrofit = new Retrofit
                .Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(PlayListsService.class);
    }

}
