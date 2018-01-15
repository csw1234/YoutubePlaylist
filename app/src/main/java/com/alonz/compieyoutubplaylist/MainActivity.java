package com.alonz.compieyoutubplaylist;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.alonz.compieyoutubplaylist.Adapters.ExpandableRcyclerAdapter;
import com.alonz.compieyoutubplaylist.Fragments.VideoFragment;
import com.alonz.compieyoutubplaylist.Model.ListItem;
import com.alonz.compieyoutubplaylist.Model.Playlist;
import com.alonz.compieyoutubplaylist.Presenter.PlayListPresenter;
import com.bignerdranch.expandablerecyclerview.Model.ParentListItem;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements PlayListPresenter.PlayListPresenterListener,VideoFragment.OnFragmentInteractionListener{

    //Define RecyclerView
    private RecyclerView mRecyclerView;
    //Define ExpandableAdapter
    private ExpandableRcyclerAdapter mPlayListAdapter;
    //Define RecyclerView ParentList
    List<ParentListItem> parentListItems = new ArrayList<>();
    //Define ProgressBar to show that were waiting for the list to load
    private ProgressBar progressBar;
    //Define error text view;
    private TextView error;

    //Define Presenter
    private PlayListPresenter playListPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Get RecyclerView by Id
        mRecyclerView = findViewById(R.id.recyclerView);
        //Get Progressbar by ID
        progressBar=findViewById(R.id.pb_listloading);
        //Show progressbar to indicate that wa are waiting for data
        progressBar.setVisibility(View.VISIBLE);
        //Get error text by ID
        error = findViewById(R.id.internet_error);
        //Get Reference to out Presenter
        playListPresenter = new PlayListPresenter(this);
        //Ask Last list from Presenter --Will return listReady() function that will activate setAdapter function
        playListPresenter.getList();
    }

    //Set last List inside the adapter
    public void setAdapter(List<Playlist> playlists){
        //Iterate over the List(PlayLists) and get each list into ParentList
        //Inside each PlayList, get a ListItems of each Playlist and set it into ChildList
        for(int i=0;i<playlists.size();i++){
            List<ListItem> listItem = playlists.get(i).getListItems();
            playlists.get(i).setChildItemList(listItem);
            parentListItems.add(playlists.get(i));
        }
        //Set LayoutManager for RecyclerView
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        //Attach List to our Expandable Adapter
        mPlayListAdapter = new ExpandableRcyclerAdapter(this,parentListItems);
        //If we get data successfully hide the progressbar
        progressBar.setVisibility(View.INVISIBLE);
        //Hide error text when data is ok
        error.setVisibility(View.INVISIBLE);
        //Set adapter to out RecyclerView
        mRecyclerView.setAdapter(mPlayListAdapter);
    }
    //Indicate that List ready to load (will be call from our Presenter)
    @Override
    public void listReady(List<Playlist> playlists) {
        setAdapter(playlists);
    }
    @Override
    public void showError(){
        progressBar.setVisibility(View.INVISIBLE);
        error.setVisibility(View.VISIBLE);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {}

    //    @Override
//    protected void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
//        mPlayListAdapter.onSaveInstanceState(outState);
//    }
//
//    @Override
//    protected void onRestoreInstanceState(Bundle savedInstanceState) {
//        super.onRestoreInstanceState(savedInstanceState);
//        mPlayListAdapter.onRestoreInstanceState(savedInstanceState);
//    }
}
