package com.example.db_14.travelplanner;

import android.app.Activity;
import android.graphics.PointF;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.skp.Tmap.TMapMarkerItem;
import com.skp.Tmap.TMapPOIItem;
import com.skp.Tmap.TMapPoint;
import com.skp.Tmap.TMapView;

import java.util.ArrayList;

public class MainActivity extends Activity {
    int id=0;
    String APPKEY = "2cfca2bc-7f91-3031-b69d-3c7eed12970c";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView text = (TextView)findViewById(R.id.textview);

        final TMapView tMapView = new TMapView(this);

        tMapView.setSKPMapApiKey(APPKEY);

        FrameLayout mapLayout = (FrameLayout) findViewById(R.id.mapview);
        mapLayout.addView(tMapView);

        tMapView.setOnClickListenerCallBack(new TMapView.OnClickListenerCallback() {
            @Override
            public boolean onPressEvent(ArrayList<TMapMarkerItem> arrayList, ArrayList<TMapPOIItem> arrayList1, TMapPoint tMapPoint, PointF pointF) {
//                TMapMarkerItem tMapMarkerItem = new TMapMarkerItem();
//                tMapMarkerItem.setTMapPoint(tMapPoint);
//                tMapMarkerItem.setName("테스트");
//                tMapMarkerItem.setVisible(TMapMarkerItem.VISIBLE);
//
//                Bitmap bitmap = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.logo_image);
//                tMapMarkerItem.setIcon(bitmap);
//                tMapMarkerItem.setPosition((float)0.5, (float)1.0);
//
//                tMapView.addMarkerItem(Integer.toString(id++), tMapMarkerItem);
//                tMapView.setCenterPoint(tMapPoint.getLongitude(), tMapPoint.getLatitude());

//                text.setText(tMapMarkerItem.getName());
                return false;
            }

            @Override
            public boolean onPressUpEvent(ArrayList<TMapMarkerItem> arrayList, ArrayList<TMapPOIItem> arrayList1, TMapPoint tMapPoint, PointF pointF) {
                return false;
            }
        });
    }
}
