package com.alonz.compieyoutubplaylist.Presenter;

import android.content.Context;


import com.alonz.compieyoutubplaylist.Model.MainPojo;
import com.alonz.compieyoutubplaylist.Model.Playlist;
import com.alonz.compieyoutubplaylist.Service.ServiceGenerator;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by alonz on 15/01/2018.
 */
//Define our Presenter -- Connects between MainActivity(View) and ServiceGenerator (Data)
public class PlayListPresenter {

    //Define Listener to indicate View that new data is coming
    private final PlayListPresenterListener mListener;
    //Create reference to Data (Retrofit)
    private final ServiceGenerator serviceGenerator;

    public interface PlayListPresenterListener{
        void listReady(List<Playlist> playlists);
        void showError();
    }

    public PlayListPresenter(PlayListPresenterListener listPresenterListener){
        this.mListener=listPresenterListener;
        this.serviceGenerator= new ServiceGenerator();
    }
    //Communicate with Data, asks new data from ServiceGenerator(Data) and use let the listener
    //publish that new data is available (In case of running, MainActivity is listening and will update the adapter
    public void getList(){
        serviceGenerator.getList().getListTitle().enqueue(new Callback<MainPojo>() {
            @Override
            public void onResponse(Call<MainPojo> call, Response<MainPojo> response) {
                MainPojo playlist = response.body();
                if (playlist != null){
                    mListener.listReady(playlist.getPlaylists());
                }
            }

            @Override
            public void onFailure(Call<MainPojo> call, Throwable t) {
                mListener.showError();
            }
        });
    }
}
