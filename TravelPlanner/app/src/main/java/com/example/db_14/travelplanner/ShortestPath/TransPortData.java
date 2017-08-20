package com.example.db_14.travelplanner.ShortestPath;

/**
 * Created by 종합관417호 on 2017-08-16.
 */

public class TransPortData {
    public String lane;
    public String sname, ename;
    public long totaltime;

    public TransPortData(String lane, String sname, String ename, long totaltime) {
        this.lane = lane;
        this.sname = sname;
        this.ename = ename;
        this.totaltime = totaltime;
    }
}
