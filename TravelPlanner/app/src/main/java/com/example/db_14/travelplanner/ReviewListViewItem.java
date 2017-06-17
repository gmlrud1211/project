package com.example.db_14.travelplanner;

/**
 * Created by heekyoung on 2017-06-16.
 */

public class ReviewListViewItem {
    private String title;
    private String reviewer;
    private int count;

    public ReviewListViewItem(String title, String reviewer, int count) {
        this.title = title;
        this.reviewer=reviewer;
        this.count = count;
    }
    public String getTitle() {
        return title;
    }
    public String getReviewer() {
        return reviewer;
    }
    public int getCount() {
        return count;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public void setReviewer(String reviewer) {
        this.reviewer= reviewer;
    }
    public void setCount(int count) {
        this.count = count;
    }
}
