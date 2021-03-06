package com.example.db_14.travelplanner.Plans;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Looper;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.db_14.travelplanner.DB.DBHelper;
import com.example.db_14.travelplanner.Dialogs.CustomDialog;
import com.example.db_14.travelplanner.Dialogs.InviteDialog;
import com.example.db_14.travelplanner.R;
import com.example.db_14.travelplanner.Reviews.ReviewAddActivity;
import com.example.db_14.travelplanner.ShortestPath.PublicTransportRoute;
import com.example.db_14.travelplanner.ShortestPath.TourRouteManager;
import com.example.db_14.travelplanner.Sights.SightData;
import com.example.db_14.travelplanner.Sights.SightViewActivity;
import com.skp.Tmap.TMapInfo;
import com.skp.Tmap.TMapMarkerItem;
import com.skp.Tmap.TMapPoint;
import com.skp.Tmap.TMapPolyLine;
import com.skp.Tmap.TMapView;
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
import java.io.Serializable;
import java.lang.reflect.Array;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static android.graphics.Paint.ANTI_ALIAS_FLAG;
import static com.example.db_14.travelplanner.R.drawable.marker;
import static com.example.db_14.travelplanner.R.drawable.start_marker;

/**
 * Created by a0104 on 2017-04-25.
 */

public class PlanViewActivity extends Activity {
    String APPKEY = "2cfca2bc-7f91-3031-b69d-3c7eed12970c", usrid;
    Dialog _dialog =  null;
    TextView pname;
    ListView planlist;
    String sdate, fdate, pno;
    ArrayList<SightData> slist;
    ArrayList<TMapPoint> points;
    Button shortpath, addreview, addTraveler;
    ListAdapter adapter;
    ArrayList<TMapMarkerItem> markers;
    ArrayList<TMapMarkerItem> labels;
    CustomDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_view);
        DBHelper dbHelper = new DBHelper(getApplicationContext(), "UserInfo.db", null, 1);
        usrid = dbHelper.getResult().get("usrid");
        TextView addsight = (TextView) findViewById(R.id.sight_add);
        pname = (TextView) findViewById(R.id.planv_pname);
        planlist = (ListView) findViewById(R.id.planv_list);
        shortpath = (Button) findViewById(R.id.shortpath);
        addreview = (Button) findViewById(R.id.addreview);
        addTraveler = (Button) findViewById(R.id.addTraveler);
        _dialog = new Dialog(this, R.style.MyDialog);
        _dialog.setCancelable(true);
        _dialog.addContentView(new ProgressBar(this), new ActionBar.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        slist = new ArrayList<SightData>();
        points = new ArrayList<TMapPoint>();
        pno = getIntent().getStringExtra("PLANNO");
        sdate = getIntent().getStringExtra("SDATE");
        fdate = getIntent().getStringExtra("FDATE");
        pname.setText(getIntent().getStringExtra("PNAME"));

        final TMapView tMapView = new TMapView(this);
        getPlanlist(pno);
        tMapView.setSKPMapApiKey(APPKEY);
        FrameLayout mapLayout = (FrameLayout) findViewById(R.id.planv_map);
        mapLayout.addView(tMapView);
        Bitmap s_marker = BitmapFactory.decodeResource(getApplicationContext().getResources(), start_marker);
        Bitmap e_marker = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.end_marker);
        markers = new ArrayList<TMapMarkerItem>();
        labels = new ArrayList<TMapMarkerItem>();
        tMapView.setTMapPathIcon(s_marker, e_marker);

        for (int i=0; i<points.size(); i++)
        {
            TMapMarkerItem item = new TMapMarkerItem();
            item.setTMapPoint(points.get(i));
            item.setVisible(TMapMarkerItem.VISIBLE);
            Bitmap bitmap1 = BitmapFactory.decodeResource(getApplicationContext().getResources(), marker);
            item.setIcon(bitmap1);
            item.setPosition((float) 0.5, (float) 1.0);
            markers.add(item);
            tMapView.addMarkerItem("marker" + Integer.toString(i), markers.get(i));
            TMapMarkerItem label = new TMapMarkerItem();
            label.setTMapPoint(points.get(i));
            label.setVisible(TMapMarkerItem.VISIBLE);
            Bitmap bitmap2 = StringToBitMap(slist.get(i).getSight());
            label.setIcon(bitmap2);
            label.setPosition((float) 0.5, (float) 3.25);
            labels.add(label);
            tMapView.addMarkerItem("label" + Integer.toString(i), labels.get(i));
        }

        adapter = new ListAdapter(getApplicationContext(), slist);
        planlist.setAdapter(adapter);

        if (points.size()>=0) {
            TMapInfo inf1 = tMapView.getDisplayTMapInfo(points);
            tMapView.setCenterPoint(inf1.getTMapPoint().getLongitude(), inf1.getTMapPoint().getLatitude());
            tMapView.setZoomLevel(10);
        }

        shortpath.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (points.size() < 2) {
                    Toast.makeText(getApplicationContext(), "여행지 수가 너무 적습니다", Toast.LENGTH_SHORT).show();
                    return;
                }

                View.OnClickListener cpListener = new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.cancel();
                        _dialog.show();
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                Looper.prepare();
                                ArrayList<TMapPoint> opt = new ArrayList<TMapPoint>();
                                TMapPolyLine line = new TMapPolyLine();

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
                                _dialog.dismiss();
                                Looper.loop();
                            }
                        }).start();

                    }
                };

                View.OnClickListener tpListener = new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(PlanViewActivity.this, TransportViewActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("SLIST", slist);
                        intent.putExtras(bundle);
                        startActivity(intent);
                        dialog.cancel();
                    }
                };

                dialog = new CustomDialog(v.getContext(), cpListener, tpListener, "최단 경로 탐색");
                dialog.show();
            }
        });

        addreview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(PlanViewActivity.this, ReviewAddActivity.class);
                in.putExtra("PNO", pno);
                in.putExtra("PNAME", pname.getText().toString());
                startActivity(in);
            }
        });

        addTraveler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InviteDialog dialog = new InviteDialog(v.getContext(), usrid, pno);
                dialog.show();
            }
        });

        planlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                tMapView.removeTMapPath();

                for (int i = 0; i <points.size(); i++) {
                    tMapView.getMarkerItemFromID("marker" + Integer.toString(i)).
                            setIcon(BitmapFactory.decodeResource(getApplicationContext().getResources(), marker));
                }

                tMapView.getMarkerItemFromID("marker" + Integer.toString(position)).
                        setIcon(BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.end_marker));
                tMapView.setCenterPoint(tMapView.getMarkerItemFromID("marker" + Integer.toString(position)).getTMapPoint().getLongitude(),tMapView.getMarkerItemFromID("marker" + Integer.toString(position)).getTMapPoint().getLatitude());
                tMapView.setZoomLevel(10);
            }
        });

        planlist.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(final AdapterView<?> parent, final View view, final int position, long id) {
                View.OnClickListener dListener = new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        deletesight(slist.get(position).contentid);
                        slist.clear();
                        points.clear();
                        markers.clear();
                        labels.clear();
                        tMapView.removeAllMarkerItem();
                        tMapView.removeTMapPath();
                        tMapView.ClearTile();
                        getPlanlist(pno);

                        for (int i=0; i<points.size(); i++)
                        {
                            TMapMarkerItem item = new TMapMarkerItem();
                            item.setTMapPoint(points.get(i));
                            item.setVisible(TMapMarkerItem.VISIBLE);
                            Bitmap bitmap = BitmapFactory.decodeResource(getApplicationContext().getResources(), marker);
                            item.setIcon(bitmap);
                            item.setPosition((float) 0.5, (float) 1.0);
                            markers.add(item);
                            tMapView.addMarkerItem("marker" + Integer.toString(i), markers.get(i));
                            TMapMarkerItem label = new TMapMarkerItem();
                            label.setTMapPoint(points.get(i));
                            label.setVisible(TMapMarkerItem.VISIBLE);
                            Bitmap bitmap2 = StringToBitMap(slist.get(i).getSight());
                            label.setIcon(bitmap2);
                            label.setPosition((float) 0.5, (float) 3.25);
                            labels.add(label);
                            tMapView.addMarkerItem("label" + Integer.toString(i), labels.get(i));
                        }

                        adapter.notifyDataSetChanged();
                        dialog.cancel();
                    }
                };

                View.OnClickListener cListener = new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(parent.getContext(), SightViewActivity.class);
                        intent.putExtra("CONTENTID", slist.get(position).contentid);
                        dialog.cancel();
                        startActivity(intent);
                    }
                };

                dialog = new CustomDialog(parent.getContext(), cListener, dListener, "여행지 정보");
                dialog.show();
                return false;
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

    private class ListAdapter extends BaseAdapter
    {
        private Context context = null;
        private ArrayList<SightData> list = new ArrayList<SightData>();

        public ListAdapter(Context context, ArrayList<SightData> list){
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

    public void deletesight(String contentid)
    {
        try{
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost("http://52.79.131.13/db_delete.php");

            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(3);
            String query = "delete from plan_info where planno="+pno+" and contentid='"+contentid+"'"; // 쿼리문 수정 및 db 테이블 추가 필요
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

    public Bitmap StringToBitMap(String text){
        Paint paint = new Paint(ANTI_ALIAS_FLAG);
        paint.setTextSize(20);
        paint.setColor(Color.BLACK);
        paint.setTextAlign(Paint.Align.LEFT);
        float baseline = -paint.ascent(); // ascent() is negative
        int width = (int) (paint.measureText(text) + 1.0f); // round
        int height = (int) (baseline + paint.descent() + 1.0f);
        Bitmap image = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(image);
        canvas.drawColor(Color.WHITE);
        canvas.drawText(text, 0, baseline, paint);
        return image;
    }
}