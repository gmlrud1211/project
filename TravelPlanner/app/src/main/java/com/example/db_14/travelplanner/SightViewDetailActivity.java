package com.example.db_14.travelplanner;

import android.app.Activity;
import android.content.Context;
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
import java.util.HashMap;

/**
 * Created by heekyoung on 2017. 6. 21..
 */

public class SightViewDetailActivity extends Activity {

    ListView layout_sight_detail;
    ArrayList<SightListViewItem> slist;
    SListAdapter adapter;
    TextView name;
    ImageView image;
    HashMap<String, String> sightdetailinfo = new HashMap<String, String>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_sight_detail_main);
        layout_sight_detail = (ListView)findViewById(R.id.layout_sight_detail);

        slist = new ArrayList<SightListViewItem>();

        adapter = new SListAdapter(getApplication(), slist);
        layout_sight_detail.setAdapter(adapter);

        layout_sight_detail.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

        name = (TextView)findViewById(R.id.sight_name);
        image = (ImageView)findViewById(R.id.sight_image);


        sightdetailinfo = (HashMap<String, String>)getIntent().getSerializableExtra("sightDetailInfo");
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

            return convertView;
        }
    }
    @Override
    protected void attachBaseContext(Context newBase){
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }
}
