package com.example.db_14.travelplanner;

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
 * Created by db-14 on 2017. 6. 18..
 */

public class BookmarkActivity extends Activity {

    ArrayList<Bdata> bList = new ArrayList<Bdata>();
    String usrid;
    Button d_bookmark;
    ListView listview;
    int is_remove=0;
    ListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmark);
        usrid = getIntent().getStringExtra("USRID");
        listview = (ListView) findViewById(R.id.blistview);
        d_bookmark = (Button) findViewById(R.id.delete_b);

        getBookmark(usrid);
        adapter = new ListAdapter(getApplicationContext(), bList);
        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(is_remove==1)
                {
                    deleteBookmark(bList.get(position).pcode);
                    bList.clear();
                    getBookmark(usrid);
                    adapter.notifyDataSetChanged();
                    return;
                }

                Intent in = new Intent(BookmarkActivity.this, SightViewActivity.class);
                in.putExtra("CONTENTID", bList.get(position).pcode);
                in.putExtra("usrid", usrid);
                startActivity(in);
            }
        });

        d_bookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "삭제할 항목을 선택하세요.", Toast.LENGTH_SHORT).show();
                is_remove=1;
            }
        });
    }

    public void deleteBookmark(String contentid)
    {
        try{
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost("http://52.79.131.13/db_delete.php");

            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(3);
            String query = "delete from bookmark where p_code="+contentid+" and usrid='"+usrid+"'"; // 쿼리문 수정 및 db 테이블 추가 필요
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

    public void getBookmark(String usrid)
    {
        BufferedInputStream reader = null;
        URL url;
        StringBuffer buffer = null;

        try {
            url = new URL("http://52.79.131.13/bookmark_db.php");
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
            String sname, pcode;
            for (int i = 0; i < array.size(); i++) {
                JSONObject entity = (JSONObject) array.get(i);
                if(entity.get("usrid").toString().equals(usrid)) {
                    sname = entity.get("sname").toString();
                    pcode = entity.get("p_code").toString();
                    bList.add(new Bdata(sname, pcode));
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    public class Bdata {
        public String sname;
        public String pcode;

        public Bdata(String sname, String pcode)
        {
            this.sname = sname;
            this.pcode = pcode;
        }
    }

    private class ListAdapter extends BaseAdapter
    {
        private Context context = null;
        private ArrayList<Bdata> list = new ArrayList<Bdata>();

        public ListAdapter(Context context, ArrayList<Bdata> list){
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
            sight.setText(list.get(pos).sname);

            return convertView;
        }
    }

}
