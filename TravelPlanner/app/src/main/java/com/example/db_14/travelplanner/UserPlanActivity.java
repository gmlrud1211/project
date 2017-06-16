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
import android.widget.Toast;

import com.tsengvn.typekit.TypekitContextWrapper;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.BufferedInputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by a0104 on 2017-02-21.
 */

public class UserPlanActivity extends Activity implements AdapterView.OnItemClickListener, View.OnClickListener {
    String usrid;
    ArrayAdapter adapter;
    ListView planview;
    Button add_plan, remove_plan;
    private ArrayList<String> plans;
    private HashMap<String, String> pinfo;
    int is_add, is_remove; double lat, lon; String contentid;
    int func=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan);
        add_plan = (Button)findViewById(R.id.addplan);
        remove_plan = (Button)findViewById(R.id.removeplan);
        add_plan.setOnClickListener(this);
        remove_plan.setOnClickListener(this);

        usrid = getIntent().getStringExtra("USRID");
        is_add = getIntent().getIntExtra("ADDPLAN", 0);
        if(is_add==1) {
            lat = Double.parseDouble(getIntent().getStringExtra("LAT"));
            lon = Double.parseDouble(getIntent().getStringExtra("LON"));
            contentid = getIntent().getStringExtra("CONTENTID");
        }
        plans = new ArrayList<String>(50);
        pinfo = new HashMap<String, String>(50);
        getPlan(usrid);

        planview = (ListView)findViewById(R.id.plan_list);

        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, plans) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent)
            {
                TextView textView = (TextView) super.getView(position, convertView, parent);
                int textColor = R.color.colorGrey;
                textView.setTextColor(UserPlanActivity.this.getResources().getColor(textColor));
                return textView; } };


        planview.setAdapter(adapter);
        planview.setOnItemClickListener(this);

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
            int idx = 0;
            for (int i = 0; i < array.size(); i++) {

                JSONObject entity = (JSONObject) array.get(i);
                usrid = entity.get("usrid").toString();
                if(usrid.equals(id)) {
                    pname = entity.get("pname").toString();
                    sdate = entity.get("sdate").toString();
                    fdate = entity.get("fdate").toString();
                    pno = entity.get("planno").toString();
                    plans.add(pname);
                    pinfo.put("pname"+String.valueOf(idx), pname);
                    pinfo.put("pno"+String.valueOf(idx), pno);
                    pinfo.put("sdate", sdate);
                    pinfo.put("fdate", fdate);
                    idx++;
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
        if (is_add==1)
        {
            addsight(pinfo.get("pno"+String.valueOf(position)), usrid, getIntent().getStringExtra("SIGHTTITLE"), lat, lon, contentid);
        }
        else if(is_remove==1)
        {
            deleteplan(pinfo.get("pno"+String.valueOf(position)));
            plans.clear();
            pinfo.clear();
            getPlan(usrid);
            adapter.notifyDataSetChanged();
            is_remove=0;
            return;
        }
        Intent intent = new Intent(UserPlanActivity.this, PlanViewActivity.class); // 플랜 뷰 액티비티랑 연결할 것
        intent.putExtra("PLANNO", pinfo.get("pno"+String.valueOf(position)));
        intent.putExtra("SDATE", pinfo.get("sdate"+String.valueOf(position)));
        intent.putExtra("FDATE", pinfo.get("fdate"+String.valueOf(position)));
        intent.putExtra("PNAME", pinfo.get("pname"+String.valueOf(position)));
        startActivity(intent);
    }


    @Override
    protected void onActivityResult(int request, int result, Intent in)
    {
        if (request==1)
        {
            if(result==RESULT_OK)
            {
                switch (func)
                {
                    case 1:
                        plans.clear();
                        pinfo.clear();
                        getPlan(usrid);
                        adapter.notifyDataSetChanged();
                        break;
                }
            }
        }
    }

    public void addsight(String pno, String usrid, String sname, double lat, double lon, String contentid)
    {
        try{
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost("http://52.79.131.13/db_insert.php");

            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(3);
            String query = "insert into plan_info(planno, usrid, sname, lat, lon, contentid) values ('"+pno+"','"+usrid+"','"+sname+"','"+lat+"','"+lon+"','"+contentid+"')"; // 쿼리문 수정 및 db 테이블 추가 필요
            nameValuePairs.add(new BasicNameValuePair("query", query));
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "utf-8"));
            HttpResponse response = httpClient.execute(httpPost);
            ResponseHandler<String> responseHandler = new BasicResponseHandler();

            final String res = httpClient.execute(httpPost, responseHandler);

            if(res.equalsIgnoreCase("success"))
            {
                Toast.makeText(getApplicationContext(), res, Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(getApplicationContext(), res, Toast.LENGTH_SHORT).show();
                // db에서 res값을 보내지 않아 처리 불가능 문제 확인 필요
            }
            finish();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.addplan:
                Intent in = new Intent(UserPlanActivity.this, PlanAddActivity.class);
                in.putExtra("USRID", usrid);
                startActivityForResult(in, 1);
                func=1;
                break;
            case R.id.removeplan:
                Toast.makeText(this, "삭제할 계획을 선택하세요.", Toast.LENGTH_SHORT).show();
                is_remove=1;
                break;
        }
    }

    public void deleteplan(String pno)
    {
        try{
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost("http://52.79.131.13/db_delete.php");

            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(3);
            String query = "delete from plan where planno="+pno; // 쿼리문 수정 및 db 테이블 추가 필요
            nameValuePairs.add(new BasicNameValuePair("query", query));
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "utf-8"));
            HttpResponse response = httpClient.execute(httpPost);
            ResponseHandler<String> responseHandler = new BasicResponseHandler();

            final String res = httpClient.execute(httpPost, responseHandler);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}

