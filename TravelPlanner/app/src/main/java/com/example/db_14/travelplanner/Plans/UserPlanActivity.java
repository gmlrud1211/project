package com.example.db_14.travelplanner.Plans;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.db_14.travelplanner.Accounts.AccountActivity;
import com.example.db_14.travelplanner.DB.DBHelper;
import com.example.db_14.travelplanner.R;
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
import java.util.List;

/**
 * Created by a0104 on 2017-02-21.
 */

public class UserPlanActivity extends Activity implements AdapterView.OnItemClickListener, View.OnClickListener {
    String usrid;
    PlanAdapter adapter;
    ListView planview;
    Button add_plan, remove_plan;
    private ArrayList<PlanData> plans;
    int is_bill;
    int is_add, is_remove, is_edit; double lat, lon; String contentid;
    int func=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan);
        final DBHelper dbHelper = new DBHelper(getApplicationContext(), "UserInfo.db", null, 1);
        usrid = dbHelper.getResult().get("usrid");

        add_plan = (Button)findViewById(R.id.addplan);
        remove_plan = (Button)findViewById(R.id.removeplan);
        add_plan.setOnClickListener(this);
        remove_plan.setOnClickListener(this);

        is_add = getIntent().getIntExtra("ADDPLAN", 0);
        is_bill = getIntent().getIntExtra("ISBILL", 0);

        if(is_bill==1)
        {
            add_plan.setVisibility(View.INVISIBLE);
            remove_plan.setVisibility(View.INVISIBLE);
        }

        if(is_add==1) {
            lat = Double.parseDouble(getIntent().getStringExtra("LAT"));
            lon = Double.parseDouble(getIntent().getStringExtra("LON"));
            contentid = getIntent().getStringExtra("CONTENTID");
        }
        plans = new ArrayList<PlanData>();
        getPlan(usrid);

        planview = (ListView)findViewById(R.id.plan_list);

        adapter = new PlanAdapter(getApplicationContext(), plans);

        planview.setAdapter(adapter);
        planview.setOnItemClickListener(this);

        planview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Intent in = new Intent(UserPlanActivity.this, PlanEditActivity.class);
                in.putExtra("PNAME", plans.get(position).pname);
                in.putExtra("PNO", plans.get(position).pno);
                in.putExtra("SDATE", plans.get(position).sdate);
                in.putExtra("FDATE", plans.get(position).fdate);
                is_edit=1;
                startActivityForResult(in, 1);
                return false;
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
                    plans.add(new PlanData(pno, pname, sdate, fdate));
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
            addsight(plans.get(position).pno, usrid, getIntent().getStringExtra("SIGHTTITLE"), lat, lon, contentid);
        }
        else if(is_remove==1)
        {
            deleteplan(plans.get(position).pno);
            plans.clear();
            getPlan(usrid);
            adapter.notifyDataSetChanged();
            is_remove=0;
            return;
        }
        else if(is_bill==1)
        {
            Intent intent = new Intent(UserPlanActivity.this, AccountActivity.class);
            intent.putExtra("PLANNO", plans.get(position).pno);
            intent.putExtra("PNAME", plans.get(position).pname);
            startActivity(intent);
            return;
        }
        else if(is_edit==1)
        {
            return;
        }
        Intent intent = new Intent(UserPlanActivity.this, PlanViewActivity.class); // 플랜 뷰 액티비티랑 연결할 것
        intent.putExtra("PLANNO", plans.get(position).pno);
        intent.putExtra("SDATE", plans.get(position).sdate);
        intent.putExtra("FDATE", plans.get(position).fdate);
        intent.putExtra("PNAME", plans.get(position).pname);
        startActivity(intent);
    }

    public class PlanData {
        String pno;
        String pname;
        String sdate;
        String fdate;

        public PlanData(String pno, String pname, String sdate, String fdate)
        {
            this.pno = pno;
            this.pname = pname;
            this.sdate = sdate;
            this.fdate = fdate;
        }
    }

    @Override
    protected void onActivityResult(int request, int result, Intent in)
    {
        if (request==1)
        {
            if(result==RESULT_OK)
            {
                plans.clear();
                getPlan(usrid);
                adapter.notifyDataSetChanged();
                is_edit=0;
            }
        }
    }

    public void addsight(String pno, String usrid, String sname, double lat, double lon, String contentid)
    {
        try{
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost("http://52.79.131.13/db_insert.php");

            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            String query = "insert into plan_info(planno, usrid, sname, lat, lon, contentid) values ('"+pno+"','"+usrid+"','"+sname+"','"+lat+"','"+lon+"','"+contentid+"')"; // 쿼리문 수정 및 db 테이블 추가 필요
            nameValuePairs.add(new BasicNameValuePair("query", query));
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "utf-8"));
            HttpResponse response = httpClient.execute(httpPost);
            ResponseHandler<String> responseHandler = new BasicResponseHandler();
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

    private class PlanAdapter extends BaseAdapter
    {
        private Context context = null;
        private ArrayList<PlanData> list = new ArrayList<PlanData>();

        public PlanAdapter(Context context, ArrayList<PlanData> list){
            super();
            this.context = context;
            this.list = list;
        }

        @Override
        public int getCount()
        {
            return list.size();
        }

        @Override
        public Object getItem(int position)
        {
            return list.get(position);
        }

        @Override
        public long getItemId(int position)
        {
            return position;
        }

        @Override
        public View getView(int pos, View convertView, ViewGroup parent)
        {
            if (convertView==null)
            {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.listview_item, parent, false);
            }
            TextView title = (TextView) convertView.findViewById(R.id.name);
            title.setText(list.get(pos).pname);
            return convertView;
        }
    }

}

