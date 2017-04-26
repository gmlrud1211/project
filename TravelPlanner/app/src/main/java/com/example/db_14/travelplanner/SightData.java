package com.example.db_14.travelplanner;

/**
 * Created by a0104 on 2017-04-25.
 */

public class SightData {
    public String day;
    public String sight;
    public double lat;
    public double lon;

    public SightData(String sight, double lat, double lon) {
        this.sight = sight;
        this.lat = lat;
        this.lon = lon;
    }

    public void setDay(String day)
    {
        this.day = day;
    }

    public String getDay()
    {
        return day;
    }

    public void setSight(String sight)
    {
        this.sight = sight;
    }

    public String getSight()
    {
        return sight;
    }

    public void setLat(double lat)
    {
        this.lat= lat;
    }

    public double getLat()
    {
        return lat;
    }

    public void setLon(double lon)
    {
        this.lon= lon;
    }

    public double getLon() { return lon; }
}
