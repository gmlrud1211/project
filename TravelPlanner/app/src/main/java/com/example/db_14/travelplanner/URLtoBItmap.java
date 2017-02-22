package com.example.db_14.travelplanner;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by a0104 on 2017-02-13.
 */

public class URLtoBItmap extends Thread {
    String strurl;
    Bitmap bitmap;
    public URLtoBItmap(String strurl) {
        this.strurl = strurl;
    }

    @Override
    public void run() {
        try {
            URL url = new URL(strurl);

            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setDoInput(true);
            conn.connect();

            InputStream is = conn.getInputStream();
            bitmap = BitmapFactory.decodeStream(is);
        }
        catch (Exception e) {

        }
    }

    public Bitmap getBitmap() {
        return bitmap;
    }
}
