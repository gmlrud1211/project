package com.example.db_14.travelplanner;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.skp.Tmap.TMapView;

/**
 * Created by User on 2017-02-09.
 */

public class TourActivity extends Activity {

    String APPKEY = "2cfca2bc-7f91-3031-b69d-3c7eed12970c";
    String TOURKEY = "?ServiceKey=dx6Je9L%2FluhYWHKwoLx0GoEk7VvDKF0ABstzCLgfe7MJIFpFQ3EhtGGs1TfPkuqbScvzFxVxbLjcrMrztNFV2w%3D%3D&MobileOS=ETC&MobileApp=TravelPlanner&_type=json";
    FrameLayout mapLayout;
    TextView tourtext;
    Button getbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tour);

        mapLayout = (FrameLayout) findViewById(R.id.tourview);
        tourtext = (TextView) findViewById(R.id.tourtest);
        getbtn = (Button) findViewById(R.id.getbtn);

        final TMapView tMapView = new TMapView(this);

        tMapView.setSKPMapApiKey(APPKEY);
        tMapView.setZoomLevel(10);
        mapLayout.addView(tMapView);

        getbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "클릭", Toast.LENGTH_SHORT).show();mo
            }
        });
    }
}

