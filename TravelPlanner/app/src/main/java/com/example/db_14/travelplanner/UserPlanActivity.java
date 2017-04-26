package com.example.db_14.travelplanner;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.tsengvn.typekit.TypekitContextWrapper;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.BufferedInputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by a0104 on 2017-02-21.
 */

public class UserPlanActivity extends Activity implements AdapterView.OnItemClickListener {
    String usrid;
    ListView planview;
    Button add_plan;
    private ArrayList<String> plans;
    private HashMap<String, String> pinfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan);
        add_plan = (Button)findViewById(R.id.addplan);
        usrid = getIntent().getStringExtra("USRID");
        plans = new ArrayList<String>(50);
        pinfo = new HashMap<String, String>(50);
        getPlan(usrid);

        add_plan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(UserPlanActivity.this, PlanAddActivity.class);
                in.putExtra("USRID", usrid);
                startActivity(in);
            }
        });

        planview = (ListView)findViewById(R.id.plan_list);

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, plans) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent)
            {
                TextView textView = (TextView) super.getView(position, convertView, parent);
                int textColor = R.color.colorGrey;
                textView.setTextColor(UserPlanActivity.this.getResources().getColor(textColor));
                return textView; } };


        planview.setAdapter(adapter);
        planview.setOnItemClickListener(this);

        add_plan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent add_p = new Intent(UserPlanActivity.this, PlanAddActivity.class);
                add_p.putExtra("USRID", usrid);
                startActivity(add_p);
            }
        });
    }

    public void getPlan(String id)
    {
        BufferedInputStream reader = null;
        URL url;
        StringBuffer buffer = null;

        try {
            url = new URL("http://52.79.131.13/plan_db.php");
            reader = new BufferedInputStream(url.openStream()); // url 오픈 후 페이지 내 텍스트 모두 읽어옴
            buffer = new StringBuffer();
            int i;
            byte[] b = new byte[4096];

            while ((i = reader.read(b)) != -1) {
                buffer.append(new String(b, 0, i));
            }

        }

        catch (Exception e) {
            Log.e("ERROR : ", e.getMessage());
        }

        try {
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(buffer.toString());
            JSONArray array = (JSONArray) jsonObject.get("result");
            String pname = ""; String usrid = ""; String sdate=""; String fdate=""; String pno="";

            for (int i = 0; i < array.size(); i++) {

                JSONObject entity = (JSONObject) array.get(i);
                usrid = entity.get("usrid").toString();
                if(usrid.equals(id)) {
                    pname = entity.get("pname").toString();
                    sdate = entity.get("sdate").toString();
                    fdate = entity.get("fdate").toString();
                    pno = entity.get("planno").toString();
                    plans.add(pname);
                    pinfo.put("pname"+String.valueOf(i), pname);
                    pinfo.put("pno"+String.valueOf(i), pno);
                    pinfo.put("sdate", sdate);
                    pinfo.put("fdate", fdate);
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parentView, View clickedView, int position, long id)
    {
        Intent intent = new Intent(UserPlanActivity.this, PlanViewActivity.class); // 플랜 뷰 액티비티랑 연결할 것
        intent.putExtra("PLANNO", pinfo.get("pno"+String.valueOf(position)));
        intent.putExtra("SDATE", pinfo.get("sdate"+String.valueOf(position)));
        intent.putExtra("FDATE", pinfo.get("fdate"+String.valueOf(position)));
        intent.putExtra("PNAME", pinfo.get("pname"+String.valueOf(position)));
        startActivity(intent);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }
}
