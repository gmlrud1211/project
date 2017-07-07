package com.example.db_14.travelplanner.Sights;

import java.io.Serializable;

/**
 * Created by heekyoung on 2017. 6. 22..
 */

public class SightListViewItem implements Serializable {

    private String subname;
    private String subdetailimg;
    private String contentid;

    public SightListViewItem(String subname, String subdetailimg, String contentid) {
        this.subname = subname;
        this.subdetailimg = subdetailimg;
        this.contentid = contentid;
    }

    public String getSubname()
    {
        return subname;
    }
    public String getSubdetailimg()
    {
        return subdetailimg;
    }
    public String getContentid() { return contentid; }

    public void setSubname(String subname)
    {
        this.subname= subname;
    }
    public void setSubdetailimg(String subdetailimg)
    {
        this.subdetailimg = subdetailimg;
    }
    public void setContentid(String contentid)
    {
        this.contentid = contentid;
    }
}
