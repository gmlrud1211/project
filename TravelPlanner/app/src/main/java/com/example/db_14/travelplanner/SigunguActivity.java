package com.example.db_14.travelplanner;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.tsengvn.typekit.TypekitContextWrapper;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by a0104 on 2017-02-13.
 */

public class SigunguActivity extends Activity {

    String areaCode, contenttypeid;
    ArrayList<HashMap<String, String>> sigunguList = new ArrayList<HashMap<String, String>>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        areaCode =  getIntent().getStringExtra("areaCode");
        contenttypeid = getIntent().getStringExtra("ContentTypeId");
        final ListView listview ;
        final ListViewAdapter adapter;

        // Adapter 생성
        adapter = new ListViewAdapter() ;

        // 리스트뷰 참조 및 Adapter달기
        listview = (ListView) findViewById(R.id.list_view);
        listview.setAdapter(adapter);

        URLConnector conn = new URLConnector();
        conn.APIareaCode("areaCode?areaCode="+areaCode+"&");
        sigunguList = conn.getList();
        adapter.addItem(sigunguList);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(SigunguActivity.this, SightsActivity.class);
                intent.putExtra("areaCode", areaCode);
                intent.putExtra("sigunguCode", sigunguList.get(i).get("code"));
                intent.putExtra("ContentTypeId", contenttypeid);
                intent.putExtra("usrid", getIntent().getStringExtra("usrid"));
                startActivity(intent);
            }
        });
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }
}
