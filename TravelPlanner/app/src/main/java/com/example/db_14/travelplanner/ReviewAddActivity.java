package com.example.db_14.travelplanner;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.tsengvn.typekit.TypekitContextWrapper;

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

    EditText subtext;
    Button add_btn;
    String pno, usrid, pname, text;
    CustomDialog dialog;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.review_add);
        subtext = (EditText)findViewById(R.id.subtext);
        add_btn = (Button)findViewById(R.id.add_ok_btn);
        pno = getIntent().getStringExtra("PNO");
        pname = getIntent().getStringExtra("PNAME");
        usrid = getIntent().getStringExtra("USRID");

        add_btn.setOnClickListener(new View.OnClickListener() {
            View.OnClickListener cListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.cancel();
                }
            };

            View.OnClickListener sListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addReviewDB(pname, subtext.getText().toString());
                    dialog.cancel();
                    Toast.makeText(getApplicationContext(), "공유가 완료되었습니다.", Toast.LENGTH_SHORT).show();
                    finish();
                }
            };

            @Override
            public void onClick(View v) {
                dialog = new CustomDialog(v.getContext(), cListener, sListener, "일정을 공유하시겠습니까?");
                dialog.show();
            }
        });
    }

    protected void addReviewDB(String pname, String subtext) {
        try{
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost("http://52.79.131.13/db_insert.php");

            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            String query = "insert into review_info(pno, usrid, text) values ('"+pno+"','"+usrid+"','"+subtext+"')"; // 쿼리문 수정 및 db 테이블 추가 필요
            nameValuePairs.add(new BasicNameValuePair("query", query));
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "utf-8"));
            HttpResponse response = httpClient.execute(httpPost);
            ResponseHandler<String> responseHandler = new BasicResponseHandler();
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }
}
