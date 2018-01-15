package com.alonz.compieyoutubplaylist.Model;


import com.bignerdranch.expandablerecyclerview.Model.ParentListItem;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by alonz on 15/01/2018.
 */
//Main PlayList
public class Playlist  implements ParentListItem {
    public List<ListItem> mChildItemList;


    @SerializedName("ListTitle")
    @Expose
    private String listTitle;
    @SerializedName("ListItems")
    @Expose
    private List<ListItem> listItems = null;

    public String getListTitle(){
        return listTitle;
    }

    public void setListTitle(String listTitle) {
        this.listTitle = listTitle;
    }

    public List<ListItem> getListItems() {
        return listItems;
    }

    public void setListItems(List<ListItem> listItems) {
        this.listItems = listItems;
    }
    public void setChildItemList(List<ListItem> list) {
        mChildItemList = list;
    }
    @Override
    public List<?> getChildItemList() {
        return mChildItemList;
    }

    @Override
    public boolean isInitiallyExpanded() {
        return false;
    }
}
