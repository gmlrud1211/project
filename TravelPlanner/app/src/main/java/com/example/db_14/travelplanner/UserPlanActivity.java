package com.example.db_14.travelplanner;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
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

/**
 * Created by a0104 on 2017-02-21.
 */

public class UserPlanActivity extends Activity {
    String usrid;
    ListView planview;
    Button add_plan;
    private ArrayList<String> plans;
    private ArrayList<String> pnos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan);
        add_plan = (Button)findViewById(R.id.addplan);
        usrid = getIntent().getStringExtra("USRID");
        plans = new ArrayList<String>(50);
        pnos = new ArrayList<String>(50);
        getPlan(usrid);


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

        add_plan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent add_p = new Intent(UserPlanActivity.this, PlanAddActivity.class);
                add_p.putExtra("usrid", usrid);
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
            String pname = ""; String pno = ""; String usrid = "";

            for (int i = 0; i < array.size(); i++) {

                JSONObject entity = (JSONObject) array.get(i);
                usrid = entity.get("usrid").toString();
                if(usrid.equals(id)) {
                    pname = entity.get("pname").toString();
                    pno = entity.get("planno").toString();
                    pnos.add(pno);
                    plans.add(pname);
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }
}
