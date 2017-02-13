package com.example.db_14.travelplanner;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;

/**
 * Created by a0104 on 2017-02-13.
 */

public class SightViewActivity extends Activity {

    TextView title, overview, addr;
    ImageView image;
    String contentid;
    HashMap<String, String> sightinfo = new HashMap<String, String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_sight);

        title = (TextView) findViewById(R.id.sight_title);
        overview = (TextView) findViewById(R.id.sight_overview);
        addr = (TextView) findViewById(R.id.sight_addr);
        image = (ImageView) findViewById(R.id.sight_image);

        sightinfo = (HashMap<String, String>) getIntent().getSerializableExtra("sightInfo");

        URLConnector conn = new URLConnector();
        conn.APIsightInfo("detailCommon?", sightinfo.get("contentid"));
        title.setText(sightinfo.get("title"));
        overview.setText(conn.getList().get(0).get("overview"));
        addr.setText(sightinfo.get("addr1")+", "+sightinfo.get("addr2"));
        URLtoBItmap thread = new URLtoBItmap(sightinfo.get("firstimage"));
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
