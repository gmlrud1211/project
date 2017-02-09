package com.example.db_14.travelplanner;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PointF;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.skp.Tmap.TMapMarkerItem;
import com.skp.Tmap.TMapPOIItem;
import com.skp.Tmap.TMapPoint;
import com.skp.Tmap.TMapView;

import java.util.ArrayList;

public class MainActivity extends Activity {
    int id=0;
    String usrid;
    String APPKEY = "2cfca2bc-7f91-3031-b69d-3c7eed12970c";
    TextView select;
    TextView text;
    Button tour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        usrid = intent.getStringExtra("USRID");

        text = (TextView)findViewById(R.id.textview);
        select = (TextView)findViewById(R.id.select);
        tour = (Button)findViewById(R.id.tourtest);

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

        FrameLayout mapLayout = (FrameLayout) findViewById(R.id.mapview);
        mapLayout.addView(tMapView);

        tMapView.setOnClickListenerCallBack(new TMapView.OnClickListenerCallback() {
            @Override
            public boolean onPressEvent(ArrayList<TMapMarkerItem> arrayList, ArrayList<TMapPOIItem> arrayList1, TMapPoint tMapPoint, PointF pointF) {
                TMapMarkerItem item = new TMapMarkerItem();
                item.setTMapPoint(tMapPoint);
                item.setVisible(TMapMarkerItem.VISIBLE);
                Bitmap bitmap = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.marker);
                item.setIcon(bitmap);

                item.setPosition((float)0.5, (float)1.0);

                tMapView.addMarkerItem("test", item);
                tMapView.setCenterPoint(tMapPoint.getLongitude(), tMapPoint.getLatitude());

                    if(select.getText().toString().equals("")) {
                        select.setText(Double.toString(tMapPoint.getLatitude()) + ", " + Double.toString(tMapPoint.getLongitude()) + "\n");
                    }
                    else
                    {
                        select.setText(select.getText().toString() + Double.toString(tMapPoint.getLatitude()) + ", " + Double.toString(tMapPoint.getLongitude()) + "\n");
                    }

                return false;
            }

            @Override
            public boolean onPressUpEvent(ArrayList<TMapMarkerItem> arrayList, ArrayList<TMapPOIItem> arrayList1, TMapPoint tMapPoint, PointF pointF) {
                return false;
            }
        });
    }
}
