package com.example.db_14.travelplanner;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
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

public class ReviewActivity extends AppCompatActivity{

    private ListView lvReviewList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_main);

        lvReviewList = (ListView)findViewById(R.id.review_activity_main);

        List reviewList = new ArrayList();
        for(int i=0; i< 300; i++){
            reviewList.add(new ReviewListViewItem("제-목"+i,"작-성-자",new Random().nextInt(999)));

        }

        lvReviewList.setAdapter(new ReviewListViewAdapter(reviewList, this));
    }
    private class ReviewListViewAdapter extends BaseAdapter{

        //ListView에 세팅할 Item 정보들
        private List reviewList;

        //ListView에 Item을 세팅할 요청자의 정보
        private Context context;


        public ReviewListViewAdapter(List reviewList, Context context) {
            this.reviewList = reviewList;
            this.context = context;
        }

        //ListView에 세팅할 아이템 갯수
        @Override
        public int getCount() {
            return reviewList.size();
        }

        //position번째 item정보 가져옴
        @Override
        public Object getItem(int position) {
            return reviewList.get(position);
        }

        //아이템의 index를 가져옴
        @Override
        public long getItemId(int position) {
            return position;
        }

        /*ListView에 Item들 세팅
            position번 째 있는 아이템을 가져와서 converView에 넣은 다음 parent에서 보여줌
        */
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if(convertView ==null) {
              LayoutInflater inflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
              convertView = inflater.inflate(R.layout.review_listview_item, parent, false);
          }

            TextView title = (TextView) convertView.findViewById(R.id.title);
            TextView reviewer = (TextView) convertView.findViewById(R.id.reviewer);
            TextView count = (TextView) convertView.findViewById(R.id.count);


            ReviewListViewItem review = (ReviewListViewItem) getItem(position);
            title.setText(review.getTitle());
            reviewer.setText(review.getReviewer());
            count.setText(review.getCount()+"");

            return convertView;
        }

    }
}
