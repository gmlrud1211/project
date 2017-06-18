package com.example.db_14.travelplanner;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by heekyoung on 2017-06-16.
 */

public class ReviewActivity extends ActionBarActivity {

    private ListView review_activity_main;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_main);

        review_activity_main = (ListView)findViewById(R.id.review_activity_main);

        List reviewList = new ArrayList();
        for(int i=0; i< 300; i++){
            reviewList.add(new ReviewListViewItem("제-목"+i,"작-성-자",new Random().nextInt(999)));

        }
        review_activity_main.setAdapter(new ReviewListViewAdapter(reviewList, this));
    }
    private class ReviewListViewAdapter extends BaseAdapter{

        private List reviewList;
        private Context context;

        public ReviewListViewAdapter(List reviewList, Context context) {
            this.reviewList = reviewList;
            this.context = context;
        }

        @Override
        public int getCount() {
            return reviewList.size();
        }

        @Override
        public Object getItem(int position) {
            return reviewList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
          if(convertView ==null) {
              LayoutInflater inflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
              convertView = inflater.inflate(R.layout.review_listview_item, parent, false);
          }

            TextView title = (TextView) convertView.findViewById(R.id.title);
            TextView reviewer = (TextView) convertView.findViewById(R.id.reviewer);
            TextView Count = (TextView) convertView.findViewById(R.id.Count);


            ReviewListViewItem review = (ReviewListViewItem) getItem(position);
            title.setText(review.getTitle());
            reviewer.setText(review.getReviewer());
            Count.setText(review.getCount()+"");

            return convertView;
        }

    }
}
