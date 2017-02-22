package com.example.db_14.travelplanner;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by a0104 on 2017-02-13.
 */

public class SightViewActivity extends Activity {

    TextView title, overview, addr;
    Button map;
    ImageView image;
    String ovStr;
    HashMap<String, String> sightinfo = new HashMap<String, String>();
    ArrayList<HashMap<String, String>> sight = new ArrayList<HashMap<String, String>>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_sight);

        title = (TextView) findViewById(R.id.sight_title);
        overview = (TextView) findViewById(R.id.sight_overview);
        addr = (TextView) findViewById(R.id.sight_addr);
        image = (ImageView) findViewById(R.id.sight_image);
        map = (Button) findViewById(R.id.sight_map);

        sightinfo = (HashMap<String, String>) getIntent().getSerializableExtra("sightInfo");

        URLConnector conn = new URLConnector();
        conn.APIsightInfo("detailCommon?", sightinfo.get("contentid"));
        sight = conn.getList();
        ovStr = sight.get(0).get("overview");

        title.setText(sightinfo.get("title"));
        overview.setText(ovStr);
        addr.setText(conn.getList().get(0).get("addr1"));

        if(conn.getList().get(0).get("firstimage") != "Image Not Found") {
            URLtoBItmap thread = new URLtoBItmap(conn.getList().get(0).get("firstimage"));
            thread.start();

            try {
                thread.join();
                image.setImageBitmap(thread.getBitmap());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SightViewActivity.this, SightMapActivity.class);
                intent.putExtra("lat", sight.get(0).get("mapy"));
                intent.putExtra("lon", sight.get(0).get("mapx"));
                startActivity(intent);
            }
        });

    }
}
