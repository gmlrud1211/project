package com.example.db_14.travelplanner;
import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by heekyoung on 2017-06-19.
 */

public class FirstFragment  extends Fragment{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.first_frag, container, false);

        TextView tv = (TextView) v.findViewById(R.id.pname);
        tv.setText(getArguments().getString("msg"));

        ImageView iv = (ImageView)v.findViewById(R.id.sight_image);
        ListView lv = (ListView)v.findViewById(R.id.planv_list);
        Button bt = (Button)v.findViewById(R.id.like);

        return v;
    }

    public static FirstFragment newInstance(String text) {

        FirstFragment f = new FirstFragment();
        Bundle b = new Bundle();
        b.putString("msg", text);

        f.setArguments(b);

        return f;
    }
}
