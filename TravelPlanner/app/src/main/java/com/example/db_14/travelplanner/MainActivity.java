package com.example.db_14.travelplanner;

import android.app.Activity;
import android.graphics.PointF;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.skp.Tmap.TMapMarkerItem;
import com.skp.Tmap.TMapPOIItem;
import com.skp.Tmap.TMapPoint;
import com.skp.Tmap.TMapView;

import java.util.ArrayList;

public class MainActivity extends Activity {

    String APPKEY = "2cfca2bc-7f91-3031-b69d-3c7eed12970c";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TMapView tMapView = new TMapView(this);
        tMapView.setSKPMapApiKey(APPKEY);

        FrameLayout mapLayout = (FrameLayout) findViewById(R.id.mapview);
        mapLayout.addView(tMapView);

        tMapView.setOnClickListenerCallBack(new TMapView.OnClickListenerCallback() {
            @Override
            public boolean onPressEvent(ArrayList<TMapMarkerItem> arrayList, ArrayList<TMapPOIItem> arrayList1, TMapPoint tMapPoint, PointF pointF) {
                
                return false;
            }

            @Override
            public boolean onPressUpEvent(ArrayList<TMapMarkerItem> arrayList, ArrayList<TMapPOIItem> arrayList1, TMapPoint tMapPoint, PointF pointF) {
                return false;
            }
        });
    }
}
