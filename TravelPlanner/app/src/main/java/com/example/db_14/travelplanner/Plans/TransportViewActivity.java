package com.example.db_14.travelplanner.Plans;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.db_14.travelplanner.R;
import com.example.db_14.travelplanner.ShortestPath.PassListAdapter;
import com.example.db_14.travelplanner.ShortestPath.PublicTransportRoute;
import com.example.db_14.travelplanner.ShortestPath.TransPortData;
import com.example.db_14.travelplanner.Sights.SightData;
import com.skp.Tmap.TMapInfo;
import com.skp.Tmap.TMapMarkerItem;
import com.skp.Tmap.TMapPoint;
import com.skp.Tmap.TMapPolyLine;
import com.skp.Tmap.TMapView;
import com.tsengvn.typekit.TypekitContextWrapper;
import java.util.ArrayList;

import static android.graphics.Paint.ANTI_ALIAS_FLAG;
import static com.example.db_14.travelplanner.R.drawable.marker;
import static com.example.db_14.travelplanner.R.drawable.start_marker;

/**
 * Created by 종합관417호 on 2017-08-14.
 */

public class TransportViewActivity extends Activity {
    String APPKEY = "2cfca2bc-7f91-3031-b69d-3c7eed12970c";
    Dialog dialog =  null;
    TextView pname;
    ListView planlist;
    ArrayList<TMapPoint> points;
    ArrayList<SightData> slist;
    PassListAdapter adapter;
    ArrayList<TMapMarkerItem> markers;
    ArrayList<TMapMarkerItem> labels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_view);
        Button shortpath = (Button) findViewById(R.id.shortpath); shortpath.setVisibility(View.INVISIBLE);
        Button addreview = (Button) findViewById(R.id.addreview); addreview.setVisibility(View.INVISIBLE);
        Button addTraveler = (Button) findViewById(R.id.addTraveler); addTraveler.setVisibility(View.INVISIBLE);
        pname = (TextView) findViewById(R.id.planv_pname); pname.setText("대중교통 경로");
        planlist = (ListView) findViewById(R.id.planv_list);
        slist = (ArrayList<SightData>) getIntent().getSerializableExtra("SLIST");

        getPoints(slist);

        dialog = new Dialog(this, R.style.MyDialog);
        dialog.setCancelable(true);
        dialog.addContentView(new ProgressBar(this), new ActionBar.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        dialog.show();

        final TMapView tMapView = new TMapView(this);
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

        if (points.size()>=0) {
            TMapInfo inf1 = tMapView.getDisplayTMapInfo(points);
            tMapView.setCenterPoint(inf1.getTMapPoint().getLongitude(), inf1.getTMapPoint().getLatitude());
            tMapView.setZoomLevel(14);
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                Looper.prepare();
                ArrayList<TMapPoint> opt = new ArrayList<TMapPoint>();
                ArrayList<TransPortData> pass = new ArrayList<TransPortData>();
                TMapPolyLine line = new TMapPolyLine();
                int res = 0;

                try {
                    PublicTransportRoute publicTransportRoute = new PublicTransportRoute(points);
                    res = publicTransportRoute.setOptimalRoute();

                    if(res == -1) {
                        Toast.makeText(getApplicationContext(), "대중교통 경로를 찾을 수 없습니다.",Toast.LENGTH_SHORT).show();
                        finish();
                    }
                    opt = publicTransportRoute.getOptimalRoute();
                    pass = publicTransportRoute.getOptimalPassList();
                    adapter = new PassListAdapter(getApplicationContext(), pass);

                    for(int i=0; i<opt.size(); i++)
                    {
                        line.addLinePoint(opt.get(i));
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
                line.setLineWidth(6);
                line.setOutLineWidth(0);
                line.setLineColor(Color.BLUE);
                tMapView.addTMapPath(line);
                dialog.dismiss();
                setAdapter();
                Looper.loop();
            }
        }).start();

    }

    public void setAdapter() {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                planlist.setAdapter(adapter);
            }
        });
    }
    public void getPoints(ArrayList<SightData> list) {
        points = new ArrayList<TMapPoint>();

        for(int i=0; i<list.size(); i++) {
            points.add(new TMapPoint(list.get(i).getLat(), list.get(i).getLon()));
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

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }
}
