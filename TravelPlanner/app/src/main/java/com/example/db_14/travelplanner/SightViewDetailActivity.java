package com.example.db_14.travelplanner;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.tsengvn.typekit.TypekitContextWrapper;

import java.util.ArrayList;

/**
 * Created by heekyoung on 2017. 6. 21..
 */

public class SightViewDetailActivity extends Activity {

    ListView layout_sight_detail;
    ArrayList<SightListViewItem> slist;
    SListAdapter adapter;
    TextView name;
    ImageView image;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_sight_detail_main);
        layout_sight_detail = (ListView)findViewById(R.id.layout_sight_detail);
        slist = new ArrayList<SightListViewItem>();
        slist = (ArrayList<SightListViewItem>)getIntent().getSerializableExtra("sightDetailInfo");

        adapter = new SListAdapter(getApplication(), slist);
        layout_sight_detail.setAdapter(adapter);

        layout_sight_detail.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(SightViewDetailActivity.this, SightViewActivity.class);
                intent.putExtra("CONTENTID", slist.get(position).getContentid());
                startActivity(intent);
            }
        });


    }

    private class SListAdapter extends BaseAdapter{

        private Context context = null;
        private ArrayList<SightListViewItem> slist = new ArrayList<SightListViewItem>();

        public SListAdapter(Context context, ArrayList<SightListViewItem> slist) {
            super();
            this.context = context;
            this.slist = slist;
        }

        @Override
        public int getCount() {
            return slist.size();
        }

        @Override
        public Object getItem(int position) {
            return slist.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if(convertView == null)
            {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.layout_sight_detail,parent,false);
            }

            ImageView image = (ImageView) convertView.findViewById(R.id.sight_image);
            TextView subname = (TextView) convertView.findViewById(R.id.sight_name);
            subname.setText(slist.get(position).getSubname());

            if(slist.get(position).getSubdetailimg() != "Image Not Found") {
                URLtoBItmap thread = new URLtoBItmap(slist.get(position).getSubdetailimg());
                thread.start();

                try {
                    thread.join();
                    image.setImageBitmap(thread.getBitmap());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            return convertView;
        }
    }
    @Override
    protected void attachBaseContext(Context newBase){
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }
}
