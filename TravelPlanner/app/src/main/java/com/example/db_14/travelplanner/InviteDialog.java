package com.example.db_14.travelplanner;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
 * Created by db-14 on 2017. 6. 21..
 */

public class InviteDialog extends Dialog {

    Button btn_1;
    Button btn_2;
    EditText id_editText;
    String usrid, pno;

    View.OnClickListener btn1listener;
    View.OnClickListener btn2listener;
    public InviteDialog(Context context, String usrid, String pno) {
        super(context);
        this.usrid = usrid;
        this.pno = pno;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog2);
        id_editText = (EditText) findViewById(R.id.invite_id);
        btn_1 = (Button)findViewById(R.id.btn_invite);
        btn_2 = (Button)findViewById(R.id.btn_cancel);

        btn1listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String invite_id = id_editText.getText().toString();
                invite_plan(invite_id);
                Toast.makeText(v.getContext(), "초대가 완료되었습니다.", Toast.LENGTH_SHORT).show();
                dismiss();
            }
        };

        btn2listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "초대가 취소되었습니다.", Toast.LENGTH_SHORT).show();
                dismiss();
            }
        };

        btn_1.setOnClickListener(btn1listener);
        btn_2.setOnClickListener(btn2listener);
    }

    public void invite_plan(String invite)
    {
        try{
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost("http://52.79.131.13/db_insert.php");

            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            String query = "insert into invite_info(pno, to_id, from_id) values ('"+pno+"','"+invite+"','"+usrid+"')"; // 쿼리문 수정 및 db 테이블 추가 필요
            nameValuePairs.add(new BasicNameValuePair("query", query));
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "utf-8"));
            HttpResponse response = httpClient.execute(httpPost);
            ResponseHandler<String> responseHandler = new BasicResponseHandler();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
