package com.alonz.compieyoutubplaylist.Adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.alonz.compieyoutubplaylist.Fragments.VideoFragment;
import com.alonz.compieyoutubplaylist.Model.ListItem;
import com.alonz.compieyoutubplaylist.Model.Playlist;
import com.alonz.compieyoutubplaylist.R;
import com.bignerdranch.expandablerecyclerview.Adapter.ExpandableRecyclerAdapter;
import com.bignerdranch.expandablerecyclerview.Model.ParentListItem;
import com.bignerdranch.expandablerecyclerview.ViewHolder.ChildViewHolder;
import com.bignerdranch.expandablerecyclerview.ViewHolder.ParentViewHolder;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by alonz on 15/01/2018.
 */

public class ExpandableRcyclerAdapter extends ExpandableRecyclerAdapter<ExpandableRcyclerAdapter.MyParentViewHolder, ExpandableRcyclerAdapter.MyChildViewHolder> {
    private LayoutInflater mInflater;
    private Context context;

    //Getting List items from the constructor and use it in our
    public ExpandableRcyclerAdapter(Context context, List<ParentListItem> itemList) {
        super(itemList);
        mInflater = LayoutInflater.from(context);
        this.context=context;
    }

    @Override
    public MyParentViewHolder onCreateParentViewHolder(ViewGroup viewGroup) {
        View view = mInflater.inflate(R.layout.playlists_list_parent, viewGroup, false);
        return new MyParentViewHolder(view);
    }

    @Override
    public MyChildViewHolder onCreateChildViewHolder(ViewGroup viewGroup) {
        View view = mInflater.inflate(R.layout.playlists_list_child, viewGroup, false);
        return new MyChildViewHolder(view);
    }

    @Override
    public void onBindParentViewHolder(MyParentViewHolder parentViewHolder, int position, ParentListItem parentListItem) {
        Playlist subcategoryParentListItem = (Playlist) parentListItem;
        // handler for row parent
        parentViewHolder.lblListHeader.setText(subcategoryParentListItem.getListTitle());

    }

    @Override
    public void onBindChildViewHolder(final MyChildViewHolder childViewHolder, int position, final Object childListItem) {
        final ListItem subcategoryChildListItem = (ListItem) childListItem;
        // handler for row child
        childViewHolder.txtListChild.setText(subcategoryChildListItem.getTitle());
        //Get thumb url from our ListItem (Child)
        String thumbUrl = subcategoryChildListItem.getThumb();
        //Fix thumbUrl
        thumbUrl=thumbUrl.replace(": ",":");
        //Present thumb image using Picasso
        Picasso.with(context).load(thumbUrl).into(childViewHolder.imageView, new com.squareup.picasso.Callback(){
            @Override
            public void onSuccess() {
                //Hide progressbar and error text
                childViewHolder.pb.setVisibility(View.INVISIBLE);
                childViewHolder.errorText.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onError() {
                //Hide progressbar and show error text
                childViewHolder.errorText.setVisibility(View.VISIBLE);
                childViewHolder.pb.setVisibility(View.INVISIBLE);
            }
        });

        //Open YouTubePlayer windows -- when image is clicked the video url will start playing
        childViewHolder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Create fragment for the player
                VideoFragment videoFragment = VideoFragment.newInstance(subcategoryChildListItem.getLink().toString());
                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                //Begin fragment transaction -- inside our fragments we will adding another fragment of "YouTubePlayerSupportFragment"
                //to show the player
                activity.getSupportFragmentManager().beginTransaction().add(R.id.content, videoFragment).addToBackStack(null).commit();
            }
        });
    }

    public class MyParentViewHolder extends ParentViewHolder {

        public TextView lblListHeader;

        public MyParentViewHolder(View itemView) {
            super(itemView);
            // init view parent
            lblListHeader =  itemView.findViewById(R.id.title);
        }
    }

    public class MyChildViewHolder extends ChildViewHolder {

        private TextView txtListChild;
        private ImageView imageView;
        private ProgressBar pb;
        private TextView errorText;

        public MyChildViewHolder(View itemView) {
            super(itemView);
            // init view child list
            txtListChild = itemView.findViewById(R.id.title);
            imageView = itemView.findViewById(R.id.image);
            pb=itemView.findViewById(R.id.pb);
            errorText=itemView.findViewById(R.id.image_error_text);
        }
    }


//    public void onSaveInstanceState(Bundle savedInstanceState) {
//        super.onSaveInstanceState(savedInstanceState);
//    }
//
//
//    public void onRestoreInstanceState(Bundle savedInstanceState) {
//        super.onRestoreInstanceState(savedInstanceState);
//    }
}
