package com.example.db_14.travelplanner;

/**
 * Created by a0104 on 2017-02-13.
 */

public class ListViewItem {
    private String titleStr ;
    private String imageURL;

    public void setTitle(String titleStr) {
        this.titleStr = titleStr ;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL ;
    }

    public String getTitle() {
        return this.titleStr;
    }

    public String getImageURL() {
        return this.imageURL;
    }
}