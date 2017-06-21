package com.example.db_14.travelplanner;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
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
 * Created by db-14 on 2017. 6. 21..
 */

public class InviteListActivity extends Activity {

    ArrayList<Idata> iList = new ArrayList<Idata>();
    String usrid;
    ListView listview;
    ListAdapter adapter;
    CustomDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmark);
        final DBHelper dbHelper = new DBHelper(getApplicationContext(), "UserInfo.db", null, 1);
        usrid = dbHelper.getResult().get("usrid");
        listview = (ListView) findViewById(R.id.blistview);
        Button d_bookmark = (Button) findViewById(R.id.delete_b);
        d_bookmark.setVisibility(View.INVISIBLE);
        TextView title = (TextView) findViewById(R.id.bookmark_title);
        title.setText("초대받은 여행");

        NotificationManager nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        nm.cancelAll();

        getInvited(usrid);
        adapter = new ListAdapter(getApplicationContext(), iList);
        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                View.OnClickListener okListener = new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        GetPlan(iList.get(position).usrid, iList.get(position).pno);
                        iList.clear();
                        getInvited(usrid);
                        Toast.makeText(getApplicationContext(), "초대를 수락했습니다.", Toast.LENGTH_SHORT).show();
                        adapter.notifyDataSetChanged();
                        dialog.dismiss();
                    }
                };

                View.OnClickListener ccListener = new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        deleteInvited(iList.get(position).usrid, iList.get(position).pno);
                        Toast.makeText(getApplicationContext(), "초대를 거절했습니다.", Toast.LENGTH_SHORT).show();
                        iList.clear();
                        getInvited(usrid);
                        adapter.notifyDataSetChanged();
                        dialog.dismiss();
                    }
                };
                dialog = new CustomDialog(parent.getContext(),ccListener, okListener, "초대를 수락하시겠습니까?");
                dialog.show();
            }
        });
    }

    public void deleteInvited(String from_id, String pno)
    {
        try{
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost("http://52.79.131.13/db_delete.php");

            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(3);
            String query = "delete from invite_info where to_id='"+usrid+"' and pno="+pno; // 쿼리문 수정 및 db 테이블 추가 필요
            nameValuePairs.add(new BasicNameValuePair("query", query));
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "utf-8"));
            HttpResponse response = httpClient.execute(httpPost);
            ResponseHandler<String> responseHandler = new BasicResponseHandler();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public void getInvited(String usrid)
    {
        BufferedInputStream reader = null;
        URL url;
        StringBuffer buffer = null;

        try {
            url = new URL("http://52.79.131.13/invite_info_db.php");
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
            String pno, from_id;
            for (int i = 0; i < array.size(); i++) {
                JSONObject entity = (JSONObject) array.get(i);
                if(entity.get("to_id").toString().equals(usrid)) {
                    pno = entity.get("pno").toString();
                    from_id = entity.get("from_id").toString();
                    iList.add(new Idata(from_id, pno));
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    public class Idata {
        public String usrid;
        public String pno;

        public Idata(String usrid, String pno)
        {
            this.usrid = usrid;
            this.pno = pno;
        }
    }

    private class ListAdapter extends BaseAdapter
    {
        private Context context = null;
        private ArrayList<Idata> list = new ArrayList<Idata>();

        public ListAdapter(Context context, ArrayList<Idata> list){
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

            TextView sight = (TextView) convertView.findViewById(R.id.name);
            sight.setText(list.get(pos).usrid+"님의 초대");

            return convertView;
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }

    public void GetPlan(String from_id, String pno)
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
            String pname = ""; String sdate=""; String fdate="";
            for (int i = 0; i < array.size(); i++) {
                JSONObject entity = (JSONObject) array.get(i);
                if(pno.equals(entity.get("planno").toString())) {
                    pname = entity.get("pname").toString();
                    sdate = entity.get("sdate").toString();
                    fdate = entity.get("fdate").toString();
                }
            }
            AddPlan(pno, pname, sdate, fdate);

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        deleteInvited(from_id, pno);
    }

    public void AddPlan(String pno, String pname, String sdate, String fdate) {

        try{
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost("http://52.79.131.13/db_insert.php");

            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            String query = "insert into plan(planno, pname, usrid, sdate, fdate) values ('"+pno+"','"+pname+"','"+usrid+"','"+sdate+"','"+fdate+"')"; // 쿼리문 수정 및 db 테이블 추가 필요
            nameValuePairs.add(new BasicNameValuePair("query", query));
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "utf-8"));
            HttpResponse response = httpClient.execute(httpPost);
            ResponseHandler<String> responseHandler = new BasicResponseHandler();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
