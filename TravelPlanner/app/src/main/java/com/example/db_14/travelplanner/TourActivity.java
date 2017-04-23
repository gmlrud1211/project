package com.example.db_14.travelplanner;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.skp.Tmap.TMapView;
import com.tsengvn.typekit.TypekitContextWrapper;

/**
 * Created by User on 2017-02-09.
 */

public class TourActivity extends Activity {

    String APPKEY = "2cfca2bc-7f91-3031-b69d-3c7eed12970c";
    FrameLayout mapLayout;
    Button getbtn, plan;
    //ArrayList<HashMap<String, String>> areaList = new ArrayList<HashMap<String, String>>();
    String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tour);

        mapLayout = (FrameLayout) findViewById(R.id.tourview);
        getbtn = (Button) findViewById(R.id.getbtn);
        plan = (Button) findViewById(R.id.plan);
        id = getIntent().getStringExtra("usrid");
        final TMapView tMapView = new TMapView(this);   // tmap

        tMapView.setSKPMapApiKey(APPKEY);               // key 연결
        tMapView.setZoomLevel(10);
        mapLayout.addView(tMapView);                    // layout에 tmap 연결 (view 띄워줌)

        getbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                URLConnector conn = new URLConnector();       // url커넥터에 요청 코드 보냄
                conn.APIareaCode("areaCode?");
                Intent list = new Intent(TourActivity.this, ListActivity.class);    // 리스트뷰 띄우줌
                list.putExtra("areaList", conn.getList());                          // 커넥터에 받아진 areacode 리스트 받아와서 보냄
                startActivity(list);                                                // 액티비티 실행
            }
        });


        plan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TourActivity.this, UserPlanActivity.class);
                intent.putExtra("usrid", id);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }
}



