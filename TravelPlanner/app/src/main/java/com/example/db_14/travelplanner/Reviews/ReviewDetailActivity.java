package com.example.db_14.travelplanner.Reviews;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.db_14.travelplanner.R;
import com.example.db_14.travelplanner.Sights.SightData;
import com.skp.Tmap.TMapPoint;
import com.tsengvn.typekit.TypekitContextWrapper;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.BufferedInputStream;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by a0104 on 2017-06-17.
 */

public class ReviewDetailActivity extends AppCompatActivity {

    ViewPager mViewPager = null;
    String pno, pname, pid, ptext;
    int plike;
    ArrayList<SightData> slist;
    ArrayList<TMapPoint> points;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_detail);
        pno = getIntent().getStringExtra("PNO");
        pname = getIntent().getStringExtra("PNAME");
        plike = getIntent().getIntExtra("PLIKE",0);
        pid = getIntent().getStringExtra("PID");
        ptext = getIntent().getStringExtra("PTEXT");

        slist = new ArrayList<SightData>();
        points = new ArrayList<TMapPoint>();

        getPlanlist(pno);

        mViewPager = (ViewPager)findViewById(R.id.pager);
        MyViewPagerAdapter adapter = new MyViewPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(adapter);

    }

    @Override
    public void onBackPressed() {
        Intent in = new Intent();
        setResult(RESULT_OK, in);
        finish();
    }

    public class MyViewPagerAdapter extends FragmentStatePagerAdapter
    {
        Fragment[] fragments = new Fragment[2];

        public MyViewPagerAdapter (FragmentManager fm) {
            super(fm);
            fragments[0] = new FirstFragment();
            fragments[1] = new SecondFragment();
            Bundle frag1 = new Bundle();
            Bundle frag2 = new Bundle();
            frag2.putString("TEXT", ptext);
            frag1.putString("PNAME", pname);
            frag2.putString("PNAME", pname);
            frag1.putInt("PLIKE", plike);
            frag2.putString("PID", pid);
            frag1.putString("PNO", pno);
            frag2.putString("PNO", pno);
            frag1.putSerializable("SLIST", slist);
            frag2.putSerializable("SLIST", slist);
            frag1.putSerializable("POINTS", points);
            fragments[0].setArguments(frag1);
            fragments[1].setArguments(frag2);
        }

        public Fragment getItem(int arg0) {

            return fragments[arg0];

        }
	/*

	 * 보여질 프레그먼트가 몇개인지 결정

	 */

        public int getCount() {

            return fragments.length;

        }

    }

    public void getPlanlist(String pno)
    {
        BufferedInputStream reader = null;
        URL url;
        StringBuffer buffer = null;

        try {
            url = new URL("http://52.79.131.13/planinfo_db.php");
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
            SightData data; TMapPoint point; String sname, contentid; double lat, lon;
            for (int i = 0; i < array.size(); i++) {
                JSONObject entity = (JSONObject) array.get(i);
                if(entity.get("planno").toString().equals(pno)) {
                    sname = entity.get("sname").toString();
                    lat = Double.parseDouble(entity.get("lat").toString());
                    lon = Double.parseDouble(entity.get("lon").toString());
                    contentid = entity.get("contentid").toString();
                    point = new TMapPoint(lat, lon);
                    data = new SightData(sname, lat, lon, contentid);
                    points.add(point);
                    slist.add(data);
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