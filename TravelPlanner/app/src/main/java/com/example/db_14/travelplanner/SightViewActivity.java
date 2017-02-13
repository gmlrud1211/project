package com.example.db_14.travelplanner;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by a0104 on 2017-02-13.
 */

public class SightViewActivity extends Activity {

    TextView title, overview;
    ImageView image;
    String contentid;
    ArrayList<HashMap<String, String>> sightinfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_sight);
        title = (TextView) findViewById(R.id.sight_title);
        overview = (TextView) findViewById(R.id.sight_overview);
        image = (ImageView) findViewById(R.id.sight_image);

        sightinfo = new ArrayList<HashMap<String, String>>();

        contentid = getIntent().getStringExtra("contentid");

        URLConnector conn = new URLConnector();
        conn.ApiJSON3("detailCommon?ServiceKey=dx6Je9L%2FluhYWHKwoLx0GoEk7VvDKF0ABstzCLgfe7MJIFpFQ3EhtGGs1TfPkuqbScvzFxVxbLjcrMrztNFV2w%3D%3D&MobileOS=ETC&MobileApp=TourAPI3.0_Guide&defaultYN=Y&firstImageYN=Y&areacodeYN=Y&catcodeYN=Y&addrinfoYN=Y&mapinfoYN=Y&overviewYN=Y&transGuideYN=Y&_type=json&contentid="+contentid);
        sightinfo = conn.getList();

        title.setText(sightinfo.get(0).get("title"));
        overview.setText(sightinfo.get(0).get("overview"));

        URLtoBItmap thread = new URLtoBItmap(sightinfo.get(0).get("firstimage"));
        thread.start();

        try {
            thread.join();
            image.setImageBitmap(thread.getBitmap());
        }
        catch (Exception e) {
            e.printStackTrace();
        }



    }
}
