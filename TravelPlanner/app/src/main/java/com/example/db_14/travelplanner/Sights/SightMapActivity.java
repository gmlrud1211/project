package com.example.db_14.travelplanner.Sights;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.example.db_14.travelplanner.R;
import com.skp.Tmap.TMapMarkerItem;
import com.skp.Tmap.TMapPoint;
import com.skp.Tmap.TMapView;
import com.tsengvn.typekit.TypekitContextWrapper;

/**
 * Created by db-14 on 2017. 2. 14..
 */
public class SightMapActivity extends Activity {

    String APPKEY = "2cfca2bc-7f91-3031-b69d-3c7eed12970c";
    FrameLayout mapLayout;
    double lat, lon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_map);
        mapLayout = (FrameLayout) findViewById(R.id.sight_mapview);

        lat = Double.parseDouble(getIntent().getStringExtra("lat"));
        lon = Double.parseDouble(getIntent().getStringExtra("lon"));

        final TMapView tMapView = new TMapView(this);

        tMapView.setSKPMapApiKey(APPKEY);
        tMapView.setZoomLevel(15);
        mapLayout.addView(tMapView);

        TMapPoint tMapPoint = new TMapPoint(lat, lon);

        TMapMarkerItem item = new TMapMarkerItem();
        item.setTMapPoint(tMapPoint);
        item.setVisible(TMapMarkerItem.VISIBLE);
        Bitmap bitmap = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.marker);
        item.setIcon(bitmap);
        item.setPosition((float) 0.5, (float) 1.0);
        tMapView.addMarkerItem("marker", item);
        tMapView.setCenterPoint(tMapPoint.getLongitude(), tMapPoint.getLatitude());

    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }
}
