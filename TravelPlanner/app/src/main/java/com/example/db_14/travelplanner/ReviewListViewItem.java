package com.example.db_14.travelplanner;

/**
 * Created by heekyoung on 2017-06-16.
 */

public class ReviewListViewItem {
    private String title;
    private String reviewer;
    private int Count;

    public ReviewListViewItem(String title, String reviewer, int Count) {
        this.title = title;
        this.reviewer=reviewer;
        this.Count = Count;
    }
    public String getTitle() {
        return title;
    }
    public String getReviewer() {
        return reviewer;
    }
    public int getCount() {
        return Count;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public void setReviewer(String reviewer) {
        this.reviewer= reviewer;
    }
    public void setCount(int Count) {
        this.Count = Count;
    }
}
