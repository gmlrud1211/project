package com.example.db_14.travelplanner;

/**
 * Created by heekyoung on 2017. 6. 22..
 */

public class SightListViewItem {

    private String subname;
    private String subdetailimg;

    public SightListViewItem(String subname, String subdetailimg) {
        this.subname = subname;
        this.subdetailimg = subdetailimg;
    }

    public String getSubname()
    {
        return subname;
    }
    public String getSubdetailimg()
    {
        return subdetailimg;
    }

    public void setSubname(String subname)
    {
        this.subname= subname;
    }
    public void setSubdetailimg()
    {
        this.subdetailimg = subdetailimg;
    }
}
