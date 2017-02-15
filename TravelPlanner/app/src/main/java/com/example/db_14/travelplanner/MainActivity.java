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
import android.widget.Toast;

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
    Button tour, path, reset;

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
        reset = (Button)findViewById(R.id.reset);

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
                item.setPosition((float) 0.5, (float) 1.0);
                // 마커 설정
                markers.add(item);
                points.add(tMapPoint);
                tMapView.addMarkerItem("marker" + Integer.toString(id), markers.get(id));
                id++;

            }
        });



        path.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ArrayList<TMapPoint> opt = new ArrayList<TMapPoint>();
                TMapPolyLine line = new TMapPolyLine();
                TMapData data = new TMapData();
                TMapPoint start, end;

                if (points.size() < 2) {
                    Toast.makeText(getApplicationContext(), "출발지와 도착지, 경유지를 최소 2개 이상 선택해주세요", Toast.LENGTH_SHORT).show();
                    return;
                }

                try {
                    TourRouteManager tourRouteManager = new TourRouteManager(points);
                    tourRouteManager.setOptimalRoute();

                    opt = tourRouteManager.getOptimalRoute();

                    if (opt.size() < 3) {
                        line = data.findPathData(opt.get(0), opt.get(opt.size() - 1));
                    } else {
                        start = opt.get(0);
                        end = opt.get(opt.size()-1);
                        opt.remove(0); opt.remove(opt.size()-1);
                        line = data.findMultiPointPathData(start, end, opt, 0);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                line.setLineWidth(2);
                line.setOutLineWidth(0);
                line.setLineColor(Color.MAGENTA);

                tMapView.addTMapPolyLine("path",line);
                points.clear();
            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tMapView.removeAllMarkerItem();
                tMapView.removeAllTMapPolyLine();
                points.clear();
                Toast.makeText(getApplicationContext(), "지도를 초기화했습니다.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
