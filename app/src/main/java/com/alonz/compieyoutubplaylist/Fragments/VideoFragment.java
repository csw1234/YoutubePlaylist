package com.alonz.compieyoutubplaylist.Fragments;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.alonz.compieyoutubplaylist.R;
import com.alonz.compieyoutubplaylist.YouTubePlayerConfig;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

//Fragment to show the YouTube video window
public class VideoFragment extends Fragment {
    //Define our player (YouTube library player)
    private YouTubePlayer player;
    // the fragment initialization parameters
    private static final String ARG_PARAM1 = "param1";
    //String that got from our list
    private String url;
    // Required empty public constructor
    public VideoFragment() {}
    //Use to create new Fragment instance
    public static VideoFragment newInstance(String param1) {
        VideoFragment fragment = new VideoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            url = getArguments().getString(ARG_PARAM1);
        }


    }

    //Get ID from YouTube URL
    //Example: https: //www.youtube.com/watch?v=SbCpzWMWb68 ->> "SbCpzWMWb68"
    public static String getYoutubeId(String ytUrl){
        String pattern = "(?<=watch\\?v=|/videos/|embed\\/|youtu.be\\/|\\/v\\/|\\/e\\/|watch\\?v%3D|watch\\?feature=player_embedded&v=|%2Fvideos%2F|embed%\u200C\u200B2F|youtu.be%2F|%2Fv%2F)[^#\\&\\?\\n]*";
        Pattern compiledPattern = Pattern.compile(pattern);
        Matcher matcher = compiledPattern.matcher(ytUrl); //url is youtube url for which you want to extract the id.
        if (matcher.find()) {
            return matcher.group();
        }return "";
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view =  inflater.inflate(R.layout.fragment_video, container, false);
        //Create new Instance of YouTubePlayerSupportFragment (Library)
        YouTubePlayerSupportFragment youTubePlayerSupportFragment = YouTubePlayerSupportFragment.newInstance();
        //Starting fragment transaction
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        //Add fragments to our this fragment
        transaction.add(R.id.youtube_player, youTubePlayerSupportFragment).commit();

        //Initialize YouTubePlayer (Load video from the ID we got from our adapter)
        youTubePlayerSupportFragment.initialize(YouTubePlayerConfig.API_KEY, new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                if(!b){
                    player=youTubePlayer;
                    player.loadVideo(getYoutubeId(url));
                    player.play();
                }
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                Log.e("youtubeErrorCode'", youTubeInitializationResult.toString());
            }
        });

        //Define "x" image view that used to close the player window
        ImageView closeView = view.findViewById(R.id.close_full_screen);
        //if closeView is pressed call the CloseWindow() functon
        closeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CloseWindow();
            }
        });
return view;

    }
    public void CloseWindow(){
        getFragmentManager().beginTransaction().remove(this).commit();
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
