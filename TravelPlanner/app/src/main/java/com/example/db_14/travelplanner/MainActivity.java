package com.example.db_14.travelplanner;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.skp.Tmap.TMapData;
import com.skp.Tmap.TMapMarkerItem;
import com.skp.Tmap.TMapPOIItem;
import com.skp.Tmap.TMapPoint;
import com.skp.Tmap.TMapPolyLine;
import com.skp.Tmap.TMapView;

import java.util.ArrayList;

public class MainActivity extends Activity {
    int id = 0;
    String usrid;
    String APPKEY = "2cfca2bc-7f91-3031-b69d-3c7eed12970c";
    TextView select;
    TextView text;
    Button tour, path;

    ArrayList<TMapMarkerItem> markers;
    ArrayList<TMapPoint> points;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        usrid = intent.getStringExtra("USRID");

        text = (TextView)findViewById(R.id.textview);
        select = (TextView)findViewById(R.id.select);
        tour = (Button)findViewById(R.id.tourtest);
        path = (Button)findViewById(R.id.path);

        markers = new ArrayList<TMapMarkerItem>();
        points = new ArrayList<TMapPoint>();

        tour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent tour = new Intent(MainActivity.this, TourActivity.class);
                startActivity(tour);
            }
        });

        final TMapView tMapView = new TMapView(this);

        text.setText(usrid + "님 반갑습니다. 클릭해주세요");

        tMapView.setSKPMapApiKey(APPKEY);
        tMapView.setZoomLevel(15);
        FrameLayout mapLayout = (FrameLayout) findViewById(R.id.mapview);
        mapLayout.addView(tMapView);
        tMapView.setOnLongClickListenerCallback(new TMapView.OnLongClickListenerCallback() {
            @Override
            public void onLongPressEvent(ArrayList<TMapMarkerItem> arrayList, ArrayList<TMapPOIItem> arrayList1, TMapPoint tMapPoint) {
                TMapMarkerItem item = new TMapMarkerItem();
                item.setTMapPoint(tMapPoint);
                item.setVisible(TMapMarkerItem.VISIBLE);
                Bitmap bitmap = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.marker);
                item.setIcon(bitmap);
                item.setPosition((float)0.5, (float)1.0);
                // 마커 설정
                markers.add(item);
                points.add(tMapPoint);
                tMapView.addMarkerItem("marker"+Integer.toString(id), markers.get(id));
                id++;

            }
        });



        path.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                TMapData tmapdata = new TMapData();
                TMapPoint start, end;
                start = points.get(0);
                end = points.get(points.size()-1);
                points.remove(0); points.remove(points.size()-1);

                tmapdata.findPathDataWithType(TMapData.TMapPathType.CAR_PATH, start, end, points, 0, new TMapData.FindPathDataListenerCallback() {
                    @Override
                    public void onFindPathData(TMapPolyLine tMapPolyLine) {
                        tMapPolyLine.setLineColor(Color.BLUE);
                        tMapView.addTMapPolyLine("path",tMapPolyLine);
                    }
                });

            }
        });
    }
}
