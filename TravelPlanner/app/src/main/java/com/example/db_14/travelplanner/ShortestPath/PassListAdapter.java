package com.example.db_14.travelplanner.ShortestPath;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.db_14.travelplanner.R;

import java.util.ArrayList;

/**
 * Created by 종합관417호 on 2017-08-16.
 */

public class PassListAdapter extends BaseAdapter {
    private Context context = null;
    private ArrayList<TransPortData> list = new ArrayList<TransPortData>();

    public PassListAdapter(Context context, ArrayList<TransPortData> list){
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
            convertView = inflater.inflate(R.layout.passlist_item, parent, false);
        }

        ImageView traffic_image = (ImageView) convertView.findViewById(R.id.traffic_image);
        TextView lane = (TextView) convertView.findViewById(R.id.lane);
        TextView start = (TextView) convertView.findViewById(R.id.start_stop);
        TextView end = (TextView) convertView.findViewById(R.id.end_stop);
        TextView total_time = (TextView) convertView.findViewById(R.id.total_time);

        if(list.get(pos).lane.contains("호선")) {
            lane.setText(list.get(pos).lane);
            traffic_image.setImageResource(R.drawable.subway);
        }
        else lane.setText(list.get(pos).lane+"번 버스");

        start.setText(list.get(pos).sname);
        end.setText(list.get(pos).ename);

        total_time.setText(String.valueOf(list.get(pos).totaltime)+"분 소요");

        return convertView;
    }
}
