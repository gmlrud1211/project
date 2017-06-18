package com.example.db_14.travelplanner;

/**
 * Created by heekyoung on 2017-06-16.
 */

public class ReviewListViewItem {
    private String pno;
    private String title;
    private String reviewer;
    private String text;
    private int like;

    public ReviewListViewItem(String pno, String title, String reviewer, int like, String text) {
        this.pno = pno;
        this.title = title;
        this.reviewer=reviewer;
        this.like = like;
        this.text = text;
    }

    public String getPno() {
        return pno;
    }
    public String getTitle() {
        return title;
    }
    public String getReviewer() {
        return reviewer;
    }
    public String getText() {
        return text;
    }
    public int getLike() {
        return like;
    }

    public void setPno(String pno) {
        this.pno = pno;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public void setReviewer(String reviewer) {
        this.reviewer= reviewer;
    }
    public void setLike(int like) {
        this.like = like;
    }
    public void setText(String text) {
        this.text = text;
    }

}
