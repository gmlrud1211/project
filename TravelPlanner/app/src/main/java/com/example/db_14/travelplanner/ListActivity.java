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
 * Created by db-14 on 2017. 2. 10..
 */
public class ListActivity extends Activity {

    ArrayList<HashMap<String, String>> areaList = new ArrayList<HashMap<String, String>>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        areaList =  (ArrayList<HashMap<String, String>>) getIntent().getSerializableExtra("areaList");

        final ListView listview ;
        final ListViewAdapter adapter;

        // Adapter 생성
        adapter = new ListViewAdapter() ;

        // 리스트뷰 참조 및 Adapter달기
        listview = (ListView) findViewById(R.id.list_view);
        listview.setAdapter(adapter);

        adapter.addItem(areaList);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(ListActivity.this, SigunguActivity.class);
                intent.putExtra("areaCode", areaList.get(i).get("code"));
                startActivity(intent);
            }
        });
    }

}

