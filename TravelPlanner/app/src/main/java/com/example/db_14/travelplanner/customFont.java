package com.example.db_14.travelplanner;

import android.app.Application;

import com.tsengvn.typekit.Typekit;

/**
 * Created by a0104 on 2017-04-23.
 */

public class customFont extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Typekit.getInstance()
                .addNormal(Typekit.createFromAsset(this, "nanumsquare.ttf"))
                .addBold(Typekit.createFromAsset(this, "nanumsquareeb.ttf"));
    }
}
