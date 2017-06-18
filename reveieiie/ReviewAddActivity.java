package com.example.db_14.travelplanner;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by heekyoung on 2017-06-17.
 */

public class ReviewAddActivity extends Activity {

    EditText pname, subtext;
    Button add_btn;


    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.review_add);

        pname = (EditText)findViewById(R.id.plan_name);
        subtext = (EditText)findViewById(R.id.subtext);

        add_btn = (Button)findViewById(R.id.add_ok_btn);


    }

    protected void addReviewDB(String pname, String subtext) {
        try {
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost("http://52.79.131.13/review_add.php"); //review_add.php파일 작성해야함

            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            //String query = "insert into review_info(seqno, pno, usrid, like) values ('"+seqno+"','"+pno+"','"+usrid"','"+like+"','"+btitle+"','"+bprice+"')"; // 쿼리문 수정 및 db 테이블 추가 필요
            //nameValuePairs.add(new BasicNameValuePair("query", query));
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "utf-8"));
            HttpResponse response = httpClient.execute(httpPost);
            ResponseHandler<String> responseHandler = new BasicResponseHandler();
        }
        catch(Exception e) {
            e.getMessage();
        }
    }
}
