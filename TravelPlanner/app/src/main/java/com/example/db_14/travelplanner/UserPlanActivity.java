package com.example.db_14.travelplanner;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

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
    private ArrayList<String> plans;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan);
        usrid = getIntent().getStringExtra("usrid");
        plans = new ArrayList<String>();
        getPlan(usrid);

        planview = (ListView)findViewById(R.id.plan_list);

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, plans) ;

        planview.setAdapter(adapter);
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
            String pname = "";

            for (int i = 0; i < array.size(); i++) {

                JSONObject entity = (JSONObject) array.get(i);
                String usrid = entity.get("usrid").toString();
                if(usrid.equals(id)) {
                    pname = entity.get("pname").toString();
                    plans.add(i, pname);
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
