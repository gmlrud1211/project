package com.example.db_14.travelplanner;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.skp.Tmap.TMapInfo;
import com.skp.Tmap.TMapMarkerItem;
import com.skp.Tmap.TMapPoint;
import com.skp.Tmap.TMapPolyLine;
import com.skp.Tmap.TMapView;

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

import static android.graphics.Paint.ANTI_ALIAS_FLAG;
import static com.example.db_14.travelplanner.R.drawable.marker;
import static com.example.db_14.travelplanner.R.drawable.start_marker;

/**
 * Created by heekyoung on 2017-06-19.
 */

public class FirstFragment  extends Fragment{

    String title, pno;
    int like;
    String APPKEY = "2cfca2bc-7f91-3031-b69d-3c7eed12970c";
    ArrayList<SightData> slist;
    ArrayList<TMapPoint> points, labels;
    Bitmap s_marker, e_marker, path;
    Button bt;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.first_frag, container, false);

        TextView tv = (TextView) v.findViewById(R.id.review_pname);
        FrameLayout mapLayout = (FrameLayout) v.findViewById(R.id.review_map);
        ListView lv = (ListView)v.findViewById(R.id.review_plist);
        bt = (Button)v.findViewById(R.id.review_like);
        tv.setText(title);
        bt.setText(String.valueOf(getReviewLike()));

        final TMapView tMapView = new TMapView(v.getContext());
        tMapView.setSKPMapApiKey(APPKEY);
        mapLayout.addView(tMapView);
        s_marker = BitmapFactory.decodeResource(v.getContext().getResources(), start_marker);
        e_marker = BitmapFactory.decodeResource(v.getContext().getResources(), R.drawable.end_marker);
        path = BitmapFactory.decodeResource(v.getContext().getResources(), marker);
        tMapView.setTMapPathIcon(s_marker, e_marker, path);

        if (points.size()>=0) {
            TMapInfo inf1 = tMapView.getDisplayTMapInfo(points);
            tMapView.setCenterPoint(inf1.getTMapPoint().getLongitude(), inf1.getTMapPoint().getLatitude());
            tMapView.setZoomLevel(10);
        }

        for (int i=0; i<points.size(); i++)
        {
            TMapMarkerItem item = new TMapMarkerItem();
            item.setTMapPoint(points.get(i));
            item.setVisible(TMapMarkerItem.VISIBLE);
            item.setIcon(path);
            item.setPosition((float) 0.5, (float) 1.0);
            tMapView.addMarkerItem("marker" + Integer.toString(i), item);
            TMapMarkerItem label = new TMapMarkerItem();
            label.setTMapPoint(points.get(i));
            label.setVisible(TMapMarkerItem.VISIBLE);
            Bitmap bitmap2 = StringToBitMap(slist.get(i).getSight());
            label.setIcon(bitmap2);
            label.setPosition((float) 0.5, (float) 3.25);
            tMapView.addMarkerItem("label" + Integer.toString(i), label);
        }

        TMapPolyLine line = new TMapPolyLine();
        for(int i=0; i<points.size(); i++)
        {
            line.addLinePoint(points.get(i));
        }

        line.setLineWidth(6);
        line.setOutLineWidth(0);
        line.setLineColor(Color.RED);
        tMapView.addTMapPath(line);

        ListAdapter adapter = new ListAdapter(v.getContext(), slist);
        lv.setAdapter(adapter);

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reviewLike();
                bt.setText(String.valueOf(getReviewLike()));
            }
        });

        return v;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            title = getArguments().getString("PNAME");
            slist = (ArrayList<SightData>) getArguments().getSerializable("SLIST");
            points = (ArrayList<TMapPoint>) getArguments().getSerializable("POINTS");
            pno = getArguments().getString("PNO");
        }
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

    public void reviewLike() {
        try{
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost("http://52.79.131.13/db_update.php");

            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            int like = getReviewLike() + 1;
            String query = "update review_info set plike="+like+" where pno="+pno;
            nameValuePairs.add(new BasicNameValuePair("query", query));
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "utf-8"));
            HttpResponse response = httpClient.execute(httpPost);
            ResponseHandler<String> responseHandler = new BasicResponseHandler();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public int getReviewLike()
    {
        String rlike="0";
        BufferedInputStream reader = null;
        URL url;
        StringBuffer buffer = null;

        try {
            url = new URL("http://52.79.131.13/review_db.php");
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
            for (int i = 0; i < array.size(); i++) {
                JSONObject entity = (JSONObject) array.get(i);
                if(entity.get("pno").toString().equals(pno)) {
                    rlike = entity.get("like").toString();
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return Integer.parseInt(rlike);
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
