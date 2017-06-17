package com.example.db_14.travelplanner;

/**
 * Created by heekyoung on 2017-06-16.
 */

public class ReviewListViewItem {
    private String title;
    private String reviewer;
<<<<<<< HEAD
    private int Count;

    public ReviewListViewItem(String title, String reviewer, int Count) {
        this.title = title;
        this.reviewer=reviewer;
        this.Count = Count;
=======
    private int count;

    public ReviewListViewItem(String title, String reviewer, int count) {
        this.title = title;
        this.reviewer=reviewer;
        this.count = count;
>>>>>>> origin/master
    }
    public String getTitle() {
        return title;
    }
    public String getReviewer() {
        return reviewer;
    }
    public int getCount() {
<<<<<<< HEAD
        return Count;
=======
        return count;
>>>>>>> origin/master
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public void setReviewer(String reviewer) {
        this.reviewer= reviewer;
    }
<<<<<<< HEAD
    public void setCount(int Count) {
        this.Count = Count;
=======
    public void setCount(int count) {
        this.count = count;
>>>>>>> origin/master
    }
}
