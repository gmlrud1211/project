package com.example.db_14.travelplanner;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.tsengvn.typekit.TypekitContextWrapper;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.BufferedInputStream;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by heekyoung on 2017-06-16.
 */

public class ReviewActivity extends Activity {

    ListView review_activity_main;
    ArrayList<ReviewListViewItem> rlist;
    RListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_main);

        review_activity_main = (ListView)findViewById(R.id.review_activity_main);
        rlist = new ArrayList<ReviewListViewItem>();

        getReview();



        adapter = new RListAdapter(getApplicationContext(),rlist);
        review_activity_main.setAdapter(adapter);
    }

    public void getReview()
    {
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
            String title, text, like, usrid, pno;
            for (int i = 0; i < array.size(); i++) {
                JSONObject entity = (JSONObject) array.get(i);
                pno = entity.get("pno").toString();
                usrid = entity.get("usrid").toString();
                title = entity.get("pname").toString();
                like = entity.get("like").toString();
                text = entity.get("text").toString();
                rlist.add(new ReviewListViewItem(pno, title, usrid, Integer.parseInt(like), text));
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    private class RListAdapter extends BaseAdapter
    {
        private Context context = null;
        private ArrayList<ReviewListViewItem> list = new ArrayList<ReviewListViewItem>();

        public RListAdapter(Context context, ArrayList<ReviewListViewItem> list){
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
                convertView = inflater.inflate(R.layout.review_listview_item, parent, false);
            }

            TextView title = (TextView) convertView.findViewById(R.id.title);
            TextView reviewer = (TextView) convertView.findViewById(R.id.reviewer);
            TextView like = (TextView) convertView.findViewById(R.id.like);
            title.setText(list.get(pos).getTitle());
            reviewer.setText(list.get(pos).getReviewer());
            like.setText(String.valueOf(list.get(pos).getLike()));

            return convertView;
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }
}
