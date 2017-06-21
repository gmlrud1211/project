package com.example.db_14.travelplanner;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by a0104 on 2017-02-13.
 */

public class SightViewActivity extends Activity implements Serializable {

    TextView titleview, overview, addr;
    Button map, bookmark, addplan, course;
    ImageView image;
    String ovStr, contentid;
    String usrid;
    HashMap<String, String> sightinfo = new HashMap<String, String>();
    ArrayList<HashMap<String, String>> sight = new ArrayList<HashMap<String, String>>();
    int is_recommend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_sight);

        final DBHelper dbHelper = new DBHelper(getApplicationContext(), "UserInfo.db", null, 1);
        usrid = dbHelper.getResult().get("usrid");
        is_recommend = getIntent().getIntExtra("is_recommend",0);

        titleview = (TextView) findViewById(R.id.sight_title);
        overview = (TextView) findViewById(R.id.sight_overview);
        addr = (TextView) findViewById(R.id.sight_addr);
        image = (ImageView) findViewById(R.id.sight_image);
        map = (Button) findViewById(R.id.sight_map);
        bookmark = (Button) findViewById(R.id.bookmark);
        addplan = (Button) findViewById(R.id.add_plan);
        course = (Button) findViewById(R.id.course_btn);

        sightinfo = (HashMap<String, String>) getIntent().getSerializableExtra("sightInfo");

        if(is_recommend==1) course.setVisibility(View.VISIBLE);

        if(sightinfo!=null) contentid = sightinfo.get("contentid");
        else contentid = getIntent().getStringExtra("CONTENTID");

        URLConnector conn = new URLConnector();
        conn.APIsightInfo("detailCommon?", contentid);
        sight = conn.getList();
        if(sight.get(0).get("overview")!="overview not Found") ovStr = sight.get(0).get("overview");
        else ovStr = "";

        ovStr = ovStr.replaceAll(System.getProperty("line.separator"), "\n");
        ovStr = ovStr.replaceAll("<br>", "\n");
        ovStr = ovStr.replaceAll("<br />", "\n");
        ovStr = ovStr.replaceAll("<BR>", "\n");
        ovStr = ovStr.replaceAll(">", "");
        ovStr = ovStr.replaceAll("&lt;", "");
        ovStr = ovStr.replaceAll("&gt;", "");
        ovStr = ovStr.replaceAll("&nbsp;", "");
        ovStr = ovStr.replaceAll("/", "");
        ovStr = ovStr.replaceAll("strong", "");
        ovStr = ovStr.replaceAll("&middot;", "");

        titleview.setText(sight.get(0).get("title"));
        overview.setText(ovStr);

        if(conn.getList().get(0).get("addr1")!="addr1 not Found")addr.setText(conn.getList().get(0).get("addr1"));
        else addr.setText("");

        if(conn.getList().get(0).get("firstimage") != "Image Not Found") {
            URLtoBItmap thread = new URLtoBItmap(conn.getList().get(0).get("firstimage"));
            thread.start();

            try {
                thread.join();
                image.setImageBitmap(thread.getBitmap());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SightViewActivity.this, SightMapActivity.class);
                intent.putExtra("lat", sight.get(0).get("mapy"));
                intent.putExtra("lon", sight.get(0).get("mapx"));
                startActivity(intent);
            }
        });

        bookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost("http://52.79.131.13/db_insert.php");

                    List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(3);
                    String query = "insert into bookmark (usrid, p_code, sname) values ('"+usrid+"', "+contentid+", '"+sight.get(0).get("title")+"')";
                    nameValuePairs.add(new BasicNameValuePair("query", query));
                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "utf-8"));
                    HttpResponse response = httpClient.execute(httpPost);

                    ResponseHandler<String> responseHandler = new BasicResponseHandler();

                    final String res = httpClient.execute(httpPost, responseHandler);

                    if(res.equalsIgnoreCase("success"))
                    {
                        Toast.makeText(getApplicationContext(), "즐겨찾기에 추가되었습니다.", Toast.LENGTH_SHORT).show();
                    }
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        addplan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(SightViewActivity.this, UserPlanActivity.class);
                in.putExtra("ADDPLAN", 1);
                in.putExtra("SIGHTTITLE", sight.get(0).get("title"));
                in.putExtra("LAT", sight.get(0).get("mapy"));
                in.putExtra("LON", sight.get(0).get("mapx"));
                in.putExtra("CONTENTID", contentid);
                startActivity(in);
                finish();
            }
        });
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }
}
