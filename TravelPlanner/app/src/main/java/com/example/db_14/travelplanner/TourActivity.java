package com.example.db_14.travelplanner;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.skp.Tmap.TMapView;

/**
 * Created by User on 2017-02-09.
 */

public class TourActivity extends Activity {

    String APPKEY = "2cfca2bc-7f91-3031-b69d-3c7eed12970c";
    FrameLayout mapLayout;
    ListView aListview;
    Button getbtn;
    //ArrayList<HashMap<String, String>> areaList = new ArrayList<HashMap<String, String>>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tour);

        mapLayout = (FrameLayout) findViewById(R.id.tourview);
        getbtn = (Button) findViewById(R.id.getbtn);
        aListview = (ListView) findViewById(R.id.Alistview);

        final TMapView tMapView = new TMapView(this);   // tmap

        tMapView.setSKPMapApiKey(APPKEY);               // key 연결
        tMapView.setZoomLevel(10);
        mapLayout.addView(tMapView);                    // layout에 tmap 연결 (view 띄워줌)

        getbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                URLConnector conn = new URLConnector("areaCode?");       // url커넥터에 요청 코드 보냄
                Intent list = new Intent(TourActivity.this, ListActivity.class);    // 리스트뷰 띄우줌
                list.putExtra("areaList", conn.getList());                          // 커넥터에 받아진 areacode 리스트 받아와서 보냄
                startActivity(list);                                                // 액티비티 실행
            }
        });

    }
}



