package com.example.db_14.travelplanner;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by a0104 on 2017-02-13.
 */

public class SightsActivity extends Activity {

    String areaCode, sigunguCode;
    ArrayList<HashMap<String, String>> sightsList = new ArrayList<HashMap<String, String>>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        areaCode =  getIntent().getStringExtra("areaCode");
        sigunguCode =  getIntent().getStringExtra("sigunguCode");

        final ListView listview ;
        final ListViewAdapter adapter;
        adapter = new ListViewAdapter();

        listview = (ListView) findViewById(R.id.list_view);
        listview.setAdapter(adapter);

        URLConnector conn = new URLConnector();
        conn.APIsights("areaBasedList?", areaCode, sigunguCode);
        sightsList = conn.getList();

        adapter.addItem(sightsList);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(SightsActivity.this, SightViewActivity.class);
                intent.putExtra("sightInfo", sightsList.get(i));
                startActivity(intent);
            }
        });
    }
}