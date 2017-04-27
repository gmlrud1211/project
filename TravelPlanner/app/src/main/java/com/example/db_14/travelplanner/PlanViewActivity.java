package com.example.db_14.travelplanner;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.skp.Tmap.TMapData;
import com.skp.Tmap.TMapInfo;
import com.skp.Tmap.TMapMarkerItem;
import com.skp.Tmap.TMapPoint;
import com.skp.Tmap.TMapPolyLine;
import com.skp.Tmap.TMapView;
import com.tsengvn.typekit.TypekitContextWrapper;

import org.json.simple.parser.JSONParser;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;

import java.io.BufferedInputStream;
import java.net.URL;
import java.util.ArrayList;

import static com.example.db_14.travelplanner.R.drawable.marker;
import static com.example.db_14.travelplanner.R.drawable.start_marker;

/**
 * Created by a0104 on 2017-04-25.
 */

public class PlanViewActivity extends Activity {
    String APPKEY = "2cfca2bc-7f91-3031-b69d-3c7eed12970c";
    TextView pname;
    ListView planlist;
    String sdate, fdate, pno;
    ArrayList<SightData> slist;
    ArrayList<TMapPoint> points;
    Button shortpath;
    ListViewAdapter adapter;
    ArrayList<TMapMarkerItem> markers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_view);
        TextView addsight = (TextView) findViewById(R.id.sight_add);
        pname = (TextView) findViewById(R.id.planv_pname);
        planlist = (ListView) findViewById(R.id.planv_list);
        shortpath = (Button) findViewById(R.id.shortpath);

        slist = new ArrayList<SightData>();
        points = new ArrayList<TMapPoint>();
        pno = getIntent().getStringExtra("PLANNO");
        sdate = getIntent().getStringExtra("SDATE");
        fdate = getIntent().getStringExtra("FDATE");
        pname.setText(getIntent().getStringExtra("PNAME"));

        getPlanlist(pno);

        final TMapView tMapView = new TMapView(this);
        tMapView.setSKPMapApiKey(APPKEY);
        FrameLayout mapLayout = (FrameLayout) findViewById(R.id.planv_map);
        mapLayout.addView(tMapView);
        Bitmap s_marker = BitmapFactory.decodeResource(getApplicationContext().getResources(), start_marker);
        Bitmap e_marker = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.end_marker);
        markers = new ArrayList<TMapMarkerItem>();
        tMapView.setTMapPathIcon(s_marker, e_marker);

        for (int i=0; i<points.size(); i++)
        {
            TMapMarkerItem item = new TMapMarkerItem();
            item.setTMapPoint(points.get(i));
            item.setVisible(TMapMarkerItem.VISIBLE);
            Bitmap bitmap = BitmapFactory.decodeResource(getApplicationContext().getResources(), marker);
            item.setIcon(bitmap);
            item.setPosition((float) 0.5, (float) 1.0);
            // 마커 설정
            markers.add(item);
            tMapView.addMarkerItem("marker" + Integer.toString(i), markers.get(i));
        }
        adapter = new ListViewAdapter(getApplicationContext(), slist);
        planlist.setAdapter(adapter);

        if (points.size()>=0) {
            TMapInfo inf1 = tMapView.getDisplayTMapInfo(points);
            tMapView.setCenterPoint(inf1.getTMapPoint().getLongitude(), inf1.getTMapPoint().getLatitude());
            tMapView.setZoomLevel(10);
        }

        shortpath.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ArrayList<TMapPoint> opt = new ArrayList<TMapPoint>();
                TMapPolyLine line = new TMapPolyLine();
                TMapData data = new TMapData();
                TMapPoint start, end;

                if (points.size() < 2) {
                    Toast.makeText(getApplicationContext(), "여행지 수가 너무 적습니다", Toast.LENGTH_SHORT).show();
                    return;
                }

                try {
                    TourRouteManager tourRouteManager = new TourRouteManager(points);
                    tourRouteManager.setOptimalRoute();

                    opt = tourRouteManager.getOptimalRoute();

                    for(int i=0; i<opt.size(); i++)
                    {
                        line.addLinePoint(opt.get(i));
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
                line.setLineWidth(6);
                line.setOutLineWidth(0);
                line.setLineColor(Color.RED);

                tMapView.addTMapPath(line);
            }
        });
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
            SightData data; TMapPoint point; String sname; double lat, lon;
            for (int i = 0; i < array.size(); i++) {
                JSONObject entity = (JSONObject) array.get(i);
                if(entity.get("planno").toString().equals(pno)) {
                    sname = entity.get("sname").toString();
                    lat = Double.parseDouble(entity.get("lat").toString());
                    lon = Double.parseDouble(entity.get("lon").toString());
                    point = new TMapPoint(lat, lon);
                    data = new SightData(sname, lat, lon);
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

    private class ListViewAdapter extends BaseAdapter
    {
        private Context context = null;
        private ArrayList<SightData> list = new ArrayList<SightData>();

        public ListViewAdapter(Context context, ArrayList<SightData> list){
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
                convertView = inflater.inflate(R.layout.planview_item, parent, false);
            }

            TextView sight = (TextView) convertView.findViewById(R.id.sight);
            sight.setText(list.get(pos).sight);

            return convertView;
        }
    }

}

